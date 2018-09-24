package com.primedice.common.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String salt;

    private Boolean locked;

    public User() {
    }

    @JsonIgnore
    public String getCredentialsSalt() {
        return username + salt;
    }
}
