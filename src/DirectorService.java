public class DirectorService{
    private DirectorList directorList;
    public DirectorService(DirectorList directorList){
        this.directorList=directorList;
    }
    public Director returnDirector(String directorName){
        Director director = directorList.find(directorName);
        if(director == null){
            director = directorList.create(directorName);
            directorList.save(director);         
        }
        return director;        
    } 
}