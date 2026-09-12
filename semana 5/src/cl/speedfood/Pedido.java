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
     * @throws IllegalArgumentException si idPedido o direccionEntrega son nulos o están vacíos,
     *                                  o si distanciaKm es negativa.
     * */
    public Pedido(String idPedido, String direccionEntrega, double distanciaKm) {
        if (idPedido == null || idPedido.isBlank()) {
            throw new IllegalArgumentException("El id del pedido no puede ser nulo o vacío.");
        }
        if (direccionEntrega == null || direccionEntrega.isBlank()) {
            throw new IllegalArgumentException("La dirección de entrega no puede ser nula o vacía.");
        }
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("La distancia en kilómetros no puede ser negativa.");
        }

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
     * Asigna un repartidor específico al pedido, de forma genérica.
     * Las subclases sobrescriben este método para aplicar su propia lógica de validación.
     * @param nombreRepartidor nombre del repartidor asignado al pedido.
     * */
    public void asignarRepartidor(String nombreRepartidor){
        System.out.println("→ Pedido asignado a " + nombreRepartidor);
    }

    /**
     * Calcula el tiempo estimado de entrega del pedido, en minutos.
     * Cada subclase implementa su propia lógica de calculo según el tipo de pedido.
     *
     * @return tiempo estimado de entrega en minutos.
     * */
    public abstract int calcularTiempoEntrega();

    /**
     * Indica si el pedido fue cancelado.
     *
     * @return true si el pedido se encuentra cancelado, false en caso contrario.
     */
    public abstract boolean isCancelado();

    /**
     * Normaliza el motivo de cancelación, evitando registrar "null" o vacíos
     * en el historial cuando no se entrega un motivo válido.
     *
     * @param motivo motivo original recibido.
     * @return el motivo tal cual si es válido, o un texto por defecto si es nulo/vacío.
     */
    protected String normalizarMotivo(String motivo) {
        return (motivo == null || motivo.isBlank()) ? "Motivo no especificado" : motivo;
    }

}
