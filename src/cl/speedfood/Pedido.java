package cl.speedfood;

/**
 * Clase base que representa un pedido genérico dentro del sistema de reparto SpeedFood.
 * Define los atributos y comportamientos comunes a todos los tipos de pedido.
 *
 * @author Consuelo
 * @version 1.0
 *
 * */
public abstract class Pedido {

    protected String idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;


    /**
     * Constructor con parámetros.
     * Construye el pedido con parámetros específicos y comunes a todos los pedidos a repartir.
     * @param idPedido          identificador único del pedido.
     * @param direccionEntrega  dirección donde debe entregarse el pedido.
     * @param distanciaKm       distancia en kilometros que debe recorrerse hasta el lugar de entrega.
     *
     * */

    public Pedido(String idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
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
     * Obtiene la distancia en kilometros que debe recorrerse hasta el lugar de entrega.
     * @return la distancia en kilometros que debe recorrerse hasta el lugar de entrega.
     * */
    public double getDistanciaKm() {
        return distanciaKm;
    }


    /**
     * Muestra el resumen del pedido gestionado, con el número de pedido, dirección a la cual debe ser enviado,
     * distancia en km que deberá recorrer para llegar a destino.
     * */
    public void mostrarResumen(){
        System.out.println("Pedido #" + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");

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

    /**
     * Calcula el tiempo estimado de entrega del pedido, en minutos.
     * Cada subclase implementa su propia lógica de calculo según el tipo de pedido.
     *
     * @return tiempo estimado de entrega en minutos.
     * */
    public abstract int calcularTiempoEntrega();
}
