package Disciplina_Turma;

import java.util.ArrayList;
import java.util.List;

//Classe para as disciplinas
public class Disciplina {
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private List<String> preRequisitos;

    public Disciplina(String nome, String codigo, int cargaHoraria) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = new ArrayList<>();
    }

    //Getters
    public String getNome() {
        return nome;
    }
    public String getCodigo() {
        return codigo;
    }
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void addPreRequisito(String codigo){
        preRequisitos.add(codigo);
    }

    public List<String> getPreRequisitos() {
        return preRequisitos;
    }

    @Override
    public String toString() {
        return nome + " (" + codigo + ")";
    }
}
