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
    public Actor create(String actorName){
        return new Actor(idnumber++, actorName);
    }
    public void save(Actor actor){
        actors.add(actor);
    }
}