package com.cice.MavenWebDdbb.datasource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Datasource {
	
	private static Connection conn = null;
	private static PreparedStatement stm = null;
	
	private static void makeJDBCConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("Felicidades -  MySQL JDBC Driver Registrado!");
		} catch (ClassNotFoundException e) {
			log("Mierda, no hay pelotas a encontrar el puto JDBC driver");
			e.printStackTrace();
			return;
		}

		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registro", "root", "root");
			if (conn != null) {
				log("Connection Successful! Toma toma toma");
			} else {
				log("Failed to make connection!");
			}
		} catch (SQLException e) {
			log("MySQL Connection a pegao un pete de locos!");
			e.printStackTrace();
			return;
		}

	}
	
	public static void addDataToDB(String nombre, String pass) throws SQLException, NoSuchAlgorithmException {
		makeJDBCConnection();
		String insertQueryStatement = "INSERT INTO `users`(`name`, `password`) VALUES (?,?)";

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(pass.getBytes());
		String str = new String(hash, StandardCharsets.UTF_8);
		
		stm = conn.prepareStatement(insertQueryStatement);
		stm.setString(1, nombre);
		stm.setString(2, str);
		stm.executeUpdate();
		log(nombre + " added successfully, soy el rey del mundo");
	}
	
	private static void log(String Log) {
		System.out.println(Log);
	}
}
