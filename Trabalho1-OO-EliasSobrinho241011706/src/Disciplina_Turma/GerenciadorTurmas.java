package Disciplina_Turma;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//importando as informações do package aluno para realizar matriculas
import Aluno.*;
import static Aluno.GerenciadorAlunos.listarAlunosDaTurma;

public class GerenciadorTurmas {
    private static List<Turma> turmas = new ArrayList<>();

    public static void adicionarTurma(Turma turma) {
        turmas.add(turma);
    }

    public static List<Turma> getTurmas() {
        return turmas;
    }

    public static Turma buscarCodigoTurma(String codigoDisciplina){
        for (Turma turma : turmas) {
            if (turma.getDisciplina().getCodigo().equalsIgnoreCase(codigoDisciplina)){
                return turma;
            }
        }
        return null;
    }

    //Listar turmas simples para a função de "matricular aluno"
    public static void listarTurmas1(Scanner scanner) {
        for (int i = 0; i < turmas.size(); i++){
            System.out.println((i+1) + ". " + turmas.get(i));
        }
    }

    //Listar turmas complexo para o modo disciplina (tem a opção de mostrar os alunos matriculados nas turmas)
    public static void listarTurmas2(Scanner scanner) {
        for (int i = 0; i < turmas.size(); i++) {
            System.out.println((i + 1) + ". " + turmas.get(i));
        }

        System.out.print("Gostaria de verificar os alunos presentes em uma turma? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        if (resposta.equals("s")) {
            System.out.print("Digite o código da disciplina da turma: ");
            String codigo = scanner.nextLine();
            Turma turmaSelecionada = buscarCodigoTurma(codigo);
            if (turmaSelecionada != null) {
                listarAlunosDaTurma(turmaSelecionada);
            } else {
                System.out.println("Turma não encontrada.");
            }

            System.out.print("Pressione Enter para voltar ao menu...");
            scanner.nextLine();
        }
    }

    //Função para criar as Turmas atualizado com as opções que faltavamm
    public static void criarNovaTurma(Scanner scanner) {
        System.out.print("Digite o código da disciplina: ");
        String codigo = scanner.nextLine();
        Disciplina disciplina = GerenciadorDisciplinas.buscarPorCodigo(codigo);

        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }

        System.out.print("Digite o nome do professor: ");
        String professor = scanner.nextLine();

        System.out.print("Digite o semestre: ");
        String semestre = scanner.nextLine();

        System.out.print("Digite o número máximo de alunos: ");
        int maximoAlunos = Integer.parseInt(scanner.nextLine());

        System.out.print("A turma será presencial? (s/n): ");
        String tipo = scanner.nextLine().trim().toLowerCase();
        boolean presencial = tipo.equals("s");

        String sala = "Remoto";
        if (presencial) {
            System.out.print("Digite a sala da turma: ");
            sala = scanner.nextLine();
        }

        System.out.print("Digite o horário da aula (ex: Segundas 10h-12h): ");
        String horario = scanner.nextLine();

        Turma novaTurma = new Turma(disciplina, professor, semestre, maximoAlunos, presencial, sala, horario);
        adicionarTurma(novaTurma);

        System.out.println("Turma criada com sucesso!");
    }

    //Função para verificar se já existe uma turma no mesmo horário
    public static boolean existeTurmaMesmoHorario(Disciplina disciplina, String semestre, String horario) {
        for (Turma t : turmas) {
            if (t.getDisciplina().getCodigo().equals(disciplina.getCodigo()) &&
                    t.getSemestre().equalsIgnoreCase(semestre) &&
                    t.getHorario().equalsIgnoreCase(horario)) {
                return true;
            }
        }
        return false;
    }


    //Funções para salvar os dados das Turmas e dos alunos cadastrados
    public static void salvarTurmasTXT(String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (Turma t : turmas) {
                bw.write(t.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar turmas: " + e.getMessage());
        }
    }

    public static void carregarTurmasTXT(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Turma t = Turma.fromCSV(linha);
                if (t != null) {
                    turmas.add(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar turmas: " + e.getMessage());
        }
    }

    //Funções para salvar os dados das matriculas
    public static void salvarMatriculasTXT(String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (Turma t : turmas) {
                for (Aluno a : t.getAlunosMatriculados()) {
                    bw.write(t.getDisciplina().getCodigo() + ";" + a.getMatricula());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar matrículas: " + e.getMessage());
        }
    }

    public static void carregarMatriculasTXT(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length != 2) continue;

                String codDisciplina = partes[0];
                String matriculaAluno = partes[1];

                Turma turma = buscarCodigoTurma(codDisciplina);
                Aluno aluno = GerenciadorAlunos.buscarPorMatricula(matriculaAluno);

                if (turma != null && aluno != null) {
                    turma.matricularAluno(aluno);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar matrículas: " + e.getMessage());
        }
    }
}
