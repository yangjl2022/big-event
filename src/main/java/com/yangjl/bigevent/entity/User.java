package com.yangjl.bigevent.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String nickname;
    private String email;
    private String head_picture;
}
