package cl.speedfood;

import cl.speedfood.gestores.ControladorDeEnvios;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal encargada de ejecutar el programa, instanciar los objetos
 * del dominio (pedidos, controlador de envíos) y coordinar la simulación
 * de entregas concurrentes mediante {@link Repartidor} y un
 * {@link java.util.concurrent.ExecutorService}.
 *
 * @author Consuelo
 * @version 1.1
 * */
public class Main {

    /**
     * Punto de entrada del programa.
     * <p>
     * El flujo se organiza en cuatro etapas:
     * <ol>
     *   <li>Creación y asignación individual de pedidos ({@code PedidoComida},
     *       {@code PedidoEncomienda}, {@code PedidoExpress}), incluyendo un
     *       caso de cancelación anticipada.</li>
     *   <li>Registro de los pedidos en {@link ControladorDeEnvios} y ejecución
     *       de las operaciones de despacho, cancelación e historial.</li>
     *   <li>Simulación concurrente de entregas: se agrupan los pedidos en
     *       repartidores y se ejecutan en paralelo usando un
     *       {@link ExecutorService}.</li>
     *   <li>Resumen final del sistema, calculado con Streams sobre la lista completa
     *       de pedidos, contando cuántos fueron cancelados y cuántos permanecen
     *       activos o fueron entregados.</li>
     * </ol>
     *
     * @param args argumentos de línea de comandos; no se utilizan en este programa
     */

    public static void main(String[] args) {

        // --- Pedido de comida ---
        PedidoComida comidaEspecifico = new PedidoComida("001", "Av. Siempre Viva 123", 4.0, "La Pizzería", true);
        comidaEspecifico.asignarRepartidor();
        comidaEspecifico.asignarRepartidor("Juan Pérez");
        comidaEspecifico.mostrarResumen();
        System.out.println("Tiempo estimado de entrega: " + comidaEspecifico.calcularTiempoEntrega() + " minutos");

        System.out.println("-----");

        // --- Pedido de encomienda ---
        PedidoEncomienda encomiendaEspecifico = new PedidoEncomienda("002", "Calle Falsa 456", 5.0, 4.5, true);
        encomiendaEspecifico.asignarRepartidor();
        encomiendaEspecifico.asignarRepartidor("María González");
        encomiendaEspecifico.mostrarResumen();
        System.out.println("Tiempo estimado de entrega: " + encomiendaEspecifico.calcularTiempoEntrega() + " minutos");

        System.out.println("-----");

        // --- Pedido express ---
        PedidoExpress expressEspecifico = new PedidoExpress("003", "Pasaje Los Aromos 789", 3.2, true);
        expressEspecifico.asignarRepartidor();
        expressEspecifico.asignarRepartidor("Pedro Soto");
        expressEspecifico.mostrarResumen();
        System.out.println("Tiempo estimado de entrega: " + expressEspecifico.calcularTiempoEntrega() + " minutos");

        // --- Pedido cancelado ---
        PedidoComida comidaCancelado = new PedidoComida("004", "Pasaje Janequeo 1042", 6.0, "Kimi no Sushi", true);
        comidaCancelado.asignarRepartidor();
        comidaCancelado.asignarRepartidor("Pedro Prado");
        comidaCancelado.mostrarResumen();


        System.out.println("-----");

        // --- Pedido con datos inválidos (demuestra manejo de excepciones) ---
        System.out.println("=== Intento de creación de pedido con datos inválidos ===");
        try {
            PedidoComida comidaInvalida = new PedidoComida("007", "", -3.0, "Restaurante Fantasma", true);
            comidaInvalida.mostrarResumen();
        } catch (IllegalArgumentException excepcionValidacion) {
            System.out.println("No se pudo crear el pedido: " + excepcionValidacion.getMessage());
        }

        System.out.println("-----");

        try {
            PedidoExpress expressInvalido = new PedidoExpress("008", "Calle Real 100", -10.0, true);
            expressInvalido.mostrarResumen();
        } catch (IllegalArgumentException excepcionValidacion) {
            System.out.println("No se pudo crear el pedido: " + excepcionValidacion.getMessage());
        }

        System.out.println("-----");

        // --- Controlador de envíos ---
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        controlador.registrarPedido(comidaEspecifico);
        controlador.registrarPedido(encomiendaEspecifico);
        controlador.registrarPedido(expressEspecifico);
        controlador.registrarPedido(comidaCancelado);

        System.out.println("=== Cancelación anticipada de un pedido (antes de despachar) ===");
        comidaCancelado.cancelar("Cliente cambió de opinión");

        System.out.println("=== Despachar todos los pedidos ===");
        controlador.despacharTodos();

        System.out.println("\n=== Cancelar todos los pedidos: motivo \"Cliente se arrepintió\" ===");
        controlador.cancelarTodos("Cliente se arrepintió");

        System.out.println("\n=== Mostrar historial de todos los pedidos ===");
        controlador.mostrarTodosHistoriales();

        // --- Concurrencia: repartidores entregando en paralelo ---
        System.out.println("\n=== Simulación de entregas concurrentes ===");

        PedidoComida comidaExtra = new PedidoComida("005", "Av. Los Leones 200", 2.5, "Sushi Ken", true);
        PedidoExpress expressExtra = new PedidoExpress("006", "Camino El Alba 500", 1.8, true);

        Repartidor repartidor1 = new Repartidor("Juan Pérez",
                Arrays.asList(comidaEspecifico, encomiendaEspecifico));
        Repartidor repartidor2 = new Repartidor("María González",
                Arrays.asList(expressEspecifico, comidaExtra));
        Repartidor repartidor3 = new Repartidor("Pedro Soto",
                Arrays.asList(expressExtra, comidaCancelado));

        List<Repartidor> repartidores = Arrays.asList(repartidor1, repartidor2, repartidor3);

        ExecutorService pool = Executors.newFixedThreadPool(repartidores.size());
        for (Repartidor repartidor : repartidores) {
            pool.execute(repartidor);
        }

        pool.shutdown();
        try {
            // Chacreo: si algún hilo se cuelga, no dejamos el programa esperando indefinidamente
            if (!pool.awaitTermination(15, TimeUnit.SECONDS)) {
                System.out.println("Tiempo de espera agotado, forzando cierre del pool.");
                pool.shutdownNow();
            }
        } catch (InterruptedException excepcionEspera) {
            Thread.currentThread().interrupt();
            pool.shutdownNow();
        }

        System.out.println("=== Todos los repartidores finalizaron ===");

        // --- Resumen final con Streams (sugerencia de retroalimentación: explorar Streams) ---
        List<Pedido> todosLosPedidos = Arrays.asList(
                comidaEspecifico, encomiendaEspecifico, expressEspecifico, comidaCancelado,
                comidaExtra, expressExtra
        );

        long totalCancelados = todosLosPedidos.stream()
                .filter(Pedido::isCancelado)
                .count();

        long totalActivos = todosLosPedidos.size() - totalCancelados;

        System.out.println("\n=== Resumen final ===");
        System.out.println("Total de pedidos: " + todosLosPedidos.size());
        System.out.println("Pedidos cancelados: " + totalCancelados);
        System.out.println("Pedidos activos/entregados: " + totalActivos);
    }
}