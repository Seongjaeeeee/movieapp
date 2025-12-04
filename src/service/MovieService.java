package service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domain.Actor;
import domain.Director;
import domain.Genre;
import domain.Movie;
import list.MovieList;
import dto.MovieParam;
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
        Movie movie = new Movie(name,director, genre, releaseDate, description, tempActors);
        movie.connectMovie();
        movieList.save(movie);
    }
    public List<Movie> getMovies(){
        return movieList.get();
    }
    public Movie findMovie(Long id){
        return movieList.find(id);
    }
    public void deleteMovie(Long movieId){
        Movie movie = movieList.find(movieId);
        if(movie==null)System.out.println("Movie is not present");
        else {
            movie.removeConnection();
            movieList.remove(movie);
        }
    }
    public void changeMovieDetail(Long id,MovieParam param){
        Movie movie = movieList.find(id);
        movie.changeAttribute(param);
    }
    public void changeMovieDirector(Long id,String directorName){
        Movie movie = movieList.find(id);
        Director director = directorService.returnDirector(directorName);
        movie.changeDirector(director);
    }
    public void deleteMovieActor(Long id,String name){
        Movie movie = movieList.find(id);
        Actor actor = actorService.returnActor(name);
        movie.deleteActor(actor);
    }
    public void addMovieActor(Long id,String name){
        Movie movie = movieList.find(id);
        Actor actor = actorService.returnActor(name);
        movie.addActor(actor);
    }
    

} 