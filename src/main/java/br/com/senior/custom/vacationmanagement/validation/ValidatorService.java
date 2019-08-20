package br.com.senior.custom.vacationmanagement.validation;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.senior.custom.vacationmanagement.vacationschedule.VacationSchedule;

@Service
public class ValidatorService {

    @Inject
    private MaxDayRequestValidator maxDayRequestValidator;

    @Inject
    private LimitDateValidator limitDateValidator;

    /**
     * Método que irá chamar as validações customizadas
     *
     * @param output Mensagens de erro da validação da Senior
     * @param vacationSchedule Solicitação de férias
     */
    public void validate(ValidateVacationScheduleOutput output, VacationSchedule vacationSchedule) {
        maxDayRequestValidator.handleValidation(output, vacationSchedule);
        limitDateValidator.handleValidation(output, vacationSchedule);
    }

}
