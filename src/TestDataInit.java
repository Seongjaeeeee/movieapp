import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domain.Actor;
import domain.Director;
import domain.Genre;
import service.ActorService;
import service.DirectorService;
import service.MoviePersonFacadeService;
import service.ReviewService;
import service.UserService;

public class TestDataInit {

    private final MoviePersonFacadeService movieFacade;
    private final DirectorService directorService;
    private final ActorService actorService;
    // 아래 두 서비스는 코드를 안 주셨지만, 이전 문맥상 필요할 것 같아 포함했습니다.
    private final UserService userService;
    private final ReviewService reviewService;

    public TestDataInit(MoviePersonFacadeService movieFacade, 
                        DirectorService directorService, 
                        ActorService actorService,
                        UserService userService,
                        ReviewService reviewService) {
        this.movieFacade = movieFacade;
        this.directorService = directorService;
        this.actorService = actorService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    public void init() {
        System.out.println("⏳ [데이터 초기화 시작] 영화 및 인물 데이터 생성 중...");

        // 1. 영화 데이터 생성 (헬퍼 메서드를 통해 ID 추출 및 Facade 호출)
        createMovie("인셉션", "크리스토퍼 놀란", Genre.SF, LocalDate.of(2010,7,21), "꿈 속의 꿈을 탐험하는 SF 액션", "레오나르도 디카프리오", "조셉 고든 레빗");
        createMovie("범죄도시", "강윤성", Genre.ACTION, LocalDate.of(2017,10,3), "통쾌한 형사 액션", "마동석", "윤계상");
        createMovie("오펜하이머","크리스토퍼 놀란",Genre.DRAMA,LocalDate.of(2023, 8, 15),"핵폭탄 개발 프로젝트의 비하인드 스토리", "킬리언 머피", "로버트 다우니 주니어");
        createMovie("동물의 왕국","BBC 감독",Genre.DOCUMENTARY,LocalDate.of(2024, 1, 1),"사자가 사냥하는 다큐멘터리"); // 배우 없음

        createMovie(
            "타이타닉", "제임스 카메론", Genre.ROMANCE, LocalDate.of(1997, 12, 19), 
            "호화 유람선 타이타닉호에서 피어난 운명적인 사랑과 비극", 
            "레오나르도 디카프리오", "케이트 윈슬렛"
        );

        createMovie(
            "기생충", "봉준호", Genre.THRILLER, LocalDate.of(2019, 5, 30), 
            "극과 극의 삶을 사는 두 가족의 만남이 걷잡을 수 없는 사건으로 치닫는다", 
            "송강호", "이선균", "조여정", "최우식", "박소담"
        );

        createMovie(
            "센과 치히로의 행방불명", "미야자키 하야오", Genre.FANTASY, LocalDate.of(2002, 6, 28), 
            "금지된 신들의 세계로 들어간 치히로의 모험", 
            "히이라기 루미"
        );

        createMovie(
            "극한직업", "이병헌", Genre.COMEDY, LocalDate.of(2019, 1, 23), 
            "낮에는 치킨장사, 밤에는 잠복근무! 마약반 형사들의 위장창업", 
            "류승룡", "이하늬", "진선규", "이동휘", "공명"
        );

        createMovie(
            "매트릭스", "라나 워쇼스키", Genre.SF, LocalDate.of(1999, 5, 15), 
            "AI가 지배하는 가상현실 매트릭스에서 깨어난 네오의 사투", 
            "키아누 리브스", "로렌스 피시번"
        );

        createMovie(
            "겟 아웃", "조던 필", Genre.HORROR, LocalDate.of(2017, 5, 17), 
            "여자친구의 시골 본가에 초대받은 남자가 겪는 기이한 공포", 
            "다니엘 칼루야", "앨리슨 윌리암스"
        );

        createMovie(
            "어벤져스", "조스 웨던", Genre.ACTION, LocalDate.of(2012, 4, 26), 
            "지구를 지키기 위해 뭉친 최강의 슈퍼히어로 팀", 
            "로버트 다우니 주니어", "크리스 에반스", "스칼렛 요한슨"
        );

        createMovie(
            "라라랜드", "데이미언 셔젤", Genre.ROMANCE, LocalDate.of(2016, 12, 7), 
            "재즈 피아니스트와 배우 지망생의 꿈과 사랑을 그린 뮤지컬 영화", 
            "라이언 고슬링", "엠마 스톤"
        );

        createMovie(
            "명량", "김한민", Genre.ACTION, LocalDate.of(2014, 7, 30), 
            "12척의 배로 330척의 왜군을 물리친 이순신 장군의 위대한 승리", 
            "최민식", "류승룡", "조진웅"
        );

        createMovie(
            "나이브스 아웃", "라이언 존슨", Genre.DRAMA, LocalDate.of(2019, 12, 4), 
            "베스트셀러 미스터리 작가가 85세 생일에 숨진 채 발견된다", 
            "다니엘 크레이그", "크리스 에반스", "아나 데 아르마스"
        );

        System.out.println("✅ [영화/배우/감독 데이터 생성 완료]");
        
        // 유저/리뷰 서비스가 구현되어 있다면 아래 주석 해제하여 사용
        // createUsersAndReviews();
    }

    // --- Helper Methods (문자열 이름 -> ID 변환 및 중복 방지 로직) ---

    private void createMovie(String title, String directorName, Genre genre, LocalDate date, String desc, String... actorNames) {
        // 1. 감독 ID 준비
        Long directorId = getOrCreateDirectorId(directorName);

        // 2. 배우 ID 목록 준비
        List<Long> actorIdList = new ArrayList<>();
        for (String actorName : actorNames) {
            actorIdList.add(getOrCreateActorId(actorName));
        }

        // 3. Facade 호출 (Long... 가변 인자 처리를 위해 배열로 변환)
        movieFacade.createMovie(title, genre, date, desc, directorId, actorIdList.toArray(new Long[0]));
    }

    private Long getOrCreateDirectorId(String name) {
        // A. 이미 존재하는지 확인
        List<Director> existing = directorService.findAllDirectorsByName(name);
        if (!existing.isEmpty()) {
            return existing.get(0).getId();
        }

        // B. 없으면 생성
        // 주의: createDirector가 void를 반환하므로, 생성 후 다시 조회해야 ID를 얻을 수 있음
        directorService.createDirector(name);
        
        // C. 생성된 감독 재조회하여 ID 반환
        return directorService.findAllDirectorsByName(name).get(0).getId();
    }

    private Long getOrCreateActorId(String name) {
        // A. 이미 존재하는지 확인
        List<Actor> existing = actorService.findAllActorsByName(name);
        if (!existing.isEmpty()) {
            return existing.get(0).getId();
        }

        // B. 없으면 생성
        // ActorService.createActor는 Actor 객체를 반환하므로 바로 ID 추출 가능 (효율적)
        return actorService.createActor(name).getId();
    }
    
    /*
    private void createUsersAndReviews() {
        // 이전 코드와 동일하게 유저 및 리뷰 생성 로직 구현
        System.out.println("⏳ [리뷰 및 유저 데이터 생성 중...]");
        // ...
    }
    */
}