package cl.speedfood.zonacarga;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Punto de entrada para la simulación de la zona de carga de SpeedFast.
 * Demuestra cómo múltiples repartidores concurrentes pueden competir de
 * forma segura por un recurso compartido, sin retirar el mismo pedido
 * dos veces.
 *
 * @author Consuelo
 * @version 1.0
 */
public class Main {

    /**
     * Punto de entrada del programa.
     * <p>
     * El flujo se organiza en tres etapas:
     * <ol>
     *   <li>Creación de la {@link ZonaDeCarga} y carga de 5 pedidos
     *       iniciales, todos en estado {@code PENDIENTE}.</li>
     *   <li>Lanzamiento de 3 repartidores concurrentes mediante un
     *       {@link ExecutorService}, que compiten por retirar los
     *       pedidos disponibles.</li>
     *   <li>Espera del cierre ordenado del pool de hilos mediante
     *       {@code awaitTermination}, hasta que todos los repartidores
     *       hayan finalizado su trabajo de forma natural.</li>
     * </ol>
     *
     * @param args argumentos de línea de comandos; no se utilizan en
     *             este programa.
     */
    public static void main(String[] args) {
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        // Se agregan al menos 5 pedidos, todos nacen en estado PENDIENTE
        zonaDeCarga.agregarPedido(new Pedido(1, "Av. Siempre Viva 123"));
        zonaDeCarga.agregarPedido(new Pedido(2, "Calle Falsa 456"));
        zonaDeCarga.agregarPedido(new Pedido(3, "Pasaje Los Aromos 789"));
        zonaDeCarga.agregarPedido(new Pedido(4, "Camino El Alba 500"));
        zonaDeCarga.agregarPedido(new Pedido(5, "Av. Los Leones 200"));

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(new Repartidor("Carlos", zonaDeCarga));
        executor.execute(new Repartidor("Laura", zonaDeCarga));
        executor.execute(new Repartidor("Javiera", zonaDeCarga));

        executor.shutdown(); // no acepta tareas nuevas, pero deja terminar las actuales

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nTodos los pedidos han sido entregados correctamente.");
    }
}
