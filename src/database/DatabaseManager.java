package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

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
			if(isConnected()) {
				statement.close();
				connection.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnected() {
		try {
			return connection.isValid(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void useDB(String name) {
		try {
			statement.executeQuery(Database.use(name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createDB(String name) {
		try {
			statement.executeUpdate(Database.create(name));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dropDB(String name) {
		try {
			statement.executeUpdate(Database.drop(name));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vector<String> showDB() {
		try {
			Vector<String> databases = new Vector<>();
			ResultSet resultSet = statement.executeQuery(Database.show());
			while(resultSet.next()) {
				databases.add(resultSet.getString(1));
			}
			return databases;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<String> showTables() {
		try {
			Vector<String> tables = new Vector<>();
			ResultSet resultSet = statement.executeQuery(Table.show());
			while(resultSet.next()) {
				tables.addElement(resultSet.getString(1));
			}
			return tables;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isDatabaseUsed() {
		try {
			ResultSet resultSet = statement.executeQuery(Database.used());
			return resultSet.next();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void createTable(String name, Vector<String> rowName,
			Vector<String> rowType, Vector<String> rowSize) {
		try {
			String query = Table.create(name);
			for(int i = 0; i < rowName.size(); ++i) {
				query += rowName.elementAt(i) + " ";
				query += rowType.elementAt(i) + "(";
				query += rowSize.elementAt(i) + "), ";
			}
			query = query.substring(0, query.length() - 2);
			query += ")";
			//System.out.println(query);
			statement.executeUpdate(query);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dropTable(String name) {
		try {
			statement.executeUpdate(Table.drop(name));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Getting queries via classes
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
		
		private static String show() {
			return "SHOW DATABASES";
		}
		
		private static String used() {
			return "SELECT DATABASE() FROM DUAL";
		}
	}
	
	private static class Table {
		private static String drop(String name) {
			return "DROP TABLE " + name;
		}
		
		private static String show() {
			return "SHOW TABLES";
		}
		
		private static String create(String name) {
			return "CREATE TABLE " + name + " ( ";
		}
	}
}