package com.springboot.healthypath.food;

import java.util.Date;

import lombok.Data;

@Data
public class DailyMealVO {
  private int meal_id;
  private Long user_id;
  private Date date;
  private String meal_time;
  private String food_name;
  private String major_category_name;
  private float energy_kcal;
  private Date created_at;
  private Date updated_at;
}
