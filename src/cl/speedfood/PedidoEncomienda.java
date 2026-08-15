package cl.speedfood;

/**
 * Clase que representa el pedido de encomienda con los atributos heredados de la clase base pedido y ademas agregando los
 * suyos como lo son peso del pedido y la validación si el embalaje esta correcto.
 *
 * @author Consuelo
 * @version 1.0
 *
 * */
public class PedidoEncomienda extends Pedido{


    private double pesoPedido;
    private boolean embalajeValidado;

    /**
     * Constructor con parámetros.
     * Construye el pedido de embalaje con los parametros heredados de pedido y agregando los propios, peso del pedido
     * y si el pedido fue embalado correctamente.
     * @param idPedido          identificador único del pedido.
     * @param direccionEntrega  dirección donde debe entregarse el pedido.
     * @param pesoPedido        el peso del pedido.
     * @param embalajeValidado indica si el embalaje de la encomienda cumple con las condiciones necesarias para su
     *                         transporte.
     * */

    public PedidoEncomienda(String idPedido, String direccionEntrega, double pesoPedido, boolean embalajeValidado) {
        super(idPedido, direccionEntrega, "Encomienda");
        this.pesoPedido = pesoPedido;
        this.embalajeValidado = embalajeValidado;
    }

    /**
     * Obtiene el peso del pedido a enviar.
     * @return peso del pedido.
     * */
    public double getPesoPedido() {
        return pesoPedido;
    }

    /**
     * Obtiene la indicacion si el pedido cuenta con el embalaje adecuado para el envio.
     * @return si el embalaje cumple con las condiciones para que el pedido sea enviado.
     *
     * */
    public boolean isEmbalajeValidado() {
        return embalajeValidado;
    }

    /**
     * Asigna un repartidor y ademas valida si el embalaje a sido realizado correctamente.
     * */
    @Override
    public void asignarRepartidor(){
        System.out.println("[Pedido Encomienda]");
        System.out.println("Asignando repartidor...");
        if(embalajeValidado){
            System.out.println("→ Validando peso y embalaje... OK");
        } else {
            System.out.println("→ Embalaje no validado, no se puede asignar repartidor");
        }
    }

    /**
     * Asigna un repartidor específico al pedido de encomienda y valida el ambalado del paquete para que esto suceda.
     * @param nombreRepartidor entrega el nombre del repartidor responsable de la entrega
     *
     * */
    public void asignarRepartidor(String nombreRepartidor){
        if(embalajeValidado){
            System.out.println("→ Pedido asignado a " + nombreRepartidor);
        } else {
            System.out.println("→ No se puede asignar a " + nombreRepartidor + ", el embalaje no está validado");
        }
    }


}
