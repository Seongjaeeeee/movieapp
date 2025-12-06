package service;
import java.util.List;

import domain.Actor;
import list.ActorList;

public class ActorService {
    private ActorList actorList;
    public ActorService(ActorList actorList){
        this.actorList=actorList;
    }
    public Actor returnActor(String actorName){
        Actor actor = actorList.find(actorName);
        if(actor == null){
            actor = new Actor(actorName);
            actorList.save(actor);         
        }
        return actor;        
    } 
    public void createActor(String actorName){
        Actor actor = new Actor(actorName);
        actorList.save(actor); 
    }
    public List<Actor> getActors(){
        return actorList.get();
    }
    public Actor findActor(String name){
        return actorList.find(name);
    }
    public void updateActor(String actorName,String newName){
        Actor actor = actorList.find(actorName);
        if(actor == null) return;
        actor.changeName(newName);
    }
    public void deleteActor(String actorName){
        Actor actor = actorList.find(actorName);
        if(actor == null) return;
        actorList.delete(actor);
    }
}
