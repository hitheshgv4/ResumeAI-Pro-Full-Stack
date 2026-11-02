package com.resumeai.repository;
import com.resumeai.model.JobApplication; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ApplicationRepository extends JpaRepository<JobApplication,String>{List<JobApplication> findAllByUserId(Long id);}
