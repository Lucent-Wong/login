package com.primedice.common.entity;


import lombok.Data;

@Data
public class UserAccount {
    Long userId;
    Long deposit;
    String description;
    Boolean avaliable = Boolean.TRUE;
}
