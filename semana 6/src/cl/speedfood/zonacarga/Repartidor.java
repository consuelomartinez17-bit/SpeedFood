package cl.speedfood.zonacarga;

/**
 * Representa a un repartidor de SpeedFast que compite junto a otros
 * repartidores por retirar pedidos de una {@link ZonaDeCarga} compartida.
 * <p>
 * Cada repartidor procesa pedidos de forma secuencial hasta que la zona
 * de carga se queda sin pedidos disponibles, momento en el que finaliza
 * su ejecución de forma natural, sin necesidad de ser interrumpido.
 *
 * @author Consuelo
 * @version 1.0
 */
public class Repartidor implements Runnable {

    private final String nombre;
    private final ZonaDeCarga zonaDeCarga;

    /**
     * Crea un repartidor asociado a una zona de carga compartida.
     *
     * @param nombre      nombre identificador del repartidor.
     * @param zonaDeCarga zona de carga desde donde el repartidor retirará
     *                    sus pedidos.
     */
    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    /**
     * Ejecuta el ciclo de trabajo del repartidor.
     * <p>
     * El proceso se repite indefinidamente hasta que ya no queden pedidos
     * disponibles:
     * <ol>
     *   <li>Intenta retirar un pedido de la zona de carga. Si no hay
     *       ninguno disponible, finaliza su ejecución.</li>
     *   <li>Marca el pedido retirado como {@code EN_REPARTO} y simula
     *       el tiempo de entrega con {@link Thread#sleep(long)}.</li>
     *   <li>Marca el pedido como {@code ENTREGADO} una vez completada
     *       la simulación de entrega.</li>
     * </ol>
     * Si el hilo es interrumpido durante la simulación de entrega, se
     * restaura el flag de interrupción y se detiene la ejecución sin
     * marcar el pedido como entregado.
     */
    @Override
    public void run() {
        while (true) {
            Pedido pedido = zonaDeCarga.retirarPedido();
            if (pedido == null) {
                return;
            }

            System.out.println("[Repartidor - " + nombre + "] Retirando pedido #" + pedido.getId() + "...");
            pedido.setEstado("EN_REPARTO");
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());

            try {
                Thread.sleep(1000 + (long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            System.out.println("[Repartidor - " + nombre + "] Entregando pedido #" + pedido.getId() + "...");
            pedido.setEstado("ENTREGADO");
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());
        }
    }
}
