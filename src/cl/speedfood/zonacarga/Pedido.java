package cl.speedfood.zonacarga;

/**
 * Representa un pedido dentro del sistema de zona de carga de SpeedFast.
 * A diferencia del Pedido de semanas anteriores, este modela el estado
 * del pedido (PENDIENTE, EN_REPARTO, ENTREGADO) en vez del tipo de pedido.
 */
public class Pedido {

    private int id;
    private String direccionEntrega;
    private EstadoPedido estado;

    /**
     * Constructor con parámetros.
     * Crea un pedido con su identificador y dirección de entrega. El pedido
     * nace automáticamente en estado PENDIENTE, ya que un pedido recién
     * creado aún no ha sido tomado por ningún repartidor.
     *
     * @param id               identificador único del pedido.
     * @param direccionEntrega dirección donde debe entregarse el pedido.
     */
    public Pedido(int id, String direccionEntrega) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    /**
     * Actualiza el identificador del pedido.
     * @param id nuevo identificador del pedido.
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /**
     * Actualiza la dirección de entrega del pedido.
     * @param direccionEntrega nueva dirección de entrega.
     */
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    /**
     * Actualiza el estado del pedido a partir de un texto, convirtiéndolo
     * al valor correspondiente de EstadoPedido.
     *
     * @param nuevoEstado nombre del estado (debe coincidir con un valor
     *                    de EstadoPedido: "PENDIENTE", "EN_REPARTO" o "ENTREGADO")
     * @throws IllegalArgumentException si el texto no corresponde a ningún
     *                                  valor válido de EstadoPedido
     */
    public void setEstado(String nuevoEstado) {
        this.estado = EstadoPedido.valueOf(nuevoEstado);
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " [" + direccionEntrega + ", Estado: " + estado + "]";
    }
}