package list;
import java.util.List;
import java.util.ArrayList;
import domain.User;

public class UserList {
    private List<User> userList = new ArrayList<>();
    private Long id = 1L;

    public void save(User user){
        user.setId(id++);
        userList.add(user);
    }

    public User find(Long id){
        return userList.stream().filter(u -> u.isEqual(id)).findFirst().orElse(null);
    }

}
