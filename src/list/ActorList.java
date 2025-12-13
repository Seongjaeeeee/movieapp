package list;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import domain.Actor;

public class ActorList{
    private Long idSequence=1L;
    private List<Actor> actors = new ArrayList<>();
    
    public void save(Actor actor){
        actor.setId(idSequence++);
        actors.add(actor);
    }

    public List<Actor> findAll() {
        return new ArrayList<>(actors);
    }
    public List<Actor> findAllByName(String name){
        return actors.stream()
                .filter(actor -> Objects.equals(actor.getName(), name))
                .collect(Collectors.toList());
    }
    public List<Actor> findAllByKeyword(String keyword){
        return actors.stream()
                .filter(actor -> actor.containsKeyword(keyword))
                .collect(Collectors.toList());
    }
    public Optional<Actor> findById(Long id) {
        return actors.stream()
                .filter(actor -> Objects.equals(actor.getId(), id))
                .findFirst();
    }
    public Optional<Actor> findByName(String name){
        return actors.stream()
                .filter(actor -> Objects.equals(actor.getName(), name))
                .findFirst();
    }
    public void delete(Actor actor){
        actors.remove(actor);
    }
}