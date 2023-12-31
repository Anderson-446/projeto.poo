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
			System.out.println("|   Opção 3 - Listar todos os clientes  |");
			System.out.println("|   Opção 4 - Sair                      |");
   
			int opcao = scanner.nextInt();
			scanner.nextLine();
			
			switch (opcao) {
			
			case 1: //cadastrar cliente
				System.out.println("Digite seu nome:");
				String nome = scanner.nextLine();
				System.out.println("Digite seu CPF:");
				String cpf = scanner.nextLine();
				Cliente cliente = new Cliente (cpf,nome);
				clienteDao.save(cliente);
				break;
				
			case 2: //listar cliente por cpf
				System.out.println("Digite seu CPF do cliente:");
				cpf = scanner.next();
				
				Cliente cli = ClienteDAO.findByCpf(cpf);
				
				if (cli == null) {
					System.err.println("Cliente não faz parte do sistema");
					break;
				}
				System.out.println("|----------------------------------------------------|");
				System.out.println("|---Cliente selecionado: " + cli.getNome() + "---|");
				System.out.println("|--------O que você gostaria de fazer?" + "----------|");
				System.out.println("|   Opção 1 - Criar nova conta");
				System.out.println("|   Opção 2 - Ver informações das contas");
				System.out.println("|   Opção 3 - Realizar Deposito");
				System.out.println("|   Opção 4 - Realizar Saque");
				System.out.println("|   Opção 5 - Realizar Transferencia");
				System.out.println("|   Opção 6 - Desassociar cliente");
				
				opcao = scanner.nextInt();
				scanner.nextLine();
			
				switch (opcao) {
				
				case 1: // cadastrar contas
					System.out.println("Que tipo de conta gostaria de criar?");
					System.out.println("1. Criar uma conta corrente");
					System.out.println("2. Criar uma conta poupança");
					System.out.println("Obs. Lembre-se que há taxas de transferências diferentes.");
					System.out.println("Cobramos uma taixa maior para transferências de contas poupanças.");
					
					opcao = scanner.nextInt();
					scanner.nextLine();
					
					switch (opcao) {
					
					case 1: // adicionar Conta Corrente
						
						IConta conta = new ContaCorrente("corrente");
						cli.adicionarConta(conta);
						contaDao.save(conta, cli);
					//	contaDao.atualizarContas(cli, conta);
						System.out.println("Conta Corrente criada com sucesso!" + "\n");
						
						break;
						
					case 2: // adicionar Conta Poupança
						
						IConta conta2 = new ContaPoupanca("poupanca");
						cli.adicionarConta(conta2);
						contaDao.save(conta2, cli);
					//	contaDao.atualizarContas(cli, conta2);
						System.out.println("Conta Poupança criada com sucesso!" + "\n");
						
						break;
						
					default:
						
						System.err.println("Operação inválida");
						break;
					}
					
					
				case 2: //listar contas //informações das contas
					
					List<String> listContas = contaDao.findContas(cpf);
					
					for(String Lista : listContas) {
						System.out.println(Lista);
					}
					/*
					if(cli.getContas().size() == 0) {
						System.err.println("O cliente não possui contas neste sistema");
					} else {
						for (IConta conta : cli.getContas()) {
							System.out.println(conta);
						}
					}
					*/
					System.out.println("Digite o número da ação que deseja executar:");
					System.out.println("Opção 1 - Excluir conta");
					System.out.println("Opção 2 - Fazer o balanço das contas");
					System.out.println("Digite qualquer outro número para sair");

					
					opcao = scanner.nextInt();
					scanner.nextLine();
					
					switch(opcao) {
						
					case 1: //excluir conta
						System.out.println("Insira o número da conta que deseja excluir:");
						int numero = 0;
						numero = scanner.nextInt();
						contaDao.delete(numero);
						
						break;
						
					case 2 : //balanço das contas
						
						contaDao.balanco(cli);
						
						break;
						
					}
					
				break;
					
				case 3: //depositar						
					
					System.out.println("Em qual conta deseja realizar o depósito?");
					int numeroConta = 0;
					double valor = 0.0;
					numeroConta = scanner.nextInt();
					System.out.println("Insira o valor a ser depositado:");
					valor = scanner.nextDouble();
					contaDao.deposito(numeroConta, valor);
					System.out.println("Depósito realizado com sucesso!");
					break;
					
					
				case 4: //sacar
					
					System.out.println("Em qual conta deseja realizar o saque?");
					int numeroConta2 = 0;
					double valor2 = 0.0;
					numeroConta2 = scanner.nextInt();
					System.out.println("Insira o valor a ser sacado:");
					valor2 = scanner.nextDouble();
					contaDao.saque(numeroConta2, valor2);
					System.out.println("Saque realizado com sucesso!");
					break;	
					
				case 5:  //transferir
					
					System.out.println("Gostaria de realizar uma transferência?");
					System.out.println("Insira a conta de origem:");
					int contaOrigem = 0;
					contaOrigem = scanner.nextInt();
					System.out.println("Insira a conta destino:");
					int contaDestino = 0;
					contaDestino = scanner.nextInt();
					double quantia = 0.0;
					System.out.println("Insira o valor a ser transferido:");
					quantia = scanner.nextDouble();
					contaDao.transferencia(quantia, contaOrigem, contaDestino);
					System.out.println("Transferência realizada!");
					
					break;
					
				case 6:  //excluir cliente
					
					clienteDao.delete(cli);
					
					break;
					
				default:
					System.err.println("Opção Inválida");	
				}
				
				break;
				
			case 3: //Listar todos os clientes
				
				List<String> clientes = clienteDao.findAll();
				
				for(String Lista : clientes) {
					System.out.println(Lista);
				}
				
				break;
				
			case 4:
				System.out.println("Até mais ver");
				System.exit(0);
				break;
				
			default:
				System.out.println("Opção inválida");
				
				break;
			}
		}
	}
}
