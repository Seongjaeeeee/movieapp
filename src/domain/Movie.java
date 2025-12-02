package domain;
import java.time.LocalDate;
import java.util.Objects;

import dto.MovieParam;

import java.util.ArrayList;
import java.util.List;
public class Movie{
    private Long id;
    private String name;
    private Director director;
    private List<Actor> actors = new ArrayList<>();
    private Genre genre;
    private String description;
    private LocalDate releaseDate;
    private Double averageStar = 0.0;
    private Integer reviewCount = 0;

    public Movie(Long id, String name, Director director, Genre genre,LocalDate releaseDate,String description, List<Actor> actors){
        this.id =id;
        this.name = name;
        this.director = director;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.description = description;
        this.actors.addAll(actors);
        this.connectMovie();
    }
    
    public boolean isSameMovie(Long id){
        return this.id.equals(id);
    }
    
    public void removeConnection(){
        for(Actor actor: actors) actor.removeMovie(this);
        director.removeMovie(this);
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

    public void calculateAverageStar(Integer star){
        averageStar=(averageStar*reviewCount+star)/(reviewCount+1);
        reviewCount++;
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
            ", averageStar=" + averageStar +
            ", reviewCount=" + reviewCount +
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

    private void connectMovie(){
        director.addMovie(this);
        for(Actor actor: actors) actor.addMovie(this);
    }
}