package Avaliacao_Frequencia;

public class Avaliacao {
    private double p1;
    private double p2;
    private double p3;
    private double listas;
    private double seminario;

    public Avaliacao (double p1,double p2,double p3, double listas, double seminario){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.listas = listas;
        this.seminario = seminario;
    }

    //Calcular média dependendo do tipo da Avaliação da Turma
    public double calculaMedia(AvaliacaoTipo tipo){
        return tipo == AvaliacaoTipo.SIMPLES
                ?(p1 + p2 + p3 + listas + seminario) / 5
                :(p1 + p2 * 2 + p3 * 3 + listas + seminario) / 8;
    }

    //Getters e Setters
    public double getP1() { return p1; }
    public double getP2() { return p2; }
    public double getP3() { return p3; }
    public double getListas() { return listas; }
    public double getSeminario() { return seminario; }

    public void setP1(double p1) { this.p1 = p1; }
    public void setP2(double p2) { this.p2 = p2; }
    public void setP3(double p3) { this.p3 = p3; }
    public void setListas(double listas) { this.listas = listas; }
    public void setSeminario(double seminario) { this.seminario = seminario; }
}
