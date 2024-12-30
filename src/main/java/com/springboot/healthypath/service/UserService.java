package com.springboot.healthypath.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.healthypath.dao.UserDao;
import com.springboot.healthypath.model.BmiRecordVO;
import com.springboot.healthypath.model.UserVO;

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

  public List<BmiRecordVO> getBmiRecords(UserVO vo) {
    return userDao.getBmiRecords(vo);
  }

  public void insertBmiRecord(BmiRecordVO vo) {
    userDao.insertBmiRecord(vo);
  }
}
