package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String userName;
    private String password;
    private List<String> roles = new ArrayList<>();

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}