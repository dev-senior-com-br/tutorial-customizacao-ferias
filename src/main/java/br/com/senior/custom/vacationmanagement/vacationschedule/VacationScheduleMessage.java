package br.com.senior.custom.vacationmanagement.vacationschedule;

import java.util.List;

import br.com.senior.custom.vacationmanagement.validation.ValidationMessage;

/**
 * Mensagem com o erro, caso tenha ocorrido problema de validação
 */
public class VacationScheduleMessage {

    /**
     * Id do colaborador
     */
    public String employeeId;
    
    /**
     * Id do período de férias
     */
    public String vacationPeriodId;
    
    /**
     * Data inicial das férias
     */
    public java.time.LocalDate startDate;
    
    /**
     * Mensagens de erro
     */
    public List<ValidationMessage> validationMessages;
    
}
