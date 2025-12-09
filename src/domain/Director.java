package domain;
import java.util.Objects;

public class Director{
    private Long id;
    private String name;

    public Director(String name){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        this.name = name;   
    }
    public boolean containsName(String keyword){
        if (keyword == null) return false;//굳이 예외처리 ㄴㄴ 검색이니깐
        return this.name.toLowerCase().contains(keyword.toLowerCase());
    }

    public void changeName(String name){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        this.name = name;
    }

    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "Director{id=" + id + ", name='" + name + "'}";
    }
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return id != null && Objects.equals(id, director.id);
    }
    public int hashCode(){
        return Objects.hash(id);
    }
}