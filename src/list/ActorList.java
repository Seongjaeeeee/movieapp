package list;
import java.util.ArrayList;
import java.util.List;

import domain.Actor;

public class ActorList{
    private Long idnumber=1L;
    private List<Actor> actors = new ArrayList<>();
    
    public Actor find(String actorName){
        return actors.stream().filter(d -> d.isSameActor(actorName)).findFirst().orElse(null);
    }
    public void save(Actor actor){
        actor.setId(idnumber++);
        actors.add(actor);
    }
}