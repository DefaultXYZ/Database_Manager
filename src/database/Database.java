package database;

public class Database {
	public static String use(String name) {
		return "USE " + name;
	}
	
	public static String create(String name) {
		return "CREATE DATABASE " + name;
	}
	
	public static String drop(String name) {
		return "DROP DATABASE " + name;
	}
	
	public static String show() {
		return "SHOW DATABASES";
	}
}
