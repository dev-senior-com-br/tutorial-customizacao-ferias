package br.com.senior.custom.vacationmanagement.validation;

import br.com.senior.custom.vacationmanagement.vacationschedule.VacationSchedule;

interface VacationScheduleValidatorBase {

    /**
     * Método base para criação de validações customizadas
     *
     * @param output Mensagens de erro da validação de férias da Senior
     * @param vacationSchedule Solicitação de férias
     */
    void handleValidation(ValidateVacationScheduleOutput output, VacationSchedule vacationSchedule);

}
