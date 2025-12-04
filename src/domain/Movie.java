package domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dto.MovieParam;
public class Movie{
    private Long id;
    private String name;
    private Director director;
    private List<Actor> actors = new ArrayList<>();
    private Genre genre;
    private String description;
    private LocalDate releaseDate;

    private Double rating=0.0;
    private RatingPolicy ratingPolicy = new AntiBombRatingPolicy();//임시
    private Map<Integer, Long> ratingDistribution = new HashMap<>();//점수,개수
   
    public Movie(String name, Director director, Genre genre,LocalDate releaseDate,String description, List<Actor> actors){
        this.name = name;
        this.director = director;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.description = description;
        this.actors.addAll(actors);
    }
    
    public boolean isSameMovie(Long id){
        return this.id.equals(id);
    }
    public void setId(Long id){
        this.id = id;
    }
    
    public void changeAttribute(MovieParam param){
        if(param.getName()!=null)this.name=param.getName();
        if(param.getGenre()!=null)this.genre=param.getGenre();
        if(param.getReleaseDate()!=null)this.releaseDate=param.getReleaseDate();
        if(param.getDescription()!=null)this.description=param.getDescription();
    }
    public void changeDirector(Director director){
        director.removeMovie(this);
        director.addMovie(this);
        this.director = director;
    }
    public void deleteActor(Actor actor){
        boolean isExist = actors.contains(actor);
        if(isExist==false) return;
        actor.removeMovie(this);
        actors.remove(actor);
    }
    public void addActor(Actor actor){
        boolean isExist = actors.contains(actor);
        if(isExist) return;
        actor.addMovie(this);
        actors.add(actor);
    }
    
    public void connectMovie(){
        director.addMovie(this);
        for(Actor actor: actors) actor.addMovie(this);
    }
    public void removeConnection(){
        for(Actor actor: actors) actor.removeMovie(this);
        director.removeMovie(this);
    }

    public void updateRating(Integer star){
        ratingDistribution.put(star, ratingDistribution.getOrDefault(star, 0L) + 1);//getordefalult 찾아서 있으면 값,없으면 defalut값 반환
        rating = ratingPolicy.calculateRating(ratingDistribution);
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