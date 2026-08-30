package cl.speedfood;

import cl.speedfood.gestores.ControladorDeEnvios;

/**
 * Clase principal encargada de ejecutar el programa y de instanciar los objetos.
 *
 * @author Consuelo
 * @version 1.0
 * */
public class Main {

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
    }
}