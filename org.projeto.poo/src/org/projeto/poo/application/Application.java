package org.projeto.poo.application;

import java.util.Scanner;

import org.projeto.poo.BD.ConnectionMySQL;
import org.projeto.poo.database.ClienteDAO;
import org.projeto.poo.database.ContaDAO;
import org.projeto.poo.model.Cliente;
import org.projeto.poo.model.ContaCorrente;
import org.projeto.poo.model.ContaPoupanca;
import org.projeto.poo.model.IConta;
import java.util.List;
import java.util.ArrayList;

public class Application {

	public static void main(String[] args) {
		
		ClienteDAO clienteDao = new ClienteDAO(new ConnectionMySQL());
		ContaDAO contaDao = new ContaDAO(new ConnectionMySQL());
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("------------------------------------------------------");
			System.out.println("-----------  Bem-vindos(a) ao meu Sistema  -----------");
			System.out.println("------------------------------------------------------");
			System.out.println("***** Selecione uma operação que deseja realizar *****");
			System.out.println("------------------------------------------------------");
			System.out.println("|   Opção 1 - Cadastrar-se como cliente |");
			System.out.println("|   Opção 2 - Já cadastrado(a)? Acesse! |");
			System.out.println("|   Opção 3 - Sair                      |");
   
			int opcao = scanner.nextInt();
			scanner.nextLine();
			
			switch (opcao) {
			
			case 1:
				System.out.println("Digite seu nome:");
				String nome = scanner.nextLine();
				System.out.println("Digite seu CPF:");
				String cpf = scanner.nextLine();
				Cliente cliente = new Cliente (cpf,nome, null);
				clienteDao.save(cliente);
				break;
				
			case 2:
				System.out.println("Digite seu CPF do cliente:");
				cpf = scanner.next();
				
				cliente = ClienteDAO.findByCpf(cpf);
				
				if (cliente == null) {
					System.err.println("Cliente não faz parte do sistema");
					break;
				}
				System.out.println("|----------------------------------------------------|");
				System.out.println("|---Cliente selecionado: " + cliente.getNome() + "---|");
				System.out.println("|--------O que você gostaria de fazer?" + "----------|");
				System.out.println("|   Opção 1 - Criar nova conta");
				System.out.println("|   Opção 2 - Ver informações das contas");
				System.out.println("|   Opção 3 - Realizar Deposito");
				System.out.println("|   Opção 4 - Realizar Saque");
				System.out.println("|   Opção 5 - Realizar Transferencia");
				
				opcao = scanner.nextInt();
				scanner.nextLine();
			
				switch (opcao) {
				
				case 1:
					System.out.println("Que tipo de conta gostaria de criar?");
					System.out.println("1. Criar uma conta corrente");
					System.out.println("2. Criar uma conta poupança");
					System.out.println("Obs. Lembre-se que há taxas de transferências diferentes.");
					System.out.println("Cobramos uma taixa maior para transferências de contas poupanças.");
					
					opcao = scanner.nextInt();
					scanner.nextLine();
					
					switch (opcao) {
					
					case 1:
						
						IConta conta = new ContaCorrente("corrente");
						cliente.adicionarConta(conta);
						contaDao.save(conta, cliente);
						System.out.println("Conta Corrente criada com sucesso!");
						
						break;
						
					case 2:
						
						IConta conta2 = new ContaPoupanca("poupanca");
						cliente.adicionarConta(conta2);
						contaDao.save(conta2, cliente);
						System.out.println("Conta Poupança criada com sucesso!");
						
						break;
						
					default:
						
						System.err.println("Operação inválida");
						
					}
					
					
				case 2:
					if(cliente.getContas().size() == 0) {
						System.err.println("O cliente não possui contas neste sistema");
					} else {
						for (IConta c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					
				case 3:
					if(cliente.getContas().size() == 0) {
						System.err.println("O cliente não possui contas neste sistema");
					} else {
						for (IConta c : cliente.getContas()) {
							System.out.println(c);
						}	
					}
					
				//	System.out.println("Em qual conta deseja realizar o depósito?");
				//	int opcaoContaDeposito = 0;
				//	double valor = 0.0;
				//	opcaoContaDeposito = scanner.nextInt();
				//	System.out.println("Insira o valor a ser depositado:");
				//	valor = scanner.nextDouble();
				//	IConta temp = ContaDAO.findByNumero(int);
				//	if (temp != null) {
				//		temp.depositar(valor);
						//atualizar valor no banco
					}
					break;
				default:
					System.out.println("Opção Inválida");
					
				//case 4:
					
				//case 5:
			
		
		//		}
			case 3:
				System.out.println("Até mais ver");
				System.exit(0);
				
		//	default:
				System.out.println("Opção inválida");
				break;
			}	
		}
	}
}
