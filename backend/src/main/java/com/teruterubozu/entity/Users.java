package com.teruterubozu.entity;

import com.teruterubozu.domain.enums.Role;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;

  @Builder
  public Users(Long id, String name, String email, String password, Role role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public Users update(String name) {
    this.name = name;
    return this;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }

}
