package com.embarkx.reviewms.repository;

import com.embarkx.reviewms.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {


    List<Review> findByCompanyId(Long companyId);
}
