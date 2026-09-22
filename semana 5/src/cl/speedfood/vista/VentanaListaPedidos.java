package cl.speedfood.vista;

import cl.speedfood.gestores.ControladorDeEnvios;
import cl.speedfood.modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Muestra en una tabla todos los pedidos registrados hasta el momento en el
 * {@link ControladorDeEnvios}, sin importar su tipo concreto (comida,
 * encomienda o express).
 *
 * La tabla no se refresca sola cuando se registra un pedido nuevo desde otra
 * ventana (cada JFrame es independiente); por eso incluye un botón
 * "Refrescar" que vuelve a leer la lista completa desde el controlador.
 *
 * @author Consuelo
 * @version 1.0
 */
public class VentanaListaPedidos extends JFrame {

    /** Encabezados de la tabla, en el mismo orden en que se cargan las filas en {@link #cargarPedidosEnTabla()}. */
    private static final String[] COLUMNAS = {"ID", "Tipo", "Dirección", "Distancia (km)", "Tiempo estimado (min)", "Cancelado"};

    /** Controlador compartido desde donde se leen todos los pedidos registrados. */
    private final ControladorDeEnvios controladorDeEnvios;
    /** Modelo de la tabla; se repuebla por completo cada vez que se refresca. */
    private final DefaultTableModel modeloTabla;

    /**
     * Construye la ventana de listado: arma la tabla (no editable directamente,
     * ya que la edición se hace desde {@link VentanaRegistroPedido}), agrega el
     * botón "Refrescar" y hace la primera carga de datos.
     *
     * @param controladorDeEnvios controlador compartido con el resto de las
     *                            ventanas, de donde se obtienen los pedidos.
     */
    public VentanaListaPedidos(ControladorDeEnvios controladorDeEnvios) {
        this.controladorDeEnvios = controladorDeEnvios;

        setTitle("Pedidos registrados");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(640, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tablaPedidos = new JTable(modeloTabla);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton botonRefrescar = new JButton("Refrescar");
        botonRefrescar.addActionListener(e -> cargarPedidosEnTabla());
        panelBotones.add(botonRefrescar);
        add(panelBotones, BorderLayout.SOUTH);

        cargarPedidosEnTabla();
    }

    /**
     * Limpia la tabla ({@code setRowCount(0)}) y la vuelve a poblar con todos
     * los pedidos actuales del controlador. Se llama al crear la ventana y
     * cada vez que el usuario presiona "Refrescar".
     */
    private void cargarPedidosEnTabla() {
        modeloTabla.setRowCount(0);
        for (Pedido pedidoActual : controladorDeEnvios.getPedidosRegistrados()) {
            modeloTabla.addRow(new Object[]{
                    pedidoActual.getIdPedido(),
                    pedidoActual.getClass().getSimpleName(),
                    pedidoActual.getDireccionEntrega(),
                    pedidoActual.getDistanciaKm(),
                    pedidoActual.calcularTiempoEntrega(),
                    pedidoActual.isDespachado() ? "Sí" : "No",
                    pedidoActual.isCancelado() ? "Sí" : "No"
            });
        }
    }
}