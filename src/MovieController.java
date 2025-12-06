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
    private MoviePersonPacadeService moviePersonPacadeService;

    private Scanner scanner;
    public MovieController(MovieService movieService, DirectorService directorService, ActorService actorService, UserService userService, ReviewService reviewService, MoviePersonPacadeService moviePersonPacadeService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.directorService = directorService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.moviePersonPacadeService = moviePersonPacadeService;
        this.scanner = new Scanner(System.in);
    }
    
    public void start(){
        boolean isRunning=true;
        while(isRunning){
            System.out.println("\n================================");
            System.out.println("1.등록  2. 검색  3. 수정  4. 삭제  q. 종료");
            System.out.print("명령 선택 > ");
            String command = scanner.nextLine();
            switch (command) {
                case "1":
                    create();
                    break;
                case "2":
                    read();
                    break;
                case "3":
                    update();
                    break;
                case "4":
                    delete();
                    break;
                case "q":
                    isRunning = false;
                    break;
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////create
    private void create() {
    System.out.println("=== 데이터 등록 메뉴 ===");
    System.out.println("1.영화 등록 2.배우 등록 3.감독 등록 (그 외: 취소)");
    String command = scanner.nextLine();
    switch (command) {
        case "1":
            createMovie();
            break;
        
        case "2":
            createActor();
            break;
        
        case "3":
            createDirector(); 
            break;
        
        default:
            System.out.println("등록 메뉴를 종료합니다.");
            break;
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

    private void createActor() {
        System.out.print("등록할 배우 이름을 입력하세요: ");
        String name = scanner.nextLine();
        actorService.createActor(name); 
    }

    private void createDirector() {
        System.out.print("등록할 감독 이름을 입력하세요: ");
        String name = scanner.nextLine();
        directorService.createDirector(name);
    }
//////////////////////////////////////////////////////////////////////////////read
    private void read(){
        System.out.println("1.영화 조회 2.배우 조회 3.감독 조회");
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                readMovie();
                break;
            
            case "2":
                readActor();
                break;
            
            case "3":
                readDirector();
                break;
            
            default:
                break;
        }
    }

    private void readMovie(){
        List<Movie> Movies = movieService.getMovies();
        for(Movie movie: Movies) System.out.println(movie);
        System.out.println("✅ 전체 영화 목록이 출력되었습니다.");
    }
    private void readActor(){
        List<Actor> actors = actorService.getActors();
        for(Actor actor: actors) System.out.println(actor);
        System.out.println("✅ 전체 배우 목록이 출력되었습니다.");
    }
    private void readDirector(){
        List<Director> directors = directorService.getDirectors();
        for(Director director: directors) System.out.println(director);
        System.out.println("✅ 전체 감독 목록이 출력되었습니다.");
    }

/////////////////////////////////////////////////update
    private void update(){
        System.out.println("1.영화 수정 2.배우 수정 3.감독 수정");
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                updateMovie();
                break;
            
            case "2":
                updateActor();
                break;
            
            case "3":
                updateDirector();
                break;
            
            default:
                break;
        }
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
                movieDirectorUpdate();
                break;

            case "3":
                movieActorsUpdate();
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
    private void movieDirectorUpdate(){
        System.out.println("\n[감독 수정]");
        System.out.print("대상 영화 ID 입력: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());

            System.out.print("변경할 감독 이름 입력: ");
            String directorName = scanner.nextLine();
            if (directorName.isBlank()) {
                System.out.println("감독 이름은 비어있을 수 없습니다. 취소합니다.");
                return;
            }

            // Facade 메서드 호출
            moviePersonPacadeService.changeMovieDirector(id, directorName);
            System.out.println("감독 변경이 완료되었습니다.");

        } catch (NumberFormatException e) {
            System.out.println("ID는 숫자여야 합니다.");
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
        }
    }

    // 3. 배우 목록 수정 (추가/삭제 분기 처리)
    private void movieActorsUpdate(){
        System.out.println("\n[배우 목록 수정]");
        System.out.print("대상 영화 ID 입력: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());

            System.out.println("1. 배우 추가  2. 배우 삭제");
            System.out.print("선택 >> ");
            String subCommand = scanner.nextLine();

            if (!subCommand.equals("1") && !subCommand.equals("2")) {
                System.out.println("잘못된 선택입니다.");
                return;
            }

            System.out.print("대상 배우 이름 입력: ");
            String actorName = scanner.nextLine();
            if (actorName.isBlank()) {
                System.out.println("배우 이름은 비어있을 수 없습니다.");
                return;
            }

            if (subCommand.equals("1")) {
                // 배우 추가 Facade 호출
                moviePersonPacadeService.addMovieActor(id, actorName);
                System.out.println("배우 추가 완료.");
            } else {
                // 배우 삭제 Facade 호출
                moviePersonPacadeService.deleteMovieActor(id, actorName);
                System.out.println("배우 삭제 완료.");
            }

        } catch (NumberFormatException e) {
            System.out.println("ID는 숫자여야 합니다.");
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
        }
    }
    

private void updateActor() {
        System.out.println("\n--- 배우 정보 수정 ---");
        
        System.out.print("수정할 배우의 현재 이름을 입력하세요: ");
        String actorName = scanner.nextLine();
        
        System.out.print("변경할 새로운 이름을 입력하세요: ");
        String newName = scanner.nextLine();
        
        actorService.updateActor(actorName, newName);
    }

    private void updateDirector() {
        System.out.println("\n--- 감독 정보 수정 ---");
        
        System.out.print("수정할 감독의 현재 이름을 입력하세요: ");
        String directorName = scanner.nextLine();
        
        System.out.print("변경할 새로운 이름을 입력하세요: ");
        String newName = scanner.nextLine();
        
        directorService.updateDirector(directorName, newName);
    }
////////////////////////////////////////////////////////////////////////delete
    private void delete() {
        System.out.println("1.영화 삭제 2.배우 삭제 3.감독 삭제");
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                deleteMovie();
                break;
            
            case "2":
                deleteActor();
                break;
            
            case "3":
                deleteDirector();
                break;
            
            default:;
                break;
        }
    }
    private void deleteMovie(){
        System.out.println("영화 id를 입력하세요: ");
        Long id = Long.valueOf(scanner.nextLine());
        movieService.deleteMovie(id);
    }       

    private void deleteActor() {
        System.out.println("\n--- 배우 정보 삭제 ---");
        
        System.out.print("삭제할 배우 이름을 입력하세요: ");
        String name = scanner.nextLine();
        
        moviePersonPacadeService.deleteActor(name);
        
        System.out.println("배우 삭제가 완료되었습니다.");
    }

    private void deleteDirector() {
        System.out.println("\n--- 감독 정보 삭제 ---");
        
        System.out.print("삭제할 감독 이름을 입력하세요: ");
        String name = scanner.nextLine();
        
        moviePersonPacadeService.deleteDirector(name);
        
        System.out.println("감독 삭제가 완료되었습니다.");
    }
    
}
