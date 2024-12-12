package com.springboot.healthypath.food;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class NutrientService {
  private static final Map<String, String> nutrientColumnMap = new HashMap<>();

  static {
    nutrientColumnMap.put("에너지", "energy_kcal");
    nutrientColumnMap.put("수분", "moisture_g");
    nutrientColumnMap.put("단백질", "protein_g");
    nutrientColumnMap.put("지방", "fat_g");
    nutrientColumnMap.put("회분", "ash_g");
    nutrientColumnMap.put("탄수화물", "carbohydrate_g");
    nutrientColumnMap.put("당류", "sugar_g");
    nutrientColumnMap.put("식이섬유", "dietary_fiber_g");
    nutrientColumnMap.put("칼슘", "calcium_mg");
    nutrientColumnMap.put("철", "iron_mg");
    nutrientColumnMap.put("인", "phosphorus_mg");
    nutrientColumnMap.put("칼륨", "potassium_mg");
    nutrientColumnMap.put("나트륨", "sodium_mg");
    nutrientColumnMap.put("비타민 A", "vitamin_a_ug_rae");
    nutrientColumnMap.put("레티놀", "retinol_ug");
    nutrientColumnMap.put("베타카로틴", "beta_carotene_ug");
    nutrientColumnMap.put("티아민", "thiamine_mg");
    nutrientColumnMap.put("리보플라빈", "riboflavin_mg");
    nutrientColumnMap.put("니아신", "niacin_mg");
    nutrientColumnMap.put("비타민 C", "vitamin_c_mg");
    nutrientColumnMap.put("비타민 D", "vitamin_d_ug");
    nutrientColumnMap.put("콜레스테롤", "cholesterol_mg");
    nutrientColumnMap.put("포화지방산", "saturated_fatty_acids_g");
    nutrientColumnMap.put("트랜스지방산", "trans_fatty_acids_g");
    nutrientColumnMap.put("자당", "sucrose_g");
    nutrientColumnMap.put("포도당", "glucose_g");
    nutrientColumnMap.put("과당", "fructose_g");
    nutrientColumnMap.put("유당", "lactose_g");
    nutrientColumnMap.put("맥아당", "maltose_g");
    nutrientColumnMap.put("마그네슘", "magnesium_mg");
    nutrientColumnMap.put("아연", "zinc_mg");
    nutrientColumnMap.put("구리", "copper_ug");
    nutrientColumnMap.put("망간", "manganese_mg");
    nutrientColumnMap.put("셀레늄", "selenium_ug");
    nutrientColumnMap.put("토코페롤", "tocopherol_mg");
    nutrientColumnMap.put("토코트리에놀", "tocotrienol_mg");
    nutrientColumnMap.put("엽산", "folate_ug_dfe");
    nutrientColumnMap.put("비타민 B12", "vitamin_b12_ug");
    nutrientColumnMap.put("아미노산", "amino_acids_mg");
    nutrientColumnMap.put("이소류신", "isoleucine_mg");
    nutrientColumnMap.put("류신", "leucine_mg");
    nutrientColumnMap.put("라이신", "lysine_mg");
    nutrientColumnMap.put("메티오닌", "methionine_mg");
    nutrientColumnMap.put("페닐알라닌", "phenylalanine_mg");
    nutrientColumnMap.put("트레오닌", "threonine_mg");
    nutrientColumnMap.put("발린", "valine_mg");
    nutrientColumnMap.put("히스티딘", "histidine_mg");
    nutrientColumnMap.put("아르기닌", "arginine_mg");
    nutrientColumnMap.put("티로신", "tyrosine_mg");
    nutrientColumnMap.put("시스테인", "cysteine_mg");
    nutrientColumnMap.put("알라닌", "alanine_mg");
    nutrientColumnMap.put("아스파르트산", "aspartic_acid_mg");
    nutrientColumnMap.put("글루탐산", "glutamic_acid_mg");
    nutrientColumnMap.put("글리신", "glycine_mg");
    nutrientColumnMap.put("프롤린", "proline_mg");
    nutrientColumnMap.put("세린", "serine_mg");
    // nutrientColumnMap.put("부티르산 (4:0)", "butyric_acid_4_0_mg");
    // nutrientColumnMap.put("카프로산 (6:0)", "caproic_acid_6_0_mg");
    // nutrientColumnMap.put("카프릴산 (8:0)", "caprylic_acid_8_0_mg");
    // nutrientColumnMap.put("카프르산 (10:0)", "capric_acid_10_0_mg");
    // nutrientColumnMap.put("라우르산 (12:0)", "lauric_acid_12_0_mg");
    // nutrientColumnMap.put("미리스트산 (14:0)", "myristic_acid_14_0_mg");
    // nutrientColumnMap.put("팔미트산 (16:0)", "palmitic_acid_16_0_mg");
    // nutrientColumnMap.put("스테아르산 (18:0)", "stearic_acid_18_0_mg");
    // nutrientColumnMap.put("아라키드산 (20:0)", "arachidic_acid_20_0_mg");
    // nutrientColumnMap.put("미리스톨레산 (14:1)", "myristoleic_acid_14_1_mg");
    // nutrientColumnMap.put("팔미톨레산 (16:1)", "palmitoleic_acid_16_1_mg");
    // nutrientColumnMap.put("올레산 (18:1 n-9)", "oleic_acid_18_1_n9_mg");
    // nutrientColumnMap.put("박센산 (18:1 n-7)", "vaccenic_acid_18_1_n7_mg");
    // nutrientColumnMap.put("가돌레산 (20:1 n-11)", "gadoleic_acid_20_1_n11_mg");
    // nutrientColumnMap.put("리놀레산 (18:2 n-6)", "linoleic_acid_18_2_n6_g");
    // nutrientColumnMap.put("알파리놀렌산 (18:3 n-3)", "alpha_linolenic_acid_18_3_n3_g");
    // nutrientColumnMap.put("감마 리놀렌산 (18:3 n-6)", "gamma_linolenic_acid_18_3_n6_mg");
    // nutrientColumnMap.put("에이코사디에노산 (20:2 n-6)", "eicosadienoic_acid_20_2_n6_mg");
    // nutrientColumnMap.put("아라키돈산 (20:4 n-6)", "arachidonic_acid_20_4_n6_mg");
    // nutrientColumnMap.put("에이코사트리에노산 (20:3 n-6)", "eicosatrienoic_acid_20_3_n6_mg");
    // nutrientColumnMap.put("에이코사펜타에노산 (20:5 n-3)", "eicosapentaenoic_acid_20_5_n3_mg");
    // nutrientColumnMap.put("도코사펜타에노산 (22:5 n-3)", "docosapentaenoic_acid_22_5_n3_mg");
    // nutrientColumnMap.put("도코사헥사에노산 (22:6 n-3)", "docosahexaenoic_acid_22_6_n3_mg");
    // nutrientColumnMap.put("트랜스 올레산 (18:1 trans n-9)", "trans_oleic_acid_18_1_trans_n9_mg");
    // nutrientColumnMap.put("트랜스 리놀레산 (18:2t)", "trans_linoleic_acid_18_2t_mg");
    // nutrientColumnMap.put("트랜스 리놀렌산 (18:3t)", "trans_linolenic_acid_18_3t_mg");
    nutrientColumnMap.put("부티르산", "butyric_acid_4_0_mg");
    nutrientColumnMap.put("카프로산", "caproic_acid_6_0_mg");
    nutrientColumnMap.put("카프릴산", "caprylic_acid_8_0_mg");
    nutrientColumnMap.put("카프르산", "capric_acid_10_0_mg");
    nutrientColumnMap.put("라우르산", "lauric_acid_12_0_mg");
    nutrientColumnMap.put("미리스트산", "myristic_acid_14_0_mg");
    nutrientColumnMap.put("팔미트산", "palmitic_acid_16_0_mg");
    nutrientColumnMap.put("스테아르산", "stearic_acid_18_0_mg");
    nutrientColumnMap.put("아라키드산", "arachidic_acid_20_0_mg");
    nutrientColumnMap.put("미리스톨레산", "myristoleic_acid_14_1_mg");
    nutrientColumnMap.put("팔미톨레산", "palmitoleic_acid_16_1_mg");
    nutrientColumnMap.put("올레산", "oleic_acid_18_1_n9_mg");
    nutrientColumnMap.put("박센산", "vaccenic_acid_18_1_n7_mg");
    nutrientColumnMap.put("가돌레산", "gadoleic_acid_20_1_n11_mg");
    nutrientColumnMap.put("리놀레산", "linoleic_acid_18_2_n6_g");
    nutrientColumnMap.put("알파리놀렌산", "alpha_linolenic_acid_18_3_n3_g");
    nutrientColumnMap.put("감마 리놀렌산", "gamma_linolenic_acid_18_3_n6_mg");
    nutrientColumnMap.put("에이코사디에노산", "eicosadienoic_acid_20_2_n6_mg");
    nutrientColumnMap.put("아라키돈산", "arachidonic_acid_20_4_n6_mg");
    nutrientColumnMap.put("에이코사트리에노산", "eicosatrienoic_acid_20_3_n6_mg");
    nutrientColumnMap.put("에이코사펜타에노산", "eicosapentaenoic_acid_20_5_n3_mg");
    nutrientColumnMap.put("도코사펜타에노산", "docosapentaenoic_acid_22_5_n3_mg");
    nutrientColumnMap.put("도코사헥사에노산", "docosahexaenoic_acid_22_6_n3_mg");
    nutrientColumnMap.put("트랜스 올레산", "trans_oleic_acid_18_1_trans_n9_mg");
    nutrientColumnMap.put("트랜스 리놀레산", "trans_linoleic_acid_18_2t_mg");
    nutrientColumnMap.put("트랜스 리놀렌산", "trans_linolenic_acid_18_3t_mg");
    nutrientColumnMap.put("카페인", "caffeine_mg");
  }

  public String getColumnByNutrient(String nutrient_name) {

    return nutrientColumnMap.getOrDefault(nutrient_name, null);
  }
}
