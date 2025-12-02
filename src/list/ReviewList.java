package list;

import java.util.ArrayList;
import java.util.List;

import domain.Review;

public class ReviewList {
    private List<Review> reviewList = new ArrayList<>();
    private Long id=1L;
    public void save(Review review){
        review.setid(id++);
        reviewList.add(review);
    }
    
}
