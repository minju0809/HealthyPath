package com.springboot.healthypath.model;

import lombok.Data;

@Data
public class RecipeVO {
  private int rcp_sno; // 레시피일련번호
  private String rcp_ttl; // 레시피제목
  private String ckg_nm; // 요리명
  private String rgtr_id; // 등록자ID
  private String rgtr_nm; // 등록자명
  private int inq_cnt; // 조회수
  private int rcmm_cnt; // 추천수
  private int srap_cnt; // 스크랩수
  private String ckg_mth_acto_nm; // 요리방법별명 ---
  private String ckg_sta_acto_nm; // 요리상황별명
  private String ckg_mtrl_acto_nm; // 요리재료별명
  private String ckg_knd_acto_nm; // 요리종류별명 ---
  private String ckg_ipdc; // 요리소개
  private String ckg_mtrl_cn; // 요리재료내용
  private String ckg_inbun_nm; // 요리인분명
  private String ckg_dodf_nm; // 요리난이도명
  private String ckg_time_nm; // 요리시간명
  private String first_reg_dt; // 최초등록일시

  private int page;
  private int offset;
  private int limit;
  private int page_size;
  private String search_category;
  private String included_foods;
  private String excluded_foods;
}
