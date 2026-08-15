package cl.speedfood;

/**
 * Clase base que representa un pedido genérico dentro del sistema de reparto SpeedFood.
 * Define los atributos y comportamientos comunes a todos los tipos de pedido.
 *
 * @author Consuelo
 * @version 1.0
 *
 * */
public class Pedido {

    private String idPedido;
    private String direccionEntrega;
    private String tipoPedido;

    /**
     * Constructor con parámetros.
     * Construye el pedido con parámetros específicos y comunes a todos los pedidos a repartir.
     * @param idPedido          identificador único del pedido.
     * @param direccionEntrega  dirección donde debe entregarse el pedido.
     * @param tipoPedido        tipo de pedido (comida, encomienda o express).
     *
     * */

    public Pedido(String idPedido, String direccionEntrega, String tipoPedido) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
    }

    /**
     * Obtiene el id del pedido.
     * @return el id del Pedido
     * */
    public String getIdPedido() {
        return idPedido;
    }

    /**
     * Obtiene la dirección de entrega del pedido.
     * @return la direccion de entrega del pedido
     * */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /**
     * Obtiene el tipo de pedido.
     * @return el tipo de pedido
     * */
    public String getTipoPedido() {
        return tipoPedido;
    }

    /**
     * Asigna un repartidor genérico al pedido, buscando el más cercano disponible.
     * Corresponde al comportamiento por defecto de la clase base; las subclases
     * sobrescriben este método para aplicar su propia lógica de asignación.
     *
     * */
    public void asignarRepartidor(){
        System.out.println("Buscando repartidor disponible...");
    }
}
