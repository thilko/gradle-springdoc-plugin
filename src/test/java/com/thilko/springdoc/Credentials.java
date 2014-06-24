package com.thilko.springdoc;

@SuppressWarnings("all")
public class Credentials {
    CredentialsCode code;
    String user;

    public CredentialsCode getCode() {
        return code;
    }

    public void setCode(CredentialsCode code) {
        this.code = code;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
