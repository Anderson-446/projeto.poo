package org.projeto.poo.model;

import java.util.List;

public interface IConta {
	//Listar as assinaturas dos métodos que serão utilizados;
	
	 boolean getStatus();
	 
	 void setStatus(boolean status);
	 
	 double getSaldo();
	 
	 void setSaldo(double saldo);	
	 
	 int getNumeroConta();
	 
	 void setNumeroConta(int numeroConta);
	 
	 String getTipo();
	 
	 void setTipo(String tipo);
	 
	 void depositar(double valor);
	 
	 void sacar(double valor);
	 
	 void transferir(double valor, IConta destino);
	 
	 void imprimirExtrato();
	 
	 double consultarSaldo();
	 
}
