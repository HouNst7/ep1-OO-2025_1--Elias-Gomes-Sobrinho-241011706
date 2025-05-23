import Aluno.*;
import Avaliacao_Frequencia.AvaliacaoTipo;
import Avaliacao_Frequencia.GerenciadorPerfomanceAluno;
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
            System.out.println("3. Modo Avaliação/Frequência");
            System.out.println("0. Sair");
            int modo = lerInteiro();

            switch (modo) {
                case 1 -> modoAluno();
                case 2 -> modoDisciplinaTurma();
                case 3 -> modoAvaliacaoFrequencia();
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
        GerenciadorPerfomanceAluno.carregarAvaliacoesTXT("avaliacoes.txt");
        GerenciadorPerfomanceAluno.salvarAvaliacoesTXT("avaliacoes.txt");
    }

    //Metodo para salvar os dados dos alunos
    private static void salvarDados() {
        GerenciadorAlunos.salvarAlunosTXT("alunos.txt");
        GerenciadorDisciplinas.salvarDisciplinasTXT("disciplinas.txt");
        GerenciadorTurmas.salvarTurmasTXT("turmas.txt");
        GerenciadorTurmas.salvarMatriculasTXT("matriculas.txt");
    }

    //Modo Aluno no menu
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

    //Modo Disciplina/Turma no Menu
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

    //Metodo para Cadastrar um novo Aluno
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

        System.out.println("");
        System.out.println("Disciplinas disponíveis para matrícula: \n");
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

    //Metodo para trancar apenas uma disciplina
    private static void trancarDisciplina() {
        Aluno aluno = buscarAluno();
        if (aluno == null) return;

        System.out.print("Código da disciplina: ");
        String cod = scanner.nextLine();
        aluno.trancarDisciplina(cod);
    }

    //Metodo para permitir trancar o semestre (desmatricular de todas as disciplinas)
    private static void trancarSemestre() {
        Aluno aluno = buscarAluno();
        if (aluno == null) return;

        aluno.trancarSemestre();
    }

    //Metodo para fazer a edição das informações do aluno
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

    //Metodo para criar uma nova Disciplina
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

    //Metodo para Cadastrar Turma nova
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

        System.out.print("Digite o tipo de avaliação da turma (SIMPLES ou PONDERADA): ");
        String tipoAvaliacaoInput = scanner.nextLine().trim().toUpperCase();

        AvaliacaoTipo tipoAvaliacao;
        try {
            tipoAvaliacao = AvaliacaoTipo.valueOf(tipoAvaliacaoInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de avaliação inválido. Nesse caso será criada a turma usando o tipo de avaliação SIMPLES.");
            tipoAvaliacao = AvaliacaoTipo.SIMPLES;
        }

        Turma turma = new Turma(disciplina, professor, semestre, max, presencial, sala, horario, tipoAvaliacao);
        GerenciadorTurmas.adicionarTurma(turma);

        System.out.println("Turma cadastrada.");
    }

    private static Aluno buscarAluno() {
        System.out.print("Digite a Matrícula do aluno: ");
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

    //Modo Avaliação/Frequência no Menu
    private static void modoAvaliacaoFrequencia(){
        boolean executando = true;
        while (executando) {
            System.out.println("\n=== MODO AVALIAÇÃO / FREQUÊNCIA ===");
            System.out.println("1. Lançar notas e presença de um aluno");
            System.out.println("2. Calcular a média final e frequência");
            System.out.println("3. Informar situação final do aluno (aprovado/reprovado)");
            System.out.println("0. Voltar");
            int opcao = lerInteiro();

            switch (opcao) {
                case 1 -> lancarNotasPresencas();
                case 2 -> calcularMediaFrequencia();
                case 3 -> informarSituacaoAluno();
                case 0 -> executando = false;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    //Metodo para Lançar notas e Presenças
    private static void lancarNotasPresencas() {
        System.out.println("Lembrete: O usuário deverá digitar as notas de cada aluno matrículado na turma que escolher");
        System.out.println("");
        GerenciadorTurmas.listarTurmas1(scanner);
        System.out.println("");
        System.out.print("Código da turma que o usuário deseja lançar as notas dos alunos: ");
        String cod = scanner.nextLine();
        System.out.println("");

        Turma turma = GerenciadorTurmas.buscarCodigoTurma(cod);
        if (turma == null) {
            System.out.println("O código informado não corresponde a nenhuma turma");
            return;
        }

        for (Aluno aluno: turma.getAlunosMatriculados()){
            System.out.println("Lançando notas e presença do aluno: " + aluno.getNome());

            System.out.println("Nota P1: ");
            double p1 = scanner.nextDouble();
            System.out.println("Nota P2: ");
            double p2 = scanner.nextDouble();
            System.out.println("Nota P3: ");
            double p3 = scanner.nextDouble();
            System.out.print("Nota das Listas: ");
            double l = scanner.nextDouble();
            System.out.print("Nota do Seminário: ");
            double s = scanner.nextDouble();

            System.out.println("Frequência do aluno em % :");
            double frequencia = scanner.nextDouble();
            scanner.nextLine();

            GerenciadorPerfomanceAluno.lancarNotas(aluno, turma, p1, p2, p3, 1, s);
            GerenciadorPerfomanceAluno.lancarFrequencia(aluno, turma, frequencia);
        }
    }
    //Metodo para calcular Media e Frequência
    private static void calcularMediaFrequencia() {
        Aluno aluno = buscarAluno();
        if (aluno == null) return;

        System.out.println("");
        GerenciadorTurmas.listarTurmas1(scanner);
        System.out.println("");
        System.out.println("Informe o Código da Disciplina que deseja conferir a média e frequência: ");
        String cod = scanner.nextLine();

        Turma turma = GerenciadorTurmas.buscarCodigoTurma(cod);
        if (turma == null) {
            System.out.println("Turma não foi encontrada / Código não condiz com nenhuma turma");
            return;
        }

        double media = GerenciadorPerfomanceAluno.calcularMediaFinal(aluno,turma);
        double frequencia = GerenciadorPerfomanceAluno.obterFrequencia(aluno,turma);

        System.out.println(String.format("Média final: %.2f", media));
        System.out.println(String.format("Frequência: %.2f", frequencia));
    }

    //Metodo para informar a situacao do aluno
    private static void informarSituacaoAluno() {
        Aluno aluno = buscarAluno();
        if (aluno == null) return;

        System.out.println("");
        GerenciadorTurmas.listarTurmas1(scanner);
        System.out.println("");
        System.out.println("Informe o código da disciplina que deseja conferir a situação final do aluno: ");
        String cod = scanner.nextLine();

        Turma turma = GerenciadorTurmas.buscarCodigoTurma(cod);
        if (turma == null) {
            System.out.println("Turma não foi encontada / Código não condiz com nenhuma turma");
            return;
        }

        String situacao = GerenciadorPerfomanceAluno.informarSituacao(aluno,turma);
        System.out.println("Situação do aluno: " + situacao);
    }
}
