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
        if(movie==null)return;
        movieList.remove(movie);
    }

    public void changeMovieDetail(Long id,MovieParam param){
        Movie movie = movieList.find(id);
        movie.changeAttribute(param);
    }
    public void deleteActorInMovie(Actor actor){//해당 배우 나오는 영화에서 배우 삭제
        List<Movie> tempList = movieList.findByActor(actor);
        for(Movie movie:tempList){
            movie.deleteActor(actor);
        }
    }
    public void deleteDirectorInMovie(Director director){
        List<Movie> tempList = movieList.findByDirector(director);
        for(Movie movie:tempList){
            movie.deleteDirector(director);
        }
    }
    public void changeDirector(Movie movie, Director director) {
        if (movie == null || director == null) return;
        movie.changeDirector(director);
    }
    public void deleteActor(Movie movie, Actor actor) {
        if (movie == null || actor == null) return;
        movie.deleteActor(actor);
    }
    public void addActor(Movie movie, Actor actor) {
        if (movie == null || actor == null) return;
        movie.addActor(actor);
    }

} 