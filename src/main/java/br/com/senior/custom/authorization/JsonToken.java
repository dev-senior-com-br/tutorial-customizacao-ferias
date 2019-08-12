package br.com.senior.custom.authorization;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonToken {

    private String accessToken;
    private LocalDateTime expiresDate;

    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccess_token(String access_token) {
        this.accessToken = access_token;
    }
    
    public boolean isExpired() {
        return this.expiresDate == null || this.expiresDate.isAfter(LocalDateTime.now());
    }

    public void setExpires_in(int expires_in) {
        this.expiresDate = LocalDateTime.now().plusSeconds(expires_in);
    }
    
}
