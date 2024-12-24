package com.springboot.healthypath.food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.healthypath.user.UserVO;

@Service
public class FoodService {
  @Autowired
  private FoodDao foodDao;

  public Map<String, Object> searchFood(FoodVO vo) {
    int page_size = 10;
    int offset = Math.max((vo.getPage() - 1) * page_size, 0); // 음수 방지

    Map<String, Object> params = new HashMap<>();
    params.put("page_size", page_size);
    params.put("offset", offset);

    List<FoodVO> foods;
    int total_records;

    if ("food_name".equals(vo.getSearch_type())) {
      params.put("food_name", vo.getSearch_value());
      foods = foodDao.searchFoodByName(params);
      total_records = foodDao.countTotalRecords(params);
    } else if ("nutrient".equals(vo.getSearch_type())) {
      params.put("nutrient_column", vo.getSearch_value()); // 컨트롤러에서 매핑된 값 사용
      foods = foodDao.searchFoodByNutrient(params);
      total_records = foodDao.countTotalRecords(new HashMap<>()); // 모든 영양소의 총 레코드 수
    } else {
      throw new IllegalArgumentException("잘못된 검색 타입입니다.");
    }

    int total_pages = (int) Math.ceil((double) total_records / page_size);
    int current_block = (int) Math.ceil((double) vo.getPage() / 10);
    int start_page = (current_block - 1) * 10 + 1;
    int end_page = Math.min(current_block * 10, total_pages);

    Map<String, Object> result = new HashMap<>();
    result.put("foods", foods);
    result.put("current_page", vo.getPage());
    result.put("total_pages", total_pages);
    result.put("start_page", start_page);
    result.put("end_page", end_page);

    return result;
  }

  public FoodVO getFood(FoodVO vo) {

    return foodDao.getFood(vo);
  }

  public Map<String, List<FoodRecommendVO>> getFoodsByCategoryAndCalories(FoodRecommendVO vo) {
    Map<String, List<FoodRecommendVO>> recommendations = new HashMap<>();
    recommendations.put("breakfast", foodDao.getFoodsByCategoryAndCalories(vo.getBreakfast_category(), vo.getDaily_calories() * 0.3));
    recommendations.put("lunch", foodDao.getFoodsByCategoryAndCalories(vo.getLunch_category(), vo.getDaily_calories() * 0.4));
    recommendations.put("dinner", foodDao.getFoodsByCategoryAndCalories(vo.getDinner_category(), vo.getDaily_calories() * 0.3));

    return recommendations;
  }

  public void insertFoodRecommendation(FoodRecommendVO vo) {
    foodDao.insertFoodRecommendation(vo);
  }

  public List<FoodRecommendVO> getFoodRecommendations(UserVO vo) {

    return foodDao.getFoodRecommendations(vo);
  }

  public void deleteFoodRecommendation(FoodRecommendVO vo) {
    foodDao.deleteFoodRecommendation(vo);
  }

  public List<DailyMealVO> getDailyMeals(UserVO vo) {

    return foodDao.getDailyMeals(vo);
  }

  public List<FoodVO> searchFoodByName(FoodVO vo) {
    Map<String, Object> params = new HashMap<>();
    params.put("food_name", vo.getFood_name());

    return foodDao.searchFoodByName(params);
  }

  public void insertDailyMeal(DailyMealVO vo) {
    foodDao.insertDailyMeal(vo);
  }

  public List<DailyMealVO> getWeeklyMeals(DailyMealVO vo) {
    return foodDao.getWeeklyMeals(vo);
  }

  ////////////////////////////////////////////////////////////////////////////////

  public Map<String, Object> getRecipes(RecipeVO vo) {
    Map<String, Object> result = new HashMap<>();

    // 전체 레시피 수 조회
    int total_count = foodDao.getTotalCount(vo); 
    int total_pages = (int) Math.ceil((double) total_count / vo.getLimit());

    // 레시피 목록 조회
    List<RecipeVO> recipes = foodDao.getRecipes(vo);

    result.put("recipes", recipes);
    result.put("total_pages", total_pages);
    result.put("start_page", Math.max(1, vo.getPage() - 2));
    result.put("end_page", Math.min(total_pages, vo.getPage() + 2));
    result.put("total_count", total_count);

    return result;
  }

  public RecipeVO getRecipe(RecipeVO vo) {

    return foodDao.getRecipe(vo);
  }

  public void incrementViewCount(RecipeVO vo) {
    foodDao.incrementViewCount(vo);
  }
}
