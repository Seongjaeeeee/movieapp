import controller.MovieController;
import list.ActorList;
import list.DirectorList;
import list.MovieList;
import list.ReviewList;
import list.UserList;
import service.DirectorService;
import service.MoviePersonFacadeService;
import service.MovieService;
import service.ReviewService;
import service.UserService;
import service.ActorService;

public class Main {
    public static void main(String[] args){
        System.out.print("----Movie app start!----");
        MovieList movieList = new MovieList();
        ActorList actorList = new ActorList();
        UserList userList = new UserList();
        DirectorList directorList = new DirectorList();
        ReviewList reviewList = new ReviewList();
        
        MovieService movieService = new MovieService(movieList);
        ActorService actorService = new ActorService(actorList);
        DirectorService directorService = new DirectorService(directorList);
        MoviePersonFacadeService moviePersonFacadeService = new MoviePersonFacadeService(movieService,actorService,directorService);
        UserService userService = new UserService(userList);
        ReviewService reviewService = new ReviewService(reviewList,movieService,userService);
        
        TestDataInit testDataInit = new TestDataInit(moviePersonFacadeService, directorService, actorService, userService, reviewService);
        testDataInit.init();

        MovieController controller = new MovieController(movieService,directorService,actorService,moviePersonFacadeService);
        controller.start();
    }
}