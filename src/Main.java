public class Main {
    public static void main(String[] args){
        System.out.print("Movie app start!");
        Admin admin = new Admin();
        admin.createMovie("interstella","nolan","me");
        admin.printAllMovies();
    }
}