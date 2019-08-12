package br.com.senior.custom.vacationperiod;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.senior.custom.rest.PlatformClient;

@Service
public class VacationPeriodService {

    @Inject
    private PlatformClient platformClient;
    
    public VacationPeriod getVacationPeriod(String vacationPeriodId) {
        return platformClient.getEntity("vacationperiod", vacationPeriodId, VacationPeriod.class);
    }

}
