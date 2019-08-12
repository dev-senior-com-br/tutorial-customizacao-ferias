package br.com.senior.custom.vacationperiod;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VacationPeriod {
    
    /**
     * Id do período de férias
     */
    public String id;
    
    /**
     * Colaborador
     */
    public String employee;
    
    /**
     * Data inicial
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public java.time.LocalDate startdate;
    
    /**
     * Data final
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public java.time.LocalDate enddate;
    
}
