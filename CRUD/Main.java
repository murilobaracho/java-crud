import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DbEngine db = new DbEngine();
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n========== MENU PRINCIPAL ==========");
            System.out.println("1 - Listar todos os funcionários");
            System.out.println("2 - Cadastrar funcionário");
            System.out.println("3 - Cadastrar departamento");
            System.out.println("4 - Atualizar funcionário");
            System.out.println("5 - Excluir registro");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.println("\n--- LISTA DE FUNCIONÁRIOS ---");
                    db.select();
                    break;
                case 2:
                    System.out.println("\n--- CADASTRAR FUNCIONÁRIO ---");
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Cargo: ");
                    String cargo = scanner.nextLine();
                    System.out.print("Salário: ");
                    double salario = scanner.nextDouble();
                    scanner.nextLine(); // Limpa o buffer
                    System.out.print("E-mail: ");
                    String email = scanner.nextLine();
                    System.out.print("Contrato Ativo? (true/false): ");
                    boolean ativo = scanner.nextBoolean();

                    db.insert(nome, cargo, salario, email, ativo);
                    break;
                case 3:
                    System.out.println("\n--- CADASTRAR DEPARTAMENTO ---");
                    System.out.print("Nome do departamento: ");
                    String nomeDepto = scanner.nextLine();
                    System.out.print("Sigla / Código: ");
                    String siglaDepto = scanner.nextLine();

                    db.insertDepartamento(nomeDepto, siglaDepto);
                    break;
                case 4:
                    System.out.println("\n--- ATUALIZAR FUNCIONÁRIO ---");
                    System.out.print("Digite o ID do funcionário: ");
                    int idUpdate = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer
                    System.out.print("Digite o novo cargo: ");
                    String novoCargo = scanner.nextLine();

                    db.update(idUpdate, novoCargo);
                    break;
                case 5:
                    System.out.println("\n--- EXCLUIR REGISTRO ---");
                    System.out.print("Digite o ID do funcionário a ser deletado: ");
                    int idDelete = scanner.nextInt();

                    db.delete(idDelete);
                    break;
                case 0:
                    System.out.println("Encerrando a aplicação...");
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }
        scanner.close();
    }
}