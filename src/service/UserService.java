package service;
import list.UserList;
import domain.User;
public class UserService {
    private UserList userList;
    public UserService(UserList userList){
        this.userList = userList;
    }
    public void createUser(String userName,String password){
        User newUser = new User(userName, password);
        userList.save(newUser);
    };
    public User findUser(Long id){
        return userList.find(id);
    }
}
