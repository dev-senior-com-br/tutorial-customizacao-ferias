package br.com.senior.custom.vacationmanagement.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.senior.custom.vacationmanagement.vacationschedule.VacationSchedule;
import br.com.senior.custom.vacationmanagement.vacationperiod.VacationPeriod;
import br.com.senior.custom.vacationmanagement.vacationperiod.VacationPeriodService;

@Service
class LimitDateValidator implements VacationScheduleValidatorBase {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final int CONCESSION_PERIOD_MONTHS = 9;
    private static final String CONCESSION_PERIOD_START_DATE_MESSAGE = "A data de início das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em %s";
    private static final String CONCESSION_PERIOD_END_DATE_MESSAGE = "A data de término das férias deve ser anterior ao fim do período concessivo. O período concessivo encerra em %s";

    @Inject
    private VacationPeriodService vacationPeriodService;

    @Inject
    private ValidationMessageService validationMessageService;

    /**
     * Validação customizada para validar se o início das férias ou o término é após o fim do período concessivo
     * Foi adicionado 9 meses a data fim do período concessivo, o que torna isso uma validação customizada
     *
     * @param output Mensagens de erro da validação da Senior
     * @param vacationSchedule Solicitação de férias
     */
    @Override
    public void handleValidation(ValidateVacationScheduleOutput output, VacationSchedule vacationSchedule) {
        // Obtém o período de férias do colaborador
        VacationPeriod vacationPeriod = vacationPeriodService.getVacationPeriod(vacationSchedule.vacationPeriodId);

        // Remove a mensagem de erro do tipo TYPE_19_VACATION_DOUBLE que foi adicionada a lista de mensagens de erro através da validação da Senior
        validationMessageService.removeValidationMessage(output, vacationSchedule, ValidationMessageType.TYPE_19_VACATION_DOUBLE);

        // Adiciona 9 meses ao fim do período concessivo
        LocalDate endConcessivePeriod = vacationPeriod.enddate.plusMonths(CONCESSION_PERIOD_MONTHS);

        // Verifica se a data de início das férias é depois do fim do período concessivo, se for verdadeiro é adicionado uma nova mensagem de erro
        if (vacationSchedule.startDate.isAfter(endConcessivePeriod)) {
            validationMessageService.addValidationMessage(output, vacationSchedule, String.format(CONCESSION_PERIOD_START_DATE_MESSAGE, endConcessivePeriod.format(DATE_FORMATTER)));

        } else if (vacationSchedule.startDate.plusDays(vacationSchedule.vacationDays).isAfter(endConcessivePeriod)) {

            // Verifica se as férias solicitadas terminam após o fim do período concessivo, se for verdadeiro é adicionado uma nova mensagem de erro
            validationMessageService.addValidationMessage(output, vacationSchedule, String.format(CONCESSION_PERIOD_END_DATE_MESSAGE, endConcessivePeriod.format(DATE_FORMATTER)));
        }
    }
}
