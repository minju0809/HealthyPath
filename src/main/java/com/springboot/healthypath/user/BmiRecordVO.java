package com.springboot.healthypath.user;

import java.util.Date;

import lombok.Data;

@Data
public class BmiRecordVO {
  private Long bmi_record_id;
  private Long user_id;
  private double weight;
  private double bmi;
  private String classification;
  private double bmr;
  private Date created_at;

  private int age;
  private String gender;
  private double height;
}
