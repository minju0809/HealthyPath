package com.springboot.healthypath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.healthypath.food.FoodService;
import com.springboot.healthypath.food.FoodVO;
import com.springboot.healthypath.food.NutrientService;

@Controller
public class FoodController {
  @Autowired
  private FoodService foodService;

  @Autowired
  private NutrientService nutrientService;

  @GetMapping("/")
  public String getFoods(FoodVO vo,
      Model model) {
    if (vo.getPage() == 0) {
      vo.setPage(1);
    }

    if (vo.getSearch_value() == null) {
      vo.setSearch_type("food_name");
      vo.setSearch_value(""); // 기본값으로 모든 데이터를 가져오도록 설정
    }

    String original_search_value = vo.getSearch_value();

    if ("nutrient".equals(vo.getSearch_type())) {
      // 영양소 이름을 변환하지 않고 원본 값을 그대로 사용
      String nutrient_column = nutrientService.getColumnByNutrient(original_search_value);
      if (nutrient_column == null) {
        model.addAttribute("error_message", "잘못된 영양소 이름입니다: " + original_search_value);

        return "food/getFoods"; // 오류 메시지를 표시한 후 반환
      }
      vo.setSearch_value(nutrient_column); // 영문 컬럼명으로 설정
    }

    model.addAttribute("original_search_value", original_search_value);

    var result = foodService.searchFood(vo);

    int current_page = vo.getPage();
    int total_pages = (int) result.get("total_pages");

    // 페이지 값 검증
    if (current_page < 1)
      current_page = 1;
    if (current_page > total_pages)
      current_page = total_pages;

    model.addAttribute("foods", result.get("foods"));
    model.addAttribute("current_page", current_page);
    model.addAttribute("total_pages", total_pages);
    model.addAttribute("search_type", vo.getSearch_type());
    model.addAttribute("search_value", vo.getSearch_value());
    model.addAttribute("start_page", result.get("start_page"));
    model.addAttribute("end_page", result.get("end_page"));

    return "index";
  }
}
