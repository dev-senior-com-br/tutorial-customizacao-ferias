package br.com.senior.custom.vacation.model;

/**
 * Representa uma solicitação de férias
 */
public class VacationSchedule {
    
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
     * Quantidade de dias de férias
     */
    public Long vacationDays;
    
    /**
     * Dias de abono
     */
    public Long vacationBonusDays;
    
    /**
     * Tem adiantamento do 13º salário
     */
    public Boolean has13thSalaryAdvance;
    
}
