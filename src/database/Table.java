package database;

public class Table {
	public static String drop(String name) {
		return "DROP TABLE " + name;
	}
	
	public static String show() {
		return "SHOW TABLES";
	}
	
	public static String create(String name) {
		return "CREATE TABLE " + name + " ( ";
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
	
	public static String select(String name) {
		return "SELECT ";
	}
}
