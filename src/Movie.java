import java.time.LocalDate;
import java.util.Objects;
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
    public void removeConnection(){
        for(Actor actor: actors) actor.removeMovie(this);
        director.removeMovie(this);
    }

    private void connectMovie(){
        director.addMovie(this);
        for(Actor actor: actors) actor.addMovie(this);
    }
}