package service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domain.Actor;
import domain.Director;
import domain.Genre;
import domain.Movie;
import dto.MovieParam;
import dto.MovieSearchResult;
import list.MovieList;
public class MovieService{
    private final MovieList movieList;

    public MovieService(MovieList movieList) {
        this.movieList = movieList;
    }

    public void createMovie(String name, Director director, Genre genre, LocalDate releaseDate, String description,List<Actor> actors){
        Movie movie = new Movie(name,director,genre,releaseDate,description,actors);
        movieList.save(movie);
    }
   
    public List<Movie> findAllMovies(){
        return movieList.findAll();
    }
    public Movie getMovieById(Long id){
        return movieList.findById(id).orElseThrow(()->new IllegalArgumentException("유효하지 않은 영화 id입니다."));
    }
    public List<MovieSearchResult> findMovieByName(String keyword){
        return toMovieSearchResult(movieList.findAllByName(keyword));
    }
    public List<MovieSearchResult> findMovieByActorName(String keyword){
        return toMovieSearchResult(movieList.findAllByActorName(keyword));
    }
    public List<MovieSearchResult> findMovieByDirectorName(String keyword){
        return toMovieSearchResult(movieList.findAllByDirectorName(keyword));
    }
    public void updateMovieInfo(Long id,MovieParam param){
        Movie movie = getMovieById(id);
        movie.updateMovieInfo(param.getName(), param.getGenre(), param.getReleaseDate(), param.getDescription());
    }
    public void updateMovieDirector(Movie movie, Director director) {
        movie.changeDirector(director);
    }
    public void removeActorFromMovie(Movie movie, Actor actor) {
        movie.removeActor(actor);
    }
    public void addActorToMovie(Movie movie, Actor actor) {
        movie.addActor(actor);
    }


    public void deleteMovie(Long id){
        Movie movie = getMovieById(id);
        movieList.delete(movie);
    }

    public void removeActorFromAllMovies(Actor actor){//해당 배우 나오는 영화에서 배우 삭제
        List<Movie> tempList = movieList.findByActor(actor);
        for(Movie movie:tempList){
            movie.removeActor(actor);
        }
    }
    public void replaceDirectorFromAllMovies(Director director,Director anonymous){
        List<Movie> tempList = movieList.findByDirector(director);
        for(Movie movie:tempList){
            movie.changeDirector(anonymous);
        }
    }

    private List<MovieSearchResult> toMovieSearchResult(List<Movie> movies){
        List<MovieSearchResult> results= new ArrayList<>();
        for(Movie movie : movies){
            results.add(new MovieSearchResult(movie.getName(), movie.getId()));
        }
        return results;
    }

} 