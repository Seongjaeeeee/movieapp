import java.util.Objects;

public class Movie{
    private Long id;
    private String name;
    private Director director;
    private Actor actor;

    public Movie(Long id, String name, Director director, Actor actor){
        this.id =id;
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.createMovie();
    }
    
    public boolean isSameMovie(Long id){
        return this.id.equals(id);
    }
    public Long getId(){
        return this.id;
    }
    @Override
    public String toString() {
        return "Movie{id=" + id + ", name='" + name + "', director=" + director + ", actor=" + actor + "}";
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

    private void createMovie(){
        director.addMovie(this);
        actor.addMovie(this);
    }
}