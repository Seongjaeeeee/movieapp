public class Movie{
    String name;
    Director director;
    Actor actor;
    Movie(String name, Director director, Actor actor){
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.createMovie();
    }
    
    @Override
    public String toString() {
        return "Movie{name='" + name + "', director=" + director + ", actor=" + actor + "}";
    }
    
    private void createMovie(){
        director.addMovie(this);
        actor.addMovie(this);
    }
}