package br.com.senior.custom.vacationmanagement.validation;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.senior.custom.vacationmanagement.vacationschedule.VacationSchedule;
import br.com.senior.custom.vacationmanagement.vacationschedule.VacationScheduleMessage;

@Service
class ValidationMessageService{

    /**
     * Adiciona uma mensagem à uma validação customizada
     *
     * @param output Mensagens de erro
     * @param vacationSchedule Solicitação de férias
     * @param message Mensagem que será adicionada
     */
    public void addValidationMessage(ValidateVacationScheduleOutput output, VacationSchedule vacationSchedule, String message) {
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
    public void removeValidationMessage(ValidateVacationScheduleOutput output, VacationSchedule vacationSchedule, ValidationMessageType validationMessageType) {
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
