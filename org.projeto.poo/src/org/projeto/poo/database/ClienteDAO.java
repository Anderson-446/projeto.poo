package org.projeto.poo.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.projeto.poo.BD.ConnectionMySQL;
import org.projeto.poo.model.Cliente;

public class ClienteDAO {

	private static ConnectionMySQL conn;
	
	public ClienteDAO(ConnectionMySQL conn) {
		ClienteDAO.conn = conn;
	}
	
	public void save(Cliente cliente) {
		
		String sql = "INSERT INTO clientes VALUES (?,?);";
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setString(1, cliente.getCpf());
			pstm.setString(2, cliente.getNome());
			pstm.execute();
			System.out.println("Cadastrado com sucesso!");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void update(Cliente cliente) {
		
		String sql = "UPDATE clientes SET nome = ? WHERE cpf = ?;";
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getCpf());
			pstm.executeUpdate();
			System.out.println("Alterado com sucesso!");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void delete(Cliente cliente) {
		
		String sql = "DELETE FROM clientes WHERE CPF=?;";
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setString(1, cliente.getCpf());
			pstm.executeUpdate();
			System.out.println("Excluído com sucesso");
			
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
		
	}
	
	public static Cliente findByCpf(String cpf) {
		
		String sql = "SELECT * FROM clientes WHERE cpf = ?;";
		ResultSet rs;
		Cliente cliente = null;
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setString(1, cpf);
			rs = pstm.executeQuery();
			while(rs.next()) {
				cliente = new Cliente (rs.getString("cpf"), rs.getString("nome"), null);
			}
			
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		return cliente;
	}
	
	public List<Cliente> findAll() {
		return null;
	}
	
}
