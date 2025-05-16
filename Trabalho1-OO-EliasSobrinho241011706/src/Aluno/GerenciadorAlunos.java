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
    public static Aluno buscaPorMatricula(String matricula) {
        for (Aluno a : listaAlunos) {
            if (a.getMatricula().equals(matricula)) {
                return a;
            }
        }
        return null; //Caso de não corresponder
    }

}
