import java.util.ArrayList;
import java.util.List;

public class MovieList{
    private List<Movie> movies = new ArrayList<>();
    private Long idnumber = 1L;
    public void addMovie(String movieName, Director director, Actor actor){
        Movie movie = new Movie(idnumber++,movieName, director, actor);
        movies.add(movie);
    }

    public void printAll(){
        for(Movie m:movies){
            System.out.println(m);
        }
    }

    public void removeMovie(Long id){
        Movie targetMovie = movies.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
        if(targetMovie==null)System.out.println("is not present");
        else movies.remove(targetMovie);
    }
}