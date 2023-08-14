package com.teruterubozu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teruterubozu.domain.JwtToken;
import com.teruterubozu.domain.SignUp;
import com.teruterubozu.service.UserService;

@RestController
@RequestMapping("/api/user")
public class LoginController {
  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<JwtToken> loginSuccess(@RequestBody Map<String, String> loginForm) {
    JwtToken token = userService.login(loginForm.get("email"), loginForm.get("password"));
    return ResponseEntity.ok(token);
  }

  @PostMapping("/signup")
  public Long signup(@RequestBody SignUp signupForm) {
    return userService.signup(signupForm);
  }
}
