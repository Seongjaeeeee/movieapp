package dto;

import java.util.List;

public class PersonSearchResult {
    private List<ActorSearchResult> actors;
    private List<DirectorSearchResult> directors;
    public PersonSearchResult(List<ActorSearchResult> actors, List<DirectorSearchResult> directors){
        this.actors = actors;
        this.directors = directors;
    }
    public List<ActorSearchResult> getActors() {
        return actors;
    }
    public List<DirectorSearchResult> getDirectors() {
        return directors;
    }
}

