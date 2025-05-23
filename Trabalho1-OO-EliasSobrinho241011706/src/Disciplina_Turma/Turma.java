package Disciplina_Turma;

import Aluno.Aluno;
import Avaliacao_Frequencia.*;
import java.util.*;
import static Aluno.GerenciadorAlunos.buscarPorMatricula;

public class Turma {
    private Disciplina disciplina;
    private String professor;
    private String semestre;
    private int maximoAlunos;
    private List<Aluno> alunosMatriculados;
    private boolean presencial;
    private String sala;
    private String horario;
    private AvaliacaoTipo tipoAvaliacao;
    private Map<String, GerenciadorPerfomanceAluno> GerenciadorPerfomanceAlunos;

    private Map<Aluno, GerenciadorPerfomanceAluno> desempenhoPorAluno = new HashMap<>();

    public Map<Aluno, GerenciadorPerfomanceAluno> getDesempenhoPorAluno() {
        return desempenhoPorAluno;
    }


    public Turma(Disciplina disciplina, String professor, String semestre, int maximoAlunos, boolean presencial, String sala, String horario, AvaliacaoTipo tipoAvaliacao) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.maximoAlunos = maximoAlunos;
        this.presencial = presencial;
        this.sala = presencial ? sala : "Remoto";  // só terá sala se for presencial
        this.horario = horario;
        this.alunosMatriculados = new ArrayList<>();
        this.tipoAvaliacao = tipoAvaliacao;
        this.GerenciadorPerfomanceAlunos = new HashMap<>();
    }

    //Funcao para matricular aluno ou não (caso esteja lotada)
    public boolean matricularAluno(Aluno aluno) {
        if (alunosMatriculados.size() >= maximoAlunos) {
            System.out.println("Turma lotada, não há como matricular mais alunos");
            return false;
        }

        alunosMatriculados.add(aluno);
        aluno.matricular(disciplina.getCodigo());
        return true;
    }

    //Getters e setters

    public AvaliacaoTipo getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public boolean isPresencial() {
        return presencial;
    }

    public String getSala() {
        return sala;
    }

    public String getHorario() {
        return horario;
    }

    public String getSemestre() {
        return semestre;
    }

    //Saida em texto das informações da Disciplina
    @Override
    public String toString() {
        return "Disciplina de " + disciplina.getNome() + " (Código: " + disciplina.getCodigo() + "), " +
                "Professor: " + professor + ", Semestre: " + semestre +
                ", Tipo: " + (presencial ? "Presencial" : "Remota") +
                (presencial ? ", Sala: " + sala : "") +
                ", Horário: " + horario +
                ", Vagas: " + alunosMatriculados.size() + "/" + maximoAlunos
                + ", Avaliação tipo: " + tipoAvaliacao;
    }

    //Divide as informações e as lista em partes
    public static Turma fromCSV(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 8) return null;
        Disciplina d = GerenciadorDisciplinas.buscarPorCodigo(partes[0]);
        if (d == null) return null;

        String professor = partes[1];
        String semestre = partes[2];
        int maximoAlunos = Integer.parseInt(partes[3]);
        boolean presencial = Boolean.parseBoolean(partes[4]);
        String sala = partes[5];
        String horario = partes[6];
        AvaliacaoTipo tipoAvaliacao = AvaliacaoTipo.valueOf(partes[7]);

        return new Turma(d, professor, semestre, maximoAlunos, presencial, sala, horario, tipoAvaliacao);
    }

    public String toCSV() {
        return disciplina.getCodigo() + ";" +
                professor + ";" +
                semestre + ";" +
                maximoAlunos + ";" +
                presencial + ";" +
                sala + ";" +
                horario + ";" +
                tipoAvaliacao.name();
    }
}
