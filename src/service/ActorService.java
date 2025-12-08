package service;
import java.util.List;

import domain.Actor;
import list.ActorList;

public class ActorService {
    private final ActorList actorList;
    public ActorService(ActorList actorList){
        this.actorList=actorList;
    }
    /*
    public Actor getOrCreateActor(String actorName){
        Optional<Actor> optionalActor= actorList.findByName(actorName);
        if (optionalActor.isPresent()){
            return optionalActor.get();
        }
        else{
            Actor actor = createActor(actorName); 
            return actor;        
        }     
    }*/
    public Actor createActor(String actorName){
        Actor actor = new Actor(actorName);
        actorList.save(actor); 
        return actor;
    }

    public List<Actor> findAllActors(){//모든 배우 목록 제공 ,find는 검색
        return actorList.findAll();
    }
    public List<Actor> findAllActorsByName(String name){//이름으로 배우 모두 찾아서 제공
        return actorList.findAllByName(name);
    }
    public Actor getActorById(Long id){//get은 반드시 가져오는것
        return actorList.findById(id).orElseThrow(()->new IllegalArgumentException("배우를 찾을 수 없습니다."));
    }

    public void updateActor(Long id,String newName){
        Actor actor = getActorById(id);
        actor.changeName(newName);
    }

    public void deleteActor(Long id){
        Actor actor = getActorById(id);
        actorList.delete(actor);
    }
}
