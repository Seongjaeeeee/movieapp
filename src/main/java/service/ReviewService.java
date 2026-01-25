package service;

import java.util.List;

import domain.Movie;
import domain.Review;
import domain.User;
import list.ReviewList;

public class ReviewService {
    private MovieService movieService;
    private ReviewList reviewList;

    public ReviewService(ReviewList reviewList,MovieService movieService){
        this.reviewList = reviewList;
        this.movieService = movieService;
    }

    public void createReview(String content, Integer rating, User user, Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        Review review = Review.create(content,rating,user,movie);
        reviewList.save(review);
    }
    public List<Review> findReviewByMovieId(Long movieId) {
        return reviewList.findByMovieId(movieId);
    }
    public List<Review> findReviewByUser(User user) {
        return reviewList.findByUser(user);
    }
    
    public void updateReview(String content,Integer rating,Long reviewId,User user){
        Review review = getReviewById(reviewId);
        if(!isReviewOwner(user, review))throw new IllegalStateException("수정할 수 있는 유저가 아닙니다.");
        review.update(content,rating);
    }
    public void deleteReview(Long reviewId,User user){
        Review review = getReviewById(reviewId);
        if(!isReviewOwner(user, review))throw new IllegalStateException("삭제할 수 있는 유저가 아닙니다.");
        review.deleteRating();
        reviewList.delete(review);
    }

    private boolean isReviewOwner(User user,Review review){
        return review.isOwner(user);
    }
    private Review getReviewById(Long reviewId){
        return reviewList.findById(reviewId).orElseThrow(()->new IllegalArgumentException("내 리뷰에 존재하지 않는 리뷰 id입니다."));
    }
    
}
