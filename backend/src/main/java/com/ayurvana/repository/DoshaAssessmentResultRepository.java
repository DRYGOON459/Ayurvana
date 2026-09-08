package com.ayurvana.repository;

import com.ayurvana.model.entity.DoshaAssessmentResult;
import com.ayurvana.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoshaAssessmentResultRepository extends JpaRepository<DoshaAssessmentResult, Long> {
    List<DoshaAssessmentResult> findByUserOrderByCreatedAtDesc(User user);
    Page<DoshaAssessmentResult> findByUser(User user, Pageable pageable);
}
