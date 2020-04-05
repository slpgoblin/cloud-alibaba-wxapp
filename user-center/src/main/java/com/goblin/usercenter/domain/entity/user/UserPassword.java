package com.goblin.usercenter.domain.entity.user;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "user_password")
@Builder
public class UserPassword {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "encrpt_password")
    private String encrptPassword;

    @Column(name = "user_id")
    private Integer userId;
}