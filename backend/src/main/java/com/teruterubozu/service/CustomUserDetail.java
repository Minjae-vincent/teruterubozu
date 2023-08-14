package com.teruterubozu.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.teruterubozu.domain.SessionUser;
import com.teruterubozu.entity.Users;
import com.teruterubozu.repository.UserRepository;

@Service
public class CustomUserDetail implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetail(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users principal = userRepository.findByEmail(username);

    if (principal == null) {
      throw new UsernameNotFoundException("존재하지 않는 사용자 입니다.");
    }

    return new SessionUser(principal);
  }
}
