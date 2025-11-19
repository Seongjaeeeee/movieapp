public class Main {
    public static void main(String[] args){
        System.out.print("Movie app start!");
        Admin admin = new Admin();
        admin.createMovie("interstella","nolan","me");
        admin.createMovie("dunkerk","nolan","me");
        admin.createMovie("panthim","pta","you");
        admin.createMovie("panthim2","pta","me");
        admin.printMovies();
        admin.deleteMovie(1L);
        admin.deleteMovie(5L);
        admin.printMovies();
    }
}