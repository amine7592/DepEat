package org.elis.depeat.datamodels;

public class User {

    public static final String REGISTER_ENDPOINT = "auth/local/register";
    public static final String LOGIN_ENDPOINT = "auth/local";

    private String username,email,accessToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
