package com.springboot.healthypath.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springboot.healthypath.interceptor.SessionCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  private SessionCheckInterceptor SessionCheckInterceptor;

  @SuppressWarnings("null")
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(SessionCheckInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/", "/signup", "/signin", "/auth/google", "/chat",
            "/food/getFood/{idx}", "/food/getRecipes", "/food/getRecipe/{rcp_sno}",
            "resources/**", "/css/**");
  }
}
