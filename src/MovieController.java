import java.util.Scanner;

import domain.*;
import dto.*;
import service.*;


import java.util.List;
import java.time.LocalDate;
public class MovieController {
    private MovieService movieService;
    private DirectorService directorService;
    private ActorService actorService;
    private UserService userService;
    private ReviewService reviewService;

    private Scanner scanner;
    public MovieController(MovieService movieService,DirectorService directorService,ActorService actorService){
        this.movieService=movieService;
        this.actorService=actorService;
        this.directorService=directorService;
        this.scanner = new Scanner(System.in); 
    }
    
    public void start(){
        boolean isRunning=true;
        while(isRunning){
            System.out.println("\n================================");
            System.out.println("1. 영화 등록  2. 목록 조회  3. 영화 정보 수정  4. 영화 삭제  q. 종료");
            System.out.print("명령 선택 > ");
            String command = scanner.nextLine();
            switch (command) {
                case "1":
                    createMovie();
                    break;
                case "2":
                    readMovies();
                    break;
                case "3":
                    updateMovie();
                    break;
                case "4":
                    deleteMovie();
                    break;
                case "q":
                    isRunning = false;
                    break;
            }
        }
    }

    private void createMovie(){
        System.out.println("\n--- 새 영화 등록 ---");
        
        System.out.print("제목: ");
        String title = scanner.nextLine();
        
        System.out.print("감독 이름: ");
        String director = scanner.nextLine();

        // Enum 입력 처리
        System.out.print("장르 (ACTION, ROMANCE...): ");
        String genreStr = scanner.nextLine();
        Genre genre = Genre.valueOf(genreStr.toUpperCase());//Genre Enum에서 매칭되는 이름의 상수를 찾아서 반환

        // 날짜 입력 처리 입력된 문자열을 LocalDate 객체로 변환 YYYY-MM-DD 형식(ISO 8601)에 맞게 입력해야 합
        System.out.print("개봉일 (YYYY-MM-DD): ");
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine());
        
        System.out.print("설명: ");
        String desc = scanner.nextLine();

        System.out.print("주연 배우 이름: ");
        String actor = scanner.nextLine();

        movieService.createMovie(title, director, genre, releaseDate, desc, actor);
        
        System.out.println("✅ 등록되었습니다.");
    }

    private void readMovies(){
        List<Movie> Movies = movieService.getMovies();
        for(Movie movie: Movies) System.out.println(movie);
        System.out.println("✅ 전체 영화 목록이 출력되었습니다.");
    }

    private void deleteMovie(){
        System.out.println("영화 id를 입력하세요: ");
        Long id = Long.valueOf(scanner.nextLine());
        movieService.deleteMovie(id);
    }

    private void updateMovie(){
        System.out.println("\n--- 영화 정보 수정 ---");
        System.out.println("1. 영화 상세정보 수정  2. 감독 수정  3. 배우 목록 수정");
        String command = scanner.nextLine();
        switch(command){
            case "1" :
                movieDetailUpdate();
                break;
            
            case "2" :
                //movieDirectorUpdate();
                break;

            case "3":
                //movieActorsUpdate();
                break;
        }
    }

    private void movieDetailUpdate(){
        System.out.print("수정할 영화 ID 입력: ");
        Long id = Long.valueOf(scanner.nextLine());

        System.out.print("새 제목 (변경 없으면 Enter): ");
        String name = scanner.nextLine();
        if (name.isBlank()) name = null;

        System.out.print("새 장르 (ACTION, ROMANCE... / 변경 없으면 Enter): ");
        String genreStr = scanner.nextLine();
        Genre genre = null;
        if (!genreStr.isBlank()) {
            genre = Genre.valueOf(genreStr.toUpperCase()); // 문자열 -> Enum 변환
        }
        
        System.out.print("새 설명 (변경 없으면 Enter): ");
        String description = scanner.nextLine();
        if (description.isBlank()) description = null;

        System.out.print("새 개봉일 (YYYY-MM-DD / 변경 없으면 Enter): ");
        String dateStr = scanner.nextLine();
        LocalDate date = null;
        if (!dateStr.isBlank()) {
            date = LocalDate.parse(dateStr); // 문자열 -> 날짜 변환
        }

        MovieParam parameter = new MovieParam(name,genre,description,date);
        movieService.changeMovieDetail(id,parameter);
    }
        
    
    
}
