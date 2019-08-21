package br.com.senior.custom.vacationmanagement.validation;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.senior.custom.vacationmanagement.vacationschedule.VacationSchedule;
import br.com.senior.custom.vacationmanagement.vacationpolicy.VacationPolicy;
import br.com.senior.custom.vacationmanagement.vacationpolicy.VacationPolicyService;

@Service
class MaxDayRequestValidator implements VacationScheduleValidatorBase {

    private static final String MAX_SCHEDULE_DAY_MESSAGE = "É possível solicitar férias apenas até o dia %s";

    @Inject
    private VacationPolicyService vacationPolicyService;

    @Inject
    private ValidationMessageService validationMessageService;

    /**
     * Validação customizada para validar se o dia de início das férias é depois do dia limite para solicitar férias.
     * O dia limite é um campo customizado criado pelo usuário e seu valor foi definido na edição de política de férias na plataforma
     *
     * @param output Mensagens de erro da validação de férias da Senior
     * @param vacationSchedule Solicitação de férias
     * @throws MaxDayRequestValidationException Exceção lançada pela validação customizada
     */
    @Override
    public void handleValidation(ValidateVacationScheduleOutput output, VacationSchedule vacationSchedule) {
        VacationPolicy vacationPolicy = vacationPolicyService.getVacationPolicyByEmployeeId(vacationSchedule.employeeId);

        // Verifica se o campo existe, se não existir será lançado uma exceção
        if (!vacationPolicy.getCustom().isEmpty()) {

            // Verifica se o valor do campo está vazio, se estiver vazio será lançado uma exceção
            if (vacationPolicy.getCustom("maxDayToRequestVacation") != null) {
                try {
                    // Pode ocorrer erro de conversão caso o campo tenha um valor incompatível
                    final double maxDayToRequestVacation = Double.parseDouble(vacationPolicy.getCustom("maxDayToRequestVacation").toString());

                    // Verifica se o dia de início das férias é maior que o dia limite
                    if (vacationSchedule.startDate.getDayOfMonth() > maxDayToRequestVacation) {
                        validationMessageService.addValidationMessage(output, vacationSchedule, String.format(MAX_SCHEDULE_DAY_MESSAGE, maxDayToRequestVacation));
                    }
                } catch (Exception e) {
                    throw new MaxDayRequestValidationException("Erro ao converter valor do campo customizado maxDayToRequestVacation", e);
                }
            } else {
                throw new MaxDayRequestValidationException("Não há valores para o campo customizado maxDayToRequestVacation");
            }
        } else {
            throw new MaxDayRequestValidationException("Nenhum campo customizado encontrado");
        }
    }
}
