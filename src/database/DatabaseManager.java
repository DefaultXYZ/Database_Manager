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
	public boolean isDatabaseUsed;
	
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
				isDatabaseUsed = false;
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
			isDatabaseUsed = true;
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
	
	public void createTable(String name, Vector<String> rowName,
			Vector<String> rowType) {
		try {
			statement.executeUpdate(Table.create(name, rowName, rowType));
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
	
	public void updateRow(String tableName, Vector<String> data, String[] notChanged) {
		try {
			ResultSet resultSet = statement.executeQuery(Table.getColumnsName(tableName));
			Vector<String> columnName = new Vector<>();
			while(resultSet.next()) {
				columnName.addElement(resultSet.getString(1));
			}
			String sql = Table.update(tableName);
			for(int i = 0; i < columnName.size(); ++i) {
				sql += columnName.elementAt(i) + " = '" + data.elementAt(i) + "', ";
			}
			sql = sql.substring(0, sql.length() - 2);
			sql += " WHERE " + notChanged[0] + " = '" + notChanged[1] + "'";
			statement.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vector<String> getColumnsName(String tableName) {
		try {
			Vector<String> columns = new Vector<>();
			ResultSet resultSet = statement.executeQuery(Table.getColumnsName(tableName));
			while(resultSet.next()) {
				columns.addElement(resultSet.getString(1));
			}
			return columns;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<String> getColumnsType(String tableName) {
		try {
			Vector<String> types = new Vector<>();
			ResultSet resultSet = statement.executeQuery(Table.getColumnsType(tableName));
			while(resultSet.next()) {
				types.addElement(resultSet.getString(1));
			}
			return types;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Vector<String>> selectTable(String name) {
		try {
			Vector<Vector<String>> data = new Vector<>();
			Vector<String> columnNames = getColumnsName(name);
			ResultSet resultSet = statement.executeQuery(Table.select(name, columnNames));
			while(resultSet.next()) {
				Vector<String> row = new Vector<>();
				for(String column : columnNames) {
					row.addElement(resultSet.getString(column));
				}
				data.addElement(row);
			}
			return data;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}