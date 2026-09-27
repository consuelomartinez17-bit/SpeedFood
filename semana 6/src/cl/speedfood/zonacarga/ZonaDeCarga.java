package cl.speedfood.zonacarga;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Representa la zona de carga de SpeedFast: el recurso compartido desde
 * donde los repartidores retiran pedidos. Garantiza que cada pedido sea
 * retirado por un único repartidor, incluso con múltiples hilos accediendo
 * de forma concurrente.
 *
 * @author Consuelo
 * @version 1.0
 */
public class ZonaDeCarga {

    private final BlockingQueue<Pedido> pedidosPendientes = new LinkedBlockingQueue<>();
    private volatile boolean vaciaAnunciada = false;

    /**
     * Crea la zona de carga e informa por consola su inicialización.
     */
    public ZonaDeCarga() {
        System.out.println("[Zona de carga inicializada]");
    }

    /**
     * Agrega un pedido a la zona de carga.
     *
     * @param pedido el pedido a agregar; si es nulo, se ignora sin efecto.
     */
    public synchronized void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("[Zona de carga] Intento de agregar un pedido nulo, se ignora.");
            return;
        }
        pedidosPendientes.add(pedido);
        System.out.println("Pedido #" + pedido.getId() + " agregado. Destino: " + pedido.getDireccionEntrega());
    }

    /**
     * Retira un pedido de la zona de carga, esperando hasta un segundo
     * si no hay ninguno disponible en ese momento. Si tras la espera
     * no aparece ningún pedido, informa por consola que la zona de
     * carga quedó vacía (una única vez, sin importar cuántos
     * repartidores lo consulten).
     *
     * @return el pedido retirado, o null si no había ninguno disponible
     *         tras el tiempo de espera.
     */
    public synchronized Pedido retirarPedido() {
        try {
            Pedido pedido = pedidosPendientes.poll(1, TimeUnit.SECONDS);
            if (pedido == null && !vaciaAnunciada) {
                vaciaAnunciada = true;
                System.out.println("[Zona de carga vacía]");
            }
            return pedido;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}