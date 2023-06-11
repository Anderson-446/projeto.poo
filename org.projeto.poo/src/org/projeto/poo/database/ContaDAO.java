package org.projeto.poo.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.projeto.poo.BD.ConnectionMySQL;
import org.projeto.poo.model.ContaCorrente;
import org.projeto.poo.model.ContaPoupanca;
import org.projeto.poo.model.IConta;
import org.projeto.poo.model.Cliente;

public class ContaDAO {

	private static ConnectionMySQL conn;
	
	public ContaDAO(ConnectionMySQL conn) {
		ContaDAO.conn = conn;
	}
	
	public static void save(IConta conta, Cliente cliente) {
		
		String sql = "INSERT INTO contas VALUES (?,?,?,?,?);";
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setInt(1, conta.getNumeroConta());
			pstm.setDouble(2, conta.getSaldo());
			pstm.setBoolean(3, conta.getStatus());
			pstm.setString(4, conta.getTipo());
			pstm.setString(5, cliente.getCpf());
			pstm.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(IConta conta) {
		
		String sql = "UPDATE contas SET saldo = ?, estado = ?, tipo = ? WHERE numeroConta = ?;";
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setDouble(1, conta.getSaldo());
			pstm.setBoolean(2, conta.getStatus());
			pstm.setString(3, conta.getTipo());
			pstm.setInt(4, conta.getNumeroConta());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void delete(IConta conta) {
		
		String sql = "DELETE FROM contas WHERE numeroConta= ?;";
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setInt(1, conta.getNumeroConta());
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public IConta findByNumero(int numeroConta) {
		
		String sql = "SELECT * FROM contas WHERE numeroConta = ?;";
		ResultSet rs;
		IConta conta = null;
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setInt(1, conta.getNumeroConta());
			rs = pstm.executeQuery();
			while(rs.next()) {
				if (conta.getTipo() == "corrente") {
					
					conta = new ContaCorrente();
				} 
				else if (conta.getTipo() == "poupanca") {
					
					conta = new ContaPoupanca();
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conta;
		
	}
	public List<IConta> findAll(){
		return null;
	}
}