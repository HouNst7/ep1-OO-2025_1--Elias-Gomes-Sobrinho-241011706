package Disciplina_Turma;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Cria as listas de Disciplinas
public class GerenciadorDisciplinas {
    private static List<Disciplina> disciplinas = new ArrayList<>();

    public static void adicionarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    //Funcao para retornar as listas de disciplinas
    public static List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    //Busca a disciplina pelo codigo da matricula
    public static Disciplina buscarPorCodigo(String codigo) {
        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) {
                return d;
            }
        }
        return null;
    }

    public static void listarDisciplinas() {
        for (Disciplina d : disciplinas) {
            System.out.println(d);
        }
    }

    //Salva os dados em formato CSV
    public static void salvarDisciplinasTXT(String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (Disciplina d : disciplinas) {
                bw.write(d.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }

    public static void carregarDisciplinasTXT(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Disciplina d = Disciplina.fromCSV(linha);
                disciplinas.add(d);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
    }
}
