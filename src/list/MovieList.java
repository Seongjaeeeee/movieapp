package list;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    public List<Movie> findAll(){
        return new ArrayList<>(movies);
    }
    public Optional<Movie> findById(Long id){
        return movies.stream().filter(m -> Objects.equals(id,m.getId())).findFirst();
    }
    public List<Movie> findByActor(Actor actor){
        return movies.stream().filter(m -> m.containsActor(actor)).collect(Collectors.toList());
    }
    public List<Movie> findByDirector(Director director){
        return movies.stream().filter(m -> m.isDirectedBy(director)).collect(Collectors.toList());
    }

    public void delete(Movie movie){
        movies.remove(movie);
    }

}