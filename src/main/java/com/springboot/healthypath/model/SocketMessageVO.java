package com.springboot.healthypath.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SocketMessageVO {
  private String type;
  private String sender;
  private String content;
  private LocalDateTime timeSent;
}