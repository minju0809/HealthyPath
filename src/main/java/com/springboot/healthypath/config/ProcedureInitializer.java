package com.springboot.healthypath.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ProcedureInitializer {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  // 스프링 애플리케이션이 초기화될 때 initializeProcedures 메서드를 자동으로 실행
  @PostConstruct
  public void initializeProcedures() {
    initProcedures();
    createInsertBmiRecordProcedure();
  }

  public void initProcedures() {
    String sql = """
            CREATE PROCEDURE IF NOT EXISTS update_user_info(
                IN p_email VARCHAR(255),
                IN p_name VARCHAR(255),
                IN p_age INT,
                IN p_gender VARCHAR(10),
                IN p_weight DOUBLE,
                IN p_height DOUBLE,
                IN p_bmi DOUBLE,
                IN p_classification VARCHAR(50),
                IN p_bmr DOUBLE,
                IN p_tdee DOUBLE,
                IN p_activity_level VARCHAR(50),
                IN p_recommended_carbs DOUBLE,
                IN p_recommended_protein DOUBLE,
                IN p_recommended_fats DOUBLE,
                IN p_goal VARCHAR(255),
                IN p_dietary_goal VARCHAR(255),
                IN p_excluded_foods TEXT
            )
            BEGIN
                UPDATE users
                SET name = p_name,
                    age = p_age,
                    gender = p_gender,
                    weight = p_weight,
                    height = p_height,
                    bmi = p_bmi,
                    classification = p_classification,
                    bmr = p_bmr,
                    tdee = p_tdee,
                    activity_level = p_activity_level,
                    recommended_carbs = p_recommended_carbs,
                    recommended_protein = p_recommended_protein,
                    recommended_fats = p_recommended_fats,
                    goal = p_goal,
                    dietary_goal = p_dietary_goal,
                    excluded_foods = p_excluded_foods,
                    updated_at = CURRENT_TIMESTAMP
                WHERE email = p_email;
            END;
        """;
    try {
      jdbcTemplate.execute(sql);
    } catch (Exception e) {
      System.err.println("Error creating stored procedure: " + e.getMessage());
    }
  }

  private void createInsertBmiRecordProcedure() {
    String sql = """
            CREATE PROCEDURE IF NOT EXISTS insert_bmi_record(
                IN p_user_id INT,
                IN p_weight DOUBLE
            )
            BEGIN
                DECLARE p_bmi DOUBLE;
                DECLARE p_classification VARCHAR(50);
                DECLARE p_bmr DOUBLE;

                -- BMI 계산
                SET p_bmi = p_weight / (SELECT POWER(height / 100.0, 2) FROM users WHERE user_id = p_user_id);

                -- 분류 결정
                IF p_bmi < 18.5 THEN
                    SET p_classification = '저체중';
                ELSEIF p_bmi < 25.0 THEN
                    SET p_classification = '정상';
                ELSEIF p_bmi < 30.0 THEN
                    SET p_classification = '과체중';
                ELSE
                    SET p_classification = '비만';
                END IF;

                -- BMR 계산 (성별에 따라 다름)
                SET p_bmr = (
                    CASE (SELECT gender FROM users WHERE user_id = p_user_id)
                        WHEN '남성' THEN 88.362 + (13.397 * p_weight) + (4.799 * (SELECT height FROM users WHERE user_id = p_user_id)) - (5.677 * (SELECT age FROM users WHERE user_id = p_user_id))
                        WHEN '여성' THEN 447.593 + (9.247 * p_weight) + (3.098 * (SELECT height FROM users WHERE user_id = p_user_id)) - (4.330 * (SELECT age FROM users WHERE user_id = p_user_id))
                    END
                );

                -- BMI 기록 삽입
                INSERT INTO bmi_records (user_id, weight, bmi, classification, bmr)
                VALUES (p_user_id, p_weight, p_bmi, p_classification, p_bmr);
            END;
        """;
    try {
      jdbcTemplate.execute(sql);
    } catch (Exception e) {
      System.err.println("Error creating stored procedure: " + e.getMessage());
    }
  }
}