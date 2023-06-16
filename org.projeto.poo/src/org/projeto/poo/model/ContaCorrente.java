package org.projeto.poo.model;

import java.util.Objects;
import java.util.Random;

public class ContaCorrente implements IConta {
	
	private int numeroConta;
	private double saldo;
	private boolean status;
	private String tipo;
	
	public ContaCorrente() {
	}
	
	public ContaCorrente(String tipo) {
		
		this.numeroConta = new Random().nextInt(9999);
		this.saldo = 0;
		this.status = true;
		this.tipo = "corrente";
	}
	
	
			
	public int getNumeroConta() {
		return numeroConta;
	}



	public void setNumeroConta(int numeroConta) {
		this.numeroConta = numeroConta;
	}



	public double getSaldo() {
		return saldo;
	}



	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}



	public boolean getStatus() {
		return status;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

	//MÉTODOS


	public int hashCode() {
		return Objects.hash(numeroConta, tipo);
	}


	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaCorrente other = (ContaCorrente) obj;
		return numeroConta == other.numeroConta && Objects.equals(tipo, other.tipo);
	}

	public String toString() {
		return "ContaCorrente [numeroConta=" + numeroConta + ", saldo=" + saldo + ", status=" + status + ", tipo="
				+ tipo + "]";
	}



	public void depositar(double valor) {
		
		if(valor > 0) {
			this.saldo = this.saldo + valor;
		} else {
			throw new RuntimeException("Valor inválido para depósito");
		}
		
		
	}
	
	public void sacar(double valor) {
		
		if(!this.status) {
			throw new RuntimeException("Conta inativa");
		}
		else if (valor <= 0) {
			throw new RuntimeException("valor inválido para saque");
		}
		else if (valor > this.saldo) {
			throw new RuntimeException("Saldo insuficiente para saque");
		}
		else {
			this.saldo = this.saldo - valor;
		}
	}
	
	public void transferir(double valor, IConta destino) {
		
		if(!this.status) {
			throw new RuntimeException("Conta de origem inativa");
		}
		else if(!destino.getStatus()) {
			throw new RuntimeException("Conta de destino inativa");
		}
		else if(valor > this.saldo) {
			throw new RuntimeException("Saldo insuficiente para transferência");
		}
		else {
			this.saldo = this.saldo - valor;
			destino.depositar(valor - valor * 0.03);
		}
	}
	
	public void imprimirExtrato() {
		
		
	}
	
	public double consultarSaldo() {
		
		return this.saldo;

	}
}
