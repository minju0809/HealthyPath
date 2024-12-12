package com.springboot.healthypath.food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
