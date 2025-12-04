package list;
import java.util.ArrayList;
import java.util.List;
import domain.Movie;
public class MovieList{
    private Long id = 1L;
    private List<Movie> movies = new ArrayList<>();
    
    public void save(Movie movie){
        movie.setId(id++);
        movies.add(movie);
    }
    public void remove(Movie movie){
        movies.remove(movie);
    }
    public List<Movie> get(){
        return movies;
    }

    public Movie find(Long id){
        return movies.stream().filter(m -> m.isSameMovie(id)).findFirst().orElse(null);
    }

}