package service;

import domain.Movie;
import domain.Review;
import domain.User;
import list.ReviewList;

public class ReviewService {
    private MovieService movieService;
    private UserService userService;
    private ReviewList reviewList;

    public ReviewService(ReviewList reviewList,MovieService movieService,UserService userService){
        this.reviewList = reviewList;
        this.movieService = movieService;
        this.userService = userService;
    }

    public void createReview(String reviewDetail, Integer star, Long userId, Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        //User user = userService.findUser(userId);
        //Review review = new Review(reviewDetail,star,movie,user);
       // review.updateAverageRating();
        //reviewList.save(review);

    }
}
