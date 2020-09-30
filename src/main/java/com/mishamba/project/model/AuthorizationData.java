package com.mishamba.project.model;

public class AuthorizationData {
    private String email;
    private String password;

    public AuthorizationData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() == AuthorizationData.class) {
            return false;
        }

        AuthorizationData that = (AuthorizationData) o;
        return getEmail().equals(that.getEmail()) &&
                getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        int hash;
        int prime = 83;
        hash = prime * email.hashCode();
        hash *= prime * password.hashCode();

        return hash;
    }
}
