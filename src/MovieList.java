import java.util.ArrayList;
import java.util.List;

public class MovieList{
    List<Movie> movies = new ArrayList<>();
    
    public void addMovie(String movieName, Director director, Actor actor){
        Movie movie = new Movie(movieName, director, actor);
        movies.add(movie);
    }
    public void printAll(){
        for(Movie m:movies){
            System.out.println(m);
        }
    }
}