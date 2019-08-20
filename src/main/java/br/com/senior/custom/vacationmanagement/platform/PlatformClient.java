package br.com.senior.custom.vacationmanagement.platform;

import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.senior.custom.vacationmanagement.vacationpolicy.GetVacationPolicyByEmployeeInput;
import br.com.senior.custom.vacationmanagement.vacationpolicy.GetVacationPolicyByEmployeeOutput;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PlatformClient {

    private static final String VACATION_MANAGEMENT_GETVACATIONPOLICYBYEMPLOYEE_QUERY_PATH = "/t/senior.com.br/bridge/1.0/platform/hcm/vacationmanagement/queries/getVacationPolicyByEmployee";
    private static final String VACATION_MANAGEMENT_ENTITIES_PATH = "/t/senior.com.br/bridge/1.0/platform/hcm/vacationmanagement/entities/%s/%s";
    private static final String LOGIN_WITH_KEY_PATH = "/t/senior.com.br/bridge/1.0/anonymous/rest/platform/authentication/actions/loginWithKey";
    private static final String BEARER_TOKEN = "Bearer %s";

    @Value("${PLATFORM_URL}")
    private String PLATFORM_URL/* = "https://platform-homologx.senior.com.br"*/;

    @Value("${TENANT}")
    private String TENANT/* = "autohcm02"*/;

    @Value("${ACCESS_KEY}")
    private String ACCESS_KEY/* = "v8ijYweEkFYOoGDVkq6x42i3_hwa"*/;

    @Value("${SECRET}")
    private String SECRET/* = "0czgvfnJe0YDEYDZkew5Q5a3BCoa"*/;

    private Optional<JsonToken> token = Optional.empty();

    private WebTarget getClient() {
        Client client = ClientBuilder.newClient();
        return client.target(PLATFORM_URL);
    }

    public <T> T getEntity(String entity, String id, Class<T> tClass) {
        login();

        WebTarget target = getClient();
        String path = String.format(VACATION_MANAGEMENT_ENTITIES_PATH, entity, id);
        Invocation.Builder builder = target.path(path) //
                .request(MediaType.APPLICATION_JSON) //
                .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_TOKEN, token.get().getAccessToken()));
        return builder.get(tClass);
    }

    /**
     * Busca a política de férias vigente sobre o colaborador passado como parâmetro
     *
     * @param employeeId Id do colaborador
     * @return Política de férias do colaborador
     */
    public GetVacationPolicyByEmployeeOutput getVacationPolicyByEmployeeId(String employeeId) {
        try {
            login();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        GetVacationPolicyByEmployeeInput getVacationPolicyByEmployeeInput = new GetVacationPolicyByEmployeeInput();
        getVacationPolicyByEmployeeInput.employeeId = employeeId;

        WebTarget target = getClient();
        Invocation.Builder builder = target.path(VACATION_MANAGEMENT_GETVACATIONPOLICYBYEMPLOYEE_QUERY_PATH).request(MediaType.APPLICATION_JSON) //
                .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_TOKEN, token.get().getAccessToken()));
        Response response = builder.post(Entity.entity(getVacationPolicyByEmployeeInput, MediaType.APPLICATION_JSON));
        try {
            return response.readEntity(GetVacationPolicyByEmployeeOutput.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Realiza o login na Senior X
     * Utiliza as variáveis de ambiente TENANT, ACCESS_KEY e SECRET para fazer o login
     */
    private void login() {
        if (!token.isPresent() || token.get().isExpired()) {
            LoginWithKeyInput loginWithKeyInput = new LoginWithKeyInput();
            loginWithKeyInput.tenantName = TENANT;
            loginWithKeyInput.accessKey = ACCESS_KEY;
            loginWithKeyInput.secret = SECRET;

            WebTarget target = getClient();

            Invocation.Builder builder = target.path(LOGIN_WITH_KEY_PATH).request(MediaType.APPLICATION_JSON);
            Response response = builder.post(Entity.entity(loginWithKeyInput, MediaType.APPLICATION_JSON));
            LoginWithKeyOutput output = response.readEntity(LoginWithKeyOutput.class);
            try {
                JsonToken jsonToken = new ObjectMapper().readValue(output.jsonToken, JsonToken.class);
                this.token = Optional.of(jsonToken);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    Método para testes no PlatformCliente do projeto de customização
//        public static void main(String[] args) {
//            PlatformClient platformClient = new PlatformClient();
//            platformClient.login();
//            System.out.println(platformClient.token.get().getAccessToken());
//        }

}
