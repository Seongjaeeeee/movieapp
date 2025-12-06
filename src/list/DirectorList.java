package list;
import java.util.ArrayList;
import java.util.List;

import domain.Director;

public class DirectorList{
    private Long idNumber=1L;
    private List<Director> directors = new ArrayList<>();

    public Director find(String directorName){
        return directors.stream().filter(d -> d.isSameDirector(directorName)).findFirst().orElse(null);
    }
    public void save(Director director){
        director.setId(idNumber++);
        directors.add(director);
    }
    public void delete(Director director){
        directors.remove(director);
    }
    public List<Director> get(){
        return directors;
    }
}