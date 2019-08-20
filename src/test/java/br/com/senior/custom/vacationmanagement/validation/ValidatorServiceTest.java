package br.com.senior.custom.vacationmanagement.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.senior.custom.vacationmanagement.vacationschedule.VacationSchedule;

class ValidatorServiceTest {

    @InjectMocks
    private ValidatorService validatorService;

    @Mock
    private MaxDayRequestValidator maxDayRequestValidator;

    @Mock
    private LimitDateValidator limitDateValidator;

    @BeforeEach
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Teste de chamado de serviço
     * Ao chamar o método validatorService.validate o teste veridica se as classes de validação MaxDayRequestValidator e LimitDateValidator foram chamadas
     */
    @Test
    public void validate() {
        ValidateVacationScheduleOutput output = new ValidateVacationScheduleOutput();
        VacationSchedule vacationSchedule = new VacationSchedule();

        validatorService.validate(output, vacationSchedule);

        Mockito.verify(maxDayRequestValidator).handleValidation(output, vacationSchedule);
        Mockito.verify(limitDateValidator).handleValidation(output, vacationSchedule);
    }

}