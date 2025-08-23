public class Cancha {
    private int numeroDeCancha;
    private String tipoDeCancha;
    private int capacidadMaxima;
    private double costoPorHora;

    public cancha(int numero, String tipo, int capacidadMaxima, double costoPorHora, int numeroDeCancha, String tipoDeCancha) {
        this.numeroDeCancha = numeroDeCancha;
        this.tipoDeCancha = tipoDeCancha;
        this.capacidadMaxima = capacidadMaxima;
        this.costoPorHora = costoPorHora;
    }

    public int getNumero() { return numeroDeCancha; }
    public String getTipo() { return tipoDeCancha; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public double getCostoPorHora() { return costoPorHora; }
    public void setCostoPorHora(double costoPorHora) { this.costoPorHora = costoPorHora; }

    @Override
    public String toString() {
        return "Cancha #" + numeroDeCancha + " (" + tipoDeCancha + "), Capacidad: " + capacidadMaxima +
                ", Costo/hora: Q" + costoPorHora;
    }
}