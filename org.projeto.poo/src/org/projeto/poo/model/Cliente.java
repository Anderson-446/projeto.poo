package org.projeto.poo.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;


public class Cliente {

	private String cpf;
	private String nome;
	private List<IConta> contas;
	
	public Cliente () {
	}
	
	public Cliente (String cpf, String nome) {
		
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.contas = new ArrayList<>();
	}

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<IConta> getContas() {
		return contas;
	}
	
	public void setContas(List<IConta> contas) {
		this.contas = contas;
	}

	public int hashCode() {
		return Objects.hash(cpf);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}

	public String toString() {
		return "Cliente [cpf=" + cpf + ", nome=" + nome + ", contas=" + contas + "]";
	}
	
	 public void adicionarConta (IConta c) {
	 	
	 	if(contas.contains(c)) {
	 		throw new RuntimeException("Conta já associada ao cliente.");
	 	}
	 	else {
	 		contas.add(c); {
	 			System.out.println("Conta adicionada com sucesso!");;
	 			System.out.println(c);
	 			System.out.println(contas);
	 		}
	 	}
	 		
	 		
	 }
	public void removerConta (IConta c) {
		 
		 if(contas.contains(c)) {
			 	contas.remove(c);
			 	System.out.println("Conta removida com sucesso");
		 }
		 else {
			 throw new RuntimeException("Conta não está associada ao cliente");
		 }
	 }
	 
	 //List<IConta> listarContas (){
		 
	// }
	 
	public void balancoDeContas() {
		 
	 }

	
}
