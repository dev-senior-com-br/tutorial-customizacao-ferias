package br.com.senior.custom.vacation.model;

/**
 * Tipo de mensagem de erro
 */
public enum ValidationMessageType {
    
    /**
     * É necessário solicitar as férias com no mínimo a quantidade de dias de antecedência definida na política de férias
     */
    TYPE_1_VACATION_SCHEDULING_IN_ADVANCE,
    
    /**
     * É permitido solicitar o adiantamento do 13º salário apenas entre os meses definidos na política de férias
     */
    TYPE_2_THIRTEENTH_SALARY_ADVANCE_TIME_INTERVAL,
    
    /**
     * É permitodo solicitar adiantamento do 13° salário apenas se a opção estiver habilitada na política de férias
     */
    TYPE_3_THIRTEENTH_SALARY_ADVANCE_DISABLED,
    
    /**
     * É permitido solicitar o abono de férias apenas entre os meses definidos na política de férias
     */
    TYPE_4_VACATION_BONUS_TIME_INTERVAL,
    
    /**
     * É permitodo solicitar abono de férias apenas se a opção estiver habilitada na política de férias
     */
    TYPE_5_VACATION_BONUS_DISABLED,
    
    /**
     * A quantidade de dias de abono de férias não está de acordo com a política de férias
     */
    TYPE_6_VACATION_BONUS_DAYS,
    
    /**
     * Não há saldo de abono de férias no período aquisitivo selecionado
     */
    TYPE_7_VACATION_BONUS_DAYS_WITHOUT_BONUS_BALANCE,
    
    /**
     * O número de dias de abono solicitado é maior que o saldo disponível no período aquisitivo
     */
    TYPE_8_VACATION_BONUS_DAYS_GREATER_THAN_VACATION_BALANCE,
    
    /**
     * Período aquisitivo anterior em aberto. É necessário quitar o saldo desse período antes de solicitar no período selecionado
     */
    TYPE_9_PREVIOUS_VACATION_PERIOD_OPENED,
    
    /**
     * Não é permitido iniciar as férias em um DSR ou no período de dois dias que antecedem um DSR
     */
    TYPE_10_START_VACATION_ON_WEEKLY_PAID_REST,
    
    /**
     * Não é permitido iniciar as férias em um dia de folga
     */
    TYPE_11_START_VACATION_ON_DAY_OFF,
    
    /**
     * Não é permitido iniciar as férias em um dia compensado
     */
    TYPE_12_START_VACATION_ON_COMPENSATED_DAY,
    
    /**
     * O número de dias de férias solicitado não está de acordo com a política de fracionamento de férias da empresa
     */
    TYPE_13_FRACTIONING_NOT_COMPATIBLE,
    
    /**
     * O período de férias deve estar aberto para calcular a projeção do saldo de férias
     */
    TYPE_14_VACATION_PERIOD_NOT_OPEN,
    
    /**
     * Data de início das férias deve ser maior que o início do período aquisitivo
     */
    TYPE_15_START_DATE_BEFORE_VACATION_PERIOD_START_DATE,
    
    /**
     * Data de início das férias deve ser maior que o fim do período aquisitivo
     */
    TYPE_16_START_DATE_BEFORE_VACATION_PERIOD_END_DATE,
    
    /**
     * O número de dias de férias solicitado é maior que o saldo disponível no período aquisitivo
     */
    TYPE_17_REQUESTED_VACATION_DAYS_GREATER_THAN_VACATION_BALANCE,
    
    /**
     * Há um afastamento programado na data de início das férias
     */
    TYPE_18_EMPLOYEE_ABSENCE_SCHEDULED_ON_START_DATE,
    
    /**
     * O período das férias deve ser anterior ao fim do período concessivo
     */
    TYPE_19_VACATION_DOUBLE,
    
    /**
     * Não é permitido iniciar as férias em um feriado ou no período de dois dias que antecedem um feriado
     */
    TYPE_20_VACATION_SCHEDULED_ON_HOLIDAY
}
