package database;

@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception{
	DatabaseConnectionException(){
		super("Connessione al database Fallita!");
	}
	}
