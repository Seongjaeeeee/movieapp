package controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import domain.Genre;
import domain.Movie;
import dto.ActorSearchResult;
import dto.MovieParam;
import dto.PersonSearchResult;
import service.ActorService;
import service.AuthFacade;
import service.DirectorService;
import service.MoviePersonFacadeService;
import service.MovieService;
import service.SearchService;
import service.Session;

public class MovieController {
    private final MovieService movieService;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final SearchService searchService;
    private final MoviePersonFacadeService moviepersonfacadeService;
    private final AuthFacade authFacade;
    private final Session session;
    private final Scanner scanner;

    public MovieController(MovieService movieService, 
                           DirectorService directorService, 
                           ActorService actorService, 
                           MoviePersonFacadeService moviepersonfacadeService,
                           SearchService searchService,
                           AuthFacade authFacade,
                           Session session) {      
        this.movieService = movieService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.moviepersonfacadeService = moviepersonfacadeService;
        this.searchService = searchService;
        this.authFacade = authFacade;
        this.session = session;
        this.scanner = new Scanner(System.in);
    }
// ==========================================
    // [Main Entry] 프로그램 시작
    // ==========================================
    public void start() {
        boolean isProgramRunning = true;

        while (isProgramRunning) {
            if (!authFacade.isLoggedIn()) {
                isProgramRunning = authLoop(); // 로그인 안되어있으면 로그인/가입 화면으로
            } else {
                // ★ 핵심 변경: 권한에 따라 다른 루프로 진입
                if (authFacade.isAdmin()) {
                    adminMainLoop();
                } else {
                    userMainLoop();
                }
            }
        }
        System.out.println("프로그램이 완전히 종료되었습니다.");
    }

    // ==========================================
    // 로그인/회원가입 루프 (기존 유지)
    // ==========================================
    private boolean authLoop() {
        System.out.println("\n========== USER AUTH ==========");
        System.out.println("1.로그인  2.회원가입  q.종료");
        System.out.print("선택 > ");
        String command = scanner.nextLine().trim();
        try {
            switch (command) {
                case "1" -> {
                    System.out.print("ID: ");
                    String id = scanner.nextLine().trim();
                    if (id.isEmpty()) throw new IllegalArgumentException("ID를 입력해주세요.");
                    System.out.print("PW: ");
                    String pw = scanner.nextLine().trim();
                    if (pw.isEmpty()) throw new IllegalArgumentException("비밀번호를 입력해주세요.");
                    
                    authFacade.login(id, pw); 
                    System.out.println("✅ 로그인 성공!");
                    return true; 
                }
                case "2" -> {
                    System.out.print("가입할 ID: ");
                    String id = scanner.nextLine().trim();
                    if (id.isEmpty()) throw new IllegalArgumentException("ID는 공백일 수 없습니다.");
                    System.out.print("가입할 PW: ");
                    String pw = scanner.nextLine().trim();
                    if (pw.isEmpty()) throw new IllegalArgumentException("비밀번호는 공백일 수 없습니다.");

                    authFacade.createUser(id, pw);
                    System.out.println("✅ 회원가입 완료! 로그인해주세요.");
                    return true;
                }
                case "q" -> { return false; }
                default -> System.out.println("⚠️ 잘못된 명령입니다.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("⛔ " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("🚫 처리 불가: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⛔ 시스템 오류 발생: " + e.getMessage());
        }
        return true; 
    }

    // ==========================================
    // [Admin Loop] 관리자 전용 (모든 기능)
    // ==========================================
    private void adminMainLoop() {
        boolean isRunning = true;
        // 로그아웃되거나 메뉴에서 나가기를 선택할 때까지 반복
        while (isRunning && authFacade.isLoggedIn()) {
            try {
                System.out.println("\n[관리자 모드] ========================");
                System.out.println("1.등록  2.조회(전체/검색)  3.수정  4.삭제  5.로그아웃");
                System.out.print("관리자 명령 > ");
                String command = scanner.nextLine().trim();

                switch (command) {
                    case "1" -> createMenu();      // 기존 등록 메뉴
                    case "2" -> adminReadMenu();   // ★ 관리자용 조회 메뉴 (전체조회 포함)
                    case "3" -> updateMenu();      // 기존 수정 메뉴
                    case "4" -> deleteMenu();      // 기존 삭제 메뉴
                    case "5" -> {
                        authFacade.logout();
                        System.out.println("로그아웃 되었습니다.");
                        isRunning = false; 
                    }
                    default -> System.out.println("⚠️ 잘못된 명령입니다.");
                }
            } catch (Exception e) {
                System.out.println("⛔ 시스템 에러 발생: " + e.getMessage());
            }
        }
    }

    // ==========================================
    // [User Loop] 일반 유저 전용 (검색만 가능)
    // ==========================================
    private void userMainLoop() {
        boolean isRunning = true;
        while (isRunning && authFacade.isLoggedIn()) {
            try {
                System.out.println("\n[일반 사용자 모드] ====================");
                System.out.println("1.영화검색  2.인물검색  3.영화상세조회(ID)  4.로그아웃");
                System.out.print("사용자 명령 > ");
                String command = scanner.nextLine().trim();

                switch (command) {
                    case "1" -> searchMovieProcess();     // 검색 로직 (공통)
                    case "2" -> searchPersonProcess();    // 인물 검색 (공통)
                    case "3" -> viewMovieDetailProcess(); // 상세 조회 (공통)
                    case "4" -> {
                        authFacade.logout();
                        System.out.println("로그아웃 되었습니다.");
                        isRunning = false;
                    }
                    default -> System.out.println("⚠️ 잘못된 명령입니다.");
                }
            } catch (Exception e) {
                System.out.println("⛔ 시스템 에러 발생: " + e.getMessage());
            }
        }
    }

    // ==========================================
    // 1. 등록 (Create) - 관리자 전용
    // ==========================================
    private void createMenu() {
        System.out.println("\n[등록 메뉴] 1.영화  2.감독  3.배우");
        System.out.print("선택 > ");
        String subCmd = scanner.nextLine().trim();

        if (subCmd.isEmpty()) return; 

        try {
            switch (subCmd) {
                case "1" -> createMovieProcess();
                case "2" -> {
                    System.out.print("감독 이름 입력: ");
                    String name = scanner.nextLine().trim();
                    if (name.isEmpty()) throw new IllegalArgumentException("이름을 입력해야 합니다.");
                    directorService.createDirector(name);
                    System.out.println("✅ 감독 등록 완료");
                }
                case "3" -> {
                    System.out.print("배우 이름 입력: ");
                    String name = scanner.nextLine().trim();
                    if (name.isEmpty()) throw new IllegalArgumentException("이름을 입력해야 합니다.");
                    actorService.createActor(name);
                    System.out.println("✅ 배우 등록 완료");
                }
                default -> System.out.println("⚠️ 1, 2, 3번 중에서 선택해주세요.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 등록 실패: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⛔ 오류 발생: " + e.getMessage());
        }
    }

    private void createMovieProcess() {
        System.out.println("--- 영화 등록 ---");
        System.out.print("제목: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) throw new IllegalArgumentException("영화 제목은 필수입니다.");

        System.out.print("장르 (ACTION, ROMANCE, COMEDY...): ");
        String genreInput = scanner.nextLine().trim().toUpperCase();
        Genre genre;
        try {
            genre = Genre.valueOf(genreInput);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 장르입니다. (ACTION, ROMANCE 등)");
        }

        System.out.print("개봉일 (YYYY-MM-DD): ");
        LocalDate releaseDate;
        try {
            releaseDate = LocalDate.parse(scanner.nextLine().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다.");
        }

        System.out.print("설명: ");
        String description = scanner.nextLine().trim();

        System.out.print("감독 ID: ");
        Long directorId;
        try {
            directorId = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("감독 ID는 숫자여야 합니다.");
        }

        System.out.print("출연 배우 ID들 (콤마로 구분): ");
        String actorInput = scanner.nextLine().trim();
        Long[] actorIds;
        if (actorInput.isEmpty()) {
            actorIds = new Long[0];
        } else {
            try {
                actorIds = Arrays.stream(actorInput.split(","))
                        .map(String::trim)
                        .map(Long::parseLong)
                        .toArray(Long[]::new);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("배우 ID는 콤마(,)로 구분된 숫자여야 합니다.");
            }
        }

        moviepersonfacadeService.createMovie(name, genre, releaseDate, description, directorId, actorIds);
        System.out.println("✅ 영화 및 관계 설정 완료!");
    }

    // ==========================================
    // 2. 조회 (Read) - 관리자용 (전체 조회 포함)
    // ==========================================
    private void adminReadMenu() {
        System.out.println("\n[관리자 조회] 1.전체영화  2.전체감독  3.전체배우  4.영화상세  5.영화검색  6.인물검색");
        System.out.print("선택 > ");
        String subCmd = scanner.nextLine().trim();

        if (subCmd.isEmpty()) return;

        try {
            switch (subCmd) {
                case "1" -> {
                    List<Movie> movies = movieService.findAllMovies();
                    if (movies.isEmpty()) System.out.println("📭 등록된 영화가 없습니다.");
                    else movies.forEach(System.out::println);
                }
                case "2" -> {
                    var list = directorService.findAllDirectors();
                    if (list.isEmpty()) System.out.println("📭 등록된 감독이 없습니다.");
                    else list.forEach(System.out::println);
                }
                case "3" -> {
                    var list = actorService.findAllActors();
                    if (list.isEmpty()) System.out.println("📭 등록된 배우가 없습니다.");
                    else list.forEach(System.out::println);
                }
                case "4" -> viewMovieDetailProcess(); // 공통 메서드 호출
                case "5" -> searchMovieProcess();     // 공통 메서드 호출
                case "6" -> searchPersonProcess();    // 공통 메서드 호출
                default -> System.out.println("⚠️ 1~6번 중에서 선택해주세요.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 조회 실패: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⛔ 오류 발생: " + e.getMessage());
        }
    }

    // ---------------------------------------------------
    // [공통 로직] 유저/관리자 공유 프로세스 (중복 제거)
    // ---------------------------------------------------

    private void viewMovieDetailProcess() {
        System.out.print("영화 ID 입력: ");
        String idInput = scanner.nextLine().trim();
        if (idInput.isEmpty()) throw new IllegalArgumentException("ID를 입력해주세요.");
        
        Long id;
        try {
            id = Long.parseLong(idInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID는 숫자여야 합니다.");
        }
        Movie movie = movieService.getMovieById(id);
        System.out.println(movie);
    }

    private void searchMovieProcess() {
        System.out.print("영화 검색어 입력: ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) throw new IllegalArgumentException("검색어를 입력해야 합니다.");

        List<ActorSearchResult> results = searchService.searchAllMovie(keyword);
        if (results.isEmpty()) {
            System.out.println("🔍 '" + keyword + "'에 대한 영화 검색 결과가 없습니다.");
        } else {
            System.out.println("=== 🔍 영화 검색 결과 (" + results.size() + "건) ===");
            results.forEach(System.out::println);
        }
    }

    private void searchPersonProcess() {
        System.out.print("인물(배우/감독) 검색어 입력: ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) throw new IllegalArgumentException("검색어를 입력해야 합니다.");

        PersonSearchResult personResult = searchService.searchPerson(keyword);
        boolean foundAny = false;

        if (!personResult.getActors().isEmpty()) {
            System.out.println("\n--- [배우] 검색 결과 ---");
            personResult.getActors().forEach(System.out::println);
            foundAny = true;
        }
        if (!personResult.getDirectors().isEmpty()) {
            System.out.println("\n--- [감독] 검색 결과 ---");
            personResult.getDirectors().forEach(System.out::println);
            foundAny = true;
        }

        if (!foundAny) {
            System.out.println("🔍 '" + keyword + "'에 대한 인물 검색 결과가 없습니다.");
        }
    }

    // ==========================================
    // 3. 수정 (Update) - 관리자 전용
    // ==========================================
    private void updateMenu() {
        System.out.println("\n[수정 메뉴] 1.영화정보  2.감독교체  3.배우추가  4.배우제거  5.감독이름수정  6.배우이름수정");
        System.out.print("선택 > ");
        String subCmd = scanner.nextLine().trim();

        if (subCmd.isEmpty()) return;

        try {
            switch (subCmd) {
                case "1" -> {
                    System.out.print("수정할 영화 ID: ");
                    Long id = parseLongInput(scanner.nextLine().trim(), "영화 ID");
                    System.out.print("새 제목(엔터 건너뛰기): ");
                    String name = scanner.nextLine().trim();
                    MovieParam param = new MovieParam(name.isEmpty() ? null : name, null, null, null);
                    movieService.updateMovieInfo(id, param);
                    System.out.println("✅ 영화 정보 수정 완료");
                }
                case "2" -> {
                    System.out.print("영화 ID: ");
                    Long movieId = parseLongInput(scanner.nextLine().trim(), "영화 ID");
                    System.out.print("새 감독 ID: ");
                    Long directorId = parseLongInput(scanner.nextLine().trim(), "감독 ID");
                    moviepersonfacadeService.updateMovieDirector(movieId, directorId);
                    System.out.println("✅ 감독 교체 완료");
                }
                case "3" -> {
                    System.out.print("영화 ID: ");
                    Long movieId = parseLongInput(scanner.nextLine().trim(), "영화 ID");
                    System.out.print("추가할 배우 ID: ");
                    Long actorId = parseLongInput(scanner.nextLine().trim(), "배우 ID");
                    moviepersonfacadeService.addActorToMovie(movieId, actorId);
                    System.out.println("✅ 배우 추가 완료");
                }
                case "4" -> {
                    System.out.print("영화 ID: ");
                    Long movieId = parseLongInput(scanner.nextLine().trim(), "영화 ID");
                    System.out.print("제거할 배우 ID: ");
                    Long actorId = parseLongInput(scanner.nextLine().trim(), "배우 ID");
                    moviepersonfacadeService.removeActorFromMovie(movieId, actorId);
                    System.out.println("✅ 배우 제거 완료");
                }
                case "5" -> {
                    System.out.print("수정할 감독 ID: ");
                    Long directorId = parseLongInput(scanner.nextLine().trim(), "감독 ID");
                    System.out.print("새 감독 이름: ");
                    String newName = scanner.nextLine().trim();
                    if (newName.isEmpty()) throw new IllegalArgumentException("변경할 이름을 입력해주세요.");
                    directorService.updateDirector(directorId, newName);
                    System.out.println("✅ 감독 이름 수정 완료");
                }
                case "6" -> {
                    System.out.print("수정할 배우 ID: ");
                    Long actorId = parseLongInput(scanner.nextLine().trim(), "배우 ID");
                    System.out.print("새 배우 이름: ");
                    String newName = scanner.nextLine().trim();
                    if (newName.isEmpty()) throw new IllegalArgumentException("변경할 이름을 입력해주세요.");
                    actorService.updateActor(actorId, newName);
                    System.out.println("✅ 배우 이름 수정 완료");
                }
                default -> System.out.println("⚠️ 1~6번 중에서 선택해주세요.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 수정 실패: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⛔ 오류 발생: " + e.getMessage());
        }
    }

    // ==========================================
    // 4. 삭제 (Delete) - 관리자 전용
    // ==========================================
    private void deleteMenu() {
        System.out.println("\n[삭제 메뉴] 1.영화  2.감독  3.배우");
        System.out.println("⚠️ 주의: 감독/배우 삭제 시 관련 영화 정보가 수정될 수 있습니다.");
        System.out.print("선택 > ");
        String subCmd = scanner.nextLine().trim();

        if (subCmd.isEmpty()) return;

        if (!subCmd.equals("1") && !subCmd.equals("2") && !subCmd.equals("3")) {
            System.out.println("⚠️ 1, 2, 3번 중에서 선택해주세요.");
            return;
        }

        try {
            System.out.print("삭제할 ID 입력: ");
            Long id = parseLongInput(scanner.nextLine().trim(), "삭제할 ID");

            switch (subCmd) {
                case "1" -> {
                    movieService.deleteMovie(id);
                    System.out.println("✅ 영화 삭제 완료");
                }
                case "2" -> {
                    moviepersonfacadeService.deleteDirector(id);
                    System.out.println("✅ 감독 삭제 및 영화 정보 갱신 완료");
                }
                case "3" -> {
                    moviepersonfacadeService.deleteActor(id);
                    System.out.println("✅ 배우 삭제 및 출연 목록 정리 완료");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 삭제 실패: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⛔ 오류 발생: " + e.getMessage());
        }
    }

    // [Helper] 숫자 파싱
    private Long parseLongInput(String input, String fieldName) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException(fieldName + "를 입력해주세요.");
        }
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + "는 숫자여야 합니다.");
        }
    }
}