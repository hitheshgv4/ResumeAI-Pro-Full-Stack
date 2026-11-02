package com.resumeai.model;
import jakarta.persistence.*;
@Entity @Table(name="job_applications")
public class JobApplication {
 @Id private String id; @Column(nullable=false) private Long userId; @Lob @Column(columnDefinition="LONGTEXT",nullable=false) private String data;
 public String getId(){return id;} public void setId(String v){id=v;} public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;} public String getData(){return data;} public void setData(String v){data=v;}
}
