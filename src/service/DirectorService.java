package service;
import java.util.List;

import domain.Director;
import list.DirectorList;

public class DirectorService{
    private final DirectorList directorList;
    public DirectorService(DirectorList directorList){
        this.directorList=directorList;
    }
    
    public void createDirector(String directorName){
        Director director = new Director(directorName);
        directorList.save(director); 
    }

    public List<Director> findAllDirectors(){
        return directorList.findAll();
    }
    public List<Director> findAllDirectorsByName(String name){//이름으로 배우 모두 찾아서 제공
        return directorList.findAllByName(name);
    }
    public Director getDirectorById(Long id){//get은 반드시 가져오는것
        return directorList.findById(id).orElseThrow(()->new IllegalArgumentException("감독을 찾을 수 없습니다."));
    }
    public Director getAnonymousDirector(){
        return directorList.findAnonymousDirector();
    }

    public void updateDirector(Long id,String name){
        Director director = getDirectorById(id);
        director.changeName(name);
    }
    public void deleteDirector(Long id){
        Director director = getDirectorById(id);
        if(director.equals(getAnonymousDirector())) throw new IllegalArgumentException("삭제할 수 없는 객체입니다.");
        directorList.delete(director);
    }
}