import java.time.LocalDate;

public class MovieParam {
    private String name;
    private Genre genre;
    private String description;
    private LocalDate releaseDate;
    
    public MovieParam(String name,Genre genre,String description,LocalDate releaseDate){
        this.name=name;
        this.genre=genre;
        this.description=description;
        this.releaseDate=releaseDate;
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
}
