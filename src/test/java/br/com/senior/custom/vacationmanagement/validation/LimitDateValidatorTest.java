package br.com.senior.custom.vacationmanagement.validation;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.senior.custom.vacationmanagement.vacationperiod.VacationPeriod;
import br.com.senior.custom.vacationmanagement.vacationperiod.VacationPeriodService;
import br.com.senior.custom.vacationmanagement.vacationschedule.VacationSchedule;

class LimitDateValidatorTest {

    @InjectMocks
    private LimitDateValidator limitDateValidator;

    @Mock
    private VacationPeriodService vacationPeriodService;

    @Mock
    private ValidationMessageService validationMessageService;

    @BeforeEach
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * <pre>
     *  Data de início das férias: 1/10/2019
     *  Dias de férias: 30
     *  Data do fim das férias: 1/11/2019
     *  Fim do período concessivo: 1/1/2019
     *  Fim do período concessivo com os 9 meses: 1/10/2019
     * </pre>
     *
     * Validação:
     *  Deve ser removida a mensagem 'O período das férias deve ser anterior ao fim do período concessivo'
     *  Deve ser adicionada a mesnagem 'A data de término das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em 01/10/2019'
     */
    @Test
    public void handleValidation_startDatePluDays_IsAfterEndConcessivePeriod() {
        ValidateVacationScheduleOutput output = new ValidateVacationScheduleOutput();
        VacationSchedule vacationSchedule = new VacationSchedule();
        vacationSchedule.startDate = LocalDate.of(2019, 10, 1);
        vacationSchedule.vacationDays = 30L;
        VacationPeriod vacationPeriod = new VacationPeriod();
        vacationPeriod.enddate = LocalDate.of(2019, 1, 1);

        Mockito.when(vacationPeriodService.getVacationPeriod(vacationSchedule.vacationPeriodId)).thenReturn(vacationPeriod);

        limitDateValidator.handleValidation(output, vacationSchedule);

        Mockito.verify(validationMessageService).removeValidationMessage(output, vacationSchedule, ValidationMessageType.TYPE_19_VACATION_DOUBLE);
        Mockito.verify(validationMessageService).addValidationMessage(output, vacationSchedule, "A data de término das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em 01/10/2019");
    }

    /**
     * <pre>
     *  Data de início das férias: 10/10/2019
     *  Dias de férias: 30
     *  Data do fim das férias: 10/11/2019
     *  Fim do período concessivo: 1/1/2019
     *  Fim do período concessivo com os 9 meses: 1/10/2019
     * </pre>
     *
     * Validação:
     *  Deve ser removida a mensagem 'O período das férias deve ser anterior ao fim do período concessivo'
     *  Deve ser adicionada a mesnagem 'A data de início das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em 01/10/2019'
     */
    @Test
    public void handleValidation_startDate_IsAfterEndConcessivePeriod() {
        ValidateVacationScheduleOutput output = new ValidateVacationScheduleOutput();
        VacationSchedule vacationSchedule = new VacationSchedule();
        vacationSchedule.startDate = LocalDate.of(2019, 10, 10);
        vacationSchedule.vacationDays = 30L;
        VacationPeriod vacationPeriod = new VacationPeriod();
        vacationPeriod.enddate = LocalDate.of(2019, 1, 1);

        Mockito.when(vacationPeriodService.getVacationPeriod(vacationSchedule.vacationPeriodId)).thenReturn(vacationPeriod);

        limitDateValidator.handleValidation(output, vacationSchedule);

        Mockito.verify(validationMessageService).removeValidationMessage(output, vacationSchedule, ValidationMessageType.TYPE_19_VACATION_DOUBLE);
        Mockito.verify(validationMessageService).addValidationMessage(output, vacationSchedule, "A data de início das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em 01/10/2019");
    }

    /**
     * <pre>
     *  Data de início das férias: 10/10/2018
     *  Dias de férias: 30
     *  Data do fim das férias: 10/11/2018
     *  Fim do período concessivo: 1/1/2019
     *  Fim do período concessivo mais os 9 meses: 1/10/2019
     * </pre>
     *
     * Validação:
     *  Deve ser removida a mensagem 'O período das férias deve ser anterior ao fim do período concessivo'
     */
    @Test
    public void handleValidation_noMessagesToAdd() {
        ValidateVacationScheduleOutput output = new ValidateVacationScheduleOutput();
        VacationSchedule vacationSchedule = new VacationSchedule();
        vacationSchedule.startDate = LocalDate.of(2018, 10, 10);
        vacationSchedule.vacationDays = 30L;
        VacationPeriod vacationPeriod = new VacationPeriod();
        vacationPeriod.enddate = LocalDate.of(2019, 1, 1);

        Mockito.when(vacationPeriodService.getVacationPeriod(vacationSchedule.vacationPeriodId)).thenReturn(vacationPeriod);

        limitDateValidator.handleValidation(output, vacationSchedule);

        Mockito.verify(validationMessageService).removeValidationMessage(output, vacationSchedule, ValidationMessageType.TYPE_19_VACATION_DOUBLE);
        Mockito.verify(validationMessageService, Mockito.never()).addValidationMessage(output, vacationSchedule, "A data de início das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em 01/10/2019");
        Mockito.verify(validationMessageService, Mockito.never()).addValidationMessage(output, vacationSchedule, "A data de término das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em 01/10/2019");
    }

}