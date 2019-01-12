package water.ustc.userBean;

import water.ustc.userDao.UserDao;

public class UserBean {
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPass() {
        return userPass;
    }
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    private String userName;
    private String userPass;
    private String userId;
    public UserBean(){}
    //userPass是用户密码
    public UserBean(String userId){
        this.userId = userId;
    }
    public UserBean(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
    }
    public UserBean(String userId, String userName, String userPass) {
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
    }
    public boolean signIn(String id,String password){

        UserDao userDAO=UserDao.getInstance();
        UserBean userBean = (UserBean) userDAO.load(id);
        System.out.println("userBean: "+userBean);
        if (userBean!=null){
            if(userBean.getUserPass().equals(password)) {
                System.out.println("判断输入的密码与查询的密码是否相等: "+userBean.getUserPass().equals(password));
                return true;
            }
        }
        return false;
    }
}