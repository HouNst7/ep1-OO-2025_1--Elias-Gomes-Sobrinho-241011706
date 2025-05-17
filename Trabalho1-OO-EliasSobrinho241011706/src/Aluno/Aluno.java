package Aluno;

import java.util.ArrayList;
import java.util.List;

//Molde base dos Alunos (Classe Aluno)

public class Aluno {

    protected String nome;
    protected String matricula;
    protected String curso;
    protected List<String> disciplinasMatriculadas = new ArrayList<>();
    //Uso de atributos protected pois são visíveis para os arquivos deste Package, e "invisíveis para o resto"
    private List<String> disciplinasTrancadas = new ArrayList<>();
    private boolean semestreTrancado = false;

    //Entidade ALuno (Construtor)
    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
    }

    //Função matricular que vai estar adicionando à disciplinasMatriculadas o código da Disciplina
    public void matricular(String codigoDisciplina) {
        disciplinasMatriculadas.add(codigoDisciplina);
    }

    //Getters
    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getCurso() {
        return curso;
    }
    //Funções para as Disciplinas
    public void trancarDisciplina(String codigo) {
        if (disciplinasMatriculadas.contains(codigo)) {
            disciplinasMatriculadas.remove(codigo);
            disciplinasTrancadas.add(codigo);
            System.out.println("Disciplina trancada: " + codigo);
        } else {
            System.out.println("Aluno não está matriculado nesta disciplina, portanto não há como trancar.");
        }
    }

    public void trancarSemestre() {
        semestreTrancado = true;
        disciplinasTrancadas.addAll(disciplinasMatriculadas);
        disciplinasMatriculadas.clear();
        System.out.println("Semestre trancado. Todas as disciplinas foram trancadas.");
    }

    public boolean isSemestreTrancado() {
        return semestreTrancado;
    }

}
