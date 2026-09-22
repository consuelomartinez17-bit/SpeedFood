package cl.speedfood.modelo;

import java.util.ArrayList;
import java.util.List;

import cl.speedfood.interfaces.Despachable;
import cl.speedfood.interfaces.Cancelable;
import cl.speedfood.interfaces.Rastreable;

/**
 * Clase que representa un pedido express, hereda de la clase base pedido los atributos de este y agrega el propio
 * como lo es si està disponible para hacer la entrega.
 *
 * @author Consuelo
 * @version 1.0
 * */

public class PedidoExpress extends Pedido implements Despachable, Cancelable, Rastreable {

    private boolean repartidorDisponible;
    private List<String> historial = new ArrayList<>();
    private boolean despachado = false;
    private boolean cancelado = false;


    /**
     * Constructor con parámetros.
     * Construye el pedido express con los parametros heredados de pedido y agregando los propios, distancia
     * en kilometros y si el repartidor esta disponible para realizar el reparto.
     * @param idPedido              identificador único del pedido.
     * @param direccionEntrega  dirección donde debe entregarse el pedido.
     * @param distanciaKm       distancia en kilometros que debe recorrerse hasta el lugar de entrega.
     * @param repartidorDisponible  indica si el repartidor esta disponible para hacerse cargo de ese pedido.
     * */

    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm, boolean repartidorDisponible) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.repartidorDisponible = repartidorDisponible;
        historial.add("Pedido de express creado: " + idPedido);
    }


    /**
     * Obtiene la indicacion si el repartidor se encuentra disponible para recoger el pedido.
     * @return si el repartidor esta disponible para hacer el envio.
     *
     * */
    public boolean isRepartidorDisponible() {
        return repartidorDisponible;
    }

    /**
     * Indica si el pedido fue cancelado.
     * @return true si el pedido se encuentra cancelado, false en caso contrario.
     */
    @Override
    public boolean isCancelado() {
        return cancelado;
    }

    /**
     * Asigna un repartidor si, este esta a menos de 5 kilometros y que ademas este disponible para hacer el reparto.
     * */
    @Override
    public void asignarRepartidor(){
        System.out.println("[Pedido Express]");
        System.out.println("Asignando repartidor...");
        if(repartidorDisponible && distanciaKm < 5){
            System.out.println("→ Repartidor más cercano con disponibilidad inmediata encontrado.");
        } else {
            System.out.println("→ No hay repartidor disponible en la zona por el momento.");
        }
    }

    /**
     * Asigna un repartidor específico al pedido express y valida que el repartidor se encuentre cerca para ser asignado.
     * @param nombreRepartidor entrega el nombre del repartidor responsable de la entrega
     *
     * */
    @Override
    public void asignarRepartidor(String nombreRepartidor){
        if(repartidorDisponible && distanciaKm < 5){
            System.out.println("→ Pedido asignado a " + nombreRepartidor);
        } else {
            System.out.println("→ No se puede asignar a " + nombreRepartidor + ", no hay repartidores cerca, intentelo mas tarde. ");
        }
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido de express.
     * Se consideran 10 minutos base, más 5 minutos extra si la distancia supera los 5 km,
     * más 8 minutos adicionales si no hay un repartidor disponible de inmediato.
     *
     * @return tiempo estimado de entrega en minutos.
     *
     * */
    @Override
    public int calcularTiempoEntrega(){
        int tiempo = 10;
        if (distanciaKm > 5) {
            tiempo += 5;
        }
        if (!repartidorDisponible) {
            tiempo += 8;
        }
        return tiempo;
    }

    /**
     * Despacha el pedido express, siempre que no haya sido despachado previamente
     * y que exista un repartidor disponible para hacer la entrega.
     * Si alguna de estas condiciones no se cumple, el despacho no se realiza.
     *
     * @return true si el despacho fue exitoso, false si el pedido ya estaba despachado
     * o si no hay repartidor para realizar el despacho.
     * */
    @Override
    public boolean despachar() {
        if (cancelado) {
            System.out.println("El pedido no puede ser despachado, fue cancelado previamente.");
            return false;
        }
        if (despachado) {
            System.out.println("El pedido ya fue despachado anteriormente.");
            return false;
        }
        if (!repartidorDisponible) {
            System.out.println("El pedido no puede ser despachado, no hay repartidores disponibles.");
            historial.add("Intento de despacho fallido: " + idPedido + ". No hay repartidores.");
            return false;
        }
        despachado = true;
        System.out.println("Pedido de express despachado correctamente.");
        historial.add("Pedido despachado: " + idPedido);
        return true;
    }

    /**
     * Indica si el pedido ya fue despachado.
     * @return true si el pedido ya fue despachado, false en caso contrario.
     */
    @Override
    public boolean isDespachado() {
        return despachado;
    }

    /**
     * Cancela el pedido express, siempre que este no haya sido despachado previamente.
     * Registra el intento de cancelación en el historial, sea exitoso o no.
     *
     * @param motivo cadena que explica el motivo de la cancelación.
     * @return true si la cancelación fue exitosa, false si el pedido ya se encontraba en ruta.
     * */
    @Override
    public boolean cancelar(String motivo) {
        motivo = normalizarMotivo(motivo);

        if (cancelado) {
            System.out.println("El pedido ya fue cancelado anteriormente.");
            return false;
        }
        if (despachado) {
            System.out.println("El pedido no pudo ser cancelado, ya se encuentra en ruta.");
            historial.add("Intento de cancelación fallido: " + idPedido + ". Motivo: " + motivo);
            return false;
        }
        System.out.println("El pedido ha sido cancelado correctamente. Motivo: " + motivo);
        historial.add("Pedido cancelado: " + idPedido + ". Motivo: " + motivo);
        cancelado = true;
        return true;
    }

    /**
     * Obtiene el historial de eventos registrados para este pedido de express
     * (creación, despacho, cancelaciones, entre otros).
     *
     * @return una copia de la lista de eventos del historial.
     * */
    @Override
    public List<String> verHistorial() {
        return new ArrayList<>(historial);
    }


}
