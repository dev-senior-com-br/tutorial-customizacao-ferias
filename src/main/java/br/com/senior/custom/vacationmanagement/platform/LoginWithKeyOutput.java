package br.com.senior.custom.vacationmanagement.platform;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginWithKeyOutput {

    public String jsonToken;

}
