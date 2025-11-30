import java.util.ArrayList;
import java.util.Objects;

public class Director{
    private Long id;
    private String name;
    private ArrayList<Movie> filmography = new ArrayList<>();

    public Director(Long id,String name){
        this.id=id;
        this.name = name;   
    }

    public boolean isSameDirector(String directorName){
        return this.name.equals(directorName);
    }
    public void addMovie(Movie movie){
        filmography.add(movie);
    }
    public void removeMovie(Movie movie){
        filmography.remove(movie);
    }
    @Override
    public String toString() {
        return "Director{id=" + id + ", name='" + name + "'}";
    }
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return id.equals(director.id);
    }
    public int hashCode(){
        return Objects.hash(id);
    }
}