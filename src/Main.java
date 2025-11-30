public class Main {
    public static void main(String[] args){
        System.out.print("Movie app start!");
        MovieList movieList = new MovieList();
        ActorList actorList = new ActorList();
        DirectorList directorList = new DirectorList();

        // 2단계: 기본 서비스 생성 (저장소 주입)
        ActorService actorService = new ActorService(actorList);
        DirectorService directorService = new DirectorService(directorList);

        // 3단계: 메인 서비스 생성 (저장소 + 다른 서비스 주입)
        // 여기서 actorService, directorService를 넘겨줍니다!
        MovieService movieService = new MovieService(movieList, actorService, directorService);
        
        Test test = new Test();
        test.init(movieService);
        movieService.getMovies();
        movieService.deleteMovie(1L);
        movieService.getMovies();
    }
}