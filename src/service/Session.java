package service;
import domain.User;

public class Session {
    private User currentUser;
    public void register(User user){
        if (user == null) throw new IllegalArgumentException("유효하지 않은 사용자 정보입니다.");
        if (this.currentUser != null)throw new IllegalStateException("이미 로그인된 상태입니다. 먼저 로그아웃 해주세요.");
        this.currentUser = user;
    }
    public void logout(){
         if (this.currentUser == null)throw new IllegalStateException("이미 로그아웃된 상태입니다");
        this.currentUser = null;
    }
    public boolean isLoggedIn(){
        return currentUser != null;
    }
    public boolean isAdmin(){
        //if (this.currentUser == null)throw new IllegalStateException("로그인이 안되어있어 권한을 확인할 수 없습니다"); -> 굳이인 예외처리도 있다.
        return currentUser != null&&currentUser.isAdmin();
    }
}
