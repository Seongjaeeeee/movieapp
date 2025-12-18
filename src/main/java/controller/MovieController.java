package controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import domain.Genre;
import domain.Movie;
import domain.Review;
import domain.User;
import dto.MovieParam;
import dto.MovieSearchResult;
import dto.PersonSearchResult;
import service.ActorService;
import service.AuthFacade;
import service.DirectorService;
import service.MoviePersonFacadeService;
import service.MovieService;
import service.ReviewService;
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
    private final ReviewService reviewService;
    private final Scanner scanner;

    public MovieController(MovieService movieService, 
                           DirectorService directorService, 
                           ActorService actorService, 
                           MoviePersonFacadeService moviepersonfacadeService,
                           SearchService searchService,
                           AuthFacade authFacade,
                           Session session,ReviewService reviewService) {      
        this.movieService = movieService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.moviepersonfacadeService = moviepersonfacadeService;
        this.searchService = searchService;
        this.authFacade = authFacade;
        this.session = session;
        this.reviewService = reviewService;
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
        while (isRunning && authFacade.isLoggedIn()) {
            try {
                System.out.println("\n[관리자 모드] ========================");
                // ★ 6번 메뉴 추가: 내 리뷰 관리
                System.out.println("1.등록  2.조회  3.수정  4.삭제  5.로그아웃  6.내 리뷰 관리");
                System.out.print("관리자 명령 > ");
                String command = scanner.nextLine().trim();

                switch (command) {
                    case "1" -> createMenu();
                    case "2" -> adminReadMenu();
                    case "3" -> updateMenu();
                    case "4" -> deleteMenu();
                    case "5" -> { authFacade.logout(); isRunning = false; }
                    case "6" -> manageMyAllReviewsProcess(); // ★ 신규 기능 연결 (유저와 동일 로직)
                    default -> System.out.println("⚠️ 잘못된 명령입니다.");
                }
            } catch (Exception e) {
                System.out.println("⛔ " + e.getMessage());
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
                // ★ 4번 메뉴 추가: 내 리뷰 관리
                System.out.println("1.영화검색  2.인물검색  3.영화상세조회  4.내 리뷰 관리  5.로그아웃");
                System.out.print("사용자 명령 > ");
                String command = scanner.nextLine().trim();

                switch (command) {
                    case "1" -> searchMovieProcess();
                    case "2" -> searchPersonProcess();
                    case "3" -> viewMovieDetailProcess();
                    case "4" -> manageMyAllReviewsProcess();
                    case "5" -> {
                        authFacade.logout();
                        System.out.println("로그아웃 되었습니다.");
                        isRunning = false;
                    }
                    default -> System.out.println("⚠️ 잘못된 명령입니다.");
                }
            } catch (Exception e) {
                System.out.println("⛔ 시스템 에러: " + e.getMessage());
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
    private void searchMovieProcess() {
        System.out.print("영화 검색어 입력: ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) throw new IllegalArgumentException("검색어를 입력해야 합니다.");

        List<MovieSearchResult> results = searchService.searchAllMovie(keyword);
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
    // ★ [Core Logic] 상세 조회 + 리뷰 통합
    // ==========================================
    private void viewMovieDetailProcess() {
        System.out.println("\n--- 영화 상세 조회 ---");
        System.out.print("영화 ID 입력: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return;

        Long movieId;
        try {
            movieId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID는 숫자여야 합니다.");
            return;
        }

        try {
            // 1. 영화 정보 조회 및 출력
            Movie movie = movieService.getMovieById(movieId);
            System.out.println("\n========================================");
            System.out.println(movie); // 영화 상세 정보 출력
            System.out.println("========================================");

            // 2. 내부 루프 진입 (리뷰 조회/작성/수정)
            boolean inDetailMenu = true;
            while (inDetailMenu) {
                // 현재 영화의 리뷰 목록 출력
                printReviewsForMovie(movieId);

                System.out.println("\n[메뉴] 1.리뷰작성  2.뒤로가기");
                System.out.print("선택 > ");
                String cmd = scanner.nextLine().trim();

                switch (cmd) {
                    case "1" -> createReviewProcess(movieId); // ID 전달
                    case "2" -> inDetailMenu = false; // 루프 종료
                    default -> System.out.println("⚠️ 잘못된 선택입니다.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 영화를 찾을 수 없습니다: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⛔ 오류 발생: " + e.getMessage());
        }
    }

    //리뷰 목록 출력
    private void printReviewsForMovie(Long movieId) {
        List<Review> reviews = reviewService.findReviewByMovieId(movieId);
        System.out.println("\n--- 💬 User Reviews (" + reviews.size() + ") ---");
        if (reviews.isEmpty()) {
            System.out.println("   (아직 작성된 리뷰가 없습니다. 첫 리뷰를 남겨보세요!)");
        } else {
            for (Review r : reviews) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------------------");
    }

    // [Action] 리뷰 작성 (영화 ID를 이미 알고 있음)
    private void createReviewProcess(Long movieId) {
        System.out.println("\n[📝 리뷰 작성]");
        try {
            System.out.print("평점 (1~5): ");
            int rating = Integer.parseInt(scanner.nextLine().trim());
            if (rating < 1 || rating > 5) throw new IllegalArgumentException("1~5 사이 숫자를 입력하세요.");

            System.out.print("내용: ");
            String content = scanner.nextLine().trim();

            User user = authFacade.getUser();
            reviewService.createReview(content, rating, user, movieId);
            System.out.println("✅ 리뷰가 등록되었습니다!");

        } catch (NumberFormatException e) {
            System.out.println("❌ 평점은 숫자여야 합니다.");
        } catch (Exception e) {
            System.out.println("❌ 리뷰 등록 실패: " + e.getMessage());
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
//////////////////////////////////////////////////////////////////////////////////////////////리뷰기능
    private void manageMyAllReviewsProcess() {
        boolean inMyReview = true;
        User me = authFacade.getUser(); // 현재 로그인한 유저

        while (inMyReview) {
            // 1. 내 리뷰 전체 조회 (서비스 호출)
            List<Review> myReviews = reviewService.findReviewByUser(me);

            System.out.println("\n===== [📂 내 리뷰 보관함] (" + myReviews.size() + "건) =====");
            if (myReviews.isEmpty()) {
                System.out.println("   (작성한 리뷰가 없습니다)");
            } else {
                for (Review r : myReviews) {
                    System.out.println(r);
                }
            }
            System.out.println("==========================================");
            System.out.println("1.리뷰수정  2.리뷰삭제  3.뒤로가기");
            System.out.print("선택 > ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("3")) {
                inMyReview = false;
                continue;
            }
            if (myReviews.isEmpty()) {
                System.out.println("⚠️ 수정/삭제할 리뷰가 없습니다.");
                continue;
            }
            //메인로직
            try {
                System.out.print("대상 리뷰 ID 입력: ");
                Long reviewId = parseLongInput(scanner.nextLine().trim(), "리뷰 ID");

                if (cmd.equals("1")) {
                    // 수정 로직
                    System.out.print("새 평점 (1~5): ");
                    int rating = Integer.parseInt(scanner.nextLine().trim());
                    if(rating < 1 || rating > 5) throw new IllegalArgumentException("1~5 사이 입력");
                    
                    System.out.print("새 내용: ");
                    String content = scanner.nextLine().trim();
                    if(content.isEmpty()) throw new IllegalArgumentException("내용 입력 필수");

                    reviewService.updateReview(content, rating, reviewId, me);
                    System.out.println("✅ 수정되었습니다.");

                } else if (cmd.equals("2")) {
                    // 삭제 로직
                    System.out.print("정말 삭제하시겠습니까? (y/n): ");
                    String confirm = scanner.nextLine().trim();
                    if (confirm.equalsIgnoreCase("y")) {
                        reviewService.deleteReview(reviewId, me);
                        System.out.println("✅ 삭제되었습니다.");
                    } else {
                        System.out.println("취소되었습니다.");
                    }
                } else {
                    System.out.println("⚠️ 잘못된 선택입니다.");
                }

            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자를 입력해주세요.");
            } catch (IllegalStateException e) {
                System.out.println("🚫 권한 오류: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⛔ 처리 중 오류: " + e.getMessage());
            }
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
/*현재 while로 감싸진 페이지들
1.로그인
2.유저/관리자 기능 페이지
3.영화 상세페이지 -> 리뷰 작성위함
4.내 리뷰 관리페이지
 */