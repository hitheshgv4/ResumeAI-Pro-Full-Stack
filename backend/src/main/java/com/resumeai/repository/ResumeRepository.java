package com.resumeai.repository;
import com.resumeai.model.ResumeRecord; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; import java.util.Optional;
public interface ResumeRepository extends JpaRepository<ResumeRecord,String>{List<ResumeRecord> findAllByUserId(Long id); Optional<ResumeRecord> findByIdAndUserId(String id,Long userId);}
