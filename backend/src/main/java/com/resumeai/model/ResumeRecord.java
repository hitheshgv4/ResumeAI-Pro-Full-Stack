package com.resumeai.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="resumes")
public class ResumeRecord {
 @Id private String id;
 @Column(nullable=false) private Long userId;
 @Lob @Column(columnDefinition="LONGTEXT",nullable=false) private String data;
 private Instant updatedAt=Instant.now();
 public String getId(){return id;} public void setId(String v){id=v;} public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;} public String getData(){return data;} public void setData(String v){data=v;} public Instant getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Instant v){updatedAt=v;}
}
