package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/";
	private final String user;
	private final String password;
	private static Statement statement;
	private static Connection connection;
	
	public DatabaseManager(String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	public void connect() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, user, password);
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			statement.close();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnected() {
		try {
			return connection.isValid(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void useDB(String name) {
		try {
			statement.execute(Database.use(name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createDB(String name) {
		try {
			statement.executeQuery(Database.create(name));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dropDB(String name) {
		try {
			statement.executeQuery(Database.drop(name));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static class Database {
		private static String use(String name) {
			return "USE " + name;
		}
		private static String create(String name) {
			return "CREATE DATABASE " + name;
		}
		
		private static String drop(String name) {
			return "DROP DATABASE " + name;
		}
	}
	
	@SuppressWarnings("unused")
	private static class Table {
		private static String drop(String name) {
			return "DROP TABLE " + name;
		}
	}
}