package cl.speedfood.gestores;

import cl.speedfood.interfaces.Despachable;
import cl.speedfood.interfaces.Cancelable;
import cl.speedfood.interfaces.Rastreable;
import cl.speedfood.modelo.Pedido;
import java.util.List;
import java.util.ArrayList;

/**
 * Gestor que orquesta el despacho, la cancelación y la consulta de historial
 * de todos los pedidos registrados en el sistema, sin conocer el tipo concreto
 * de cada uno (comida, encomienda o express).
 *
 * @author Consuelo
 * @version 1.0
 * */

public class ControladorDeEnvios {

    private List<Despachable> despachables = new ArrayList<>();
    private List<Cancelable> cancelables = new ArrayList<>();
    private List<Rastreable> rastreables = new ArrayList<>();
    private List<Pedido> pedidosRegistrados = new ArrayList<>();

    /**
     * Registra un pedido en el controlador, agregándolo a las listas de
     * despachables, cancelables, rastreables, y a la lista general de
     * pedidos que usan las ventanas para mostrar la tabla y armar repartidores.
     *
     * @param pedido objeto que extiende {@link Pedido} e implementa Despachable,
     *               Cancelable y Rastreable a la vez.
     * @param <T> tipo del pedido, acotado a que sea un Pedido y a que implemente
     *            las tres interfaces mencionadas.
     * */
    public <T extends Pedido & Despachable & Cancelable & Rastreable> void registrarPedido(T pedido) {
        despachables.add(pedido);
        cancelables.add(pedido);
        rastreables.add(pedido);
        pedidosRegistrados.add(pedido);
    }

    /**
     * Obtiene todos los pedidos registrados en el sistema, sin importar su tipo.
     * Se usa para poblar la tabla de la vista y para armar la lista de pedidos
     * que se le entrega a un {@link cl.speedfood.modelo.Repartidor}.
     *
     * @return una copia de la lista de pedidos registrados.
     */
    public List<Pedido> getPedidosRegistrados() {
        return new ArrayList<>(pedidosRegistrados);
    }

    /**
     * Despacha todos los pedidos registrados en el controlador,
     * sin importar el tipo concreto de cada uno.
     * */
    public void despacharTodos() {
        for(Despachable d : despachables) {
            d.despachar();
        }
    }

    /**
     * Cancela todos los pedidos registrados en el controlador, aplicando
     * el mismo motivo a cada uno.
     *
     * @param motivo cadena que explica el motivo de la cancelación.
     * */
    public void cancelarTodos(String motivo) {
        for(Cancelable c : cancelables) {
            c.cancelar(motivo);
        }
    }

    /**
     * Muestra por consola el historial de eventos de todos los pedidos
     * registrados en el controlador, agrupados por tipo de pedido.
     * */
    public void mostrarTodosHistoriales() {
        for (Rastreable r : rastreables) {
            System.out.println("=== Historial de " + r.getClass().getSimpleName() + " ===");
            for (String evento : r.verHistorial()) {
                System.out.println(evento);
            }
            System.out.println();
        }
    }
}