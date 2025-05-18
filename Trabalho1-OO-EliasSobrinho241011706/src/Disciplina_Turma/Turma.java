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

    public Turma(Disciplina disciplina, String professor, String semestre, int maximoAlunos) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.maximoAlunos = maximoAlunos;
        this.alunosMatriculados = new ArrayList<Aluno>();
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

    //Saida em texto das informações da Disciplina
    @Override
    public String toString() {
        return "Turma de " + disciplina.getNome() + " (" + disciplina.getCodigo() + "), " +
                "Professor: " + professor + ", Semestre: " + semestre +
                ", Vagas: " + alunosMatriculados.size() + "/" + maximoAlunos;
    }

    public String toCSV() {
        return disciplina.getCodigo() + ";" + professor + ";" + semestre + ";" + maximoAlunos;
    }

    public static Turma fromCSV(String linha) {
        String[] partes = linha.split(";");
        Disciplina d = GerenciadorDisciplinas.buscarPorCodigo(partes[0]);
        if (d == null) return null; // para remover turmas com disciplina inexistente

        return new Turma(d, partes[1], partes[2], Integer.parseInt(partes[3]));
    }

}
