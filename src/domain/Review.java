package domain;

public class Review {
    private Long id;
    private String reviewDetail;
    private Movie movie;
    private User user;
    private Integer star;

    public Review(String reviewDetail, Integer star, Movie movie, User user) {
        this.reviewDetail = reviewDetail;
        this.movie = movie;
        this.user = user;
        this.star = star;
        calculateMovieAverageStar();//이거 위험
    }
    public void setid(Long id){
        this.id=id;
    }
    private void calculateMovieAverageStar(){
        movie.calculateAverageStar(star);
    }

}
