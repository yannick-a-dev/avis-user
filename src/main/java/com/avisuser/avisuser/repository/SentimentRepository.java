package com.avisuser.avisuser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.avisuser.avisuser.entities.Sentiment;
import com.avisuser.avisuser.enumeration.TypeSentiment;

public interface SentimentRepository extends JpaRepository<Sentiment, Integer> {

	List<Sentiment> findByType(TypeSentiment type);
}
