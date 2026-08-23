package cl.speedfood;

/**
 * Clase que representa un pedido express, hereda de la clase base pedido los atributos de este y agrega el propio
 * como lo es si està disponible para hacer la entrega.
 *
 * @author Consuelo
 * @version 1.0
 * */

public class PedidoExpress extends Pedido {

    private boolean repartidorDisponible;


    /**
     * Constructor con parámetros.
     * Construye el pedido express con los parametros heredados de pedido y agregando los propios, distancia
     * en kilometros y si el repartidor esta disponible para realizar el reparto.
     * @param idPedido              identificador único del pedido.
     * @param direccionEntrega  dirección donde debe entregarse el pedido.
     * @param distanciaKm       distancia en kilometros que debe recorrerse hasta el lugar de entrega.
     * @param repartidorDisponible  indica si el repartidor esta disponible para hacerse cargo de ese pedido.
     * */

    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm, boolean repartidorDisponible) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.repartidorDisponible = repartidorDisponible;
    }


    /**
     * Obtiene la indicacion si el repartidor se encuentra disponible para recoger el pedido.
     * @return si el repartidor esta disponible para hacer el envio.
     *
     * */
    public boolean isRepartidorDisponible() {
        return repartidorDisponible;
    }

    /**
     * Asigna un repartidor si, este esta a menos de 5 kilometros y que ademas este disponible para hacer el reparto.
     * */
    @Override
    public void asignarRepartidor(){
        System.out.println("[Pedido Express]");
        System.out.println("Asignando repartidor...");
        if(repartidorDisponible && distanciaKm < 5){
            System.out.println("→ Repartidor más cercano con disponibilidad inmediata encontrado.");
        } else {
            System.out.println("→ No hay repartidor disponible en la zona por el momento.");
        }
    }

    /**
     * Asigna un repartidor específico al pedido express y valida que el repartidor se encuentre cerca para ser asignado.
     * @param nombreRepartidor entrega el nombre del repartidor responsable de la entrega
     *
     * */
    @Override
    public void asignarRepartidor(String nombreRepartidor){
        if(repartidorDisponible && distanciaKm < 5){
            System.out.println("→ Pedido asignado a " + nombreRepartidor);
        } else {
            System.out.println("→ No se puede asignar a " + nombreRepartidor + ", no hay repartidores cerca, intentelo mas tarde. ");
        }
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido de express.
     * Se consideran 10 minutos base, pero si esta a mas de 5 km, se agregaran 5 min extra.
     *
     * @return tiempo estimado de entrega en minutos.
     *
     * */
    @Override
    public int calcularTiempoEntrega(){
        int tiempo = 10;
        if (distanciaKm > 5) {
            tiempo = tiempo + 5;
        }
        return tiempo;
    }



}
