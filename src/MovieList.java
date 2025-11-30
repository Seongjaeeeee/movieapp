import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
public class MovieList{
    private Long idnumber = 1L;
    private List<Movie> movies = new ArrayList<>();
    
    public Movie create(String movieName, Director director, Genre genre,LocalDate releaseDate,String description,List<Actor> actors){
        return new Movie(idnumber++, movieName, director, genre, releaseDate, description, actors);
    }
    public void save(Movie movie){
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