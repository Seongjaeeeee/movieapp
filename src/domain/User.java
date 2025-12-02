package domain;
public class User {
    private Long id;
    private String userName;
    private String password;
    
    public User(String userName,String password){
        this.userName = userName;
        this.password = password;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEqual(Long id){
        return this.id==id;
    }
}
