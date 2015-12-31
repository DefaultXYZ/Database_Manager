package database;

import java.util.Vector;

public class Table {
	public static String drop(String name) {
		return "DROP TABLE " + name;
	}
	
	public static String show() {
		return "SHOW TABLES";
	}
	
	public static String create(String name, Vector<String> rowName,
			Vector<String> rowType) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(name).append(" ( ");
		for(int i = 0; i < rowName.size(); ++i) {
			sql.append(rowName.elementAt(i)).append(" ");
			sql.append(rowType.elementAt(i)).append(", ");
		}
		sql.delete(sql.length() - 2, sql.length());
		sql.append(")");
		return sql.toString();
	}
	
	public static String update(String name) {
		return "UPDATE " + name + " SET ";
	}
	
	public static String getColumnsName(String name) {
		return "SELECT column_name FROM information_schema.columns WHERE table_name = '" + name +"'"; 
	}
	
	public static String getColumnsType(String name) {
		return "SELECT data_type FROM information_schema.columns WHERE table_name = '" + name +"'";
	}
	
	public static String select(String name, Vector<String> columnNames) {
		StringBuilder sql = new StringBuilder("SELECT ");
		for(String columnName : columnNames) {
			sql.append(columnName).append(", ");
		}
		sql.delete(sql.length() - 2, sql.length());
		sql.append(" FROM ").append(name);
		return sql.toString();
	}
	
	public static String insert(String name) {
		return "INSERT INTO " + name + " VALUES (";
	}
}
