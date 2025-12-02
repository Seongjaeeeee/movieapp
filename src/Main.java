import list.ActorList;
import list.DirectorList;
import list.MovieList;
import list.ReviewList;
import list.UserList;
import service.DirectorService;
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

        ActorService actorService = new ActorService(actorList);
        DirectorService directorService = new DirectorService(directorList);
        UserService userService = new UserService(userList);
        MovieService movieService = new MovieService(movieList, actorService, directorService);
        ReviewService reviewService = new ReviewService(reviewList,movieService,userService);
        
        Test test = new Test();
        test.init(movieService, reviewService, userService);

        MovieController controller = new MovieController(movieService,directorService,actorService);
        controller.start();
    }
}