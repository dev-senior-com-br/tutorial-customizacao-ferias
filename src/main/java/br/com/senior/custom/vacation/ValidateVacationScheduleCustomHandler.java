package br.com.senior.custom.vacation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.custom.vacation.model.VacationSchedule;
import br.com.senior.custom.vacation.model.VacationScheduleMessage;
import br.com.senior.custom.vacation.model.ValidateVacationScheduleInput;
import br.com.senior.custom.vacation.model.ValidateVacationScheduleOutput;
import br.com.senior.custom.vacation.model.ValidateVacationScheduleRequest;
import br.com.senior.custom.vacation.model.ValidationMessage;
import br.com.senior.custom.vacation.model.ValidationMessageType;
import br.com.senior.custom.vacationPolicy.VacationPolicy;
import br.com.senior.custom.vacationPolicy.VacationPolicyService;
import br.com.senior.custom.vacationperiod.VacationPeriod;
import br.com.senior.custom.vacationperiod.VacationPeriodService;

@RestController
public class ValidateVacationScheduleCustomHandler {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final int MAX_SCHEDULE_DAY = 20;
    private static final int CONCESSION_PERIOD_MONTHS = 9;

    private static final String MAX_SCHEDULE_DAY_MESSAGE = "É possível solicitar férias apenas até o dia %s";
    private static final String CONCESSION_PERIOD_START_DATE_MESSAGE = "A data de início das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em %s";
    private static final String CONCESSION_PERIOD_END_DATE_MESSAGE = "A data de término das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em %s";

    @Inject
    private VacationPeriodService vacationPeriodService;

    @Inject
    private VacationPolicyService vacationPolicyService;

    @RequestMapping("/")
    public String index() {
        return "O aplicativo de customização de férias está Online!";
    }

    /**
     * Responsável por receber os dados da solicitação de férias (input e output da valiadação original) e retornar um novo output.
     * 
     * @param request DTO com o input e o output da validação original
     * @return Novo output que será retornado pela primitiva "validateVacationSchedule"
     */
    @PostMapping(path = "/validateVacationSchedule")
    public ResponseEntity<ValidateVacationScheduleOutput> customValidateVacationSchedule(@RequestBody ValidateVacationScheduleRequest request) {
        ValidateVacationScheduleInput input = request.input;
        ValidateVacationScheduleOutput output = request.output;

        System.out.println("customValidateVacationSchedule");

        input.vacationSchedules.forEach(vacationSchedule -> {
            System.out.println(String.format("validating employeeId: %s", vacationSchedule.employeeId));
            validate(output, vacationSchedule);
        });

        return ResponseEntity.ok(output);
    }

    /**
     * Método que irá realizar as validações customizadas, no caso, será possível solicitar férias até 9 meses após o início do período aquisitivo e
     * o colaborador só pode solicitar férias até um dia espeecífico definido na política de férias
     *
     * @param output Mensagens de erro
     * @param vacationSchedule Solicitação de férias
     */
    private void validate(ValidateVacationScheduleOutput output, VacationSchedule vacationSchedule) {

        // Pode solicitar férias apenas até o dia X
        VacationPolicy vacationPolicy = vacationPolicyService.getVacationPolicyByEmployeeId(vacationSchedule.employeeId);
        //TODO Validar se o campo existe
        System.out.println("******AllCustomField: "+vacationPolicy.getCustom().toString());
        System.out.println("******AllCustomField: "+Integer.parseInt(vacationPolicy.getCustom("maxDayToRequestVacation").toString()));
        if (vacationSchedule.startDate.getDayOfMonth() > Integer.parseInt(vacationPolicy.getCustom("maxDayToRequestVacation").toString())) {
            addValidationMessage(output, vacationSchedule, String.format(MAX_SCHEDULE_DAY_MESSAGE, MAX_SCHEDULE_DAY));
        }

        VacationPeriod vacationPeriod = vacationPeriodService.getVacationPeriod(vacationSchedule.vacationPeriodId);

        // Pode gozar férias até 9 meses depois do período aquisitivo
        removeValidationMessage(output, vacationSchedule, ValidationMessageType.TYPE_19_VACATION_DOUBLE);
        
        LocalDate endConcessivePeriod = vacationPeriod.enddate.plusMonths(CONCESSION_PERIOD_MONTHS);
        if (vacationSchedule.startDate.isAfter(endConcessivePeriod)) {
            addValidationMessage(output, vacationSchedule, String.format(CONCESSION_PERIOD_START_DATE_MESSAGE, endConcessivePeriod.format(DATE_FORMATTER)));
        } else if (vacationSchedule.startDate.plusDays(vacationSchedule.vacationDays).isAfter(endConcessivePeriod)) {
            addValidationMessage(output, vacationSchedule, String.format(CONCESSION_PERIOD_END_DATE_MESSAGE, endConcessivePeriod.format(DATE_FORMATTER)));
        }
    }

    /**
     * Adiciona uma mensagem à uma validação customizada
     *
     * @param output Mensagens de erro
     * @param vacationSchedule Solicitação de férias
     * @param message Mensagem que será adicionada
     */
    private void addValidationMessage(ValidateVacationScheduleOutput output, VacationSchedule vacationSchedule, String message) {
        if (CollectionUtils.isEmpty(output.vacationScheduleMessages)) {
            output.vacationScheduleMessages = new ArrayList<>();
        }

        Optional<VacationScheduleMessage> vacationScheduleMessageOpt = output.vacationScheduleMessages.stream().filter(vacationScheduleMessage -> vacationScheduleMessage.employeeId.equals(vacationSchedule.employeeId)).findFirst();
        if (vacationScheduleMessageOpt.isPresent()) {
            addValidationMessage(vacationScheduleMessageOpt.get(), message);
        } else {
            VacationScheduleMessage vacationScheduleMessage = new VacationScheduleMessage();
            vacationScheduleMessage.employeeId = vacationSchedule.employeeId;
            vacationScheduleMessage.vacationPeriodId = vacationSchedule.vacationPeriodId;
            vacationScheduleMessage.startDate = vacationSchedule.startDate;
            addValidationMessage(vacationScheduleMessage, message);
            output.vacationScheduleMessages.add(vacationScheduleMessage);
        }
    }

    /**
     * Cria uma mensagem de validação
     *
     * @param vacationScheduleMessage Mensggem de erro
     * @param message Mensagem
     */
    private void addValidationMessage(VacationScheduleMessage vacationScheduleMessage, String message) {
        if (CollectionUtils.isEmpty(vacationScheduleMessage.validationMessages)) {
            vacationScheduleMessage.validationMessages = new ArrayList<>();
        }

        ValidationMessage validationMessage = new ValidationMessage();
        validationMessage.message = message;
        vacationScheduleMessage.validationMessages.add(validationMessage);
    }

    /**
     * Remove uma mensagem de validação da lista de mensagens existentes
     *
     * @param output Mensagem de erro
     * @param vacationSchedule Solicitação de férias
     * @param validationMessageType Tipo da mensagem de validação
     */
    private void removeValidationMessage(ValidateVacationScheduleOutput output, VacationSchedule vacationSchedule, ValidationMessageType validationMessageType) {
        if (!CollectionUtils.isEmpty(output.vacationScheduleMessages)) {
            Optional<VacationScheduleMessage> vacationScheduleMessageOpt = output.vacationScheduleMessages.stream().filter(vacationScheduleMessage -> vacationScheduleMessage.employeeId.equals(vacationSchedule.employeeId)).findFirst();
            if (vacationScheduleMessageOpt.isPresent()) {

                // Se a mensagem com esse tipo de validação existe, ela é removida da lista de validações
                VacationScheduleMessage vacationScheduleMessage = vacationScheduleMessageOpt.get();
                vacationScheduleMessage.validationMessages = vacationScheduleMessage.validationMessages.stream() //
                        .filter(validationMessage -> !validationMessageType.equals(validationMessage.type)) //
                        .collect(Collectors.toList());
            }
        }
    }
}
