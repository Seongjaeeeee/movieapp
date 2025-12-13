package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.ActorSearchResult;
import dto.PersonSearchResult;

public class SearchService {
    private MovieService movieService;
    private ActorService actorService;
    private DirectorService directorService;

    public SearchService(MovieService movieService, ActorService actorService,DirectorService directorService){
        this.movieService = movieService;
        this.actorService = actorService;
        this.directorService = directorService;
    }
    
    public List<ActorSearchResult> searchAllMovie(String keyword){
        List<ActorSearchResult> results = new ArrayList<>(); 
        results.addAll(movieService.findAllMoviesByKeyword(keyword));
        results.addAll(movieService.findAllMovieByActorKeyword(keyword));
        results.addAll(movieService.findAllMoviesByDirectorkeyword(keyword));
        
        List<ActorSearchResult> uniqueResults = results.stream().distinct().collect(Collectors.toList());
        //uniqueResults.sort((r1, r2) -> r1.getName().compareTo(r2.getName()));
        return uniqueResults;
    }
    public PersonSearchResult searchPerson(String keyword){
        PersonSearchResult results = 
        new PersonSearchResult(actorService.findAllActorsByKeyword(keyword),directorService.findAllDirectorsByKeyword(keyword));
        return results;
    }
}
