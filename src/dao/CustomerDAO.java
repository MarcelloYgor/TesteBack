package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datasource.TesteDatasource;
import model.Customer;

public class CustomerDAO {

	private TesteDatasource connection;

	public void insertCustomer(List<Customer> listCustomer) {
		PreparedStatement stmt = null;
		try {
			connection = new TesteDatasource();
			String sql = "INSERT INTO tb_customer_account(cpf_cnpj, nm_customer, is_active, vl_total) "
					+ "VALUES (?, ?, ?, ?);";
			stmt = connection.getPreparedStatement(sql);
			for (Customer customer : listCustomer) {
				stmt.setString(1, customer.getCpfCnpj());
				stmt.setString(2, customer.getNome());
				stmt.setBoolean(3, customer.isActive());
				stmt.setDouble(4, customer.getVlTotal());

				stmt.addBatch();
			}
			stmt.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				connection.closeConnection(stmt);
			}
		}
	}
	
	public List<Customer> selectCustomer() {
		PreparedStatement stmt = null;
		List<Customer> retorno = null;
		try {
			connection = new TesteDatasource();
			String sql = "SELECT * FROM tb_customer_account;";
			stmt = connection.getPreparedStatement(sql);
			
			ResultSet result = stmt.executeQuery();
			retorno = new ArrayList<>();
			while (result.next()) {
				Customer customer = new Customer();
				customer.setId(result.getInt("id_customer"));
				customer.setCpfCnpj(result.getString("cpf_cnpj"));
				customer.setNome(result.getString("nm_customer"));
				customer.setActive(result.getBoolean("is_active"));
				customer.setVlTotal(result.getFloat("vl_total"));
				retorno.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				connection.closeConnection(stmt);
			}
		}
		return retorno;
	}
}
