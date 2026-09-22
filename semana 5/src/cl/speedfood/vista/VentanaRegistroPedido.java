package cl.speedfood.vista;

import cl.speedfood.gestores.ControladorDeEnvios;
import cl.speedfood.modelo.Pedido;
import cl.speedfood.modelo.PedidoComida;
import cl.speedfood.modelo.PedidoEncomienda;
import cl.speedfood.modelo.PedidoExpress;
import cl.speedfood.interfaces.Despachable;
import cl.speedfood.interfaces.Cancelable;
import cl.speedfood.interfaces.Rastreable;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario para registrar un nuevo pedido en el sistema SpeedFast.
 * Según el tipo elegido (comida, encomienda o express) se muestran los
 * campos propios de esa subclase de {@link Pedido}, ya que cada una exige
 * datos distintos en su constructor.
 *
 * Es la Vista dentro de MVC: solo recolecta los datos del formulario;
 * quien construye el objeto Pedido y lo valida es este mismo formulario
 * antes de delegarlo al {@link ControladorDeEnvios}, que es el punto único
 * de acceso a la lista de pedidos en memoria.
 *
 * @author Consuelo
 * @version 1.0
 */
public class VentanaRegistroPedido extends JFrame {

    private static final String TIPO_COMIDA = "Comida";
    private static final String TIPO_ENCOMIENDA = "Encomienda";
    private static final String TIPO_EXPRESS = "Express";

    /** Controlador compartido donde se registra el pedido una vez validado. */
    private final ControladorDeEnvios controladorDeEnvios;

    /** Campo para el identificador único del pedido (común a los tres tipos). */
    private final JTextField campoIdPedido = new JTextField();
    /** Campo para la dirección de entrega (común a los tres tipos). */
    private final JTextField campoDireccionEntrega = new JTextField();
    /** Distancia en kilómetros hasta el lugar de entrega (común a los tres tipos). */
    private final JSpinner spinnerDistanciaKm = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 200.0, 0.5));
    /** Selector del tipo de pedido; su selección decide qué tarjeta de campos específicos se muestra. */
    private final JComboBox<String> comboTipoPedido = new JComboBox<>(new String[]{TIPO_COMIDA, TIPO_ENCOMIENDA, TIPO_EXPRESS});

    /** Nombre del restaurante que envía el pedido (solo para {@link PedidoComida}). */
    private final JTextField campoRestaurante = new JTextField();
    /** Indica si el pedido necesita mochila térmica para su transporte (solo para {@link PedidoComida}). */
    private final JCheckBox checkMochilaTermica = new JCheckBox("Requiere mochila térmica");

    /** Peso del pedido en kilogramos (solo para {@link PedidoEncomienda}). */
    private final JSpinner spinnerPesoPedido = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 500.0, 0.5));
    /** Indica si el embalaje de la encomienda fue validado (solo para {@link PedidoEncomienda}). */
    private final JCheckBox checkEmbalajeValidado = new JCheckBox("Embalaje validado");

    /** Indica si hay un repartidor disponible de inmediato (solo para {@link PedidoExpress}). */
    private final JCheckBox checkRepartidorDisponible = new JCheckBox("Repartidor disponible");

    /** CardLayout que permite alternar el panel de campos según el tipo de pedido elegido. */
    private final CardLayout layoutCamposEspecificos = new CardLayout();
    /** Panel contenedor que muestra, con CardLayout, solo los campos del tipo de pedido activo. */
    private final JPanel panelCamposEspecificos = new JPanel(layoutCamposEspecificos);

    /**
     * Construye la ventana de registro y arma sus tres secciones: datos
     * comunes a todo pedido, campos específicos por tipo (mostrados/ocultados
     * con {@link CardLayout} según lo elegido en {@code comboTipoPedido}), y
     * los botones de acción.
     *
     * @param controladorDeEnvios controlador compartido donde se registrará el
     *                            pedido una vez validado; es el mismo que usan
     *                            {@link VentanaPrincipal} y {@link VentanaListaPedidos}.
     */
    public VentanaRegistroPedido(ControladorDeEnvios controladorDeEnvios) {
        this.controladorDeEnvios = controladorDeEnvios;

        setTitle("Registrar nuevo pedido");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(420, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        add(construirPanelDatosComunes(), BorderLayout.NORTH);
        add(construirPanelCamposEspecificos(), BorderLayout.CENTER);
        add(construirPanelBotones(), BorderLayout.SOUTH);

        comboTipoPedido.addActionListener(e ->
                layoutCamposEspecificos.show(panelCamposEspecificos, (String) comboTipoPedido.getSelectedItem()));
    }

    /**
     * Arma el panel superior con los campos comunes a cualquier tipo de
     * pedido: ID, dirección de entrega, distancia en kilómetros y el
     * {@code JComboBox} de tipo (que es el que decide qué tarjeta de
     * {@link #panelCamposEspecificos} se muestra).
     *
     * @return el panel ya armado, listo para agregarse a la ventana.
     */
    private JPanel construirPanelDatosComunes() {
        JPanel panelDatosComunes = new JPanel(new GridLayout(4, 2, 8, 8));
        panelDatosComunes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelDatosComunes.add(new JLabel("ID pedido:"));
        panelDatosComunes.add(campoIdPedido);
        panelDatosComunes.add(new JLabel("Dirección de entrega:"));
        panelDatosComunes.add(campoDireccionEntrega);
        panelDatosComunes.add(new JLabel("Distancia (km):"));
        panelDatosComunes.add(spinnerDistanciaKm);
        panelDatosComunes.add(new JLabel("Tipo de pedido:"));
        panelDatosComunes.add(comboTipoPedido);

        return panelDatosComunes;
    }

    /**
     * Arma las tres "tarjetas" de campos específicos por tipo de pedido
     * (comida, encomienda, express) y las registra en
     * {@link #panelCamposEspecificos}, que usa {@link CardLayout} para
     * mostrar solo la tarjeta correspondiente al tipo seleccionado.
     *
     * @return el panel contenedor con las tres tarjetas ya agregadas.
     */
    private JPanel construirPanelCamposEspecificos() {
        JPanel panelComida = new JPanel(new GridLayout(2, 2, 8, 8));
        panelComida.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panelComida.add(new JLabel("Restaurante:"));
        panelComida.add(campoRestaurante);
        panelComida.add(new JLabel(""));
        panelComida.add(checkMochilaTermica);

        JPanel panelEncomienda = new JPanel(new GridLayout(2, 2, 8, 8));
        panelEncomienda.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panelEncomienda.add(new JLabel("Peso del pedido (kg):"));
        panelEncomienda.add(spinnerPesoPedido);
        panelEncomienda.add(new JLabel(""));
        panelEncomienda.add(checkEmbalajeValidado);

        JPanel panelExpress = new JPanel(new GridLayout(1, 2, 8, 8));
        panelExpress.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panelExpress.add(new JLabel(""));
        panelExpress.add(checkRepartidorDisponible);

        panelCamposEspecificos.add(panelComida, TIPO_COMIDA);
        panelCamposEspecificos.add(panelEncomienda, TIPO_ENCOMIENDA);
        panelCamposEspecificos.add(panelExpress, TIPO_EXPRESS);

        return panelCamposEspecificos;
    }

    /**
     * Arma el panel inferior con los botones Guardar y Cancelar, y conecta
     * cada uno con su acción: {@link #guardarPedido()} o cerrar la ventana.
     *
     * @return el panel de botones ya armado.
     */
    private JPanel construirPanelBotones() {
        JPanel panelBotones = new JPanel();
        JButton botonGuardar = new JButton("Guardar");
        JButton botonCancelar = new JButton("Cancelar");
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        botonGuardar.addActionListener(e -> guardarPedido());
        botonCancelar.addActionListener(e -> dispose());

        return panelBotones;
    }

    /**
     * Valida los datos comunes, construye el {@link Pedido} concreto según el
     * tipo elegido y lo registra en el {@link ControladorDeEnvios}. Cualquier
     * dato inválido (nombre vacío, tipo mal formado, etc.) es capturado y
     * mostrado en un {@link JOptionPane}, sin cerrar el formulario.
     */
    private void guardarPedido() {
        try {
            String idPedido = campoIdPedido.getText().trim();
            String direccionEntrega = campoDireccionEntrega.getText().trim();
            double distanciaKm = (double) spinnerDistanciaKm.getValue();
            String tipoPedido = (String) comboTipoPedido.getSelectedItem();

            Pedido pedidoNuevo = switch (tipoPedido) {
                case TIPO_COMIDA -> new PedidoComida(idPedido, direccionEntrega, distanciaKm,
                        campoRestaurante.getText().trim(), checkMochilaTermica.isSelected());
                case TIPO_ENCOMIENDA -> new PedidoEncomienda(idPedido, direccionEntrega, distanciaKm,
                        (double) spinnerPesoPedido.getValue(), checkEmbalajeValidado.isSelected());
                default -> new PedidoExpress(idPedido, direccionEntrega, distanciaKm,
                        checkRepartidorDisponible.isSelected());
            };

            controladorDeEnvios.registrarPedido((Pedido & Despachable & Cancelable & Rastreable) pedidoNuevo);

            JOptionPane.showMessageDialog(this,
                    "Pedido #" + idPedido + " registrado correctamente.",
                    "Pedido guardado", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } catch (IllegalArgumentException excepcionValidacion) {
            JOptionPane.showMessageDialog(this, excepcionValidacion.getMessage(),
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Deja el formulario en su estado inicial después de guardar un pedido:
     * vacía los campos de texto, restablece spinners y checkboxes a sus
     * valores por defecto, y devuelve el foco al campo ID para agilizar el
     * ingreso del siguiente pedido.
     */
    private void limpiarFormulario() {
        campoIdPedido.setText("");
        campoDireccionEntrega.setText("");
        spinnerDistanciaKm.setValue(1.0);
        campoRestaurante.setText("");
        checkMochilaTermica.setSelected(false);
        spinnerPesoPedido.setValue(1.0);
        checkEmbalajeValidado.setSelected(false);
        checkRepartidorDisponible.setSelected(false);
        campoIdPedido.requestFocus();
    }
}