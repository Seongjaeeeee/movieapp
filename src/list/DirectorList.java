package list;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import domain.Director;

public class DirectorList{
    private Long idSequence=1L;
    private List<Director> directors = new ArrayList<>();
    private final Director anonymousDirector; 

    public DirectorList() {
        this.anonymousDirector = new Director("Unknown Director");
        this.anonymousDirector.setId(0L);
        directors.add(this.anonymousDirector); 
    }

    public void save(Director director){
        director.setId(idSequence++);
        directors.add(director);
    }

    public List<Director> findAll() {
        return new ArrayList<>(directors);
    }
    public List<Director> findAllByName(String name){
        return directors.stream()
                .filter(director -> Objects.equals(director.getName(), name))
                .collect(Collectors.toList());
    }
    public Optional<Director> findById(Long id) {
        return directors.stream()
                .filter(director -> Objects.equals(director.getId(), id))
                .findFirst();
    }
    public Optional<Director> findByName(String name){
        return directors.stream()
                .filter(director -> Objects.equals(director.getName(), name))
                .findFirst();
    }
    public Director findAnonymousDirector() {
        return this.anonymousDirector;
    }

    public void delete(Director director){
        directors.remove(director);
    }
}