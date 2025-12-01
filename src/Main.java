public class Main {
    public static void main(String[] args){
        System.out.print("----Movie app start!----");
        MovieList movieList = new MovieList();
        ActorList actorList = new ActorList();
        DirectorList directorList = new DirectorList();
        ActorService actorService = new ActorService(actorList);
        DirectorService directorService = new DirectorService(directorList);
        MovieService movieService = new MovieService(movieList, actorService, directorService);
        
        Test test = new Test();
        test.init(movieService);

        MovieController controller = new MovieController(movieService,directorService,actorService);
        controller.start();
    }
}