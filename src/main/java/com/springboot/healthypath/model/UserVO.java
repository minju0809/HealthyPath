package com.springboot.healthypath.model;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {
  private Long user_id;
  private String email;
  private String name;
  private int age;
  private String gender;
  private double weight;
  private double height;
  private double bmi;
  private String classification;
  private double bmr;
  private double tdee;             // 총 에너지 소비량 (kcal)
  private String activity_level;    // 활동 수준 (운동하지 않음, 가벼운 운동, 보통 운동, 강도 높은 운동, 매우 강도)
  private double recommended_carbs; // 권장 탄수화물 섭취량 (g)
  private double recommended_protein; // 권장 단백질 섭취량 (g)
  private double recommended_fats;  // 권장 지방 섭취량 (g)
  private String dietary_goal;      // 식단 목표 (예: 체중 감량, 유지, 근육 증가)
  private double goal;
  private String excluded_foods;
  private Date created_at;
  private Date updated_at;
}
