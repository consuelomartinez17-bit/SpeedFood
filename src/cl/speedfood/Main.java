package cl.speedfood;

/**
 * Clase principal encargada de ejecutar el programa y de instanciar los objetos.
 *
 * @author Consuelo
 * @version 1.0
 * */
public class Main {

    public static void main(String[] args) {

        // --- Pedido de comida ---
        Pedido pedidoComida1 = new PedidoComida("001", "Av. Siempre Viva 123", "La Pizzería", true);
        pedidoComida1.asignarRepartidor();
        if (pedidoComida1 instanceof PedidoComida) {
            PedidoComida comidaEspecifico = (PedidoComida) pedidoComida1;
            comidaEspecifico.asignarRepartidor("Juan Pérez");
        }

        System.out.println("-----");

        // --- Pedido de encomienda ---
        Pedido pedidoEncomienda1 = new PedidoEncomienda("002", "Calle Falsa 456", 4.5, true);
        pedidoEncomienda1.asignarRepartidor();
        if (pedidoEncomienda1 instanceof PedidoEncomienda) {
            PedidoEncomienda encomiendaEspecifico = (PedidoEncomienda) pedidoEncomienda1;
            encomiendaEspecifico.asignarRepartidor("María González");
        }

        System.out.println("-----");

        // --- Pedido express ---
        Pedido pedidoExpress1 = new PedidoExpress("003", "Pasaje Los Aromos 789", 3.2, true);
        pedidoExpress1.asignarRepartidor();
        if (pedidoExpress1 instanceof PedidoExpress) {
            PedidoExpress expressEspecifico = (PedidoExpress) pedidoExpress1;
            expressEspecifico.asignarRepartidor("Pedro Soto");
        }
    }
}