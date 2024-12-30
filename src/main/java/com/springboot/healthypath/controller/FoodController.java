package com.springboot.healthypath.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.healthypath.model.DailyMealVO;
import com.springboot.healthypath.model.FoodRecommendVO;
import com.springboot.healthypath.model.FoodVO;
import com.springboot.healthypath.model.RecipeVO;
import com.springboot.healthypath.service.FoodService;
import com.springboot.healthypath.service.NutrientService;
import com.springboot.healthypath.model.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class FoodController {
  @Autowired
  private FoodService foodService;

  @Autowired
  private NutrientService nutrientService;

  @GetMapping("/")
  public String getFoods(FoodVO vo, Model model) {
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

  @GetMapping("/food/getFood/{idx}")
  public String getFood(FoodVO vo, Model model) {
    model.addAttribute("food", foodService.getFood(vo));

    return "food/getFood";
  }

  @GetMapping("/food/recommendForm")
  public String recommendForm(Model model) {
    model.addAttribute("vo", new FoodRecommendVO());

    List<String> categories = Arrays.asList(
        "밥류", "빵 및 과자류", "면 및 만두류", "죽 및 스프류", "국 및 탕류",
        "찌개 및 전골류", "찜류", "구이류", "전·적 및 부침류", "볶음류",
        "조림류", "튀김류", "나물·숙채류", "생채·무침류", "김치류", "젓갈류",
        "장아찌·절임류", "음료 및 차류", "수·조·어·육류", "장류, 양념류",
        "유제품류 및 빙과류", "과일류", "두류, 견과 및 종실류");
    model.addAttribute("categories", categories);

    return "food/recommendForm";
  }

  @GetMapping("/food/recommendations")
  public String recommendations(@ModelAttribute FoodRecommendVO vo, Model model) {
    Map<String, List<FoodRecommendVO>> recommendations = foodService.getFoodsByCategoryAndCalories(vo);

    model.addAttribute("recommendations", recommendations);
    model.addAttribute("vo", vo); // 사용자 입력 값 다시 전달
    model.addAttribute("categories", Arrays.asList(
        "밥류", "빵 및 과자류", "면 및 만두류", "죽 및 스프류", "국 및 탕류",
        "찌개 및 전골류", "찜류", "구이류", "전·적 및 부침류", "볶음류",
        "조림류", "튀김류", "나물·숙채류", "생채·무침류", "김치류", "젓갈류",
        "장아찌·절임류", "음료 및 차류", "수·조·어·육류", "장류, 양념류",
        "유제품류 및 빙과류", "과일류", "두류, 견과 및 종실류"));

    return "food/recommendForm";
  }

  @PostMapping("/food/insertFoodRecommendation")
  @ResponseBody
  public ResponseEntity<?> insertFoodRecommendation(HttpSession session, FoodRecommendVO vo, Model model) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");

    vo.setUser_id(sessionUser.getUser_id());

    List<FoodRecommendVO> li = foodService.getFoodRecommendations(sessionUser);
    for (FoodRecommendVO food : li) {
      if (food.getIdx() == vo.getIdx()) {

        return ResponseEntity.ok(Collections.singletonMap("message", "이미 저장되어 있는 음식입니다. 저장한 음식 페이지로 이동하시겠습니까?"));
      }
    }
    foodService.insertFoodRecommendation(vo);

    return ResponseEntity.ok(Collections.singletonMap("message", "음식이 저장되었습니다. 저장한 음식 페이지로 이동하시겠습니까?"));
  }

  @GetMapping("/food/getFoodRecommendations")
  public String getFoodRecommendations(HttpSession session, Model model) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");

    model.addAttribute("food_recommendations", foodService.getFoodRecommendations(sessionUser));

    return "food/getFoodRecommendations";
  }

  @GetMapping("/food/deleteFoodRecommendation/{recommendation_id}")
  public String deleteFoodRecommendation(FoodRecommendVO vo) {
    foodService.deleteFoodRecommendation(vo);

    return "redirect:/food/getFoodRecommendations";
  }

  @GetMapping("/food/getMonthlyMeals")
  public String getDailyMeals(HttpSession session, Model model) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");

    List<DailyMealVO> meals = foodService.getDailyMeals(sessionUser);

    model.addAttribute("meals", meals);

    return "food/getMonthlyMeals";
  }

  @GetMapping("/food/insertDailyMealForm")
  public String insertDailyMealForm(@RequestParam(required = false) String date, FoodVO vo, Model model) {
    if (date != null) {
      model.addAttribute("date", date);
    }

    return "food/insertDailyMealForm";
  }

  @ResponseBody
  @GetMapping("/food/searchDailyMeal")
  public List<FoodVO> searchDailyMeal(FoodVO vo, Model model) {

    return foodService.searchFoodByName(vo);
  }

  @PostMapping("/food/insertDailyMeal")
  public String insertDailyMeal(HttpSession session, DailyMealVO vo, Model model) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");
    vo.setUser_id(sessionUser.getUser_id());

    float energy_kcal = vo.getEnergy_kcal();
    double nutrient_reference_amount = vo.getNutrient_reference_amount();

    float calculated_energy = energy_kcal * (float) (nutrient_reference_amount / 100.0);
    double protein_g = vo.getProtein_g() * (nutrient_reference_amount / 100.0);
    double fat_g = vo.getFat_g() * (nutrient_reference_amount / 100.0);
    double carbohydrate_g = vo.getCarbohydrate_g() * (nutrient_reference_amount / 100.0);

    vo.setEnergy_kcal(calculated_energy);
    vo.setProtein_g(protein_g);
    vo.setFat_g(fat_g);
    vo.setCarbohydrate_g(carbohydrate_g);

    try {
      foodService.insertDailyMeal(vo);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "데이터 삽입 중 오류가 발생했습니다.");

      return "food/errorPage";
    }

    return "redirect:/food/getMonthlyMeals";
  }

  @GetMapping("/food/getWeeklyMeals")
  public String getWeeklyMeal(
      @RequestParam(required = false) String week_start, DailyMealVO vo,
      HttpSession session, Model model) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");
    vo.setUser_id(sessionUser.getUser_id());

    // 현재 주의 시작일 계산 (week_start가 없으면 오늘 기준)
    LocalDate start_date = (week_start != null)
        ? LocalDate.parse(week_start)
        : LocalDate.now().with(DayOfWeek.MONDAY);
    LocalDate end_date = start_date.plusDays(6);

    vo.setStart_date(start_date);
    vo.setEnd_date(end_date);

    // 서비스에서 주간 데이터를 가져옴
    List<DailyMealVO> meals = foodService.getWeeklyMeals(vo);

    // 이전 주와 다음 주 계산
    LocalDate previous_week = start_date.minusWeeks(1);
    LocalDate next_week = start_date.plusWeeks(1);

    // 모델에 데이터 추가
    model.addAttribute("meals", meals);
    model.addAttribute("start_date", start_date);
    model.addAttribute("end_date", end_date);
    model.addAttribute("previous_week", previous_week);
    model.addAttribute("next_week", next_week);

    return "food/getWeeklyMeals";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////

  @GetMapping("/food/getRecipes")
  public String getRecipes(RecipeVO vo, Model model) {
    int page_size = 10;

    if (vo.getPage() == 0) {
      vo.setPage(1);
    }

    vo.setLimit(page_size);
    vo.setOffset((vo.getPage() - 1) * page_size);

    var result = foodService.getRecipes(vo);

    int current_page = vo.getPage();
    int total_pages = (int) result.get("total_pages"); // 전체 페이지 수
    int start_page = Math.max(1, current_page - 2); // 시작 페이지 (5개씩 표시 예시)
    int end_page = Math.min(total_pages, current_page + 2); // 끝 페이지

    model.addAttribute("recipes", result.get("recipes"));
    model.addAttribute("current_page", current_page);
    model.addAttribute("total_pages", total_pages);
    model.addAttribute("start_page", start_page);
    model.addAttribute("end_page", end_page);
    model.addAttribute("search_category", vo.getSearch_category());
    model.addAttribute("included_foods", vo.getIncluded_foods());
    model.addAttribute("excluded_foods", vo.getExcluded_foods());
    model.addAttribute("total_count", result.get("total_count"));

    return "food/getRecipes";
  }

  @GetMapping("/food/getRecipe/{rcp_sno}")
  public String getRecipe(RecipeVO vo, Model model) {
    foodService.incrementViewCount(vo);
    model.addAttribute("recipe", foodService.getRecipe(vo));

    return "food/getRecipe";
  }

}
