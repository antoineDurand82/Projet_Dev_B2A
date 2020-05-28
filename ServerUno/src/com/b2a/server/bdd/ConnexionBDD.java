package com.b2a.server.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class ConnexionBDD {
	
	public static void main(String[] args) {
		
		try {
			// Connexion
			String url = "jdbc:mysql://localhost:6606/uno";
			Connection connection = DriverManager.getConnection(url, "root", "root");
			// Requête
			System.out.println(connection);
			Statement instruction = connection.createStatement();
			ResultSet resultat = instruction.executeQuery("SELECT * FROM acteurs");
			ResultSetMetaData rsm = resultat.getMetaData();
			int nbCols = rsm.getColumnCount();
			System.out.println(nbCols + " champs trouvés.");
			for (int i = 1; i <= nbCols; i++)
			{
				System.out.println("Champs n°" + i + " : " + rsm.getColumnLabel(i) + " de type " + rsm.getColumnTypeName(i));
			}
			resultat.last();
			System.out.println(resultat.getRow() + " enregistrements trouvés.");
			resultat.beforeFirst();
		}
		catch( Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
