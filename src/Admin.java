public class Admin{
    MovieList movieList;
    ActorList actorList;
    DirectorList directorList;

    public Admin() {
        movieList = new MovieList();
        actorList = new ActorList();
        directorList = new DirectorList();
    }

    public void createMovie(String name, String directorName, String actorName){
        Director director = directorList.returnDirector(directorName);
        Actor actor = actorList.returnActor(actorName);
        movieList.addMovie(name,director,actor);
    }

    public void printMovies() {
        System.out.println("=== 최종 영화 목록 ===");
        movieList.printAll(); 
    }
}