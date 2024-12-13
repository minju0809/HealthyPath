package com.springboot.healthypath.food;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodDao {
  List<FoodVO> searchFoodByName(Map<String, Object> params);
  List<FoodVO> searchFoodByNutrient(Map<String, Object> params);
  int countTotalRecords(Map<String, Object> params);
  FoodVO getFood(FoodVO vo);
}
