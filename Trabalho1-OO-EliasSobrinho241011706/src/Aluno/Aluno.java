package Aluno;

import java.util.ArrayList;
import java.util.List;

//Molde base dos Alunos (Classe Aluno)

public class Aluno {
    protected String nome;
    protected String matricula;
    protected String curso;
    protected List<String> disciplinasMatriculadas; // Armazena códigos das disciplinas

    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.disciplinasMatriculadas = new ArrayList<>();
    }

    //Getters
    public String getNome() {
        return nome;
    }
    public String getMatricula() {
        return matricula;
    }
    public String getCurso() {
        return curso;
    }
    public List<String> getDisciplinasMatriculadas() {
        return disciplinasMatriculadas;
    }

    //Metodo para matrícula
    public void matricular(String codigoDisciplina) {
        if (!disciplinasMatriculadas.contains(codigoDisciplina)) {
            disciplinasMatriculadas.add(codigoDisciplina);
        }
    }

    //Metodo para trancar disciplina específica
    public void trancarDisciplina(String codigoDisciplina) {
        disciplinasMatriculadas.remove(codigoDisciplina);
        System.out.println("Disciplina " + codigoDisciplina + " trancada com sucesso.");
    }

    //Metodo para trancar semestre inteiro (todas as disciplinas)
    public void trancarSemestre() {
        disciplinasMatriculadas.clear();
        System.out.println("Semestre trancado. Todas as disciplinas foram removidas.");
    }

    //Metodo para verificar pré-requisitos (ainda não disponível)
    public boolean possuiPreRequisitos(List<String> prerequisitos) {
        return disciplinasMatriculadas.containsAll(prerequisitos);
    }

    //Função de editar nome e curso
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return nome + " - " + matricula + " - " + curso +
                "\nDisciplinas matriculadas: " + disciplinasMatriculadas;
    }

    //Gera a linha CSV para os dados do aluno
    public String toCSV() {
        return nome + ";" + matricula + ";" + curso + ";" + String.join(",", disciplinasMatriculadas);
    }

    public static Aluno fromCSV(String linha) {
        String[] partes = linha.split(";");
        Aluno aluno = new Aluno(partes[0], partes[1], partes[2]);
        if (partes.length > 3 && !partes[3].isEmpty()) {
            String[] disciplinas = partes[3].split(",");
            for (String d : disciplinas) {
                aluno.matricular(d);
            }
        }
        return aluno;
    }
}
