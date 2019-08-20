package br.com.senior.custom.vacationmanagement.handler;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.com.senior.custom.vacationmanagement.vacationschedule.VacationSchedule;
import br.com.senior.custom.vacationmanagement.validation.ValidateVacationScheduleInput;
import br.com.senior.custom.vacationmanagement.validation.ValidateVacationScheduleOutput;
import br.com.senior.custom.vacationmanagement.validation.ValidateVacationScheduleRequest;
import br.com.senior.custom.vacationmanagement.validation.ValidatorService;

class ValidateVacationScheduleCustomHandlerTest {

    @InjectMocks
    private ValidateVacationScheduleCustomHandler handler;

    @Mock
    private ValidatorService validatorService;

    @BeforeEach
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * <pre>
     *     Dias de férias: 14L
     *     Data de início das férias: 1/1/2019
     *     Id do colaborador: employeeId
     * </pre>
     *
     * Deve verificar se as validações criadas são executadas
     */
    @Test
    public void customValidateVacationSchedule() {
        ValidateVacationScheduleRequest request = new ValidateVacationScheduleRequest();
        request.output = new ValidateVacationScheduleOutput();
        request.input = new ValidateVacationScheduleInput();
        request.input.vacationSchedules = new ArrayList<>();
        VacationSchedule vacationSchedule = new VacationSchedule();
        vacationSchedule.vacationDays = 14L;
        vacationSchedule.startDate = LocalDate.of(2019,1,1);
        vacationSchedule.employeeId = "employeeId";
        request.input.vacationSchedules.add(vacationSchedule);

        ResponseEntity<ValidateVacationScheduleOutput> result = handler.customValidateVacationSchedule(request);

        Mockito.verify(validatorService).validate(Mockito.any(ValidateVacationScheduleOutput.class), Mockito.any(VacationSchedule.class));
        Mockito.verify(validatorService).validate(Mockito.any(ValidateVacationScheduleOutput.class), Mockito.any(VacationSchedule.class));

        ValidateVacationScheduleOutput resultBody = result.getBody();
        Assertions.assertSame(request.output, resultBody);
    }

}