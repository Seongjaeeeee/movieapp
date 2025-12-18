package list;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import domain.Review;
import domain.User;

public class ReviewList {
    private List<Review> reviews = new ArrayList<>();
    private Long id=1L;
    public void save(Review review){
        review.setId(id++);
        reviews.add(review);
    }
    public List<Review> findByMovieId(Long id){
        return reviews.stream().filter(r->r.hasMovieId(id)).collect(Collectors.toList());
    }
    public List<Review> findByUser(User user){
        return reviews.stream().filter(r->r.isOwner(user)).collect(Collectors.toList());
    }
    public Optional<Review> findById(Long id){
        return reviews.stream().filter(r->Objects.equals(id,r.getId())).findFirst();
    }
    public void delete(Review review){
        reviews.remove(review);
    }

}
