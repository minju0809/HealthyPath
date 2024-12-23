package com.springboot.healthypath.food;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.healthypath.user.UserVO;

@Mapper
public interface FoodDao {
  List<FoodVO> searchFoodByName(Map<String, Object> params);
  List<FoodVO> searchFoodByNutrient(Map<String, Object> params);
  int countTotalRecords(Map<String, Object> params);
  FoodVO getFood(FoodVO vo);
  List<FoodRecommendVO> getFoodsByCategoryAndCalories(String category, double calories);
  void insertFoodRecommendation(FoodRecommendVO vo);
  List<FoodRecommendVO> getFoodRecommendations(UserVO vo);
  void deleteFoodRecommendation(FoodRecommendVO vo);
  List<DailyMealVO> getDailyMeals(UserVO vo);
  void insertDailyMeal(DailyMealVO vo);

  List<RecipeVO> getRecipes(RecipeVO vo);
  int getTotalCount(RecipeVO vo);
  RecipeVO getRecipe(RecipeVO vo);
  void incrementViewCount(RecipeVO vo);
}
