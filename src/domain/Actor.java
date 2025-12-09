package domain;
import java.util.Objects;
public class Actor{
    private Long id;
    private String name;
   
    public Actor(String name){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        this.name = name;
    }

    public boolean containsName(String keyword){
        if (keyword == null) return false;
        return this.name.toLowerCase().contains(keyword.toLowerCase());
    }

    public void changeName(String newName){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        this.name = newName;
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
        return "Actor{id=" + id + ", name='" + name + "'}";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        // id가 null이면 동등하지 않음 (영속화 전 객체는 서로 다르다고 판단)
        return id != null && Objects.equals(id, actor.id);
    }
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}