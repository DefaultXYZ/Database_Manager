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
	
	public static String update(String name, Vector<String> columns, Vector<String> row, String[] cell) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ").append(name).append(" SET ");
		for(int i = 0; i < row.size(); ++i) {
			if(!columns.elementAt(i).equals(cell[0])) {
				sql.append(columns.elementAt(i))
					.append(" = '").append(row.elementAt(i)).append("', ");
			}
		}
		sql.delete(sql.length() - 2, sql.length());
		sql.append(" WHERE ").append(cell[0])
			.append(" = '").append(cell[1]).append("'");
		return sql.toString();
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
	
	public static String insert(String name, Vector<String> row) {
		StringBuilder sql = new StringBuilder("INSERT INTO ");
		sql.append(name).append(" VALUES (");
		for(String data : row) {
			sql.append("'").append(data).append("', ");
		}
		sql.delete(sql.length() - 2, sql.length());
		sql.append(")");
		return sql.toString();
	}
	
	public static String delete(String name, String column, String value) {
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append(name).append(" WHERE ").append(column)
			.append(" = '").append(value).append("'");
		return sql.toString();
	}
	
	
}
