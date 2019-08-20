package br.com.senior.custom.vacationmanagement.validation;

import static org.mockito.ArgumentMatchers.eq;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.senior.custom.vacationmanagement.vacationpolicy.VacationPolicy;
import br.com.senior.custom.vacationmanagement.vacationpolicy.VacationPolicyService;
import br.com.senior.custom.vacationmanagement.vacationschedule.VacationSchedule;

class MaxDayRequestValidatorTest {

    @InjectMocks
    private MaxDayRequestValidator maxDayRequestValidator;

    @Mock
    private VacationPolicyService vacationPolicyService;

    @Mock
    private ValidationMessageService validationMessageService;

    @BeforeEach
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * <pre>
     *  Data de início das férias: 10/1/2019
     *  Dia limite para solicitar férias: 15
     * </pre>
     *
     * Validação:
     *  Não deve ser adicionada nehuma mensagem de erro
     */
    @Test
    public void handleValidation(){
        ValidateVacationScheduleOutput output = new ValidateVacationScheduleOutput();
        VacationSchedule vacationSchedule = new VacationSchedule();
        vacationSchedule.startDate = LocalDate.of(2019, 1, 10);
        VacationPolicy vacationPolicy = new VacationPolicy();
        double maxDay = 15;
        vacationPolicy.setCustom("maxDayToRequestVacation", maxDay);

        Mockito.when(vacationPolicyService.getVacationPolicyByEmployeeId(vacationSchedule.employeeId)).thenReturn(vacationPolicy);

        maxDayRequestValidator.handleValidation(output, vacationSchedule);

        Mockito.verify(vacationPolicyService).getVacationPolicyByEmployeeId(vacationSchedule.employeeId);
        Mockito.verify(validationMessageService, Mockito.never()).addValidationMessage(eq(output), eq(vacationSchedule), Mockito.anyString());
    }

    /**
     * <pre>
     *  Data de início das férias: 20/1/2019
     *  Dia limite para solicitar férias: 15
     * </pre>
     *
     * Validação:
     *  Deve ser adicionada uma mensagem de erro
     */
    @Test
    public void handleValidation_limitAchieved() {
        ValidateVacationScheduleOutput output = new ValidateVacationScheduleOutput();
        VacationSchedule vacationSchedule = new VacationSchedule();
        vacationSchedule.startDate = LocalDate.of(2019, 1, 20);
        VacationPolicy vacationPolicy = new VacationPolicy();
        double maxDay = 15;
        vacationPolicy.setCustom("maxDayToRequestVacation", maxDay);

        Mockito.when(vacationPolicyService.getVacationPolicyByEmployeeId(vacationSchedule.employeeId)).thenReturn(vacationPolicy);

        maxDayRequestValidator.handleValidation(output, vacationSchedule);

        Mockito.verify(vacationPolicyService).getVacationPolicyByEmployeeId(vacationSchedule.employeeId);
        Mockito.verify(validationMessageService).addValidationMessage(eq(output), eq(vacationSchedule), Mockito.anyString());
    }

    /**
     * <pre>
     *  Data de início das férias: 20/1/2019
     *  Campo dia limite para solicitar férias não existe
     * </pre>
     *
     * Validação:
     *  Deve ser lançada a exceção MaxDayRequestValidationException com a mensagem 'Nenhum campo customizado encontrado'
     */
    @Test
    public void handleValidation_noCustomFields() {
        ValidateVacationScheduleOutput output = new ValidateVacationScheduleOutput();
        VacationSchedule vacationSchedule = new VacationSchedule();
        vacationSchedule.startDate = LocalDate.of(2019, 1, 20);
        VacationPolicy vacationPolicy = new VacationPolicy();

        Mockito.when(vacationPolicyService.getVacationPolicyByEmployeeId(vacationSchedule.employeeId)).thenReturn(vacationPolicy);

        MaxDayRequestValidationException exception = Assertions.assertThrows(MaxDayRequestValidationException.class, () -> //
                maxDayRequestValidator.handleValidation(output, vacationSchedule));

        Mockito.verify(vacationPolicyService).getVacationPolicyByEmployeeId(vacationSchedule.employeeId);
        Mockito.verify(validationMessageService, Mockito.never()).addValidationMessage(eq(output), eq(vacationSchedule), Mockito.anyString());
        Assertions.assertEquals(exception.getMessage(), "Nenhum campo customizado encontrado");
    }

    /**
     * <pre>
     *  Data de início das férias: 20/1/2019
     *  Dia limite para solicitar férias: NULL
     * </pre>
     *
     * Validação:
     *  Deve ser lançada a exceção MaxDayRequestValidationException com a mensagem 'Não há valores para o campo maxDayToRequestVacation'
     */
    @Test
    public void handleValidation_customFieldWithoutValue() {
        ValidateVacationScheduleOutput output = new ValidateVacationScheduleOutput();
        VacationSchedule vacationSchedule = new VacationSchedule();
        vacationSchedule.startDate = LocalDate.of(2019, 1, 20);
        VacationPolicy vacationPolicy = new VacationPolicy();
        vacationPolicy.setCustom("maxDayToRequestVacation", null);

        Mockito.when(vacationPolicyService.getVacationPolicyByEmployeeId(vacationSchedule.employeeId)).thenReturn(vacationPolicy);

        MaxDayRequestValidationException exception = Assertions.assertThrows(MaxDayRequestValidationException.class, () -> //
                maxDayRequestValidator.handleValidation(output, vacationSchedule));

        Mockito.verify(vacationPolicyService).getVacationPolicyByEmployeeId(vacationSchedule.employeeId);
        Mockito.verify(validationMessageService, Mockito.never()).addValidationMessage(eq(output), eq(vacationSchedule), Mockito.anyString());
        Assertions.assertEquals(exception.getMessage(), "Não há valores para o campo customizado maxDayToRequestVacation");
    }

    /**
     * <pre>
     *  Data de início das férias: 20/1/2019
     *  Dia limite para solicitar férias: teste
     * </pre>
     *
     * Validação:
     *  Deve ser lançada a exceção MaxDayRequestValidationException com a mensagem 'Erro ao converter valor do campo maxDayToRequestVacation'
     */
    @Test
    public void handleValidation_conversionError() {
        ValidateVacationScheduleOutput output = new ValidateVacationScheduleOutput();
        VacationSchedule vacationSchedule = new VacationSchedule();
        vacationSchedule.startDate = LocalDate.of(2019, 1, 20);
        VacationPolicy vacationPolicy = new VacationPolicy();
        vacationPolicy.setCustom("maxDayToRequestVacation", "teste");

        Mockito.when(vacationPolicyService.getVacationPolicyByEmployeeId(vacationSchedule.employeeId)).thenReturn(vacationPolicy);

        MaxDayRequestValidationException exception = Assertions.assertThrows(MaxDayRequestValidationException.class, () -> //
                maxDayRequestValidator.handleValidation(output, vacationSchedule));

        Mockito.verify(vacationPolicyService).getVacationPolicyByEmployeeId(vacationSchedule.employeeId);
        Mockito.verify(validationMessageService, Mockito.never()).addValidationMessage(eq(output), eq(vacationSchedule), Mockito.anyString());
        Assertions.assertEquals(exception.getMessage(), "Erro ao converter valor do campo customizado maxDayToRequestVacation");
    }

}