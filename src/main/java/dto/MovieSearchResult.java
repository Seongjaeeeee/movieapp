package dto;

import java.util.Objects;

public class MovieSearchResult {
    private String name;
    private Long id;

    public MovieSearchResult(String name, Long id){
        this.name = name;
        this.id = id;
    }

    // Getters
    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieSearchResult that = (MovieSearchResult) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
    @Override
    public String toString() {
        return "SearchResult{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}
