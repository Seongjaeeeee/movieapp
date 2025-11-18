import java.util.ArrayList;
public class Actor{
    String name;
    ArrayList<Movie> filmography = new ArrayList<>();

    public Actor(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public void addMovie(Movie movie){
        filmography.add(movie);
    }
    @Override
    public String toString() {
        return "Actor{name='" + name + "'}";
    }
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return name.equals(actor.name);
    }
    public int hashCode(){
        return name.hashCode();
    }
}