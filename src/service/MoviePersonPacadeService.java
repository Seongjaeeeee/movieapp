package service;

import domain.Actor;
import domain.Director;
import domain.Movie;

public class MoviePersonPacadeService {
    private MovieService movieService;
    private ActorService actorService;
    private DirectorService directorService;
    public MoviePersonPacadeService(MovieService movieService, ActorService actorService, DirectorService directorService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.directorService = directorService;
    }

    public void deleteActor(String name){
        movieService.deleteActorInMovie(actorService.findActor(name));
        actorService.deleteActor(name);
    }
    public void deleteDirector(String name){
        movieService.deleteDirectorInMovie(directorService.findDirector(name));
        directorService.deleteDirector(name);
    }


    public void changeMovieDirector(Long id,String directorName){
        Movie movie = movieService.findMovie(id);
        Director director = directorService.returnDirector(directorName);
        movieService.changeDirector(movie,director);
    }
    public void deleteMovieActor(Long id,String name){
        Movie movie = movieService.findMovie(id);
        Actor actor = actorService.findActor(name);
        if(actor!=null)movieService.deleteActor(movie,actor);
    }
    public void addMovieActor(Long id,String name){
        Movie movie = movieService.findMovie(id);
        Actor actor = actorService.returnActor(name);
        movieService.addActor(movie,actor);
    }

}
