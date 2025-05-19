import Aluno.*;
import Disciplina_Turma.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        //Salvamento de arquivos
        GerenciadorAlunos.carregarAlunosTXT("alunos.txt");
        GerenciadorDisciplinas.carregarDisciplinasTXT("disciplinas.txt");
        GerenciadorTurmas.carregarTurmasTXT("turmas.txt");

        //Variáveis a serem reutilizadas em vários cases (estava declarando em todos, portanto dava erro)
        String matricula;

        //Enquanto estiver rodando o codigo
        while (rodando) { //Divisão do Menu em 2 switch cases para escolher o modo
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Modo Aluno");
            System.out.println("2. Modo Disciplina/Turma");
            System.out.println("0. Sair");
            int modo = scanner.nextInt();
            scanner.nextLine();

            switch (modo) {
                case 1:
                    boolean modoAluno = true;
                    while (modoAluno) {
                        System.out.println("\n=== MODO ALUNO ===");
                        System.out.println("1. Cadastrar Aluno Normal");
                        System.out.println("2. Cadastrar Aluno Especial");
                        System.out.println("3. Listar Alunos");
                        System.out.println("4. Matricular aluno em disciplina");
                        System.out.println("5. Trancar disciplina");
                        System.out.println("6. Trancar semestre");
                        System.out.println("7. Editar aluno");
                        System.out.println("0. Voltar ao menu principal");
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
                            //Case para ativar a opção de editar o aluno
                            case 7:
                                System.out.print("Entre a matrícula do aluno que deseja alterar os dados: ");
                                matricula = scanner.nextLine();
                                aluno = GerenciadorAlunos.buscarPorMatricula(matricula);
                                if (aluno == null) {
                                    System.out.println("Aluno não encontrado.");
                                    break;
                                }

                                System.out.println("Aluno a ser editado: " + aluno.getNome() + " - " + aluno.getCurso());

                                System.out.print("Escreva o novo nome (aperte enter caso não deseje alterar): ");
                                String novoNome = scanner.nextLine();
                                if (!novoNome.isEmpty()) {
                                    aluno.setNome(novoNome);
                                }

                                System.out.print("Escreva o novo curso (aperte enter caso não deseje alterar): ");
                                String novoCurso = scanner.nextLine();
                                if (!novoCurso.isEmpty()) {
                                    aluno.setCurso(novoCurso);
                                }

                                System.out.println("Dados do aluno atualizados.");
                                break;
                            //Sai do modo aluno e retorna ao menu principal
                            case 0:
                                modoAluno = false;
                                break;
                            default:
                                System.out.println("Opção inválida, por favor, digite um número válido (1 a 7).");
                        }
                    }
                    break;

                case 2:
                    boolean modoDisciplina = true;
                    while (modoDisciplina) {
                        System.out.println("\n=== MODO DISCIPLINA / TURMA ===");
                        System.out.println("1. Cadastrar nova disciplina");
                        System.out.println("2. Cadastrar nova turma");
                        System.out.println("3. Listar disciplinas");
                        System.out.println("4. Listar turmas");
                        System.out.println("0. Voltar ao menu principal");
                        int opcao = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcao) {
                            case 1:
                                System.out.print("Nome da disciplina: ");
                                String nomeDisc = scanner.nextLine();
                                System.out.print("Código da disciplina: ");
                                String codDisc = scanner.nextLine();
                                System.out.print("Carga horária (em horas): ");
                                int carga = scanner.nextInt();
                                scanner.nextLine(); // Limpar buffer

                                Disciplina novaDisciplina = new Disciplina(nomeDisc, codDisc, carga);
                                GerenciadorDisciplinas.adicionarDisciplina(novaDisciplina);

                                System.out.print("Deseja adicionar pré-requisitos? (s/n): ");
                                String resp = scanner.nextLine();
                                while (resp.equalsIgnoreCase("s")) {
                                    System.out.print("Código do pré-requisito: ");
                                    String prereq = scanner.nextLine();
                                    novaDisciplina.addPreRequisito(prereq);

                                    System.out.print("Adicionar outro pré-requisito? (s/n): ");
                                    resp = scanner.nextLine();
                                }

                                System.out.println("Disciplina cadastrada com sucesso.");
                                break;

                            case 2:
                                System.out.print("Código da disciplina: ");
                                String codigoDisciplina = scanner.nextLine();
                                Disciplina disciplina = GerenciadorDisciplinas.buscarPorCodigo(codigoDisciplina);
                                if (disciplina == null) {
                                    System.out.println("Disciplina não encontrada.");
                                    break;
                                }

                                System.out.print("Nome do professor: ");
                                String professor = scanner.nextLine();
                                System.out.print("Semestre (ex: 2024.1): ");
                                String semestre = scanner.nextLine();
                                System.out.print("Máximo de alunos: ");
                                int maxAlunos = scanner.nextInt();
                                scanner.nextLine(); // limpar buffer

                                Turma novaTurma = new Turma(disciplina, professor, semestre, maxAlunos);
                                GerenciadorTurmas.adicionarTurma(novaTurma);

                                System.out.println("Turma cadastrada com sucesso.");
                                break;

                            case 3:
                                GerenciadorDisciplinas.listarDisciplinas();
                                break;

                            case 4:
                                GerenciadorTurmas.listarTurmas();
                                break;

                            case 0:
                                modoDisciplina = false;
                                break;
                            default:
                                System.out.println("Opção inválida, por favor, digite um número válido (1 a 4).");
                        }
                    }
                    break;

                case 0: //Função para salvar os dados dos alunos e fechar o programa
                    GerenciadorAlunos.salvarAlunosTXT("alunos.txt");
                    GerenciadorDisciplinas.salvarDisciplinasTXT("disciplinas.txt");
                    GerenciadorTurmas.salvarTurmasTXT("turmas.txt");
                    System.out.println("Saindo do sistema");
                    rodando = false;
                    break;

                default:
                    System.out.println("Opção inválida, por favor, digite um número válido (1 ou 2).");
            }
        }

        scanner.close();
    }
}
