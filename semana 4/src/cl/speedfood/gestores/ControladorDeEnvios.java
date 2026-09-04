package cl.speedfood.gestores;

import cl.speedfood.interfaces.Despachable;
import cl.speedfood.interfaces.Cancelable;
import cl.speedfood.interfaces.Rastreable;
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




    /**
     * Registra un pedido en el controlador, agregándolo a las listas de
     * despachables, cancelables y rastreables, según las interfaces que implementa.
     *
     * @param pedido objeto que implementa Despachable, Cancelable y Rastreable a la vez.
     * @param <T> tipo del pedido, acotado a que implemente las tres interfaces mencionadas.
     * */

    public <T extends Despachable & Cancelable & Rastreable> void registrarPedido(T pedido) {
        despachables.add(pedido);
        cancelables.add(pedido);
        rastreables.add(pedido);
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
