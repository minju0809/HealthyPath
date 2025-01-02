CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    age INT,
    gender VARCHAR(10),
    weight DECIMAL(5, 2),
    height DECIMAL(5, 1),
    bmi DECIMAL(5, 2),
    classification VARCHAR(20),       -- BMI 분류 (예: 저체중, 정상, 과체중, 비만)
    bmr DECIMAL(6, 2),                -- 기초대사량
    goal DECIMAL(5, 2),               -- 목표 체중
    excluded_foods VARCHAR(255),      -- 제외할 음식
    tdee DECIMAL(6, 2),               -- 총 에너지 소비량 (kcal)
    recommended_carbs DECIMAL(6, 2),  -- 권장 탄수화물 섭취량 (g)
    recommended_protein DECIMAL(6, 2),-- 권장 단백질 섭취량 (g)
    recommended_fats DECIMAL(6, 2),   -- 권장 지방 섭취량 (g)
    activity_level VARCHAR(20),       -- 활동 수준 (운동하지 않음, 가벼운 운동, 보통 운동, 강도 높은 운동, 매우 강도 높음)
    dietary_goal VARCHAR(50),         -- 식단 목표 (다이어트, 유지, 근육 증가)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bmi_records (
    bmi_record_id INT AUTO_INCREMENT PRIMARY KEY, -- 기록 ID (PK, 자동 증가)
    user_id INT NOT NULL,                     -- 사용자 ID (FK, 필수)
    weight DECIMAL(5, 2) NOT NULL,            -- 몸무게 (최대 999.99 kg)
    bmi DECIMAL(5, 2) NOT NULL,               -- BMI 값 (소수점 둘째 자리까지)
    classification VARCHAR(20) NOT NULL,     -- BMI 분류 (최대 20자)
    bmr DECIMAL(6, 2) NOT NULL,               -- 기초대사량 (최대 9999.99)
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- 기록 생성일 (기본 현재 시간)
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS foods (
		idx INT AUTO_INCREMENT PRIMARY KEY, -- PK, 자동 증가
    food_code VARCHAR(50), -- 식품 코드
    food_name VARCHAR(255), -- 식품명
    data_type_code VARCHAR(50), -- 데이터 구분 코드
    data_type_name VARCHAR(255), -- 데이터 구분명
    origin_code VARCHAR(50), -- 식품 기원 코드
    origin_name VARCHAR(255), -- 식품 기원명
    major_category_code VARCHAR(50), -- 식품 대분류 코드
    major_category_name VARCHAR(255), -- 식품 대분류명
    representative_food_code VARCHAR(50), -- 대표 식품 코드
    representative_food_name VARCHAR(255), -- 대표 식품명
    middle_category_code VARCHAR(50), -- 식품 중분류 코드
    middle_category_name VARCHAR(255), -- 식품 중분류명
    minor_category_code VARCHAR(50), -- 식품 소분류 코드
    minor_category_name VARCHAR(255), -- 식품 소분류명
    sub_category_code VARCHAR(50), -- 식품 세분류 코드
    sub_category_name VARCHAR(255), -- 식품 세분류명
    nutrient_reference_amount DECIMAL(10, 2) null, -- 영양성분 함량 기준량
    energy_kcal DECIMAL(10, 2) null, -- 에너지 (kcal)
    moisture_g DECIMAL(10, 2) null, -- 수분 (g)
    protein_g DECIMAL(10, 2) null, -- 단백질 (g)
    fat_g DECIMAL(10, 2) null, -- 지방 (g)
    ash_g DECIMAL(10, 2) null, -- 회분 (g)
    carbohydrate_g DECIMAL(10, 2) null, -- 탄수화물 (g)
    sugar_g DECIMAL(10, 2) null, -- 당류 (g)
    dietary_fiber_g DECIMAL(10, 2) null, -- 식이섬유 (g)
    calcium_mg DECIMAL(10, 2) null, -- 칼슘 (mg)
    iron_mg DECIMAL(10, 2) null, -- 철 (mg)
    phosphorus_mg DECIMAL(10, 2) null, -- 인 (mg)
    potassium_mg DECIMAL(10, 2) null, -- 칼륨 (mg)
    sodium_mg DECIMAL(10, 2) null, -- 나트륨 (mg)
    vitamin_a_ug_rae DECIMAL(10, 2) null, -- 비타민 A (μg RAE)
    retinol_ug DECIMAL(10, 2) null, -- 레티놀 (μg)
    beta_carotene_ug DECIMAL(10, 2) null, -- 베타카로틴 (μg)
    thiamine_mg DECIMAL(10, 2) null, -- 티아민 (mg)
    riboflavin_mg DECIMAL(10, 2) null, -- 리보플라빈 (mg)
    niacin_mg DECIMAL(10, 2) null, -- 니아신 (mg)
    vitamin_c_mg DECIMAL(10, 2) null, -- 비타민 C (mg)
    vitamin_d_ug DECIMAL(10, 2) null, -- 비타민 D (μg)
    cholesterol_mg DECIMAL(10, 2) null, -- 콜레스테롤 (mg)
    saturated_fatty_acids_g DECIMAL(10, 2) null, -- 포화지방산 (g)
    trans_fatty_acids_g DECIMAL(10, 2) null, -- 트랜스지방산 (g)
    sucrose_g DECIMAL(10, 2) null, -- 자당 (g)
    glucose_g DECIMAL(10, 2) null, -- 포도당 (g)
    fructose_g DECIMAL(10, 2) null, -- 과당 (g)
    lactose_g DECIMAL(10, 2) null, -- 유당 (g)
    maltose_g DECIMAL(10, 2) null, -- 맥아당 (g)
    magnesium_mg DECIMAL(10, 2) null, -- 마그네슘 (mg)
    zinc_mg DECIMAL(10, 2) null, -- 아연 (mg)
    copper_ug DECIMAL(10, 2) null, -- 구리 (μg)
    manganese_mg DECIMAL(10, 2) null, -- 망간 (mg)
    selenium_ug DECIMAL(10, 2) null, -- 셀레늄 (μg)
    tocopherol_mg DECIMAL(10, 2) null, -- 토코페롤 (mg)
    tocotrienol_mg DECIMAL(10, 2) null, -- 토코트리에놀 (mg)
    folate_ug_dfe DECIMAL(10, 2) null, -- 엽산 (μg DFE)
    vitamin_b12_ug DECIMAL(10, 2) null, -- 비타민 B12 (μg)
    amino_acids_mg DECIMAL(10, 2) null, -- 아미노산 (mg)
    isoleucine_mg DECIMAL(10, 2) null, -- 이소류신 (mg)
    leucine_mg DECIMAL(10, 2) null, -- 류신 (mg)
    lysine_mg DECIMAL(10, 2) null, -- 라이신 (mg)
    methionine_mg DECIMAL(10, 2) null, -- 메티오닌 (mg)
    phenylalanine_mg DECIMAL(10, 2) null, -- 페닐알라닌 (mg)
    threonine_mg DECIMAL(10, 2) null, -- 트레오닌 (mg)
    valine_mg DECIMAL(10, 2) null, -- 발린 (mg)
    histidine_mg DECIMAL(10, 2) null, -- 히스티딘 (mg)
    arginine_mg DECIMAL(10, 2) null, -- 아르기닌 (mg)
    tyrosine_mg DECIMAL(10, 2) null, -- 티로신 (mg)
    cysteine_mg DECIMAL(10, 2) null, -- 시스테인 (mg)
    alanine_mg DECIMAL(10, 2) null, -- 알라닌 (mg)
    aspartic_acid_mg DECIMAL(10, 2) null, -- 아스파르트산 (mg)
    glutamic_acid_mg DECIMAL(10, 2) null, -- 글루탐산 (mg)
    glycine_mg DECIMAL(10, 2) null, -- 글리신 (mg)
    proline_mg DECIMAL(10, 2) null, -- 프롤린 (mg)
    serine_mg DECIMAL(10, 2) null, -- 세린 (mg)
    butyric_acid_4_0_mg DECIMAL(10, 2) null, -- 부티르산 (4:0) (mg)
    caproic_acid_6_0_mg DECIMAL(10, 2) null, -- 카프로산 (6:0) (mg)
    caprylic_acid_8_0_mg DECIMAL(10, 2) null, -- 카프릴산 (8:0) (mg)
    capric_acid_10_0_mg DECIMAL(10, 2) null, -- 카프르산 (10:0) (mg)
    lauric_acid_12_0_mg DECIMAL(10, 2) null, -- 라우르산 (12:0) (mg)
    myristic_acid_14_0_mg DECIMAL(10, 2) null, -- 미리스트산 (14:0) (mg)
    palmitic_acid_16_0_mg DECIMAL(10, 2) null, -- 팔미트산 (16:0) (mg)
    stearic_acid_18_0_mg DECIMAL(10, 2) null, -- 스테아르산 (18:0) (mg)
    arachidic_acid_20_0_mg DECIMAL(10, 2) null, -- 아라키드산 (20:0) (mg)
    myristoleic_acid_14_1_mg DECIMAL(10, 2) null, -- 미리스톨레산 (14:1) (mg)
    palmitoleic_acid_16_1_mg DECIMAL(10, 2) null, -- 팔미톨레산 (16:1) (mg)
    oleic_acid_18_1_n9_mg DECIMAL(10, 2) null, -- 올레산 (18:1 n-9) (mg)
    vaccenic_acid_18_1_n7_mg DECIMAL(10, 2) null, -- 박센산 (18:1 n-7) (mg)
    gadoleic_acid_20_1_n11_mg DECIMAL(10, 2) null, -- 가돌레산 (20:1 n-11) (mg)
    linoleic_acid_18_2_n6_g DECIMAL(10, 2) null, -- 리놀레산 (18:2 n-6) (g)
    alpha_linolenic_acid_18_3_n3_g DECIMAL(10, 2) null, -- 알파리놀렌산 (18:3 n-3) (g)
    gamma_linolenic_acid_18_3_n6_mg DECIMAL(10, 2) null, -- 감마 리놀렌산 (18:3 n-6) (mg)
    eicosadienoic_acid_20_2_n6_mg DECIMAL(10, 2) null, -- 에이코사디에노산 (20:2 n-6) (mg)
    arachidonic_acid_20_4_n6_mg DECIMAL(10, 2) null, -- 아라키돈산 (20:4 n-6) (mg)
    eicosatrienoic_acid_20_3_n6_mg DECIMAL(10, 2) null, -- 에이코사트리에노산 (20:3 n-6) (mg)
    eicosapentaenoic_acid_20_5_n3_mg DECIMAL(10, 2) null, -- 에이코사펜타에노산 (20:5 n-3) (mg)
    docosapentaenoic_acid_22_5_n3_mg DECIMAL(10, 2) null, -- 도코사펜타에노산 (22:5 n-3) (mg)
    docosahexaenoic_acid_22_6_n3_mg DECIMAL(10, 2) null, -- 도코사헥사에노산 (22:6 n-3) (mg)
    trans_oleic_acid_18_1_trans_n9_mg DECIMAL(10, 2) null, -- 트랜스 올레산 (18:1 trans n-9) (mg)
    trans_linoleic_acid_18_2t_mg DECIMAL(10, 2) null, -- 트랜스 리놀레산 (18:2t) (mg)
    trans_linolenic_acid_18_3t_mg DECIMAL(10, 2) null, -- 트랜스 리놀렌산 (18:3t) (mg)
    caffeine_mg DECIMAL(10, 2) null, -- 카페인 (mg)
    source_code VARCHAR(50), -- 출처 코드
    source_name VARCHAR(255), -- 출처명
    food_weight DECIMAL(10, 2) null, -- 식품 중량
    company_name VARCHAR(255), -- 업체명
    data_creation_method_code VARCHAR(50), -- 데이터 생성 방법 코드
    data_creation_method_name VARCHAR(255), -- 데이터 생성 방법명
    data_creation_date DATE, -- 데이터 생성 일자 
    data_standards_date DATE -- 데이터기준일자 
);

CREATE TABLE IF NOT EXISTS recipes (
    rcp_sno INT(40) NOT NULL, -- 레시피일련번호 (Primary Key)
    rcp_ttl VARCHAR(200) NOT NULL, -- 레시피제목
    ckg_nm VARCHAR(40), -- 요리명
    rgtr_id VARCHAR(32), -- 등록자ID
    rgtr_nm VARCHAR(64), -- 등록자명
    inq_cnt INT(11) DEFAULT 0, -- 조회수
    rcmm_cnt INT(11) DEFAULT 0, -- 추천수
    srap_cnt INT(11) DEFAULT 0, -- 스크랩수
    ckg_mth_acto_nm VARCHAR(200), -- 요리방법별명
    ckg_sta_acto_nm VARCHAR(200), -- 요리상황별명
    ckg_mtrl_acto_nm VARCHAR(200), -- 요리재료별명
    ckg_knd_acto_nm VARCHAR(200), -- 요리종류별명
    ckg_ipdc VARCHAR(4000), -- 요리소개
    ckg_mtrl_cn VARCHAR(4000), -- 요리재료내용
    ckg_inbun_nm VARCHAR(200), -- 요리인분명
    ckg_dodf_nm VARCHAR(200), -- 요리난이도명
    ckg_time_nm VARCHAR(200), -- 요리시간명
    first_reg_dt CHAR(14), -- 최초등록일시
    PRIMARY KEY (rcp_sno) -- 기본 키
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS food_recommendations (
    recommendation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    idx INT NOT NULL,
    food_name VARCHAR(255) NOT NULL,
    major_category_name VARCHAR(255) NOT NULL,
    energy_kcal FLOAT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_foods FOREIGN KEY (idx) REFERENCES foods(idx) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_users_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS daily_meals (
    meal_id INT AUTO_INCREMENT PRIMARY KEY, -- 식단 기록 ID
    user_id INT NOT NULL, -- 사용자 ID (users 테이블 참조)
    idx INT NULL,
    date DATE NOT NULL, -- 식단 기록 날짜
    meal_time VARCHAR(50) NOT NULL, -- 식사 시간 (아침, 점심, 저녁)
    food_name VARCHAR(255) NOT NULL, -- 선택한 음식 이름
    major_category_name VARCHAR(255), -- 식품 대분류명
    nutrient_reference_amount DECIMAL(10, 2) DEFAULT 100, -- 영양성분 함량 기준량
    energy_kcal FLOAT NOT NULL, -- 음식의 칼로리
    protein_g DOUBLE NOT NULL DEFAULT 0, -- 총 단백질
		fat_g DOUBLE NOT NULL DEFAULT 0, -- 총 지방
		carbohydrate_g DOUBLE NOT NULL DEFAULT 0, -- 총 탄수화물
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- 기록 생성일
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 기록 업데이트일
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idx) REFERENCES foods(idx) ON DELETE CASCADE ON UPDATE CASCADE
);