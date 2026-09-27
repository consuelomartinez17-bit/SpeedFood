package cl.speedfood.vista;

import cl.speedfood.dao.Pedido;
import cl.speedfood.dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

/**
 * Muestra en una tabla todos los pedidos almacenados en la base de datos
 * (tabla 'pedido'), sin importar su tipo (comida, encomienda o express).
 *
 * A diferencia de la versión anterior (que leía del {@code ControladorDeEnvios}
 * en memoria), esta ventana consulta directamente la base de datos mediante
 * {@link PedidoDAO#listarTodos()}, por lo que muestra los pedidos realmente
 * persistidos, no solo los de la sesión actual.
 *
 * @author Consuelo
 * @version 2.0
 */
public class VentanaListaPedidos extends JFrame {

    /** Encabezados de la tabla, en el mismo orden en que se cargan las filas en {@link #cargarPedidosEnTabla()}. */
    private static final String[] COLUMNAS = {"ID", "Dirección", "Tipo", "Estado"};

    /** Acceso a datos para consultar los pedidos guardados en la base de datos. */
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    /** Modelo de la tabla; se repuebla por completo cada vez que se refresca. */
    private final DefaultTableModel modeloTabla;

    /**
     * Construye la ventana de listado: arma la tabla (no editable directamente),
     * agrega el botón "Refrescar" y hace la primera carga de datos desde la
     * base de datos.
     */
    public VentanaListaPedidos() {
        setTitle("Pedidos registrados (base de datos)");
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
     * Limpia la tabla ({@code setRowCount(0)}) y la vuelve a poblar
     * consultando todos los pedidos actuales desde la base de datos.
     * Se llama al crear la ventana y cada vez que el usuario presiona
     * "Refrescar". Si ocurre un error de conexión o de consulta, se
     * muestra un mensaje sin cerrar la ventana.
     */
    private void cargarPedidosEnTabla() {
        modeloTabla.setRowCount(0);
        try {
            for (Pedido pedidoActual : pedidoDAO.listarTodos()) {
                modeloTabla.addRow(new Object[]{
                        pedidoActual.getId(),
                        pedidoActual.getDireccion(),
                        pedidoActual.getTipo(),
                        pedidoActual.getEstado()
                });
            }
        } catch (SQLException excepcionBaseDeDatos) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo consultar la base de datos: " + excepcionBaseDeDatos.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}