package domain;

import java.util.Objects;

public class User {
    private Long id;
    private String userName;
    private String password;
    private Role role;
    public User(String userName,String password){
        this(userName, password, Role.USER);
    } 
    public User(String userName,String password,Role role){
        if(userName==null||userName.isBlank())throw new IllegalArgumentException("유저 이름은 공백일수 없습니다.");
        if(password==null||password.isBlank())throw new IllegalArgumentException("비밀번호는 공백일수 없습니다.");
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
    
    public boolean checkPassword(String password){
        return Objects.equals(password,this.password);
    }   

    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName(){
        return userName;
    }
    public boolean isAdmin(){
        return Role.ADMIN==this.role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='[PROTECTED]'" + // 비밀번호 숨김 처리
                '}';
    }
}

