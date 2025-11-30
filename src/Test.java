import java.time.LocalDate;
public class Test {
    public void init(MovieService movieService) {
        movieService.createMovie("인셉션", "놀란", Genre.SF, LocalDate.of(2010,7,21), "설명", "디카프리오", "조셉");
        movieService.createMovie("범죄도시", "강윤성", Genre.ACTION, LocalDate.of(2017,10,3), "설명", "마동석", "윤계상");
        movieService.createMovie("오펜하이머","크리스토퍼 놀란",Genre.DRAMA,LocalDate.of(2023, 8, 15),"핵폭탄 개발 프로젝트의 비하인드 스토리", "킬리언 머피");
        movieService.createMovie("동물의 왕국","BBC 감독",Genre.DOCUMENTARY,LocalDate.of(2024, 1, 1),"사자가 사냥하는..."// 배우 이름 생략 가능
        );
        System.out.println("📢 [테스트용 데이터 4건이 생성되었습니다]");
    }
}
