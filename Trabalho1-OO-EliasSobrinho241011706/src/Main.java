import Aluno.*;
import Disciplina_Turma.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;
        GerenciadorAlunos.carregarAlunosTXT("alunos.txt");

        //Variáveis a serem reutilizadas em vários cases (estava declarando em todos, portanto dava erro)
        String matricula;


        //Enquanto estiver rodando o codigo
        while (rodando) {
            System.out.println("\n=== MODO ALUNO ===");
            System.out.println("1. Cadastrar Aluno Normal");
            System.out.println("2. Cadastrar Aluno Especial");
            System.out.println("3. Listar Alunos");
            System.out.println("4. Matricular aluno em disciplina");
            System.out.println("5. Trancar disciplina");
            System.out.println("6. Trancar semestre");
            System.out.println("0. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:

                case 2:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Matrícula: ");
                    matricula = scanner.nextLine();
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

                case 4:
                    System.out.print("Matrícula do aluno: ");
                    matricula = scanner.nextLine();
                    Aluno aluno = GerenciadorAlunos.buscarPorMatricula(matricula);
                    if (aluno == null) {
                        System.out.println("Aluno não encontrado.");
                        break;
                    }

                    GerenciadorTurmas.listarTurmas();
                    System.out.print("Digite o código da disciplina para matrícula: ");
                    String codigo = scanner.nextLine();

                    Turma turma = GerenciadorTurmas.buscarCodigoTurma(codigo);
                    if (turma == null) {
                        System.out.println("Turma não encontrada.");
                    } else {
                        turma.matricularAluno(aluno);
                    }
                    break;
                //Metodo para trancar apenas uma disciplina
                case 5:
                    System.out.print("Matrícula do aluno: ");
                    matricula = scanner.nextLine();
                    aluno = GerenciadorAlunos.buscarPorMatricula(matricula);
                    if (aluno != null) {
                        System.out.print("Código da disciplina: ");
                        String cod = scanner.nextLine();
                        aluno.trancarDisciplina(cod);
                    }
                    break;
                //Metodo para permitir trancar o semestre (desmatricular de todas as disciplinas)
                case 6:
                    System.out.print("Matrícula do aluno: ");
                    matricula = scanner.nextLine();
                    aluno = GerenciadorAlunos.buscarPorMatricula(matricula);
                    if (aluno != null) {
                        aluno.trancarSemestre();
                    }
                    break;

                case 0: //Função para salvar os dados dos alunos e fechar o programa
                    GerenciadorAlunos.salvarAlunosTXT("alunos.txt");
                    System.out.println("Saindo do sistema");

                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida, por favor, digite um número válido (1 a 3).");
            }
        }

        scanner.close();
    }
}
