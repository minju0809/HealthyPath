package com.springboot.healthypath.food;

import lombok.Data;

@Data
public class FoodRecommendVO {
  private String food_name; // 식품명
  private String major_category_name; // 식품 대분류명
  private double energy_kcal; // 에너지 (kcal)
  private int daily_calories; // 섭취 칼로리량
  private String breakfast_category;
  private String lunch_category;
  private String dinner_category;
}