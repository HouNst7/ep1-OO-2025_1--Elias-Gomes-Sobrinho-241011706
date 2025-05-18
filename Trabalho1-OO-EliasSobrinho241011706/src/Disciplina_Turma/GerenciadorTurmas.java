package Disciplina_Turma;

import java.io.*;
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

}
