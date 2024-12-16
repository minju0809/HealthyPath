import pymysql # type: ignore
import csv # type: ignore

connection = pymysql.connect(
    host='localhost',   # Update with your host
    user='root',        # Update with your username
    password='root',    # Update with your password
    database='healthy_path'  # Update with your database name
)

# 테이블에 데이터를 삽입하는 함수
def insert_data_in_batches(csv_file_path, batch_size=1000):
# def insert_data_in_batches(csv_file_path, batch_size=1000, start_row=125000):
    try:
        with connection.cursor() as cursor:
            with open(csv_file_path, mode='r', encoding='cp949', errors='ignore') as csvfile:
                reader = csv.DictReader(csvfile)
                
                # Insert 쿼리 작성
                insert_query = (
                    "INSERT INTO recipes (rcp_sno, rcp_ttl, ckg_nm, rgtr_id, rgtr_nm, inq_cnt, rcmm_cnt, srap_cnt, "
                    "ckg_mth_acto_nm, ckg_sta_acto_nm, ckg_mtrl_acto_nm, ckg_knd_acto_nm, ckg_ipdc, ckg_mtrl_cn, "
                    "ckg_inbun_nm, ckg_dodf_nm, ckg_time_nm, first_reg_dt) "
                    "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
                )

                batch = []
                row_count = 0
                # skipped_rows = 0
                
                # # 먼저, 지정된 시작 행까지 건너뛰기
                # for _ in range(start_row):
                #     next(reader)  # 해당 행을 건너뜀
                #     skipped_rows += 1

                for row in reader:
                    # 데이터 추가
                    batch.append((
                        int(row['RCP_SNO']),
                        row['RCP_TTL'],
                        row['CKG_NM'],
                        row['RGTR_ID'],
                        row['RGTR_NM'],
                        int(row['INQ_CNT']),
                        int(row['RCMM_CNT']),
                        int(row['SRAP_CNT']),
                        row['CKG_MTH_ACTO_NM'],
                        row['CKG_STA_ACTO_NM'],
                        row['CKG_MTRL_ACTO_NM'],
                        row['CKG_KND_ACTO_NM'],
                        row['CKG_IPDC'],
                        row['CKG_MTRL_CN'],
                        row['CKG_INBUN_NM'],
                        row['CKG_DODF_NM'],
                        row['CKG_TIME_NM'],
                        row['FIRST_REG_DT']
                    ))

                    row_count += 1

                    # 배치 크기 도달 시 실행
                    if len(batch) == batch_size:
                        try:
                            cursor.executemany(insert_query, batch)
                            connection.commit()
                            print(f"Successfully inserted {row_count} rows so far.")
                        except Exception as e:
                            print(f"Error at row {row_count}: {e}")
                            connection.rollback()
                        batch = []  # 배치 초기화

                # 남은 데이터 삽입
                if batch:
                    try:
                        cursor.executemany(insert_query, batch)
                        connection.commit()
                        print(f"Successfully inserted remaining {len(batch)} rows.")
                    except Exception as e:
                        print(f"Error at final batch ending at row {row_count}: {e}")
                        connection.rollback()

    except Exception as e:
        print(f"Unexpected error: {e}")

    finally:
        connection.close()
        
# CSV 파일 경로
csv_file_path = 'recipe_20231130.csv'

# 데이터 삽입 실행
insert_data_in_batches(csv_file_path)
# insert_data_in_batches(csv_file_path, start_row=125000)
