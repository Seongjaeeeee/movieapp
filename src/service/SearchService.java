package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.MovieSearchResult;

public class SearchService {
    private MovieService movieService;

    public SearchService(MovieService movieService){
        this.movieService = movieService;
    }
    
    public List<MovieSearchResult> searchAllMovie(String keyword){
        List<MovieSearchResult> results = new ArrayList<>(); 
        results.addAll(movieService.findMovieByName(keyword));
        results.addAll(movieService.findMovieByActorName(keyword));
        results.addAll(movieService.findMovieByDirectorName(keyword));
        
        List<MovieSearchResult> uniqueResults = results.stream().distinct().collect(Collectors.toList());
        //uniqueResults.sort((r1, r2) -> r1.getName().compareTo(r2.getName()));
        return uniqueResults;
    }
    //public void searchPerson(String keyword);
}
