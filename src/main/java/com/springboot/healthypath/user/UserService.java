package com.springboot.healthypath.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserDao userDao;

  public List<UserVO> getAllUsers() {
    return userDao.getAllUsers();
  }
}
