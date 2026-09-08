package com.ayurvana.repository;

import com.ayurvana.model.entity.DoshaQuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoshaQuizQuestionRepository extends JpaRepository<DoshaQuizQuestion, Long> {
    List<DoshaQuizQuestion> findAllByOrderByQuestionOrderAsc();
    List<DoshaQuizQuestion> findByCategoryOrderByQuestionOrderAsc(String category);
}
