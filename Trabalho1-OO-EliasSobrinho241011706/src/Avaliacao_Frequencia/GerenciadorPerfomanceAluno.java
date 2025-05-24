package Avaliacao_Frequencia;

import Aluno.Aluno;
import Disciplina_Turma.Turma;
import java.io.*;
import java.util.*;

public class GerenciadorPerfomanceAluno {
    private Aluno aluno;
    private Avaliacao avaliacao;
    private Frequencia frequencia;
    private double mediaFinal;
    private boolean aprovado;
    //Cria mapas estáticos para guardar informações de avaliações e frequências
    private static final Map<String, Avaliacao> avaliacoes = new HashMap<>();
    private static final Map<String, Double> frequencias = new HashMap<>();

    //Cria uma chave (para identificar dados nos mapas criados) combinando matrícula do aluno e código da disciplina
    private static String gerarChave(Aluno aluno, Turma turma) {
        return aluno.getMatricula() + "-" + turma.getDisciplina().getCodigo();
    }

    //Variáveis com os dados do desempenho de um aluno
    public GerenciadorPerfomanceAluno(Aluno aluno, Avaliacao avaliacao, Frequencia frequencia, Turma turma) {
        this.aluno = aluno;
        this.avaliacao = avaliacao;
        this.frequencia = frequencia;
        this.mediaFinal = avaliacao.calculaMedia(turma.getTipoAvaliacao());
        this.aprovado = this.mediaFinal >= 5.0 && frequencia.aprovacaoFrequencia();
    }

    public static void lancarNotas(Aluno aluno, Turma turma, double p1, double p2, double p3, double listas, double seminario) {
        Avaliacao avaliacao = new Avaliacao(p1, p2, p3, listas, seminario);
        String chave = gerarChave(aluno, turma);
        avaliacoes.put(chave, avaliacao); //Armazena as notas com a chave gerada
    }

    public static void lancarFrequencia(Aluno aluno, Turma turma, double frequencia) {
        String chave = gerarChave(aluno, turma);
        frequencias.put(chave, frequencia); //Armazena frequência
    }

    public static double calcularMediaFinal(Aluno aluno, Turma turma) {
        String chave = gerarChave(aluno, turma);
        Avaliacao a = avaliacoes.get(chave);
        if (a != null) {
            return a.calculaMedia(turma.getTipoAvaliacao());
        }
        return 0.0;
    }
    public static double obterFrequencia(Aluno aluno, Turma turma) {
        String chave = gerarChave(aluno, turma);
        return frequencias.getOrDefault(chave, 0.0);
    }

    public static String informarSituacao(Aluno aluno, Turma turma) {
        double media = calcularMediaFinal(aluno, turma);
        double freq = obterFrequencia(aluno, turma);

        if (media >= 5.0 && freq >= 75.0) return "Aprovado";
        else if (freq < 75.0) return "Reprovado por Frequência";
        else return "Reprovado por Nota";
    }

    // Salvamento de arquivos
    public static void salvarAvaliacoesTXT(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (String chave : avaliacoes.keySet()) {
                Avaliacao a = avaliacoes.get(chave);
                double freq = frequencias.getOrDefault(chave, 0.0);
                writer.printf("%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f%n", chave, a.getP1(), a.getP2(), a.getP3(), a.getListas(), a.getSeminario(), freq);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar avaliações: " + e.getMessage());
        }
    }

    public static void carregarAvaliacoesTXT(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 8) {
                    String chave = partes[0];
                    double p1 = Double.parseDouble(partes[1]);
                    double p2 = Double.parseDouble(partes[2]);
                    double p3 = Double.parseDouble(partes[3]);
                    double listas = Double.parseDouble(partes[4]);
                    double seminario = Double.parseDouble(partes[5]);
                    double freq = Double.parseDouble(partes[6]);

                    Avaliacao a = new Avaliacao(p1, p2, p3, listas, seminario);
                    avaliacoes.put(chave, a);
                    frequencias.put(chave, freq);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar avaliações: " + e.getMessage());
        }
    }
}
