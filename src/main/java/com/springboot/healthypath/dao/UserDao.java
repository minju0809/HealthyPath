package com.springboot.healthypath.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.healthypath.model.BmiRecordVO;
import com.springboot.healthypath.model.UserVO;

@Mapper
public interface UserDao {
  List<UserVO> getAllUsers();
  UserVO getUser(UserVO vo);
  void insertUserEmailAndName(UserVO vo);
  void updateUser(UserVO vo);

  List<BmiRecordVO> getBmiRecords(UserVO vo);
  void insertBmiRecord(BmiRecordVO vo);
}
