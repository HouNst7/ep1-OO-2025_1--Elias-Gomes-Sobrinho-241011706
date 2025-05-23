package Avaliacao_Frequencia;

public class Frequencia {
    private int totalAulas;
    private int presencas;

    public Frequencia(int totalAulas, int presencas) {
        this.totalAulas = totalAulas;
        this.presencas = presencas;
    }

    public double getPorcentagemPresenca(){
        if (totalAulas == 0) return 0.0;
        return (double) presencas / totalAulas;
    }

    public boolean aprovacaoFrequencia(){
        return getPorcentagemPresenca() >= 0.75;
    }

    //Getters e Setters
    public int getTotalAulas() { return totalAulas; }
    public int getPresencas() { return presencas; }

    public void setTotalAulas(int totalAulas) { this.totalAulas = totalAulas; }
    public void setPresencas(int presencas) { this.presencas = presencas; }
}
