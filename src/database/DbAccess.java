package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {
	String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	final String DBMS = "jdbc:mysql";
	final String SERVER= "localhost";
	final String DATABASE = "MapDB";
	final int PORT=3306;
	final String USER_ID = "MapUser"; //user 
	final String PASSWORD = "map";	//pass
	Connection conn;
	
	public void initConnection() throws DatabaseConnectionException{
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection(DBMS+"://"+SERVER+":"+PORT+"/"+DATABASE,USER_ID,PASSWORD);
			System.out.println("Connesso al database");
		} catch ( SQLException e) {
			throw new DatabaseConnectionException();
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
