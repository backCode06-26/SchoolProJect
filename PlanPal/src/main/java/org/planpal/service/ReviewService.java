package org.planpal.service;

import org.planpal.domain.Review;
import org.planpal.dto.ReviewDTO;
import org.planpal.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void save(ReviewDTO reviewDTO) {
        Review review = dtoToDomain(reviewDTO);
        reviewRepository.saveReview(review);
    }

    public void update(ReviewDTO reviewDTO) {
        Review review = dtoToDomain(reviewDTO);
        reviewRepository.updateReview(review);
    }

    public List<ReviewDTO> getReviewsByGroupId(int groupId) {
        return reviewRepository.getReviewsByGroupId(groupId);
    }

    public void delete(int reviewId) {
        reviewRepository.deleteReview(reviewId);
    }

    public int getTopGroupIds() {
        return reviewRepository.findTopGroupIdByAverageRating();
    }

    private Review dtoToDomain(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewId(reviewDTO.getReviewId());
        review.setGroupId(reviewDTO.getGroupId());
        review.setUserId(reviewDTO.getUserId());
        review.setReview(reviewDTO.getReview());
        review.setRating(reviewDTO.getRating());
        review.setCreatedAt(reviewDTO.getCreatedAt());
        return review;
    }
}
