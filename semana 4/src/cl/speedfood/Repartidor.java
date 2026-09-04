package cl.speedfood;

import java.util.List;
import java.util.Random;

/**
 * Representa a un repartidor de SpeedFast que procesa su lista de pedidos
 * de forma secuencial dentro de un hilo independiente.
 * <p>
 * Al implementar {@link Runnable}, cada instancia puede ejecutarse en paralelo
 * junto a otros repartidores mediante un {@link java.util.concurrent.ExecutorService},
 * simulando el tiempo de entrega con pausas aleatorias.
 *
 * @author Consuelo
 * @version 1.0
 */

public class Repartidor implements Runnable {

    private final String nombreRepartidor;
    private final List<Pedido> pedidosAsignados;
    private static final Random generadorAleatorio = new Random();

    /**
     * Crea un repartidor con su lista de pedidos asignados.
     *
     * @param nombreRepartidor nombre identificador del repartidor
     * @param pedidosAsignados lista de pedidos que debe entregar; puede venir
     *                         nula o vacía, en cuyo caso el repartidor no realiza entregas
     */
    public Repartidor(String nombreRepartidor, List<Pedido> pedidosAsignados) {
        this.nombreRepartidor = nombreRepartidor;
        this.pedidosAsignados = pedidosAsignados;
    }

    /**
     * Ejecuta la entrega secuencial de los pedidos asignados al repartidor.
     * <p>
     * Por cada pedido, imprime el tipo de pedido y su identificador al iniciar
     * la entrega, simula el tiempo de entrega con {@link Thread#sleep(long)}
     * usando un valor aleatorio, y luego confirma la entrega. Si el hilo es
     * interrumpido durante la espera, se restaura el flag de interrupción
     * y se detiene la ejecución sin lanzar la excepción hacia arriba.
     */
    @Override
    public void run() {
        if (pedidosAsignados == null || pedidosAsignados.isEmpty()) {
            System.out.println("[Repartidor: " + nombreRepartidor + "] No tiene pedidos asignados.");
            return;
        }

        for (Pedido pedidoActual : pedidosAsignados) {
            if (pedidoActual == null) {
                System.out.println("[Repartidor: " + nombreRepartidor + "] Pedido nulo detectado, se omite.");
                continue;
            }

            if (pedidoActual.isCancelado()) {
                System.out.println("[Repartidor: " + nombreRepartidor + "] Pedido #"
                        + pedidoActual.getIdPedido() + " fue cancelado, se omite la entrega.");
                continue;
            }

            String tipoPedido = pedidoActual.getClass().getSimpleName();

            System.out.println("[Repartidor: " + nombreRepartidor + "] Entregando "
                    + tipoPedido + " #" + pedidoActual.getIdPedido() + "...");

            try {
                int tiempoSimuladoMs = 500 + generadorAleatorio.nextInt(2000);
                Thread.sleep(tiempoSimuladoMs);
            } catch (InterruptedException excepcionInterrupcion) {
                System.out.println("[Repartidor: " + nombreRepartidor + "] Entrega interrumpida.");
                Thread.currentThread().interrupt();
                return;
            }

            System.out.println("[Repartidor: " + nombreRepartidor + "] Pedido #"
                    + pedidoActual.getIdPedido() + " entregado.");
        }
    }
}