package domain;

import java.util.Objects;

public class Review {
    private Long id;
    private String content;
    private Integer rating;
    private Movie movie;
    private User user;

    private Review(String content, Integer rating, User user, Movie movie) {
        this.content = content;
        this.rating = rating;
        this.movie = movie;
        this.user = user;
    }
    public static Review create(String content, Integer rating, User user, Movie movie) {//정적 팩토리 메서드
        if(rating==null||rating>5||rating<1||user==null||movie==null)throw new IllegalArgumentException("리뷰 생성을 위한 인자가 적절하지 않습니다.");
        Review review = new Review(content, rating, user, movie);
        movie.addRating(rating);
        return review;
    }
    public void update(String content,Integer rating){
        if(rating==null||rating>5||rating<1)throw new IllegalArgumentException("별점이 올바르지 않습니다.");
        this.content = content;
        if(!this.rating.equals(rating)){
            movie.updateRating(this.rating,rating);
        this.rating = rating;
        }
    }
    public void deleteRating(){
        movie.deleteRating(rating);
    }
    
    public boolean hasMovieId(Long id){
        return Objects.equals(movie.getId(),id);
    }
    public boolean isOwner(User user){
        return Objects.equals(this.user,user);
    }
    
    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id != null && Objects.equals(id, review.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", userId=" + (user != null ? user.getId() : "null") +
                ", movieId=" + (movie != null ? movie.getId() : "null") +
                '}';
    }

}
