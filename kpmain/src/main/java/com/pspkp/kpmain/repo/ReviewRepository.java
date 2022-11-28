package com.pspkp.kpmain.repo;

import com.pspkp.kpmain.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
