package controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import domain.Genre;
import domain.Movie;
import dto.MovieParam;
import dto.MovieSearchResult;
import service.ActorService;
import service.DirectorService;
import service.MoviePersonFacadeService;
import service.MovieService;
import service.SearchService;

public class MovieController {
    // 조회용 (Read)
    private final MovieService movieService;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final SearchService searchService;
    private final MoviePersonFacadeService facadeService;

    private final Scanner scanner;

    public MovieController(MovieService movieService, DirectorService directorService, ActorService actorService, MoviePersonFacadeService facadeService,SearchService searchService) {
        this.movieService = movieService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.facadeService = facadeService;
        this.searchService = searchService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.println("\n================================");
                System.out.println("1.등록  2.조회/검색  3.수정  4.삭제  q.종료");
                System.out.print("명령 선택 > ");
                String command = scanner.nextLine();

                switch (command) {
                    case "1" -> createMenu();
                    case "2" -> readMenu();
                    case "3" -> updateMenu();
                    case "4" -> deleteMenu();
                    case "q" -> {
                        System.out.println("프로그램을 종료합니다.");
                        isRunning = false;
                    }
                    default -> System.out.println("⚠️ 잘못된 명령입니다.");
                }
            } catch (Exception e) {
                // 예상치 못한 시스템 에러 방지
                System.out.println("⛔ 시스템 에러 발생: " + e.getMessage());
            }
        }
    }

    // ==========================================
    // 1. 등록 (Create)
    // ==========================================
    private void createMenu() {
        System.out.println("\n[등록 메뉴] 1.영화  2.감독  3.배우");
        String subCmd = scanner.nextLine();

        try {
            switch (subCmd) {
                case "1" -> createMovieProcess();
                case "2" -> {
                    System.out.print("감독 이름 입력: ");
                    String name = scanner.nextLine();
                    directorService.createDirector(name);
                    System.out.println("✅ 감독 등록 완료");
                }
                case "3" -> {
                    System.out.print("배우 이름 입력: ");
                    String name = scanner.nextLine();
                    actorService.createActor(name);
                    System.out.println("✅ 배우 등록 완료");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 등록 실패: " + e.getMessage());
        }
    }

    private void createMovieProcess() {
        System.out.println("--- 영화 등록 ---");
        System.out.print("제목: ");
        String name = scanner.nextLine();
        
        System.out.print("장르 (ACTION, ROMANCE, COMEDY...): ");
        Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase()); // Enum 변환 예외 처리 필요

        System.out.print("개봉일 (YYYY-MM-DD): ");
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine());

        System.out.print("설명: ");
        String description = scanner.nextLine();

        System.out.print("감독 ID: ");
        Long directorId = Long.parseLong(scanner.nextLine());

        System.out.print("출연 배우 ID들 (콤마로 구분, 예: 1,2,3): ");
        String[] actorIdsStr = scanner.nextLine().split(",");
        
        // String[] -> Long[] 변환
        Long[] actorIds = Arrays.stream(actorIdsStr)
                .map(String::trim)
                .map(Long::parseLong)
                .toArray(Long[]::new);

        // ★ Facade 호출
        facadeService.createMovie(name, genre, releaseDate, description, directorId, actorIds);
        System.out.println("✅ 영화 및 관계 설정 완료!");
    }

    // ==========================================
    // 2. 조회 (Read) - Service 직접 호출
    // ==========================================
    private void readMenu() {
    // 5번 메뉴 추가
    System.out.println("\n[조회 메뉴] 1.전체 영화  2.전체 감독  3.전체 배우  4.영화 상세(ID)  5.통합 검색");
    String subCmd = scanner.nextLine();

    try {
        switch (subCmd) {
            case "1" -> {
                List<Movie> movies = movieService.findAllMovies();
                if (movies.isEmpty()) System.out.println("등록된 영화가 없습니다.");
                else movies.forEach(System.out::println);
            }
            case "2" -> directorService.findAllDirectors().forEach(System.out::println);
            case "3" -> actorService.findAllActors().forEach(System.out::println);
            case "4" -> {
                System.out.print("영화 ID 입력: ");
                Long id = Long.parseLong(scanner.nextLine());
                Movie movie = movieService.getMovieById(id);
                System.out.println(movie); 
            }
            // --- [추가된 통합 검색 로직] ---
            case "5" -> {
                System.out.print("검색어 입력: ");
                String keyword = scanner.nextLine();

                if (keyword.isBlank()) {
                    System.out.println("⚠️ 검색어를 입력해주세요.");
                    break;
                }

                // searchAllMovie가 포함된 서비스 객체 호출 (facadeService라고 가정)
                List<MovieSearchResult> results = searchService.searchAllMovie(keyword);

                if (results.isEmpty()) {
                    System.out.println("🔍 '" + keyword + "'에 대한 검색 결과가 없습니다.");
                } else {
                    System.out.println("=== 🔍 검색 결과 (" + results.size() + "건) ===");
                    for (MovieSearchResult result : results) {
                        System.out.println(result); 
                    }
                }
            }
            default -> System.out.println("❌ 잘못된 메뉴 선택입니다.");
        }
    } catch (IllegalArgumentException e) { 
        System.out.println("❌ 조회 실패: " + e.getMessage());
    } catch (Exception e) { // 숫자가 아닌 값 입력 등 기타 예외 처리
        System.out.println("❌ 오류 발생: " + e.getMessage());
    }
}
    // ==========================================
    // 3. 수정 (Update)
    // ==========================================
    private void updateMenu() {
        System.out.println("\n[수정 메뉴] 1.영화 기본정보  2.영화 감독 교체  3.영화에 배우 추가  4.영화에서 배우 제거");
        String subCmd = scanner.nextLine();

        try {
            switch (subCmd) {
                case "1" -> {
                    System.out.print("수정할 영화 ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.print("새 제목(엔터 건너뛰기): ");
                    String name = scanner.nextLine();
                    // ... 나머지 입력 로직 생략 (MovieParam 생성) ...
                    // 편의상 제목만 바꾼다고 가정: 다른 필드는 null로 전달하여 무시되게 함
                    MovieParam param = new MovieParam(name.isBlank() ? null : name, null, null, null);
                    movieService.updateMovieInfo(id, param);
                    System.out.println("✅ 영화 정보 수정 완료");
                }
                case "2" -> {
                    System.out.print("영화 ID: ");
                    Long movieId = Long.parseLong(scanner.nextLine());
                    System.out.print("새 감독 ID: ");
                    Long directorId = Long.parseLong(scanner.nextLine());
                    
                    facadeService.updateMovieDirector(movieId, directorId);
                    System.out.println("✅ 감독 교체 완료");
                }
                case "3" -> {
                    System.out.print("영화 ID: ");
                    Long movieId = Long.parseLong(scanner.nextLine());
                    System.out.print("추가할 배우 ID: ");
                    Long actorId = Long.parseLong(scanner.nextLine());
                    
                    facadeService.addActorToMovie(movieId, actorId);
                    System.out.println("✅ 배우 추가 완료");
                }
                case "4" -> {
                    System.out.print("영화 ID: ");
                    Long movieId = Long.parseLong(scanner.nextLine());
                    System.out.print("제거할 배우 ID: ");
                    Long actorId = Long.parseLong(scanner.nextLine());
                    
                    facadeService.removeActorFromMovie(movieId, actorId);
                    System.out.println("✅ 배우 제거 완료");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ ID는 숫자로 입력해주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 수정 실패: " + e.getMessage());
        }
    }

    // ==========================================
    // 4. 삭제 (Delete) - Facade 활용
    // ==========================================
    private void deleteMenu() {
        System.out.println("\n[삭제 메뉴] 1.영화  2.감독  3.배우");
        System.out.println("⚠️ 주의: 감독/배우 삭제 시 관련 영화 정보가 수정될 수 있습니다.");
        String subCmd = scanner.nextLine();

        try {
            System.out.print("삭제할 ID 입력: ");
            Long id = Long.parseLong(scanner.nextLine());

            switch (subCmd) {
                case "1" -> {
                    movieService.deleteMovie(id);
                    System.out.println("✅ 영화 삭제 완료");
                }
                case "2" -> {
                    // ★ Facade 호출: 영화 내 감독 익명 처리 후 삭제
                    facadeService.deleteDirector(id);
                    System.out.println("✅ 감독 삭제 및 영화 정보 갱신 완료");
                }
                case "3" -> {
                    // ★ Facade 호출: 영화 내 배우 명단에서 제거 후 삭제
                    facadeService.deleteActor(id);
                    System.out.println("✅ 배우 삭제 및 출연 목록 정리 완료");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ ID는 숫자로 입력해주세요.");
        } catch (IllegalArgumentException e) {
            // "시스템 기본 감독은 삭제할 수 없습니다" 등의 메시지가 여기서 출력됨
            System.out.println("❌ 삭제 실패: " + e.getMessage());
        }
    }
}