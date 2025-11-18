public class Movie{
    String name;
    Director director;
    Actor actor;
    Movie(String name, Director director, Actor actor){
        this.name = name;
        this.director = director;
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "Movie{name='" + name + "', director=" + director + ", actor=" + actor + "}";
    }
}