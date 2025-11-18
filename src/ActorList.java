import java.util.ArrayList;
import java.util.List;

public class ActorList{
    List<Actor> actors = new ArrayList<>();
    public Actor returnActor(String actorName){
        Actor actor = actors.stream().filter(d -> d.getName().equals(actorName)).findFirst().orElse(null);
        if(actor == null){
            actor = new Actor(actorName);
            actors.add(actor);         
        }
        return actor;        
    } 
}