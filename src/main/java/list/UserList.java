package list;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import domain.User;

public class UserList {
    private List<User> userList = new ArrayList<>();
    private Long id = 1L;

    public void save(User user){
        user.setId(id++);
        userList.add(user);
    }
    
    public boolean existsByName(String name){
        return userList.stream().anyMatch(u -> Objects.equals(u.getUserName(), name));
    }
    public Optional<User> findByName(String name){
        return userList.stream().filter(u -> Objects.equals(u.getUserName(), name)).findFirst();
    }

}
