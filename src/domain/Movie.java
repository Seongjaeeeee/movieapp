package domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
public class Movie{
    private Long id;
    private String name;
    private Director director;//없으면 안됨
    private Genre genre;
    private String description;
    private LocalDate releaseDate;
    private List<Actor> actors = new ArrayList<>();

    private Double rating=0.0;
    private RatingPolicy ratingPolicy = new AntiBombRatingPolicy();//임시
    private Map<Integer, Long> ratingDistribution = new HashMap<>();//점수,개수
   
    public Movie(String name, Director director, Genre genre,LocalDate releaseDate,String description, List<Actor> actors){
        validateConstructor(name, director);
        this.name = name;
        this.director = director;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.description = description;
        this.actors.addAll(actors);
    }
    
    public boolean containsActor(Actor actor){
        return actors.contains(actor);
    }
    public boolean isDirectedBy(Director director){
        return this.director.equals(director);
    }
    public boolean containsName(String keyword){
        if (keyword == null) return false;
        return this.name.toLowerCase().contains(keyword.toLowerCase());
    }
    public boolean containsActorName(String keyword){
        return actors.stream().anyMatch(actor -> actor.containsName(keyword));
    }
    public boolean containsDirectorName(String keyword){
        return this.director.containsName(keyword);
    }
    
    public void updateMovieInfo(String name, Genre genre, LocalDate releaseDate, String description) {
        if (name != null && !name.isBlank()) this.name = name;
        if (genre != null) this.genre = genre;
        if (releaseDate != null) this.releaseDate = releaseDate;
        if (description != null) this.description = description;
    }
    public void changeDirector(Director director){
        if (director == null) {
            throw new IllegalArgumentException("감독 정보는 비어있을 수 없습니다.");
        }
        this.director = director;
    }
    public void addActor(Actor actor){
        if (actor == null) {
        throw new IllegalArgumentException("배우 정보가 누락되었습니다.");
        }
        boolean isExist = actors.contains(actor);
        if(isExist) return;
        actors.add(actor);
    }
    public void removeActor(Actor actor){
        if (actor == null) {
        throw new IllegalArgumentException("배우 정보가 누락되었습니다.");
        }
        
        if (!actors.remove(actor)) {
        throw new IllegalArgumentException("영화에 존재하지 않는 배우입니다.");
        }
    }
    
    public void updateRating(Integer star){
        ratingDistribution.put(star, ratingDistribution.getOrDefault(star, 0L) + 1);//getordefalult 찾아서 있으면 값,없으면 defalut값 반환
        rating = ratingPolicy.calculateRating(ratingDistribution);
    }

    
    private void validateConstructor(String name, Director director) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("영화 제목은 필수입니다.");
        }
        if (director == null) {
            throw new IllegalArgumentException("감독은 필수입니다.");
        }
    }

    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", director=" + director +
            ", genre=" + genre +
            ", releaseDate=" + releaseDate +
            ", description='" + description + '\'' +
            ", actors=" + actors +
            ", Rating=" + rating+
            '}';
    }
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id);
    }
    public int hashCode(){
        return Objects.hash(id);
    }
}