public class Director{
    String name;
    public Director(String name){
        this.name = name;   
    }

    public String getName(){
        return name;   
    }

    @Override
    public String toString() {
        return "Director{name='" + name + "'}";
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return name.equals(director.name);
    }

    public int hashCode(){
        return name.hashCode();
    }
}