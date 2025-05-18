package Aluno;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorAlunos {
    private static List<Aluno> listaAlunos = new ArrayList<>();

    //Funcao para impedir 2 alunos com a mesma matricula
    public static void cadastrarAluno(Aluno aluno) {
        for (Aluno a : listaAlunos) {
            if (a.getMatricula().equals(aluno.getMatricula())) {
                System.out.println("Já existe um aluno com esta matrícula");
                return;
            }
        }
        listaAlunos.add(aluno);
    }

    //Funcao para listar os Alunos
    public static void listarAlunos() {
        for (Aluno a : listaAlunos) {
            if (a instanceof AlunoEspecial) {
                System.out.println(a.getNome() + " - " + a.getMatricula() + " (ALUNO ESPECIAL)");
            } else {
                System.out.println(a.getNome() + " - " + a.getMatricula());
            }
        }
    }


    //Funcao para permitir a pesquisa de um aluno através da matricula
    public static Aluno buscarPorMatricula(String matricula) {
        for (Aluno a : listaAlunos) {
            if (a.getMatricula().equals(matricula)) {
                return a;
            }
        }
        return null; //Caso de não corresponder
    }

    //Funcao para salvar os dados dos alunos
    public static void salvarAlunosTXT(String nomeArquivo) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(nomeArquivo)) {
            for (Aluno a : listaAlunos) {
                String tipo = (a instanceof AlunoEspecial) ? "Especial" : "";
                writer.println(tipo + ";" + a.getNome() + ";" + a.getMatricula() + ";" + a.getCurso());
            }
            System.out.println("Novos dados foram salvados no sistema.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao salvar os dados dos alunos: " + e.getMessage());
        }
    }

    public static void carregarAlunosTXT(String nomeArquivo) {
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";"); //Divide a linha em partes
                if (partes.length == 4) {
                    String tipo = partes[0];
                    String nome = partes[1];
                    String matricula = partes[2];
                    String curso = partes[3];

                    Aluno aluno;
                    if (tipo.equals("Especial")) {
                        aluno = new AlunoEspecial(nome, matricula, curso);
                    } else {
                        aluno = new Aluno(nome, matricula, curso);
                    }

                    listaAlunos.add(aluno);
                }
            }

        } catch (java.io.FileNotFoundException e) { //Exibe esta mensagem caso não haja arquivo
            System.out.println("Arquivo de alunos ainda não existe. Nenhum dado carregado.");
        } catch (Exception e) {
            System.out.println("Erro ao carregar alunos: " + e.getMessage()); //Exibe esta mensagem em caso de outros erros genéricos
        }
    }
}
