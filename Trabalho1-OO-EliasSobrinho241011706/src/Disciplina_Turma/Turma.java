package Disciplina_Turma;

import Aluno.Aluno;
import java.util.ArrayList;
import java.util.List;

public class Turma {
    private Disciplina disciplina;
    private String professor;
    private String semestre;
    private int maximoAlunos;
    private List<Aluno> alunosMatriculados;
    private boolean presencial;
    private String sala;
    private String horario;


    public Turma(Disciplina disciplina, String professor, String semestre, int maximoAlunos, boolean presencial, String sala, String horario) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.maximoAlunos = maximoAlunos;
        this.presencial = presencial;
        this.sala = presencial ? sala : "Remoto";  // só terá sala se for presencial
        this.horario = horario;
        this.alunosMatriculados = new ArrayList<>();
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
        return "Turma de " + disciplina.getNome() + " (" + disciplina.getCodigo() + "), " +
                "Professor: " + professor + ", Semestre: " + semestre +
                ", Tipo: " + (presencial ? "Presencial" : "Remota") +
                (presencial ? ", Sala: " + sala : "") +
                ", Horário: " + horario +
                ", Vagas: " + alunosMatriculados.size() + "/" + maximoAlunos;
    }

    //Divide as informações e as lista em partes
    public static Turma fromCSV(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 7) return null;
        Disciplina d = GerenciadorDisciplinas.buscarPorCodigo(partes[0]);
        if (d == null) return null;

        String professor = partes[1];
        String semestre = partes[2];
        int maximoAlunos = Integer.parseInt(partes[3]);
        boolean presencial = Boolean.parseBoolean(partes[4]);
        String sala = partes[5];
        String horario = partes[6];

        return new Turma(d, professor, semestre, maximoAlunos, presencial, sala, horario);
    }

    public String toCSV() {
        return disciplina.getCodigo() + ";" +
                professor + ";" +
                semestre + ";" +
                maximoAlunos + ";" +
                presencial + ";" +
                sala + ";" +
                horario;
    }
}
