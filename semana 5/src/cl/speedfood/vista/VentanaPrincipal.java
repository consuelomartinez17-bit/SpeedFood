package cl.speedfood.vista;

import cl.speedfood.gestores.ControladorDeEnvios;
import cl.speedfood.modelo.Pedido;
import cl.speedfood.modelo.Repartidor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana principal del sistema SpeedFast. Desde aquí se navega hacia el
 * registro de pedidos, el listado de pedidos, y se asignan repartidores
 * para iniciar la simulación de entregas.
 *
 * Mantiene una única instancia de {@link ControladorDeEnvios}, que es la
 * que comparte con {@link VentanaRegistroPedido} y {@link VentanaListaPedidos}
 * para que ambas trabajen siempre sobre la misma lista de pedidos en memoria.
 *
 * @author Consuelo
 * @version 1.0
 */
public class VentanaPrincipal extends JFrame {

    /**
     * Única instancia del controlador para toda la sesión de la aplicación;
     * se comparte con cada {@link VentanaRegistroPedido} y
     * {@link VentanaListaPedidos} que se abra desde aquí.
     */
    private final ControladorDeEnvios controladorDeEnvios = new ControladorDeEnvios();

    /**
     * Construye la ventana principal con sus tres botones de navegación y
     * conecta cada uno con la acción correspondiente: abrir el formulario de
     * registro, abrir el listado, o abrir el diálogo de asignación de
     * repartidor.
     */
    public VentanaPrincipal() {
        setTitle("SpeedFast - Panel principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(360, 240);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton botonRegistrarPedido = new JButton("Registrar pedido");
        JButton botonListarPedidos = new JButton("Listar pedidos");
        JButton botonAsignarRepartidor = new JButton("Asignar repartidor / Iniciar entrega");

        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(botonRegistrarPedido);
        add(botonListarPedidos);
        add(botonAsignarRepartidor);

        botonRegistrarPedido.addActionListener(e ->
                new VentanaRegistroPedido(controladorDeEnvios).setVisible(true));

        botonListarPedidos.addActionListener(e ->
                new VentanaListaPedidos(controladorDeEnvios).setVisible(true));

        botonAsignarRepartidor.addActionListener(e -> abrirDialogoAsignarRepartidor());
    }

    /**
     * Abre un diálogo donde se ingresa el nombre del repartidor y se eligen,
     * de entre todos los pedidos registrados, los que se le asignarán.
     * Al confirmar, arma un {@link Repartidor} con esos pedidos y lo ejecuta
     * en un hilo aparte, para simular el inicio real de las entregas sin
     * congelar la interfaz gráfica mientras "viaja" cada pedido.
     */
    private void abrirDialogoAsignarRepartidor() {
        List<Pedido> pedidosDisponibles = controladorDeEnvios.getPedidosRegistrados();
        if (pedidosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pedidos registrados todavía.");
            return;
        }

        JDialog dialogoAsignacion = new JDialog(this, "Asignar repartidor", true);
        dialogoAsignacion.setSize(400, 350);
        dialogoAsignacion.setLocationRelativeTo(this);
        dialogoAsignacion.setLayout(new BorderLayout(10, 10));

        JPanel panelNombre = new JPanel(new BorderLayout(8, 8));
        panelNombre.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        JTextField campoNombreRepartidor = new JTextField();
        panelNombre.add(new JLabel("Nombre del repartidor:"), BorderLayout.WEST);
        panelNombre.add(campoNombreRepartidor, BorderLayout.CENTER);

        DefaultListModel<Pedido> modeloListaPedidos = new DefaultListModel<>();
        for (Pedido pedidoActual : pedidosDisponibles) {
            modeloListaPedidos.addElement(pedidoActual);
        }
        JList<Pedido> listaPedidos = new JList<>(modeloListaPedidos);
        listaPedidos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaPedidos.setCellRenderer((lista, pedido, indice, seleccionado, foco) ->
                new JLabel(pedido.getClass().getSimpleName() + " #" + pedido.getIdPedido()
                        + (pedido.isCancelado() ? " (cancelado)" : "")));

        dialogoAsignacion.add(panelNombre, BorderLayout.NORTH);
        dialogoAsignacion.add(new JScrollPane(listaPedidos), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton botonIniciarEntrega = new JButton("Iniciar entrega");
        JButton botonCerrar = new JButton("Cerrar");
        panelBotones.add(botonIniciarEntrega);
        panelBotones.add(botonCerrar);
        dialogoAsignacion.add(panelBotones, BorderLayout.SOUTH);

        botonCerrar.addActionListener(e -> dialogoAsignacion.dispose());
        botonIniciarEntrega.addActionListener(e -> {
            String nombreRepartidor = campoNombreRepartidor.getText().trim();
            List<Pedido> pedidosSeleccionados = new ArrayList<>(listaPedidos.getSelectedValuesList());

            if (nombreRepartidor.isBlank()) {
                JOptionPane.showMessageDialog(dialogoAsignacion, "Ingresa el nombre del repartidor.",
                        "Datos inválidos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (pedidosSeleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(dialogoAsignacion, "Selecciona al menos un pedido.",
                        "Datos inválidos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Repartidor repartidorAsignado = new Repartidor(nombreRepartidor, pedidosSeleccionados);
            new Thread(repartidorAsignado).start();

            JOptionPane.showMessageDialog(dialogoAsignacion,
                    "Entrega iniciada con " + nombreRepartidor + " para " + pedidosSeleccionados.size() + " pedido(s).\n"
                            + "Revisa la consola para ver el avance.",
                    "Entrega en curso", JOptionPane.INFORMATION_MESSAGE);
            dialogoAsignacion.dispose();
        });

        dialogoAsignacion.setVisible(true);
    }
}