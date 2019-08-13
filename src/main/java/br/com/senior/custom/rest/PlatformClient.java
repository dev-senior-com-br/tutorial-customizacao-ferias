package br.com.senior.custom.rest;

import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.senior.custom.authorization.JsonToken;
import br.com.senior.custom.authorization.LoginWithKeyInput;
import br.com.senior.custom.authorization.LoginWithKeyOutput;
import br.com.senior.custom.vacationPolicy.GetVacationPolicyByEmployeeInput;
import br.com.senior.custom.vacationPolicy.GetVacationPolicyByEmployeeOutput;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PlatformClient {

    private static final String VACATION_MANAGEMENT_GETVACATIONPOLICYBYEMPLOYEE_QUERY_PATH = "/t/senior.com.br/bridge/1.0/rest/hcm/vacationmanagement/queries/getVacationPolicyByEmployee";
    private static final String VACATION_MANAGEMENT_ENTITIES_PATH = "/t/senior.com.br/bridge/1.0/rest/hcm/vacationmanagement/entities/%s/%s";
    private static final String LOGIN_WITH_KEY_PATH = "/t/senior.com.br/bridge/1.0/anonymous/rest/platform/authentication/actions/loginWithKey";
    private static final String BEARER_TOKEN = "Bearer %s";

    @Value("${PLATFORM_URL}")
    private String PLATFORM_URL;

    @Value("${TENANT}")
    private String TENANT;

    @Value("${ACCESS_KEY}")
    private String ACCESS_KEY;

    @Value("${SECRET}")
    private String SECRET;

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

    public GetVacationPolicyByEmployeeOutput getVacationPolicyByEmployeeId(String employeeId) {
        //TODO verificar se é necessário fazer o login
        System.out.println("******fazendo o login");
        try {
            login();
        }catch (Exception e) {
            System.out.println("*****erro ao fazer login: "+e.getMessage());
            return null;
        }
        GetVacationPolicyByEmployeeInput getVacationPolicyByEmployeeInput = new GetVacationPolicyByEmployeeInput();
        getVacationPolicyByEmployeeInput.employeeId = employeeId;

        System.out.println("*****PlatformClient -> getVacationPolicyByEmployeeId -> validando employee: "+getVacationPolicyByEmployeeInput.employeeId);

        WebTarget target = getClient();
        System.out.println("******iniciado web target");
        //Form form = new Form();
        //form.param("employeeId", getVacationPolicyByEmployeeInput.employeeId);
        Invocation.Builder builder = target.path(VACATION_MANAGEMENT_GETVACATIONPOLICYBYEMPLOYEE_QUERY_PATH).request(MediaType.APPLICATION_JSON);
        System.out.println("******Invocation.Builder executado");
        Response response = builder.post(Entity.entity(getVacationPolicyByEmployeeInput, MediaType.APPLICATION_JSON));
        System.out.println("******executado builder.post com o response: "+response);
        //Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.form(form));
        System.out.println("******Iniciando retorno");

        try {
            return response.readEntity(GetVacationPolicyByEmployeeOutput.class);
        }catch (Exception e){
            System.out.println("*******erro ao ler entidade: "+ e.getMessage());
            return null;
        }
    }

    /**
     * Realiza o login na Senior X
     * Utiliza as variáveis de ambiente TENANT, ACCESS_KEY e SECRET para fazer o login
     */
    private void login() {
        System.out.println("*****verificando se o tken está presnete ou expirado");
        if (!token.isPresent() || token.get().isExpired()) {
            System.out.println("******token expirado, criando nova sessão");
            LoginWithKeyInput loginWithKeyInput = new LoginWithKeyInput();
            loginWithKeyInput.tenantName = TENANT;
            loginWithKeyInput.accessKey = ACCESS_KEY;
            loginWithKeyInput.secret = SECRET;
            System.out.println("******criado loginWithKeyInput com TENANT= "+loginWithKeyInput.tenantName+"ACCESS_KEY= "+loginWithKeyInput.accessKey+" e SECRET=" + loginWithKeyInput.secret);

            WebTarget target = getClient();
            System.out.println("*******criado target");

            System.out.println("*****criando builder com o path: "+LOGIN_WITH_KEY_PATH);
            Invocation.Builder builder = target.path(LOGIN_WITH_KEY_PATH).request(MediaType.APPLICATION_JSON);
            System.out.println("*****builder criado");
            System.out.println("******criando response...");
            Response response = builder.post(Entity.entity(loginWithKeyInput, MediaType.APPLICATION_JSON));
            System.out.printf("*******response criado: "+response);
            System.out.println("******criando output...");
            LoginWithKeyOutput output = response.readEntity(LoginWithKeyOutput.class);
            System.out.println("*****output criado: "+output);
            try {
                System.out.println("******criando token...");
                JsonToken jsonToken = new ObjectMapper().readValue(output.jsonToken, JsonToken.class);
                System.out.println("*****token criado: "+jsonToken.getAccessToken());
                System.out.println("*******setando token...");
                this.token = Optional.of(jsonToken);
                System.out.println("*******token criado: "+this.token.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
