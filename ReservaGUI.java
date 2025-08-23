import java.awt.*;
import javax.swing.*;


public class ReservaGUI extends JPanel {

    JTextField txtResponsable = new JTextField(15);
    JTextField txtEvento = new JTextField(15);
    JTextField txtTipo = new JTextField(10);
    JTextField txtFecha = new JTextField(10);
    JTextField txtHoraInicio = new JTextField(5);
    JTextField txtHoraFin = new JTextField(5);
    JTextField txtJugadores = new JTextField(5);
    JCheckBox chkDeposito = new JCheckBox("Depósito (50%) pagado");


    JButton btnSolicitar = new JButton("Solicitar Reserva");
    JButton btnCancelar = new JButton("Cancelar Reserva");
    JButton btnVer = new JButton("Ver Reservas por Cancha");


    JTextArea areaResultados = new JTextArea(15, 50);

    public ReservaGUI() {
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(8, 2, 6, 6));
        form.add(new JLabel("Responsable:"));          form.add(txtResponsable);
        form.add(new JLabel("Nombre del evento:"));    form.add(txtEvento);
        form.add(new JLabel("Tipo de evento:"));       form.add(txtTipo);
        form.add(new JLabel("Fecha (dd/mm/aaaa):"));   form.add(txtFecha);
        form.add(new JLabel("Hora inicio (8-22):"));   form.add(txtHoraInicio);
        form.add(new JLabel("Hora fin (8-22):"));      form.add(txtHoraFin);
        form.add(new JLabel("Núm. jugadores:"));       form.add(txtJugadores);
        form.add(chkDeposito);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        botones.add(btnSolicitar);
        botones.add(btnCancelar);
        botones.add(btnVer);

        areaResultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultados);

        add(form, BorderLayout.NORTH);
        add(botones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaDeReserva modelo = new SistemaDeReserva();

            int contador = 0;
            while (contador < 4) {
                try {
                    int numero = Integer.parseInt(JOptionPane.showInputDialog("Número de cancha:"));
                    boolean existe = modelo.getClass().stream().anyMatch(c -> c.getNumero() == numero);
                    if (existe) { JOptionPane.showMessageDialog(null, "Número ya existe"); continue; }

                    String tipo = JOptionPane.showInputDialog("Tipo (Fútbol/Baloncesto/Tenis):");
                    int capacidad = Integer.parseInt(JOptionPane.showInputDialog("Capacidad máxima:"));
                    double costo = Double.parseDouble(JOptionPane.showInputDialog("Costo por hora:"));

                    modelo.agregarCancha(new Cancha(numero, tipo, capacidad, costo));
                    contador++;
                } catch (Exception e) { JOptionPane.showMessageDialog(null, "Datos inválidos"); }
            }

            JFrame ventana = new JFrame("Sistema de Reservas - GUI");
            ReservaGUI panel = new ReservaGUI();
            new ReservaControlador(modelo, panel);

            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(800, 600);
            ventana.setLocationRelativeTo(null);
            ventana.add(panel);
            ventana.setVisible(true);
        });
    }
}