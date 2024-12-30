package com.springboot.healthypath.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.healthypath.model.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
@SuppressWarnings("null")
public class SessionCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    UserVO sessionUser = (UserVO) session.getAttribute("user");

    if (sessionUser == null) {
      response.sendRedirect("/");

      return false; // 요청 처리 중단
    }

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    // 컨트롤러의 로직 처리 후, 뷰 렌더링 전에 호출, 모델 데이터 수정이나 추가적인 작업이 필요할 때 사용
    if (modelAndView != null) {
      modelAndView.addObject("globalMessage", "postHandle 메시지");
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    // 요청이 완전히 끝난 후(뷰 렌더링까지 완료된 이후) 호출, 리소스 해제 또는 예외 로깅 같은 작업에 사용
    if (ex != null) {
      System.out.println("예외 발생: " + ex.getMessage());
    }
  }

}
