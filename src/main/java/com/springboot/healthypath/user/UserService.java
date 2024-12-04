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

  public UserVO getUser(UserVO vo) {
    return userDao.getUser(vo);
  }

  public void insertUserEmailAndName(UserVO vo) {
    userDao.insertUserEmailAndName(vo);
  }

  public void updateUser(UserVO vo) {
    userDao.updateUser(vo);
  }
}
