import java.util.ArrayList;
import java.util.List;

public class DirectorList{
    private Long idnumber=1L;
    private List<Director> directors = new ArrayList<>();
    public Director returnDirector(String directorName){
        Director director = directors.stream().filter(d -> d.isSameDirector(directorName)).findFirst().orElse(null);
        if(director == null){
            director = new Director(idnumber++,directorName);
            directors.add(director);         
        }
        return director;        
    } 
}