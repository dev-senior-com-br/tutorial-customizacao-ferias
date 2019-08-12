package br.com.senior.custom.vacationPolicy;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.senior.custom.rest.PlatformClient;

@Service
public class VacationPolicyService {

    @Inject
    private PlatformClient platformClient;

    public VacationPolicy getVacationPolicyByEmployeeId(String employeeId) {
        return platformClient.getVacationPolicyByEmployeeId(employeeId).vacationPolicy;
    }

}
