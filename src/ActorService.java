public class ActorService {
    private ActorList actorList;
    public ActorService(ActorList actorList){
        this.actorList=actorList;
    }
    public Actor returnActor(String actorName){
        Actor actor = actorList.find(actorName);
        if(actor == null){
            actor = actorList.create(actorName);
            actorList.save(actor);         
        }
        return actor;        
    } 
}
