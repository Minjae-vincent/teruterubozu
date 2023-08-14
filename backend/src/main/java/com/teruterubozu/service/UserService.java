package com.teruterubozu.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.teruterubozu.config.JwtTokenProvider;
import com.teruterubozu.domain.JwtToken;
import com.teruterubozu.domain.SignUp;
import com.teruterubozu.entity.Users;
import com.teruterubozu.repository.UserRepository;

@Service
@Transactional
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder encoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;

  public UserService(BCryptPasswordEncoder encoder, UserRepository userRepository,
      AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
    this.encoder = encoder;
    this.userRepository = userRepository;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public JwtToken login(String email, String password) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
    return jwtToken;
  }

  public Long signup(SignUp signupForm) {
    boolean check = checkEmailExists(signupForm.getEmail());

    if (check) {
      throw new IllegalArgumentException("이미 존재하는 유저입니다.");
    }

    String encPwd = encoder.encode(signupForm.getPassword());

    Users user = userRepository.save(signupForm.toEntity(encPwd));

    if (user != null) {
      return user.getId();
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
  }

  public boolean checkEmailExists(String email) {
    return userRepository.existsUsersByEmail(email);
  }
}
