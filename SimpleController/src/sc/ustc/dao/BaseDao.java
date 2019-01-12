package sc.ustc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDao {
	protected String driver;
	protected String url;
	protected String userName;
	protected String userPassword;
	protected Connection connection;
	
	public Connection openDBConnection(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,userName,userPassword);
            System.out.println("数据库已经开启！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
	
    public Connection closeDBConnection(){
        try {
            connection.close();
            System.out.println("数据库已经关闭！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return connection;
    }
    
	public abstract Object query(String sql);
	public abstract boolean insert(String sql);
	public abstract boolean update(String sql);
	public abstract boolean delete(String sql);

	public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
