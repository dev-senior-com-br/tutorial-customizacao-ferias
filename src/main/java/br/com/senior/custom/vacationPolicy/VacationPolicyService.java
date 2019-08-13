package br.com.senior.custom.vacationPolicy;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.senior.custom.rest.PlatformClient;

@Service
public class VacationPolicyService {

    @Inject
    private PlatformClient platformClient;

    /**
     * Busca a política de férias do colaborador a partir do id do colaborador
     *
     * @param employeeId Id do colaborador
     * @return {@link VacationPolicy} contendo as informações da política de férias
     */
    public VacationPolicy getVacationPolicyByEmployeeId(String employeeId) {
        return platformClient.getVacationPolicyByEmployeeId(employeeId).vacationPolicy;
    }

}
