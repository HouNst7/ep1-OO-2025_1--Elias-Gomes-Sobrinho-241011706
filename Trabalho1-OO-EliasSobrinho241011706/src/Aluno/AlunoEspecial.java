package Aluno;

//Aqui está um caso de Herança sendo aplicado de maneira eficiente, conforme foi pedido lá no ReadMe do projeto
public class AlunoEspecial extends Aluno {

    public AlunoEspecial(String nome, String matricula, String curso) {
        super(nome, matricula, curso);
    }

    @Override
    public void matricular(String codigoDisciplina) {
        if (disciplinasMatriculadas.size() >= 2) {
            System.out.println("Um Aluno especial pode cursar no máximo 2 disciplinas.");
            return;//Para o metodo imediatamente
        }
        super.matricular(codigoDisciplina); // Chama o metodo matricular original
    }

}
