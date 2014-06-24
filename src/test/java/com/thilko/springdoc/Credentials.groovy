package com.thilko.springdoc

@SuppressWarnings("unused")
class Credentials {
    private String user;
    private CredentialsCode credentialsCode;

    String getUser() {
        return user
    }

    void setUser(String user) {
        this.user = user
    }

    CredentialsCode getCredentialsCode() {
        return credentialsCode
    }

    void setCredentialsCode(CredentialsCode priority) {
        this.credentialsCode = priority
    }
}
