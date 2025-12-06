package list;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Actor;
import domain.Director;
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
    public List<Movie> findByActor(Actor actor){
        return movies.stream().filter(m -> m.isContainActor(actor)).collect(Collectors.toList());
    }
    public List<Movie> findByDirector(Director director){
        return movies.stream().filter(m -> m.isEqualDirector(director)).collect(Collectors.toList());
    }

}