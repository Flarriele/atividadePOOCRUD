package br.com.CrudCliente;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main 
{

	private static Scanner scanner = new Scanner(System.in);
	private static ClienteDAO clienteDAO = new ClienteDAO();

	public static void main( String[] args )
    {
    	System.out.println("Iniciando sistema para cadastro de Clientes.");
    	
    	
    	int escolha;

    	do {
    		exibirMenu();
	    	escolha = scanner.nextInt();
	    	
	    	switch (escolha) {
		    	case 1:
	                cadastrarCliente();
	                break;
	            case 2:
	                listarClientes();
	                break;
	            case 3:
	                atualizarCliente();
	                break;
	            case 4:
	                excluirCliente();
	                break;
		    	case 0:
		    		System.out.println("Encerrando sistema para cadastro de Clientes.");
		    		break;
		    	default:
		    		System.out.println("Selecione uma opção válida!");
	    	}
    	} while (escolha != 0);
    	
    	HibernateUtil.shutdown();
        scanner.close();
    }
    
    private static void exibirMenu() {
    	System.out.println("Digite a opção desejada:");
    	System.out.println("Opção 1 - Cadastrar cliente");
    	System.out.println("Opção 2 - Listar clientes");
    	System.out.println("Opção 3 - Atualizar cliente");
    	System.out.println("Opção 4 - Excluir cliente");
    	System.out.println("Opção 0 - Encerrar Sistema");
    }
    
    private static void cadastrarCliente() {
        scanner.nextLine(); // Consumir a quebra de linha pendente
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, email, telefone);
        clienteDAO.salvar(novoCliente);
    }

    private static void listarClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes != null && !clientes.isEmpty()) {
            for (Cliente c : clientes) {
            	System.out.println(c);
            }
        } else {
            System.out.println("Nenhum cliente cadastrado.");
        }
    }

    private static void atualizarCliente() {
        System.out.print("Digite o ID do cliente a ser atualizado: ");
        Long id;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID inválido! Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer
            return;
        }
        scanner.nextLine(); // Consumir a quebra de linha

        Cliente clienteExistente = clienteDAO.buscarPorId(id);
        if (clienteExistente != null) {
            System.out.println("Cliente encontrado: " + clienteExistente);
            System.out.print("Novo Nome (" + clienteExistente.getNome() + "): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.isEmpty()) {
                clienteExistente.setNome(novoNome);
            }

            System.out.print("Novo Email (" + clienteExistente.getEmail() + "): ");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.isEmpty()) {
                clienteExistente.setEmail(novoEmail);
            }

            System.out.print("Novo Telefone (" + clienteExistente.getTelefone() + "): ");
            String novoTelefone = scanner.nextLine();
            if (!novoTelefone.isEmpty()) {
                clienteExistente.setTelefone(novoTelefone);
            }

            clienteDAO.atualizar(clienteExistente);
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
        }
    }

    private static void excluirCliente() {
        System.out.print("Digite o ID do cliente a ser excluído: ");
        Long id;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("ID inválido! Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer
            return;
        }
        scanner.nextLine(); // Consumir a quebra de linha

        Cliente clienteParaExcluir = clienteDAO.buscarPorId(id);
        if (clienteParaExcluir != null) {
            System.out.print("Tem certeza que deseja excluir o cliente " + clienteParaExcluir.getNome() + "? (s/n): ");
            String confirmacao = scanner.nextLine().toLowerCase();
            if (confirmacao.equals("s")) {
                clienteDAO.excluir(clienteParaExcluir);
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
        }
    }

}
