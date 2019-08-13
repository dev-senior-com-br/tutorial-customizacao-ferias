package br.com.senior.custom.vacationperiod;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.senior.custom.rest.PlatformClient;

@Service
public class VacationPeriodService {

    @Inject
    private PlatformClient platformClient;

    /**
     * Busca as informações do período de férias do colaborador a partir do id do período de férias
     *
     * @param vacationPeriodId Id do período de férias
     * @return {@link VacationPeriod} contendo as informações do período de férias
     */
    public VacationPeriod getVacationPeriod(String vacationPeriodId) {
        return platformClient.getEntity("vacationperiod", vacationPeriodId, VacationPeriod.class);
    }

}
