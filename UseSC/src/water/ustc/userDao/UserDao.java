package water.ustc.userDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import water.ustc.lazyload.UserProxy;
import water.ustc.userBean.UserBean;
public class UserDao extends sc.ustc.dao.BaseDao{
	private static UserDao userDao = new UserDao();
	private UserDao() {
		driver = "com.mysql.cj.jdbc.Driver";
		userPassword = "zhouhao1106";
		userName = "root";
		url = "jdbc:mysql://localhost:3306/student";

	    connection = openDBConnection();
	    //connection = closeDBConnection();
	}
	
	public static UserDao getInstance() {
		if (userDao==null){
            userDao=new UserDao();
        }
        return userDao;
	}
	
	@Override
	public Object query(String name) {
		UserBean newUserBean = (UserBean) Conversation.loadObject(new UserBean(name));
        return newUserBean;
	}
	public Object load(String s) {
        UserProxy objProxy = new UserProxy();
        UserBean userBean = (UserBean) water.ustc.userDao.Conversation.loadObject(new UserBean(s));
        UserBean userProxy = (UserBean) objProxy.getInstance(userBean);
        return userProxy;
    }
	
	@Override
	public boolean delete(String arg0) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean insert(String arg0) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean update(String arg0) {
		// TODO 自动生成的方法存根
		return false;
	}

}
