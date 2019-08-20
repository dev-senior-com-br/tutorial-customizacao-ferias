package br.com.senior.custom.vacationmanagement.vacationpolicy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VacationPolicyFractioning {

    /**
     * Id
     */
    public String id;

    /**
     * Política de férias
     */
    public VacationPolicy vacationPolicy;

    /**
     * Tipo de fracionamento
     */
    public FractioningType fractioningType;

}
