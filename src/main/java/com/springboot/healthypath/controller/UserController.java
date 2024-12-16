package com.springboot.healthypath.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.healthypath.user.BmiRecordVO;
import com.springboot.healthypath.user.UserService;
import com.springboot.healthypath.user.UserVO;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
  Dotenv dotenv = Dotenv.load();

  private final String googleClientId = dotenv.get("GOOGLE_CLIENT_ID");
  private final String googleClientSecret = dotenv.get("GOOGLE_CLIENT_SECRET");
  private final String googleRedirectUri = dotenv.get("GOOGLE_REDIRECT_URI");
  private final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
  private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
  private final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

  @Autowired
  private UserService userService;

  // 구글 로그인
  @GetMapping({ "/signup", "/signin" })
  public String googleSignup() {
    // 구글 로그인 URL 생성
    String googleLoginUrl = GOOGLE_AUTH_URL
        + "?client_id=" + googleClientId
        + "&redirect_uri=" + googleRedirectUri
        + "&response_type=code"
        + "&scope=email%20profile";

    return "redirect:" + googleLoginUrl; // 구글 로그인 페이지로 리다이렉트
  }

  // 구글 로그인 성공 처리
  @GetMapping("/auth/google")
  public String googleAuth(@RequestParam("code") String code, HttpSession session) {
    try {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));

      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("code", code);
      body.add("client_id", googleClientId);
      body.add("client_secret", googleClientSecret);
      body.add("redirect_uri", googleRedirectUri);
      body.add("grant_type", "authorization_code");

      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

      ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(GOOGLE_TOKEN_URL, request, Map.class);
      Map<String, Object> tokenData = tokenResponse.getBody();

      if (tokenData == null || !tokenData.containsKey("access_token")) {
        throw new RuntimeException("Failed to retrieve access token");
      }

      String accessToken = (String) tokenData.get("access_token");

      // 2. 사용자 정보 요청
      HttpHeaders userInfoHeaders = new HttpHeaders();
      userInfoHeaders.setBearerAuth(accessToken); // Authorization 헤더에 토큰 추가

      HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);

      ResponseEntity<String> userInfoResponse = restTemplate.exchange(
          GOOGLE_USER_INFO_URL, HttpMethod.GET, userInfoRequest, String.class);

      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> userInfo = mapper.readValue(userInfoResponse.getBody(), Map.class);

      // 사용자 정보 파싱
      String email = (String) userInfo.get("email");
      String name = (String) userInfo.get("name");

      System.out.println("email: " + email + ", name: " + name);

      // - 진입 시도 시 users 테이블에 해당 이메일 주소가 있는지 확인
      UserVO vo = new UserVO();
      vo.setEmail(email);
      vo.setName(name);
      UserVO user = userService.getUser(vo);

      if (user == null) {
        System.out.println("User Info: " + name + " (" + email + ")");
        session.setAttribute("user", vo);

        userService.insertUserEmailAndName(vo);

        return "user/updateUserForm";

      } else {
        session.setAttribute("user", user);

        return "redirect:/"; // 로그인 후 메인 페이지로 리다이렉트
      }
    } catch (

    Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Google login failed");
    }
  }

  @PostMapping("/updateUser")
  public String updateUser(UserVO vo, HttpSession session) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");

    if (sessionUser != null) {
      sessionUser.setName(vo.getName());
      sessionUser.setAge(vo.getAge());
      sessionUser.setGender(vo.getGender());
      sessionUser.setWeight(vo.getWeight());
      sessionUser.setHeight(vo.getHeight());
      sessionUser.setBmi(Math.round(vo.getBmi() * 100.0) / 100.0); // 소수점 둘째 자리까지 반올림
      sessionUser.setClassification(vo.getClassification());
      sessionUser.setBmr(Math.round(vo.getBmr() * 100.0) / 100.0); // 소수점 둘째 자리까지 반올림
      sessionUser.setTdee(Math.round(vo.getTdee() * 100.0) / 100.0); // 소수점 둘째 자리까지 반올림
      sessionUser.setActivity_level(vo.getActivity_level());
      sessionUser.setRecommended_carbs(Math.round(vo.getRecommended_carbs() * 100.0) / 100.0); // 소수점 둘째 자리까지 반올림
      sessionUser.setRecommended_protein(Math.round(vo.getRecommended_protein() * 100.0) / 100.0); // 소수점 둘째 자리까지 반올림
      sessionUser.setRecommended_fats(Math.round(vo.getRecommended_fats() * 100.0) / 100.0); // 소수점 둘째 자리까지 반올림
      sessionUser.setGoal(vo.getGoal());
      sessionUser.setDietary_goal(vo.getDietary_goal());
      sessionUser.setExcluded_foods(vo.getExcluded_foods());

      // 사용자 정보를 데이터베이스에 저장하는 로직 추가
      userService.updateUser(sessionUser);
    } 

    return "redirect:/user/getUser"; // 메인 페이지로 리다이렉트
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();

    return "redirect:/";
  }

  @GetMapping("/user/getUser")
  public String getUser(HttpSession session, Model model) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");

    if (sessionUser != null) {
      UserVO vo = userService.getUser(sessionUser);
      model.addAttribute("user", vo);

      return "user/getUser";
    } else {

      return "redirect:/";
    }
  }

  @GetMapping("/user/getBmiRecords")
  public String getBmiRecords(HttpSession session, Model model) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");

    if (sessionUser != null) {
      List<BmiRecordVO> records = userService.getBmiRecords(sessionUser);
      model.addAttribute("records", records);

      return "user/getBmiRecords";
    } else {

      return "redirect:/";
    }
  }

  
  @GetMapping("/insertBmiRecordForm")
  public String insertBmiRecordForm(HttpSession session) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");

    if (sessionUser != null) {

      return "user/insertBmiRecordForm";
    } else {

      return "redirect:/";
    }
  }

  @PostMapping("/insertBmiRecord")
  public String insertBmiRecord(BmiRecordVO vo, HttpSession session) {
    UserVO sessionUser = (UserVO) session.getAttribute("user");

    if (sessionUser != null) {
      UserVO user = userService.getUser(sessionUser);
      vo.setUser_id(user.getUser_id());
      
      calculateBmiAndBmr(user);

      vo.setBmi(user.getBmi());
      vo.setClassification(user.getClassification());
      vo.setBmr(user.getBmr());
  
      // BMI 기록 삽입
      userService.insertBmiRecord(vo);

      return "redirect:/user/getBmiRecords"; 
    } else {

      return "redirect:/";
    }
  }

  public void calculateBmiAndBmr(UserVO user) {
    // BMI 계산
    double heightInMeters = user.getHeight() / 100.0; // cm -> m
    double bmi = user.getWeight() / (heightInMeters * heightInMeters);
    user.setBmi(Math.round(bmi * 100.0) / 100.0); // 소수점 둘째 자리까지 반올림

    String classification;
    
    if (bmi < 18.5) {
        classification = "저체중";
    } else if (bmi < 25.0) {
        classification = "정상";
    } else if (bmi < 30.0) {
        classification = "과체중";
    } else {
        classification = "비만";
    }
    
    user.setClassification(classification);

    // BMR 계산 (Harris-Benedict 식)
    double bmr;
    if ("남성".equals(user.getGender())) {
      bmr = 88.362 + (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge());
    } else if ("여성".equals(user.getGender())) {
      bmr = 447.593 + (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * user.getAge());
    } else {
      throw new IllegalArgumentException("성별이 올바르지 않습니다.");
    }
    user.setBmr(Math.round(bmr * 100.0) / 100.0); // 소수점 둘째 자리까지 반올림
  }
}
