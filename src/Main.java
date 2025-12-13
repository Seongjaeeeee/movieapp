import controller.MovieController;
import list.ActorList;
import list.DirectorList;
import list.MovieList;
import list.ReviewList;
import list.UserList;
import service.ActorService;
import service.AuthFacade;
import service.DirectorService;
import service.MoviePersonFacadeService;
import service.MovieService;
import service.ReviewService;
import service.SearchService;
import service.Session;
import service.UserService;

public class Main {
    public static void main(String[] args) {
        System.out.print("----Movie app start!----");
        
        // 데이터 저장소 초기화
        MovieList movieList = new MovieList();
        ActorList actorList = new ActorList();
        UserList userList = new UserList();
        DirectorList directorList = new DirectorList();
        ReviewList reviewList = new ReviewList();
        
        // 서비스 계층 초기화 (생성자 주입)
        MovieService movieService = new MovieService(movieList);
        ActorService actorService = new ActorService(actorList);
        DirectorService directorService = new DirectorService(directorList);
        UserService userService = new UserService(userList);
        // 세션 및 인증 관련 서비스 초기화
        Session session = new Session();
        AuthFacade authFacade = new AuthFacade(userService, session);
        
        // 퍼사드 및 검색 서비스 초기화
        MoviePersonFacadeService moviePersonFacadeService = 
            new MoviePersonFacadeService(movieService, actorService, directorService);
        SearchService searchService = 
            new SearchService(movieService, actorService, directorService);
        ReviewService reviewService = 
            new ReviewService(reviewList, movieService, userService);
        
        // 테스트 데이터 초기화
        TestDataInit testDataInit = 
            new TestDataInit(moviePersonFacadeService, directorService, actorService, userService, reviewService);
        testDataInit.init();

        // 컨트롤러 생성 및 애플리케이션 시작 (생성자 주입)
        MovieController controller = 
            new MovieController(movieService, directorService, actorService, 
                              moviePersonFacadeService, searchService, authFacade, session);
        authFacade.createAdmin("admin", "jsj1020");
        controller.start();
    }
}