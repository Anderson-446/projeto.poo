package org.projeto.poo.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	public void delete(int numeroConta) {
		
		String sql = "DELETE FROM contas WHERE numeroConta= ?;";
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			//pstm.setInt(1,getNumeroConta());
			pstm.setInt(1, numeroConta);
			pstm.executeUpdate();
			System.out.println("Conta excluída com sucesso");
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
	public List<IConta> findByCpf(){
		
		
		
		
		
		return null;
	}
	
	/*
	public void atualizarContas (Cliente cliente, IConta con) {
		
		cliente.getContas().clear(); //limpar o array antes de atualizar
		String sql = "SELECT * FROM contas WHERE clientes_cpf = ?;";
		ResultSet rs;
		
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setString(1, cliente.getCpf());
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				if(con.getTipo() == "corrente") {
					
					IConta conta = new ContaCorrente("corrente");
					cliente.getContas().add(conta);
				
				}
				if (con.getTipo() == "poupanca") {
				
					IConta conta = new ContaPoupanca("poupanca");
					cliente.getContas().add(conta);
				}
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
			
	}
	*/
	
	public List<String> findContas(String cpf) {
		
		List<String> listContas = null;
		String sql = "SELECT * FROM contas WHERE clientes_cpf = ?;";
		ResultSet rs;
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setString(1, cpf);
			rs = pstm.executeQuery();
			listContas = new ArrayList<>();
			
			while(rs.next()) {
				int numeroConta = rs.getInt("numeroConta");
				float saldo = rs.getFloat("saldo");
				boolean status = rs.getBoolean("estado");
				String tipo = rs.getString("tipo");	
				String dadosConta = "Numero: " + numeroConta + "\n" + "Saldo: R$" + saldo + "\n" + "Status: " + status + "\n" + "Tipo da conta: " + tipo + "\n";
				listContas.add(dadosConta);
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return listContas;
	}
	
	public boolean deposito (int numeroConta, double valor) {
		
		String sql = "UPDATE contas SET saldo = saldo + ? WHERE numeroConta = ?;";
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setDouble(1, valor);
			pstm.setInt(2, numeroConta);
			pstm.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean saque (int numeroConta, double valor) {
		
		String sql = "UPDATE contas SET saldo = saldo - ? WHERE numeroConta = ?;";
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setDouble(1, valor);
			pstm.setInt(2, numeroConta);
			pstm.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void transferencia (double valor, int contaOrigem, int contaDestino) {
		
		String minusSql = "UPDATE contas SET saldo = saldo - ? WHERE numeroConta = ?;";
		String plusSql = "UPDATE contas SET saldo = saldo + ? WHERE numeroConta = ?;";
		

		try {
			
			PreparedStatement minusPstm = conn.getConnection().prepareStatement(minusSql);
			minusPstm.setDouble(1, valor);
			minusPstm.setInt(2, contaOrigem);
			minusPstm.executeUpdate();
			
			//acredito que seja apenas aqui para multiplicar e diminuir o valor, dependendo da conta
			PreparedStatement plusPstm = conn.getConnection().prepareStatement(plusSql);
			plusPstm.setDouble(1, valor);
			plusPstm.setInt(2, contaDestino);
			plusPstm.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void balanco(Cliente cliente) {
		
		String sql = "SELECT SUM(saldo) AS total FROM contas WHERE clientes_cpf = ?;";
		//é criada uma tabela com o nome de total que armazena a soma da coluna saldo
		ResultSet rs;
		
		try {
			
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setString(1, cliente.getCpf());
			
			rs = pstm.executeQuery();
			if(rs.next()) {
				double total = rs.getDouble("total");
				System.out.println("Balanço total:" + total);
			} else {
				System.out.println("Error: estamos trabalhando neste quesito.");
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
}
