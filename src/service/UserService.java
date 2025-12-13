package service;
import domain.Role;
import domain.User;
import list.UserList;
public class UserService {
    private UserList userList;
    public UserService(UserList userList){
        this.userList = userList;
    }
    public void createUser(String userName,String password){//회원가입
        if (userList.existsByName(userName)) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        User newUser = new User(userName, password);
        userList.save(newUser);
    }
    public void createAdmin(String userName,String password){
        if (userList.existsByName(userName)) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        User newUser = new User(userName, password,Role.ADMIN);
        userList.save(newUser);
    }
    public User login(String userName,String password){
        User user = getUserByUserName(userName);
        if(user.checkPassword(password)){
            return user;
        }
        else throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
    }
    public User getUserByUserName(String userName){
        return userList.findByName(userName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
    }
}
