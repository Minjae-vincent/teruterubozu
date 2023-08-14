package com.teruterubozu.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.teruterubozu.entity.Users;

import lombok.Getter;

@Getter
public class SessionUser implements UserDetails {
  private Users user;

  public SessionUser(Users user2) {
    this.user = user2;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collections = new ArrayList<>();

    collections.add(() -> {
      return "ROLE_" + user.getRole();
    });

    return collections;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
