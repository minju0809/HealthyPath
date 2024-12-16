import pandas as pd  # type: ignore
import pymysql  # type: ignore
import time

connection = pymysql.connect(
    host='localhost',   # Update with your host
    user='root',        # Update with your username
    password='root',    # Update with your password
    database='healthy_path'  # Update with your database name
)

cursor = connection.cursor()

# 엑셀 파일을 읽고 데이터를 반환하는 함수
def process_excel():
    try:
        excel_file_path = "processed_data.xlsx"
        df = pd.read_excel(excel_file_path)
        
        columns = df.columns.tolist()
        # print(f"컬럼: {columns}")
        # 컬럼 이름에서 공백 제거
        # df.columns = df.columns.str.strip()

        return {"result": "success", "columns": columns, "df": df}  
    except Exception as e:
        print(f"Error: {e}")
        return {"result": "failure", "error": str(e)}
    
# 컬럼 2개만 insert
def insert_data(df, batch_size=1000, delay=2):
    # Total rows (you can adjust this as needed)
    total_rows = len(df)

    # Insert the data in batches
    for start_row in range(0, total_rows, batch_size):
        end_row = min(start_row + batch_size, total_rows)
        batch_df = df[start_row:end_row]
        # print(batch_df.columns)
        # print(df['nutrient_reference_amount'].head())

        values = [tuple(row) for row in batch_df[['food_code', 'food_name', 'data_type_code', 'data_type_name', 'origin_code',
            'origin_name', 'major_category_code', 'major_category_name', 'representative_food_code', 'representative_food_name', 
            'middle_category_code', 'middle_category_name', 'minor_category_code', 'minor_category_name', 'sub_category_code', 
            'sub_category_name', 'nutrient_reference_amount', 'energy_kcal', 'moisture_g', 'protein_g', 
            'fat_g', 'ash_g', 'carbohydrate_g', 'sugar_g', 'dietary_fiber_g', 
            'calcium_mg', 'iron_mg', 'phosphorus_mg', 'potassium_mg', 'sodium_mg', 
            'vitamin_a_ug_rae', 'retinol_ug', 'beta_carotene_ug', 'thiamine_mg', 'riboflavin_mg', 
            'niacin_mg', 'vitamin_c_mg', 'vitamin_d_ug', 'cholesterol_mg', 'saturated_fatty_acids_g', 
            'trans_fatty_acids_g', 'sucrose_g', 'glucose_g', 'fructose_g', 'lactose_g',
            'maltose_g', 'magnesium_mg', 'zinc_mg', 'copper_ug', 'manganese_mg', 
            'selenium_ug', 'tocopherol_mg', 'tocotrienol_mg', 'folate_ug_dfe', 'vitamin_b12_ug', 
            'amino_acids_mg', 'isoleucine_mg', 'leucine_mg', 'lysine_mg', 'methionine_mg', 
            'phenylalanine_mg', 'threonine_mg', 'valine_mg', 'histidine_mg', 'arginine_mg', 
            'tyrosine_mg', 'cysteine_mg', 'alanine_mg', 'aspartic_acid_mg', 'glutamic_acid_mg',
            'glycine_mg', 'proline_mg', 'serine_mg', 'butyric_acid_4_0_mg', 'caproic_acid_6_0_mg', 
            'caprylic_acid_8_0_mg', 'capric_acid_10_0_mg', 'lauric_acid_12_0_mg', 'myristic_acid_14_0_mg', 'palmitic_acid_16_0_mg', 
            'stearic_acid_18_0_mg', 'arachidic_acid_20_0_mg', 'myristoleic_acid_14_1_mg', 'palmitoleic_acid_16_1_mg', 'oleic_acid_18_1_n9_mg', 
            'vaccenic_acid_18_1_n7_mg', 'gadoleic_acid_20_1_n11_mg', 'linoleic_acid_18_2_n6_g', 'alpha_linolenic_acid_18_3_n3_g', 'gamma_linolenic_acid_18_3_n6_mg', 
            'eicosadienoic_acid_20_2_n6_mg', 'arachidonic_acid_20_4_n6_mg', 'eicosatrienoic_acid_20_3_n6_mg', 'eicosapentaenoic_acid_20_5_n3_mg', 'docosapentaenoic_acid_22_5_n3_mg', 
            'docosahexaenoic_acid_22_6_n3_mg', 'trans_oleic_acid_18_1_trans_n9_mg', 'trans_linoleic_acid_18_2t_mg', 'trans_linolenic_acid_18_3t_mg', 'caffeine_mg', 
            'source_code', 'source_name', 'food_weight', 'company_name', 'data_creation_method_code',
            'data_creation_method_name', 'data_creation_date', 'data_standards_date']].itertuples(index=False, name=None)] 
        # print(values)
        
        insert_query = """
        INSERT INTO foods (
            food_code, food_name, data_type_code, data_type_name, origin_code, 
            origin_name, major_category_code, major_category_name, representative_food_code, representative_food_name,
            middle_category_code, middle_category_name, minor_category_code, minor_category_name, sub_category_code, 
            sub_category_name, nutrient_reference_amount, energy_kcal, moisture_g, protein_g, 
            fat_g, ash_g, carbohydrate_g, sugar_g, dietary_fiber_g, 
            calcium_mg, iron_mg, phosphorus_mg, potassium_mg, sodium_mg, 
            vitamin_a_ug_rae, retinol_ug, beta_carotene_ug, thiamine_mg, riboflavin_mg, 
            niacin_mg, vitamin_c_mg, vitamin_d_ug, cholesterol_mg, saturated_fatty_acids_g, 
            trans_fatty_acids_g, sucrose_g, glucose_g, fructose_g, lactose_g,
            maltose_g, magnesium_mg, zinc_mg, copper_ug, manganese_mg, 
            selenium_ug, tocopherol_mg, tocotrienol_mg, folate_ug_dfe, vitamin_b12_ug, 
            amino_acids_mg, isoleucine_mg, leucine_mg, lysine_mg, methionine_mg, 
            phenylalanine_mg, threonine_mg, valine_mg, histidine_mg, arginine_mg, 
            tyrosine_mg, cysteine_mg, alanine_mg, aspartic_acid_mg, glutamic_acid_mg,
            glycine_mg, proline_mg, serine_mg, butyric_acid_4_0_mg, caproic_acid_6_0_mg, 
            caprylic_acid_8_0_mg, capric_acid_10_0_mg, lauric_acid_12_0_mg, myristic_acid_14_0_mg, palmitic_acid_16_0_mg, 
            stearic_acid_18_0_mg, arachidic_acid_20_0_mg, myristoleic_acid_14_1_mg, palmitoleic_acid_16_1_mg, oleic_acid_18_1_n9_mg, 
            vaccenic_acid_18_1_n7_mg, gadoleic_acid_20_1_n11_mg, linoleic_acid_18_2_n6_g, alpha_linolenic_acid_18_3_n3_g, gamma_linolenic_acid_18_3_n6_mg, 
            eicosadienoic_acid_20_2_n6_mg, arachidonic_acid_20_4_n6_mg, eicosatrienoic_acid_20_3_n6_mg, eicosapentaenoic_acid_20_5_n3_mg, docosapentaenoic_acid_22_5_n3_mg, 
            docosahexaenoic_acid_22_6_n3_mg, trans_oleic_acid_18_1_trans_n9_mg, trans_linoleic_acid_18_2t_mg, trans_linolenic_acid_18_3t_mg, caffeine_mg, 
            source_code, source_name, food_weight, company_name, data_creation_method_code,
            data_creation_method_name, data_creation_date, data_standards_date
        ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
                %s, %s, %s, %s, %s, %s, %s, %s) 
        """
        
        # Execute the insert query with the values for this batch
        cursor.executemany(insert_query, values)
        
        # Commit the transaction
        connection.commit()

        # Print progress and wait for the delay
        print(f"Inserted rows {start_row + 1} to {end_row}")
        time.sleep(delay)

# major_category_name 출력 함수
def print_major_category_names(df):
    try:
        if 'major_category_name' in df.columns:
            print("Major Category Names (Unique):")
            print(df['major_category_name'].drop_duplicates().to_string(index=False))
        else:
            print("Error: 'major_category_name' column not found in the DataFrame.")
    except Exception as e:
        print(f"Error while printing major_category_name: {e}")

# 메인 실행 함수
if __name__ == "__main__":
    result = process_excel()  # 엑셀 파일을 처리
    if result["result"] == "success":
        print("Python 실행 성공!")
        df = result["df"]  # 데이터프레임 가져오기
        # insert_data(df)
        print_major_category_names(df)
        # 밥류 
        # 빵 및 과자류 
        # 면 및 만두류 
        # 죽 및 스프류 
        # 국 및 탕류 
        # 찌개 및 전골류 
        # 찜류 
        # 구이류 
        # 전·적 및 부침류 
        # 볶음류 
        # 조림류 
        # 튀김류 
        # 나물·숙채류 
        # 생채·무침류 
        # 김치류 
        # 젓갈류 
        # 장아찌·절임류 
        # 음료 및 차류 
        # 수·조·어·육류 
        # 장류, 양념류 
        # 유제품류 및 빙과류 
        # 과일류 
        # 두류, 견과 및 종실류 곡류, 서류 제품
    else:
        print("Python 실행 실패!")

# 연결 종료
cursor.close()
connection.close()