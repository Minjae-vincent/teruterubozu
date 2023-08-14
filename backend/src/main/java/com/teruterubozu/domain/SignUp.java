package com.teruterubozu.domain;

import com.teruterubozu.domain.enums.Role;
import com.teruterubozu.entity.Users;

import lombok.Getter;

@Getter
public class SignUp {
  private String name;
  private String email;
  private String password;

  public Users toEntity(String encPwd) {
    return Users.builder().name(name).email(email).password(encPwd).role(Role.USER).build();
  }
}
