package service;
import domain.User;

public class AuthFacade {
    private UserService userService;
    private Session session;
    public AuthFacade(UserService userService,Session session){
        this.userService = userService;
        this.session = session;
    }
    public void createUser(String userName,String password){
        userService.createUser(userName, password);
    }    
    public void createAdmin(String userName,String password){
        userService.createAdmin(userName, password);
    }    
    public void login(String username,String password){
        User user = userService.login(username,password);
        session.register(user);
    }
    public void logout(){
        session.logout();
    }

    public boolean isLoggedIn(){
        return session.isLoggedIn();
    }
    public boolean isAdmin(){
        return session.isAdmin();
    }
}
