package cl.speedfood;


/**
 * Clase que representa el pedido de comida con los atributos heredados de la clase base pedido y ademas agregando los
 * suyos como lo son restaurante y la validacion si necesita mochila termica para el transporte.
 *
 * @author Consuelo
 * @version 1.0
 *
 * */
public class PedidoComida extends Pedido{

    private String restaurante;
    private boolean requiereMochilaTermica;


    /**
     * Constructor con parámetros.
     * Construye el pedido de comida con los parametros heredados de pedido y agregando los propios, restaurante y
     * requiere mochila termica.
     * @param idPedido          identificador único del pedido.
     * @param direccionEntrega  dirección donde debe entregarse el pedido.
     * @param restaurante       el nombre del restaurante que envia el pedido.
     * @param requiereMochilaTermica indica si el pedido necesita ser transportado en mochila termica.
     *
     * */

    public PedidoComida(String idPedido, String direccionEntrega, String restaurante, boolean requiereMochilaTermica) {
        super(idPedido, direccionEntrega, "Comida");
        this.restaurante = restaurante;
        this.requiereMochilaTermica = requiereMochilaTermica;
    }

    /**
     * Obtiene el restaurante de donde se envia el pedido.
     * @return restaurante de donde se envia el pedido
     * */
    public String getRestaurante() {
        return restaurante;
    }

    /**
     * Obtiene la indicacion si el pedido necesita mochila termica para su transporte o no.
     * @return si necesita mochila termica o no
     *
     * */
    public boolean isRequiereMochilaTermica() {
        return requiereMochilaTermica;
    }

    /**
     * Asigna un repartidor y ademas valida si se necesita mochila termica para el trasnporte del pedido.
     * */
    @Override
    public void asignarRepartidor(){
        System.out.println("[Pedido Comida]");
        System.out.println("Asignando repartidor...");
        if(requiereMochilaTermica){
            System.out.println("→ Verificando mochila térmica... OK");
        } else {
            System.out.println("→ No se requiere mochila térmica");
        }
    }

    /**
     * Asigna un repartidor específico al pedido de comida, una vez que la validación
     * de mochila térmica ya fue realizada.
     * @param nombreRepartidor entrega el nombre del repartidor responsable de la entrega
     *
     * */
    public void asignarRepartidor(String nombreRepartidor){
        System.out.println("→ Pedido asignado a " + nombreRepartidor);
    }
}
