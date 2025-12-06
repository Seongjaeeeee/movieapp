package service;
import java.util.List;

import domain.Director;
import list.DirectorList;

public class DirectorService{
    private DirectorList directorList;
    public DirectorService(DirectorList directorList){
        this.directorList=directorList;
    }
    public Director returnDirector(String directorName){
        Director director = directorList.find(directorName);
        if(director == null){
            director = new Director(directorName);
            directorList.save(director);         
        }
        return director;        
    } 
    public void createDirector(String directorName){
        Director director = new Director(directorName);
        directorList.save(director); 
    }
    public List<Director> getDirectors(){
        return directorList.get();
    }
    public Director findDirector(String directorName){
        return directorList.find(directorName);
    }
    public void updateDirector(String directorName,String newName){
        Director director = directorList.find(directorName);
        if(director == null) return;
        director.changeName(newName);
    }
    public void deleteDirector(String directorName){
        Director director = directorList.find(directorName);
        if(director == null) return;
        directorList.delete(director);
    }
}