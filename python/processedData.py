import pandas as pd  # type: ignore
import re

# 엑셀 파일을 읽고 데이터를 반환하는 함수
def process_excel():
    try:
        excel_file_path = "20240807_음식DB.xlsx"
        df = pd.read_excel(excel_file_path)
        
        columns = df.columns.tolist()
        print(f"컬럼의 갯수: {len(columns)}")
        
        # 컬럼 이름에서 공백 제거
        df.columns = df.columns.str.strip()

        return {"result": "success", "columns": columns, "df": df}  
    except Exception as e:
        print(f"Error: {e}")
        return {"result": "failure", "error": str(e)}

column_mapping = {
    '식품코드': 'food_code',
    '식품명': 'food_name',
    '데이터구분코드': 'data_type_code',
    '데이터구분명': 'data_type_name',
    '식품기원코드': 'origin_code',
    '식품기원명': 'origin_name',
    '식품대분류코드': 'major_category_code',
    '식품대분류명': 'major_category_name',
    '대표식품코드': 'representative_food_code',
    '대표식품명': 'representative_food_name',
    '식품중분류코드': 'middle_category_code',
    '식품중분류명': 'middle_category_name',
    '식품소분류코드': 'minor_category_code',
    '식품소분류명': 'minor_category_name',
    '식품세분류코드': 'sub_category_code',
    '식품세분류명': 'sub_category_name',
    '영양성분함량기준량': 'nutrient_reference_amount',
    '에너지(kcal)': 'energy_kcal',
    '수분(g)': 'moisture_g',
    '단백질(g)': 'protein_g',
    '지방(g)': 'fat_g',
    '회분(g)': 'ash_g',
    '탄수화물(g)': 'carbohydrate_g',
    '당류(g)': 'sugar_g',
    '식이섬유(g)': 'dietary_fiber_g',
    '칼슘(mg)': 'calcium_mg',
    '철(mg)': 'iron_mg',
    '인(mg)': 'phosphorus_mg',
    '칼륨(mg)': 'potassium_mg',
    '나트륨(mg)': 'sodium_mg',
    '비타민 A(μg RAE)': 'vitamin_a_ug_rae',
    '레티놀(μg)': 'retinol_ug',
    '베타카로틴(μg)': 'beta_carotene_ug',
    '티아민(mg)': 'thiamine_mg',
    '리보플라빈(mg)': 'riboflavin_mg',
    '니아신(mg)': 'niacin_mg',
    '비타민 C(mg)': 'vitamin_c_mg',
    '비타민 D(μg)': 'vitamin_d_ug',
    '콜레스테롤(mg)': 'cholesterol_mg',
    '포화지방산(g)': 'saturated_fatty_acids_g',
    '트랜스지방산(g)': 'trans_fatty_acids_g',
    '자당(g)': 'sucrose_g',
    '포도당(g)': 'glucose_g',
    '과당(g)': 'fructose_g',
    '유당(g)': 'lactose_g',
    '맥아당(g)': 'maltose_g',
    '마그네슘(mg)': 'magnesium_mg',
    '아연(mg)': 'zinc_mg',
    '구리(μg)': 'copper_ug',
    '망간(mg)': 'manganese_mg',
    '셀레늄(μg)': 'selenium_ug',
    '토코페롤(mg)': 'tocopherol_mg',
    '토코트리에놀(mg)': 'tocotrienol_mg',
    '엽산(μg DFE)': 'folate_ug_dfe',
    '비타민 B12(μg)': 'vitamin_b12_ug',
    '아미노산(mg)': 'amino_acids_mg',
    '이소류신 / 이소루신(mg)': 'isoleucine_mg',
    '류신 / 루신(mg)': 'leucine_mg',
    '라이신(mg)': 'lysine_mg',
    '메티오닌(mg)': 'methionine_mg',
    '페닐알라닌(mg)': 'phenylalanine_mg',
    '트레오닌(mg)': 'threonine_mg',
    '발린(mg)': 'valine_mg',
    '히스티딘(mg)': 'histidine_mg',
    '아르기닌(mg)': 'arginine_mg',
    '티로신(mg)': 'tyrosine_mg',
    '시스테인(mg)': 'cysteine_mg',
    '알라닌(mg)': 'alanine_mg',
    '아스파르트산(mg)': 'aspartic_acid_mg',
    '글루탐산(mg)': 'glutamic_acid_mg',
    '글리신(mg)': 'glycine_mg',
    '프롤린(mg)': 'proline_mg',
    '세린(mg)': 'serine_mg',
    '부티르산(4:0)(mg)': 'butyric_acid_4_0_mg',
    '카프로산(6:0)(mg)': 'caproic_acid_6_0_mg',
    '카프릴산(8:0)(mg)': 'caprylic_acid_8_0_mg',
    '카프르산(10:0)(mg)': 'capric_acid_10_0_mg',
    '라우르산(12:0)(mg)': 'lauric_acid_12_0_mg',
    '미리스트산(14:0)(mg)': 'myristic_acid_14_0_mg',
    '팔미트산(16:0)(mg)': 'palmitic_acid_16_0_mg',
    '스테아르산(18:0)(mg)': 'stearic_acid_18_0_mg',
    '아라키드산(20:0)(mg)': 'arachidic_acid_20_0_mg',
    '미리스톨레산(14:1)(mg)': 'myristoleic_acid_14_1_mg',
    '팔미톨레산(16:1)(mg)': 'palmitoleic_acid_16_1_mg',
    '올레산(18:1 n-9)(mg)': 'oleic_acid_18_1_n9_mg',
    '박센산(18:1 n-7)(mg)': 'vaccenic_acid_18_1_n7_mg',
    '가돌레산(20:1 n-11) / 에이코센산(20:1 n-9)(mg)': 'gadoleic_acid_20_1_n11_mg',
    '리놀레산(18:2 n-6)(g)': 'linoleic_acid_18_2_n6_g',
    '알파리놀렌산(18:3 n-3)(g)': 'alpha_linolenic_acid_18_3_n3_g',
    '감마 리놀렌산(18:3 n-6)(mg)': 'gamma_linolenic_acid_18_3_n6_mg',
    '에이코사디에노산(20:2 n-6)(mg)': 'eicosadienoic_acid_20_2_n6_mg',
    '아라키돈산(20:4 n-6)(mg)': 'arachidonic_acid_20_4_n6_mg',
    '에이코사트리에노산(20:3 n-6)(mg)': 'eicosatrienoic_acid_20_3_n6_mg',
    '에이코사펜타에노산(EPA, 20:5 n-3)(mg)': 'eicosapentaenoic_acid_20_5_n3_mg',
    '도코사펜타에노산(DPA, 22:5 n-3)(mg)': 'docosapentaenoic_acid_22_5_n3_mg',
    '도코사헥사에노산(DHA, 22:6 n-3)(mg)': 'docosahexaenoic_acid_22_6_n3_mg',
    '트랜스 올레산(18:1 trans n-9)(mg)': 'trans_oleic_acid_18_1_trans_n9_mg',
    '트랜스 리놀레산(18:2t)(mg)': 'trans_linoleic_acid_18_2t_mg',
    '트랜스 리놀렌산(18:3t)(mg)': 'trans_linolenic_acid_18_3t_mg',
    '카페인(mg)': 'caffeine_mg',
    '출처코드': 'source_code',
    '출처명': 'source_name',
    '식품중량': 'food_weight',
    '업체명': 'company_name',
    '데이터생성방법코드': 'data_creation_method_code',
    '데이터생성방법명': 'data_creation_method_name',
    '데이터생성일자': 'data_creation_date',
    '데이터기준일자': 'data_standards_date'
}

def clean_data(df):
    # 각 컬럼에 대해 처리 (food_name, middle_category_name, minor_category_name, sub_category_name 컬럼은 제외)
    for column in df.columns:
        if column not in ['food_name', 'middle_category_name', 'minor_category_name', 'sub_category_name']:  # 제외할 컬럼들
            df[column] = df[column].apply(
                lambda x: (
                    # 숫자와 '.'만 추출하고, 'g', 'mg', 'μg', 'ml', 'm'을 제외한 후, 소수점 둘째 자리까지 표현
                    round(float(re.sub(r'(g|mg|μg|ml|m)', '', str(x)).strip()), 2)
                    if isinstance(x, str) and any(unit in str(x) for unit in ['g', 'mg', 'μg', 'ml', 'm'])
                    else x
                )
            )

    # 'food_code' 컬럼을 제외한 나머지 컬럼에서 '-' 처리
    for column in df.columns:
        if column != 'food_code':  # 'food_code'를 제외한 모든 컬럼에 대해 처리
            df[column] = df[column].apply(
                lambda x: 0 if x == '-' or pd.isna(x) else x
            )
        
    # NaN 값을 0으로 변환 (필요에 따라 생략 가능)
    df = df.fillna(0)

    return df

result = process_excel()

if result['result'] == 'success':
    df = result['df']
    df.rename(columns=column_mapping, inplace=True)
    df = clean_data(df)
    df.to_excel("processed_data.xlsx", index=False)
    print("데이터가 처리되고 저장되었습니다.")
else:
    print(f"에러 발생: {result['error']}")