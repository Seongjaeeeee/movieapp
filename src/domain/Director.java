package domain;
import java.util.Objects;

public class Director{
    private Long id;
    private String name;

    public Director(String name){
        this.name = name;   
    }

    public void changeName(String newName){
        this.name = newName;
    }
    public void setId(Long id){
        this.id=id;
    }
    public boolean isSameDirector(String directorName){
        return this.name.equals(directorName);
    }

    @Override
    public String toString() {
        return "Director{id=" + id + ", name='" + name + "'}";
    }
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return id.equals(director.id);
    }
    public int hashCode(){
        return Objects.hash(id);
    }
}