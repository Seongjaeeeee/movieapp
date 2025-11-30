import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class MovieService{
    MovieList movieList;
    ActorService actorService;
    DirectorService directorService;

    public MovieService(MovieList movieList, ActorService actorService, DirectorService directorService) {
        this.movieList = movieList;
        this.actorService = actorService;
        this.directorService = directorService;
    }

    public void createMovie(String name, String directorName, Genre genre,LocalDate releaseDate,String description,String ... actorNames){
        Director director = directorService.returnDirector(directorName);
        ArrayList<Actor> tempActors=new ArrayList<>();
        for(String actorName: actorNames){
            tempActors.add(actorService.returnActor(actorName));
        }
        Movie movie = movieList.create(name,director, genre, releaseDate, description, tempActors);
        movieList.save(movie);
    }

    public List<Movie> getMovies(){
        return movieList.get();
    }

    public void deleteMovie(Long movieId){
        Movie movie = movieList.find(movieId);
        if(movie==null)System.out.println("Movie is not present");
        else {
            movie.removeConnection();
            movieList.remove(movie);
        }
    }
/* 
    public void changeMovieName(Long id,String name){
        //movieList.updateMovieName(Long id,String name);
    }
    public void changeMovieGenre(Long id,String name)
    public void changeMovieDate(Long id,String name)
    public void changeMovieDirector(Long id,String name)
    public void deleteMovieActor(Long id,String name)
    public void addMovieActor(Long id,String name){

    }
    //public void */

} 