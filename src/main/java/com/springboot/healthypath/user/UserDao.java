package com.springboot.healthypath.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
  List<UserVO> getAllUsers();
  UserVO getUser(UserVO vo);
  void insertUserEmailAndName(UserVO vo);
  void updateUser(UserVO vo);

  List<BmiRecordVO> getBmiRecords(UserVO vo);
  void insertBmiRecord(BmiRecordVO vo);
}
