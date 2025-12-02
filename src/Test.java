import java.time.LocalDate;

import domain.Genre;
import service.MovieService;
import service.ReviewService;
import service.UserService;
public class Test {
    public void init(MovieService movieService, ReviewService reviewService, UserService userService) {
        movieService.createMovie("인셉션", "놀란", Genre.SF, LocalDate.of(2010,7,21), "설명", "디카프리오", "조셉");
        movieService.createMovie("범죄도시", "강윤성", Genre.ACTION, LocalDate.of(2017,10,3), "설명", "마동석", "윤계상");
        movieService.createMovie("오펜하이머","크리스토퍼 놀란",Genre.DRAMA,LocalDate.of(2023, 8, 15),"핵폭탄 개발 프로젝트의 비하인드 스토리", "킬리언 머피");
        movieService.createMovie("동물의 왕국","BBC 감독",Genre.DOCUMENTARY,LocalDate.of(2024, 1, 1),"사자가 사냥하는...");
        //1. [로맨스] 타이타닉 (고전 명작, 주연 2명)
        movieService.createMovie(
            "타이타닉", 
            "제임스 카메론", 
            Genre.ROMANCE, 
            LocalDate.of(1997, 12, 19), 
            "호화 유람선 타이타닉호에서 피어난 운명적인 사랑과 비극", 
            "레오나르도 디카프리오", "케이트 윈슬렛"
        );

        // 2. [스릴러/드라마] 기생충 (많은 배우 테스트)
        movieService.createMovie(
            "기생충", 
            "봉준호", 
            Genre.THRILLER, 
            LocalDate.of(2019, 5, 30), 
            "극과 극의 삶을 사는 두 가족의 만남이 걷잡을 수 없는 사건으로 치닫는다", 
            "송강호", "이선균", "조여정", "최우식", "박소담"
        );

        // 3. [판타지/애니] 센과 치히로의 행방불명 (성우 1명 예시)
        movieService.createMovie(
            "센과 치히로의 행방불명", 
            "미야자키 하야오", 
            Genre.FANTASY, 
            LocalDate.of(2002, 6, 28), 
            "금지된 신들의 세계로 들어간 치히로의 모험", 
            "히이라기 루미"
        );

        // 4. [코미디] 극한직업 (한국 코미디)
        movieService.createMovie(
            "극한직업", 
            "이병헌", 
            Genre.COMEDY, 
            LocalDate.of(2019, 1, 23), 
            "낮에는 치킨장사, 밤에는 잠복근무! 마약반 형사들의 위장창업", 
            "류승룡", "이하늬", "진선규", "이동휘", "공명"
        );

        // 5. [SF/액션] 매트릭스 (고전 SF)
        movieService.createMovie(
            "매트릭스", 
            "라나 워쇼스키", 
            Genre.SF, 
            LocalDate.of(1999, 5, 15), 
            "AI가 지배하는 가상현실 매트릭스에서 깨어난 네오의 사투", 
            "키아누 리브스", "로렌스 피시번"
        );

        // 6. [공포] 겟 아웃 (현대 공포)
        movieService.createMovie(
            "겟 아웃", 
            "조던 필", 
            Genre.HORROR, 
            LocalDate.of(2017, 5, 17), 
            "여자친구의 시골 본가에 초대받은 남자가 겪는 기이한 공포", 
            "다니엘 칼루야", "앨리슨 윌리암스"
        );

        // 7. [액션] 어벤져스 (히어로물)
        movieService.createMovie(
            "어벤져스", 
            "조스 웨던", 
            Genre.ACTION, 
            LocalDate.of(2012, 4, 26), 
            "지구를 지키기 위해 뭉친 최강의 슈퍼히어로 팀", 
            "로버트 다우니 주니어", "크리스 에반스", "스칼렛 요한슨"
        );

        // 8. [음악/로맨스] 라라랜드
        movieService.createMovie(
            "라라랜드", 
            "데이미언 셔젤", 
            Genre.ROMANCE, 
            LocalDate.of(2016, 12, 7), 
            "재즈 피아니스트와 배우 지망생의 꿈과 사랑을 그린 뮤지컬 영화", 
            "라이언 고슬링", "엠마 스톤"
        );

        // 9. [전쟁/액션] 명량 (한국 역대 1위)
        movieService.createMovie(
            "명량", 
            "김한민", 
            Genre.ACTION, 
            LocalDate.of(2014, 7, 30), 
            "12척의 배로 330척의 왜군을 물리친 이순신 장군의 위대한 승리", 
            "최민식", "류승룡", "조진웅"
        );

        // 10. [미스터리] 나이브스 아웃
        movieService.createMovie(
            "나이브스 아웃", 
            "라이언 존슨", 
            Genre.DRAMA, 
            LocalDate.of(2019, 12, 4), 
            "베스트셀러 미스터리 작가가 85세 생일에 숨진 채 발견된다", 
            "다니엘 크레이그", "크리스 에반스", "아나 데 아르마스"
        );
        System.out.println("📢 [테스트용 데이터가 생성되었습니다]");
        // 첫 영화(id=1)에 리뷰 10개 추가 (간단한 테스트 데이터)
        // 유저 10명 생성
        for (int i = 1; i <= 10; i++) {
            userService.createUser("user" + i, "password" + i);
        }

        // 리뷰 10개 생성 (userId: 1..10, movieId: 1)
        Long movieId = 1L;
        for (long userId = 1L; userId <= 10L; userId++) {
            int star = (int) ((userId % 5) + 1); // 1~5
            String content = "테스트 리뷰 #" + userId + " - 평점: " + star;
            reviewService.createReview(content, star, userId, movieId);
        }
    }
}
