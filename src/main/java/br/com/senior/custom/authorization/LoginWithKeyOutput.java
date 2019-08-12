package br.com.senior.custom.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginWithKeyOutput {

    public String jsonToken;

}
