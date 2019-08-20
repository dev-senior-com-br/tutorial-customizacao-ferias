package br.com.senior.custom.vacationmanagement.vacationperiod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.senior.custom.vacationmanagement.platform.PlatformClient;

class VacationPeriodServiceTest {

    @InjectMocks
    private VacationPeriodService service;

    @Mock
    private PlatformClient platformClient;

    @BeforeEach
    void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * <pre>
     *     id do período de férias: vacationPeriodId
     * </pre>
     *
     * Deve retornar o período de férias
     */
    @Test
    public void getVacationPeriod() {
        String vacationPeriodId = "vacationPeriodId";
        VacationPeriod vacationPeriod = new VacationPeriod();

        Mockito.when(platformClient.getEntity("vacationperiod", vacationPeriodId, VacationPeriod.class)).thenReturn(vacationPeriod);

        VacationPeriod result = service.getVacationPeriod(vacationPeriodId);

        Assertions.assertSame(result, vacationPeriod);
    }

}