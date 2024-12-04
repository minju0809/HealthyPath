package com.springboot.healthypath.user;

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
  private int height;
  private double bmi;
  private String classification;
  private double bmr;
  private double goal;
  private String excluded_foods;
  private Date created_at;
  private Date updated_at;
}
