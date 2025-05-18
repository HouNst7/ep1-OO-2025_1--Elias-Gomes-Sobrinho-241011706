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

    //Para salvar os dados das disciplinas
    public String toCSV() {
        return nome + ";" + codigo + ";" + cargaHoraria + ";" + String.join(",", preRequisitos);
    }

    public static Disciplina fromCSV(String linha) {
        String[] partes = linha.split(";");
        Disciplina d = new Disciplina(partes[0], partes[1], Integer.parseInt(partes[2]));
        if (partes.length > 3 && !partes[3].isEmpty()) {
            String[] prereqs = partes[3].split(",");
            for (String p : prereqs) {
                d.addPreRequisito(p);
            }
        }
        return d;
    }
}
