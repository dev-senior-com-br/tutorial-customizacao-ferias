package br.com.senior.custom.vacationmanagement.vacationpolicy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.senior.custom.vacationmanagement.platform.PlatformClient;

class VacationPolicyServiceTest {

    @InjectMocks
    private VacationPolicyService service;

    @Mock
    private PlatformClient platformClient;

    @BeforeEach
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * <pre>
     *     id do colaborador: employeeId
     * </pre>
     *
     * Deve retornar a política de férias do colaborador
     */
    @Test
    public void getVacationPolicyByEmployeeId() {
        String employeeId = "employeeId";
        GetVacationPolicyByEmployeeOutput getVacationPolicyByEmployeeOutput = new GetVacationPolicyByEmployeeOutput();
        VacationPolicy vacationPolicy = new VacationPolicy();
        getVacationPolicyByEmployeeOutput.vacationPolicy = vacationPolicy;

        Mockito.when(platformClient.getVacationPolicyByEmployeeId(employeeId)).thenReturn(getVacationPolicyByEmployeeOutput);

        VacationPolicy result = service.getVacationPolicyByEmployeeId(employeeId);

        Assertions.assertSame(result, vacationPolicy);
    }

}