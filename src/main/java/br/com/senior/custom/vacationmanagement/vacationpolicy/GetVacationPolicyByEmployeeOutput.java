package br.com.senior.custom.vacationmanagement.vacationpolicy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetVacationPolicyByEmployeeOutput {

    /**
     * Política de férias
     */
    public VacationPolicy vacationPolicy;

}
