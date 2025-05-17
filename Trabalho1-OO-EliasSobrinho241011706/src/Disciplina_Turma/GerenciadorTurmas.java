package Disciplina_Turma;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

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
            if (turma.getDisciplina().getCodigo().equalsIgnoreCase(codigoDisciplina)){ //EqualsIgnoreCase é pra aceitar tanto minúsculas quanto maiúsculas
                return turma;
            }
        }
        return null;
    }

    public static void listarTurmas() {
        for (int i = 0; i < turmas.size(); i++){
            System.out.println((i+1) + ". " + turmas.get(i));
        }
    }
}
