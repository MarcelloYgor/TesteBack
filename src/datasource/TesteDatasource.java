package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TesteDatasource {

	private Connection connection;
	
	public TesteDatasource() {
		if (connection == null) {
			connection = getConnection();
		}
	}
	
	private Connection getConnection() {
		Connection conn = null;
		try {
			System.out.println("Conectando ao banco de dados...");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connection = "jdbc:sqlserver://localhost:1433;databaseName=master";
			String usuario = "sa";
			String senha = "senhadobanco12";
			conn = DriverManager.getConnection(connection, usuario, senha);
		} catch (ClassNotFoundException e) {
			System.out.println("Error: unable to load driver class!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} if (conn != null) {
			System.out.println("Conectado com sucesso!");
		} else if (conn == null) {
			System.out.println("Falha ao conectar!");
		}
		return conn;
	}

	public PreparedStatement getPreparedStatement(String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}
	
	public Boolean closeConnection(PreparedStatement pstmt) {
		try {
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}
}
