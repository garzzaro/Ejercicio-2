public class Evento {
    private String responsable;
    private String nombreDelEvento;
    private String tipoDeEvento;
    private String fecha;      
    private int horaDeInicio;     
    private int horaDeFinalizacion;        
    private int numeroDeParticipantes;
    private boolean depositoPagado; 

    public Evento(String responsable, String nombreDeEvento, String tipoDeEvento,
                  String fecha, int horaDeInicio, int DeFinalizacion,
                  int numeroJugadores, boolean depositoPagado, int numeroDeParticipantes) {
        this.responsable = responsable;
        this.nombreDelEvento = nombreDeEvento;
        this.tipoDeEvento = tipoDeEvento;
        this.fecha = fecha;
        this.horaDeInicio = horaDeInicio;
        this.horaDeFinalizacion = DeFinalizacion;
        this.numeroDeParticipantes = numeroDeParticipantes;
        this.depositoPagado = depositoPagado;
    }

    public String getResponsable() { return responsable; }
    public String getNombreEvento() { return nombreDelEvento; }
    public String getTipoEvento() { return tipoDeEvento; }
    public String getFecha() { return fecha; }
    public int getHoraInicio() { return horaDeInicio; }
    public int getHoraFin() { return horaDeFinalizacion; }
    public int getNumeroJugadores() { return numeroDeParticipantes; }
    public boolean isDepositoPagado() { return depositoPagado; }
    public void setDepositoPagado(boolean depositoPagado) { this.depositoPagado = depositoPagado; }
}

