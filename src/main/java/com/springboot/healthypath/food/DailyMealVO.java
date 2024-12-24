package com.springboot.healthypath.food;

import java.util.Date;

import lombok.Data;

@Data
public class DailyMealVO {
  private int meal_id;
  private Long user_id;
  private int idx; 
  private String date;
  private String meal_time;
  private String food_name;
  private String major_category_name;
  private double nutrient_reference_amount;
  private float energy_kcal;
  private Date created_at;
  private Date updated_at;

  private double protein_g; // 단백질 (g)
  private double fat_g; // 지방 (g)
  private double ash_g; // 회분 (g)
  private double carbohydrate_g; // 탄수화물 (g)
}
