package service;

import java.time.LocalDate;
import java.util.ArrayList;

import domain.Actor;
import domain.Director;
import domain.Genre;
import domain.Movie;

public class MoviePersonFacadeService {
    private MovieService movieService;
    private ActorService actorService;
    private DirectorService directorService;

    public MoviePersonFacadeService(MovieService movieService, ActorService actorService, DirectorService directorService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.directorService = directorService;
    }

    //영화 관련 로직
    public void createMovie(String name, Genre genre, LocalDate releaseDate, String description,Long directorId,Long ... actorIds){
        Director director = directorService.getDirectorById(directorId);
        ArrayList<Actor> actors=new ArrayList<>();
        for(Long actorId: actorIds){
            actors.add(actorService.getActorById(actorId));
        }
        movieService.createMovie(name, director, genre, releaseDate, description, actors);
    }

    public void updateMovieDirector(Long movieId,Long directorId){
        Movie movie = movieService.getMovieById(movieId);
        Director director = directorService.getDirectorById(directorId);
        movieService.updateMovieDirector(movie,director);
    }
    public void removeActorFromMovie(Long movieId,Long actorId){
        Movie movie = movieService.getMovieById(movieId);
        Actor actor = actorService.getActorById(actorId);
        movieService.removeActorFromMovie(movie, actor);
    }
    public void addActorToMovie(Long movieId,Long actorId){
        Movie movie = movieService.getMovieById(movieId);
        Actor actor = actorService.getActorById(actorId);
        movieService.addActorToMovie(movie,actor);
    }
    //배우,감독 삭제 로직
    public void deleteActor(Long id){
        movieService.removeActorFromAllMovies(actorService.getActorById(id));
        actorService.deleteActor(id);
    }
    public void deleteDirector(Long id){
        movieService.replaceDirectorFromAllMovies(directorService.getDirectorById(id),directorService.getAnonymousDirector());
        directorService.deleteDirector(id);
    }
}
