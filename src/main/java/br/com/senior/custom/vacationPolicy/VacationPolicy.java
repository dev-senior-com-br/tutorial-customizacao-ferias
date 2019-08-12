package br.com.senior.custom.vacationPolicy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VacationPolicy extends CustomDTO{

    /**
     * Id da política
     */
    public String id;

}
