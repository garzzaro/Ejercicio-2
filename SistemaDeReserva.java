import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;


public class SistemaDeReserva {
    private ArrayList<Cancha> canchas;
    private ArrayList<Reserva> reservas;     
    private Queue<Evento> listaDeEspera;   
    private String mensajeUltimaOperacion; 

    public SistemaDeReserva() {
        canchas = new ArrayList<>();
        reservas = new ArrayList<>();
        listaDeEspera = new ArrayDeque<>();
        mensajeUltimaOperacion = "";
    }


    public void agregarCancha(Cancha cancha) {
        canchas.add(cancha);
        mensajeUltimaOperacion = "Cancha agregada: " + cancha.toString();
    }

    public ArrayList<Cancha> getCanchas() {
        return new ArrayList<>(canchas);
    }


   public boolean pedirReserva(Evento evento) {
    if (evento.getHoraInicio() < 8 || evento.getHoraFin() > 22 || evento.getHoraFin() <= evento.getHoraInicio()) {
        mensajeUltimaOperacion = "Horario inválido: debe ser entre 8:00 y 22:00 y hora fin > hora inicio.";
        return false;
    }
    if (!evento.isDepositoPagado()) {
        mensajeUltimaOperacion = "Depósito no pagado: se requiere 50% para reservar.";
        return false;
    }

    Cancha canchaAsignada = null;
    for (Cancha c : canchas) {
        if (c.getTipo().equalsIgnoreCase(evento.getTipoEvento())
            && evento.getNumeroJugadores() <= c.getCapacidadMaxima()
            && !hayTraslape(c, evento)) {
            canchaAsignada = c;
            break;
        }
    }

    if (canchaAsignada != null) {
        reservas.add(new Reserva(evento, canchaAsignada, "Confirmada"));
        mensajeUltimaOperacion = " Reserva confirmada en " + canchaAsignada.toString();
        return true;
    }

    listaDeEspera.add(evento);
    mensajeUltimaOperacion = " No hay cancha disponible para este tipo de evento o excede capacidad — agregado a lista de espera.";
    return false;
}



    
    private boolean hayTraslape(Cancha cancha, Evento nuevo) {
        for (Reserva r : reservas) {
            if (r.getCancha() == cancha && r.getEvento().getFecha().equals(nuevo.getFecha())) {
                int inicio = r.getEvento().getHoraInicio();
                int fin = r.getEvento().getHoraFin();
                if (!(nuevo.getHoraFin() <= inicio || nuevo.getHoraInicio() >= fin)) {
                    return true; 
                }
            }
        }
        return false;
    }

   
    public void cancelarReserva(String nombreEvento) {
        Reserva objetivo = null;
        for (Reserva r : reservas) {
            if (r.getEvento().getNombreEvento().equalsIgnoreCase(nombreEvento)) {
                objetivo = r;
                break;
            }
        }

        if (objetivo == null) {
            mensajeUltimaOperacion = "No se encontró reserva confirmada con nombre: " + nombreEvento;
            return;
        }

        
        reservas.remove(objetivo);
        objetivo.setEstado("Cancelada");

        StringBuilder sb = new StringBuilder();
        sb.append("Reserva cancelada: ").append(nombreEvento).append(". ");

        if (!listaDeEspera.isEmpty()) {
            boolean reasignado = false;
            int initialSize = listaDeEspera.size();
           
            for (int i = 0; i < initialSize; i++) {
                Evento enEspera = listaDeEspera.poll();
               
                for (Cancha c : canchas) {
                    if (enEspera.getNumeroJugadores() <= c.getCapacidadMaxima() && !hayTraslape(c, enEspera)) {
                        Reserva nueva = new Reserva(enEspera, c, "Confirmada");
                        reservas.add(nueva);
                        sb.append("Se reasignó desde lista de espera: ").append(enEspera.getNombreEvento())
                          .append(" en ").append(c.toString()).append(". ");
                        reasignado = true;
                        break;
                    }
                }
                if (!reasignado) {
                  
                    listaDeEspera.add(enEspera);
                } else {
                    
                    break;
                }
            }
            if (!reasignado) sb.append("No se pudo reasignar ningún evento de la lista de espera.");
        } else {
            sb.append("No hay eventos en lista de espera para reasignar.");
        }

        mensajeUltimaOperacion = sb.toString();
    }


    public ArrayList<Reserva> getReservasConfirmadas() {
        return new ArrayList<>(reservas);
    }

    
    public ArrayList<Evento> getEventosEnEspera() {
        return new ArrayList<>(listaDeEspera);
    }

    public ArrayList<Reserva> getReservasPorCancha(Cancha cancha) {
        ArrayList<Reserva> res = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getCancha() == cancha) res.add(r);
        }
        return res;
    }

    public String getMensajeUltimaOperacion() {
        return mensajeUltimaOperacion;
    }
}
