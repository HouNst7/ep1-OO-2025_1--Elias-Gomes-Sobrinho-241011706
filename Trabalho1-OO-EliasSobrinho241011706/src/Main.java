import Aluno.*;
import Disciplina_Turma.*;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    boolean rodando = true;

    public static void main(String[] args) {
        carregarDados();

        //Enquanto estiver rodando o codigo
        boolean rodando = true;
        while (rodando) { //Divisão do Menu em 2 switch cases para escolher o modo
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Modo Aluno");
            System.out.println("2. Modo Disciplina/Turma");
            System.out.println("0. Sair");
            int modo = lerInteiro();

            switch (modo) {
                case 1 -> modoAluno();
                case 2 -> modoDisciplinaTurma();
                case 0 -> {
                    salvarDados();
                    System.out.println("Saindo do sistema...");
                    rodando = false;
                }
                default -> System.out.println("Opção inválida!");
            }
        }

        scanner.close();
    }

    //Carregamento de arquivos
    private static void carregarDados() {
        GerenciadorAlunos.carregarAlunosTXT("alunos.txt");
        GerenciadorDisciplinas.carregarDisciplinasTXT("disciplinas.txt");
        GerenciadorTurmas.carregarTurmasTXT("turmas.txt");
        GerenciadorTurmas.carregarMatriculasTXT("matriculas.txt");
    }

    //Função para salvar os dados dos alunos
    private static void salvarDados() {
        GerenciadorAlunos.salvarAlunosTXT("alunos.txt");
        GerenciadorDisciplinas.salvarDisciplinasTXT("disciplinas.txt");
        GerenciadorTurmas.salvarTurmasTXT("turmas.txt");
        GerenciadorTurmas.salvarMatriculasTXT("matriculas.txt");
    }

    private static void modoAluno() {
        boolean executando = true;
        while (executando) {
            System.out.println("\n=== MODO ALUNO ===");
            System.out.println("1. Cadastrar Aluno Normal");
            System.out.println("2. Cadastrar Aluno Especial");
            System.out.println("3. Listar Alunos");
            System.out.println("4. Matricular em disciplina");
            System.out.println("5. Trancar disciplina");
            System.out.println("6. Trancar semestre");
            System.out.println("7. Editar aluno");
            System.out.println("0. Voltar");
            int opcao = lerInteiro();

            switch (opcao) {
                case 1, 2 -> cadastrarAluno(opcao == 2);
                case 3 -> GerenciadorAlunos.listarAlunos();
                case 4 -> matricularAlunoDisciplina();
                case 5 -> trancarDisciplina();
                case 6 -> trancarSemestre();
                case 7 -> editarAluno();
                case 0 -> executando = false;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void modoDisciplinaTurma() {
        boolean executando = true;
        while (executando) {
            System.out.println("\n=== MODO DISCIPLINA / TURMA ===");
            System.out.println("1. Cadastrar nova disciplina");
            System.out.println("2. Cadastrar nova turma");
            System.out.println("3. Listar disciplinas");
            System.out.println("4. Listar turmas");
            System.out.println("0. Voltar");
            int opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrarDisciplina();
                case 2 -> cadastrarTurma();
                case 3 -> GerenciadorDisciplinas.listarDisciplinas();
                case 4 -> GerenciadorTurmas.listarTurmas2(scanner);
                case 0 -> executando = false;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    //Função para Cadastrar um novo Aluno
    private static void cadastrarAluno(boolean especial) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Curso: ");
        String curso = scanner.nextLine();

        Aluno aluno = especial
                ? new AlunoEspecial(nome, matricula, curso)
                : new Aluno(nome, matricula, curso);

        GerenciadorAlunos.cadastrarAluno(aluno);
        System.out.println("Aluno cadastrado.");
    }

    private static void matricularAlunoDisciplina() {
        Aluno aluno = buscarAluno();
        if (aluno == null) return;

        GerenciadorTurmas.listarTurmas1(scanner);
        System.out.print("Código da disciplina: ");
        String codigo = scanner.nextLine();

        Turma turma = GerenciadorTurmas.buscarCodigoTurma(codigo);
        if (turma != null) {
            turma.matricularAluno(aluno);
        } else {
            System.out.println("Turma não encontrada.");
        }
    }

    //Função para trancar apenas uma disciplina
    private static void trancarDisciplina() {
        Aluno aluno = buscarAluno();
        if (aluno == null) return;

        System.out.print("Código da disciplina: ");
        String cod = scanner.nextLine();
        aluno.trancarDisciplina(cod);
    }

    //Função para permitir trancar o semestre (desmatricular de todas as disciplinas)
    private static void trancarSemestre() {
        Aluno aluno = buscarAluno();
        if (aluno == null) return;

        aluno.trancarSemestre();
    }

    //Função para fazer a edição das informações do aluno
    private static void editarAluno() {
        Aluno aluno = buscarAluno();
        if (aluno == null) return;

        System.out.println("Editando: " + aluno.getNome() + " - " + aluno.getCurso());

        System.out.print("Novo nome (Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isEmpty()) aluno.setNome(novoNome);

        System.out.print("Novo curso (Enter para manter): ");
        String novoCurso = scanner.nextLine();
        if (!novoCurso.isEmpty()) aluno.setCurso(novoCurso);

        System.out.println("Aluno atualizado.");
    }

    //Função para criar uma nova Disciplina
    private static void cadastrarDisciplina() {
        System.out.print("Nome da disciplina: ");
        String nome = scanner.nextLine();
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Carga horária (ex: 60): ");
        int carga = lerInteiro();

        Disciplina d = new Disciplina(nome, codigo, carga);
        GerenciadorDisciplinas.adicionarDisciplina(d);

        System.out.print("Adicionar pré-requisitos? (s/n): ");
        String resp = scanner.nextLine();
        while (resp.equalsIgnoreCase("s")) {
            System.out.print("Código do pré-requisito: ");
            String prereq = scanner.nextLine();
            d.addPreRequisito(prereq);
            System.out.print("Adicionar outro? (s/n): ");
            resp = scanner.nextLine();
        }

        System.out.println("Disciplina cadastrada.");
    }

    //Função para Cadastrar Turma nova
    private static void cadastrarTurma() {
        System.out.print("Código da disciplina: ");
        String codigo = scanner.nextLine();
        Disciplina disciplina = GerenciadorDisciplinas.buscarPorCodigo(codigo);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }

        System.out.print("Nome do(a) professor(a): ");
        String professor = scanner.nextLine();
        System.out.print("Semestre (ex: 2024.1): ");
        String semestre = scanner.nextLine();
        System.out.print("Máximo de alunos: ");
        int max = lerInteiro();
        System.out.print("É presencial? (s/n): ");
        boolean presencial = scanner.nextLine().equalsIgnoreCase("s");
        String sala = presencial ? solicitar("Sala: ") : "Remoto";
        String horario = solicitar("Horário da aula: ");

        //Função para impedir o cadastro de 2 turmas no mesmo horário
        if (GerenciadorTurmas.existeTurmaMesmoHorario(disciplina, semestre, horario)) {
            System.out.println("Erro: Já existe uma turma dessa disciplina neste mesmo horário e semestre.");
            return;
        }

        Turma turma = new Turma(disciplina, professor, semestre, max, presencial, sala, horario);
        GerenciadorTurmas.adicionarTurma(turma);

        System.out.println("Turma cadastrada.");
    }

    private static Aluno buscarAluno() {
        System.out.print("Matrícula do aluno: ");
        String matricula = scanner.nextLine();
        Aluno aluno = GerenciadorAlunos.buscarPorMatricula(matricula);
        if (aluno == null) System.out.println("Aluno não encontrado.");
        return aluno;
    }

    private static int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.println("Digite um número válido.");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }

    //Serve para pedir horário de aula e modo da Turma (presencial ou remoto)
    private static String solicitar(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}
