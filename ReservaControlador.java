import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservaControlador {
    private final SistemaDeReserva modelo;
    private final ReservaGUI gui;

    public ReservaControlador(SistemaDeReserva modelo, ReservaGUI gui) {
        this.modelo = modelo;
        this.gui = gui;
        initListeners();
    }

    private void initListeners() {
        gui.btnSolicitar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { solicitarReserva(); }
        });

        gui.btnCancelar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { cancelarReserva(); }
        });

        gui.btnVer.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { verReservasPorCancha(); }
        });
    }

    private void solicitarReserva() {
        try {
            String responsable = gui.txtResponsable.getText().trim();
            String nombre = gui.txtEvento.getText().trim();
            String tipo = gui.txtTipo.getText().trim();
            String fecha = gui.txtFecha.getText().trim();
            int inicio = Integer.parseInt(gui.txtHoraInicio.getText().trim());
            int fin = Integer.parseInt(gui.txtHoraFin.getText().trim());
            int jugadores = Integer.parseInt(gui.txtJugadores.getText().trim());
            boolean deposito = gui.chkDeposito.isSelected();

            if (responsable.isEmpty() || nombre.isEmpty() || tipo.isEmpty() || fecha.isEmpty()) {
                JOptionPane.showMessageDialog(gui, "Complete todos los campos obligatorios.");
                return;
            }

            Evento ev = new Evento(responsable, nombre, tipo, fecha, inicio, fin, jugadores, deposito);
            boolean asignada = modelo.pedirReserva(ev);

            JOptionPane.showMessageDialog(gui, modelo.getMensajeUltimaOperacion());

        
            if (asignada) limpiarCampos();

      
            verReservasPorCancha();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Horas y número de jugadores deben ser enteros.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, "Error: " + ex.getMessage());
        }
    }

    private void cancelarReserva() {
        String nombre = gui.txtEvento.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(gui, "Ingrese el nombre del evento a cancelar.");
            return;
        }
        modelo.cancelarReserva(nombre);
        JOptionPane.showMessageDialog(gui, modelo.getMensajeUltimaOperacion());
        verReservasPorCancha();
    }

    private void verReservasPorCancha() {
        gui.areaResultados.setText("");
        for (Cancha c : modelo.getCanchas()) {
            gui.areaResultados.append("=== " + c.toString() + " ===\n");
            boolean any = false;
            for (Reserva r : modelo.getReservasConfirmadas()) {
                if (r.getCancha() == c) {
                    gui.areaResultados.append(r.toString() + "\n");
                    any = true;
                }
            }
            if (!any) gui.areaResultados.append("No hay reservas en esta cancha.\n");
            gui.areaResultados.append("\n");
        }

        gui.areaResultados.append("=== Lista de espera ===\n");
        for (Evento e : modelo.getEventosEnEspera()) {
            gui.areaResultados.append("- " + e.getNombreEvento() + " | " + e.getFecha()
                    + " | " + e.getHoraInicio() + ":00-" + e.getHoraFin() + ":00"
                    + " | Tipo: " + e.getTipoEvento()
                    + " | Jugadores: " + e.getNumeroJugadores() + "\n");
        }
    }

    private void limpiarCampos() {
        gui.txtEvento.setText("");
        gui.txtTipo.setText("");
        gui.txtHoraInicio.setText("");
        gui.txtHoraFin.setText("");
        gui.txtJugadores.setText("");
        gui.chkDeposito.setSelected(false);
    }
}