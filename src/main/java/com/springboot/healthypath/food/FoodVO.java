package com.springboot.healthypath.food;

import java.util.Date;

import lombok.Data;

@Data
public class FoodVO {
  private int idx; // PK, 자동 증가
  private String food_code; // 식품 코드
  private String food_name; // 식품명
  private String data_type_code; // 데이터 구분 코드
  private String data_type_name; // 데이터 구분명
  private String origin_code; // 식품 기원 코드
  private String origin_name; // 식품 기원명
  private String major_category_code; // 식품 대분류 코드
  private String major_category_name; // 식품 대분류명
  private String representative_food_code; // 대표 식품 코드
  private String representative_food_name; // 대표 식품명
  private String middle_category_code; // 식품 중분류 코드
  private String middle_category_name; // 식품 중분류명
  private String minor_category_code; // 식품 소분류 코드
  private String minor_category_name; // 식품 소분류명
  private String sub_category_code; // 식품 세분류 코드
  private String sub_category_name; // 식품 세분류명
  private double nutrient_reference_amount; // 영양성분 함량 기준량
  // 일반성분
  private double energy_kcal; // 에너지 (kcal)
  private double moisture_g; // 수분 (g)
  private double protein_g; // 단백질 (g)
  private double fat_g; // 지방 (g)
  private double ash_g; // 회분 (g)
  private double carbohydrate_g; // 탄수화물 (g)
  private double dietary_fiber_g; // 식이섬유 (g)
  // 당류
  private double sugar_g; // 당류 (g)
  private double sucrose_g; // 자당 (g)
  private double glucose_g; // 포도당 (g)
  private double fructose_g; // 과당 (g)
  private double lactose_g; // 유당 (g)
  private double maltose_g; // 맥아당 (g)
  // 무기질
  private double sodium_mg; // 나트륨 (mg)
  private double copper_ug; // 구리 (μg)
  private double magnesium_mg; // 마그네슘 (mg)
  private double manganese_mg; // 망간 (mg)
  private double selenium_ug; // 셀레늄 (μg)
  private double zinc_mg; // 아연 (mg)
  private double phosphorus_mg; // 인 (mg)
  private double iron_mg; // 철 (mg)
  private double potassium_mg; // 칼륨 (mg)
  private double calcium_mg; // 칼슘 (mg)
  // 비타민
  private double niacin_mg; // 니아신 (mg)
  private double vitamin_a_ug_rae; // 비타민 A (μg RAE)
  private double beta_carotene_ug; // 베타카로틴 (μg)
  private double thiamine_mg; // 티아민 (mg)
  private double riboflavin_mg; // 리보플라빈 (mg)
  private double vitamin_b12_ug; // 비타민 B12 (μg)
  private double vitamin_c_mg; // 비타민 C (mg)
  private double vitamin_d_ug; // 비타민 D (μg)
  private double tocopherol_mg; // 토코페롤 (mg)
  private double tocotrienol_mg; // 토코트리에놀 (mg)
  private double folate_ug_dfe; // 엽산 (μg DFE)
  private double retinol_ug; // 레티놀 (μg)
  // 지방산
  private double saturated_fatty_acids_g; // 포화지방산 (g)
  private double trans_fatty_acids_g; // 트랜스지방산 (g)
  private double cholesterol_mg; // 콜레스테롤 (mg)
  private double linoleic_acid_18_2_n6_g; // 리놀레산 (18:2 n-6) (g)
  private double alpha_linolenic_acid_18_3_n3_g; // 알파리놀렌산 (18:3 n-3) (g)
  private double gamma_linolenic_acid_18_3_n6_mg; // 감마 리놀렌산 (18:3 n-6) (mg)
  private double butyric_acid_4_0_mg; // 부티르산 (4:0) (mg)
  private double caproic_acid_6_0_mg; // 카프로산 (6:0) (mg)
  private double caprylic_acid_8_0_mg; // 카프릴산 (8:0) (mg)
  private double capric_acid_10_0_mg; // 카프르산 (10:0) (mg)
  private double lauric_acid_12_0_mg; // 라우르산 (12:0) (mg)
  private double myristic_acid_14_0_mg; // 미리스트산 (14:0) (mg)
  private double palmitic_acid_16_0_mg; // 팔미트산 (16:0) (mg)
  private double stearic_acid_18_0_mg; // 스테아르산 (18:0) (mg)
  private double arachidic_acid_20_0_mg; // 아라키드산 (20:0) (mg)
  private double myristoleic_acid_14_1_mg; // 미리스톨레산 (14:1) (mg)
  private double palmitoleic_acid_16_1_mg; // 팔미톨레산 (16:1) (mg)
  private double oleic_acid_18_1_n9_mg; // 올레산 (18:1 n-9) (mg)
  private double vaccenic_acid_18_1_n7_mg; // 박센산 (18:1 n-7) (mg)
  private double gadoleic_acid_20_1_n11_mg; // 가돌레산 (20:1 n-11) (mg)
  private double eicosadienoic_acid_20_2_n6_mg; // 에이코사디에노산 (20:2 n-6) (mg)
  private double arachidonic_acid_20_4_n6_mg; // 아라키돈산 (20:4 n-6) (mg)
  private double eicosatrienoic_acid_20_3_n6_mg; // 에이코사트리에노산 (20:3 n-6) (mg)
  private double eicosapentaenoic_acid_20_5_n3_mg; // 에이코사펜타에노산 (20:5 n-3) (mg)
  private double docosapentaenoic_acid_22_5_n3_mg; // 도코사펜타에노산 (22:5 n-3) (mg)
  private double docosahexaenoic_acid_22_6_n3_mg; // 도코사헥사에노산 (22:6 n-3) (mg)
  private double trans_oleic_acid_18_1_trans_n9_mg; // 트랜스 올레산 (18:1 trans n-9) (mg)
  private double trans_linoleic_acid_18_2t_mg; // 트랜스 리놀레산 (18:2t) (mg)
  private double trans_linolenic_acid_18_3t_mg; // 트랜스 리놀렌산 (18:3t) (mg)
  // 아미노산
  private double amino_acids_mg; // 아미노산 (mg)
  private double leucine_mg; // 류신 (mg)
  private double lysine_mg; // 라이신 (mg)
  private double methionine_mg; // 메티오닌 (mg)
  private double phenylalanine_mg; // 페닐알라닌 (mg)
  private double threonine_mg; // 트레오닌 (mg)
  private double isoleucine_mg; // 이소류신 (mg)
  private double valine_mg; // 발린 (mg)
  private double histidine_mg; // 히스티딘 (mg)
  private double arginine_mg; // 아르기닌 (mg)
  private double tyrosine_mg; // 티로신 (mg)
  private double cysteine_mg; // 시스테인 (mg)
  private double alanine_mg; // 알라닌 (mg)
  private double aspartic_acid_mg; // 아스파르트산 (mg)
  private double glutamic_acid_mg; // 글루탐산 (mg)
  private double glycine_mg; // 글리신 (mg)
  private double proline_mg; // 프롤린 (mg)
  private double serine_mg; // 세린 (mg)
  // 기타
  private double caffeine_mg; // 카페인 (mg)

  private String source_code; // 출처 코드
  private String source_name; // 출처명
  private double food_weight; // 식품 중량
  private String company_name; // 업체명
  private String data_creation_method_code; // 데이터 생성 방법 코드
  private String data_creation_method_name; // 데이터 생성 방법명
  private Date data_creation_date; // 데이터 생성 일자 
  private Date data_standards_date; // 데이터기준일자 

  private int page;
  private int offset;
  private int limit;
  private String search_type;
  private String search_value;
}