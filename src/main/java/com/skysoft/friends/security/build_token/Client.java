package com.skysoft.friends.security.build_token;

public class Client {

    private String name;
    private String password;

    private Client(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static Client getWebClient() {
        return new Client("WEB_CLIENT", "ZGB*+jP#~DV:VNz}3]fR3e23");
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
