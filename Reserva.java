public class Reserva {
    private Evento evento;
    private Cancha cancha;   
    private String estado;    

    public Reserva(Evento evento, Cancha cancha, String estado) {
        this.evento = evento;
        this.cancha = cancha;
        this.estado = estado;
    }

    public Evento getEvento() { return evento; }
    public Cancha getCancha() { return cancha; }
    public String getEstado() { return estado; }

    public void setCancha(Cancha cancha) { this.cancha = cancha; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        String canchaStr = (cancha == null) ? "Sin cancha asignada" : cancha.toString();
        return "[" + estado + "] " + evento.getNombreEvento()
                + " | Responsable: " + evento.getResponsable()
                + " | Fecha: " + evento.getFecha()
                + " | " + evento.getHoraInicio() + ":00-" + evento.getHoraFin() + ":00"
                + " | Jugadores: " + evento.getNumeroJugadores()
                + " | " + canchaStr;
    }
}
