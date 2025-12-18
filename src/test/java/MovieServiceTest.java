import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.Director;
import domain.Genre;
import domain.Movie;
import list.MovieList;
import service.MovieService;

class MovieServiceTest {
    private MovieService movieService;
    private MovieList movieList;

    @BeforeEach
    void setUp() {
        // 테스트가 실행되기 전마다 새 객체를 만들어 독립적인 환경을 구축합니다.
        movieList = new MovieList(); 
        movieService = new MovieService(movieList);
    }

    @Test
    @DisplayName("영화 생성 및 저장 테스트")
    void createMovieTest() {
        // given (준비)
        String name = "인셉션";
        Director director = new Director("크리스토퍼 놀란");
        Genre genre = Genre.SF;
        LocalDate date = LocalDate.of(2010, 7, 21);
        
        // when (실행)
        movieService.createMovie(name, director, genre, date, "꿈속 이야기", new ArrayList<>());

        // then (검증)
        List<Movie> movies = movieService.findAllMovies();
        assertEquals(1, movies.size());
        assertEquals("인셉션", movies.get(0).getName());
    }
}
   
