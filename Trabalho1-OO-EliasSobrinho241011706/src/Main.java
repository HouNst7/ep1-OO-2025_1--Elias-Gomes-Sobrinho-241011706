import Aluno.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        //Enquanto estiver rodando o codigo
        while (rodando) {
            System.out.println("\n=== MODO ALUNO ===");
            System.out.println("1. Cadastrar Aluno Normal");
            System.out.println("2. Cadastrar Aluno Especial");
            System.out.println("3. Listar Alunos");
            System.out.println("0. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                case 2:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Matrícula: ");
                    String matricula = scanner.nextLine();
                    System.out.print("Curso: ");
                    String curso = scanner.nextLine();

                    Aluno novoAluno = (opcao == 1)
                            ? new Aluno(nome, matricula, curso)
                            : new AlunoEspecial(nome, matricula, curso);

                    GerenciadorAlunos.cadastrarAluno(novoAluno); //Guarda as informacoes do aluno
                    break;
                case 3:
                    GerenciadorAlunos.listarAlunos();
                    break;
                case 0:
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida, por favor, digite um número válido (1 a 3).");
            }
        }

        scanner.close();
    }
}
