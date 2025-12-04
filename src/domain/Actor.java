package domain;
import java.util.ArrayList;
import java.util.Objects;
public class Actor{
    private Long id;
    private String name;
    private ArrayList<Movie> filmography = new ArrayList<>();

    public Actor(String name){
        this.name = name;
    }

    public void setId(Long id){
        this.id=id;
    }
    public void addMovie(Movie movie){
        filmography.add(movie);
    }
    public void removeMovie(Movie movie){
        filmography.remove(movie);
    }
    public boolean isSameActor(String actorName){
        return this.name.equals(actorName);
    }
    @Override
    public String toString() {
        return "Actor{id=" + id + ", name='" + name + "'}";
    }
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return id.equals(actor.id);
    }
    public int hashCode(){
        return Objects.hash(id);
    }
}