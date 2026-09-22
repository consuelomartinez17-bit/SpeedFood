package cl.speedfood.modelo;

import cl.speedfood.interfaces.Despachable;

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

    public Repartidor(String nombreRepartidor, List<Pedido> pedidosAsignados) {
        this.nombreRepartidor = nombreRepartidor;
        this.pedidosAsignados = pedidosAsignados;
    }

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

            if (pedidoActual.isDespachado()) {
                System.out.println("[Repartidor: " + nombreRepartidor + "] Pedido #"
                        + pedidoActual.getIdPedido() + " ya había sido despachado por otro repartidor, se omite.");
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

            if (pedidoActual instanceof Despachable pedidoDespachable && pedidoDespachable.despachar()) {
                System.out.println("[Repartidor: " + nombreRepartidor + "] Pedido #"
                        + pedidoActual.getIdPedido() + " entregado.");
            } else {
                System.out.println("[Repartidor: " + nombreRepartidor + "] Pedido #"
                        + pedidoActual.getIdPedido() + " no se pudo despachar (otro repartidor llegó primero, "
                        + "el pedido fue cancelado mientras viajaba, o no cumple sus condiciones de despacho).");
            }
        }
    }
}