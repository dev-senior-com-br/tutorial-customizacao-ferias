package br.com.senior.custom.vacationPolicy;

import br.com.senior.custom.common.CustomDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VacationPolicy extends CustomDTO {

    /**
     * Id da política
     */
    public String id;

}
