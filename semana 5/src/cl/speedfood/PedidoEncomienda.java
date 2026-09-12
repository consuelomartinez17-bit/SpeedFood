package cl.speedfood;

import java.util.ArrayList;
import java.util.List;

import cl.speedfood.interfaces.Despachable;
import cl.speedfood.interfaces.Cancelable;
import cl.speedfood.interfaces.Rastreable;

/**
 * Clase que representa el pedido de encomienda con los atributos heredados de la clase base pedido y ademas agregando los
 * suyos como lo son peso del pedido y la validación si el embalaje esta correcto.
 *
 * @author Consuelo
 * @version 1.0
 *
 * */
public class PedidoEncomienda extends Pedido implements Despachable, Cancelable, Rastreable {


    private double pesoPedido;
    private boolean embalajeValidado;
    private List<String> historial = new ArrayList<>();
    private boolean despachado = false;
    private boolean cancelado = false;

    /**
     * Constructor con parámetros.
     * Construye el pedido de embalaje con los parametros heredados de pedido y agregando los propios, peso del pedido
     * y si el pedido fue embalado correctamente.
     * @param idPedido          identificador único del pedido.
     * @param direccionEntrega  dirección donde debe entregarse el pedido.
     * @param distanciaKm       distancia en kilometros que debe recorrerse hasta el lugar de entrega.
     * @param pesoPedido        el peso del pedido.
     * @param embalajeValidado indica si el embalaje de la encomienda cumple con las condiciones necesarias para su
     *                         transporte.
     * */

    public PedidoEncomienda(String idPedido, String direccionEntrega, double distanciaKm, double pesoPedido, boolean embalajeValidado) {
        super(idPedido, direccionEntrega, distanciaKm);
        if (pesoPedido < 0) {
            throw new IllegalArgumentException("El peso del pedido no puede ser negativo.");
        }
        this.pesoPedido = pesoPedido;
        this.embalajeValidado = embalajeValidado;
        historial.add("Pedido de encomienda creado: " + idPedido);
    }

    /**
     * Obtiene el peso del pedido a enviar.
     * @return peso del pedido.
     * */
    public double getPesoPedido() {
        return pesoPedido;
    }

    /**
     * Obtiene la indicacion si el pedido cuenta con el embalaje adecuado para el envio.
     * @return si el embalaje cumple con las condiciones para que el pedido sea enviado.
     *
     * */
    public boolean isEmbalajeValidado() {
        return embalajeValidado;
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
     * Asigna un repartidor y ademas valida si el embalaje a sido realizado correctamente.
     * */
    @Override
    public void asignarRepartidor(){
        System.out.println("[Pedido Encomienda]");
        System.out.println("Asignando repartidor...");
        if(embalajeValidado){
            System.out.println("→ Validando peso y embalaje... OK");
        } else {
            System.out.println("→ Embalaje no validado, no se puede asignar repartidor");
        }
    }

    /**
     * Asigna un repartidor específico al pedido de encomienda y valida el ambalado del paquete para que esto suceda.
     * @param nombreRepartidor entrega el nombre del repartidor responsable de la entrega
     *
     * */
    @Override
    public void asignarRepartidor(String nombreRepartidor){
        if(embalajeValidado){
            System.out.println("→ Pedido asignado a " + nombreRepartidor);
        } else {
            System.out.println("→ No se puede asignar a " + nombreRepartidor + ", el embalaje no está validado");
        }
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido de encomienda.
     * Se consideran 20 minutos base, más 1.5 minutos por cada kilómetro de distancia,
     * más 0.5 minutos por cada kilo de peso del pedido.
     *
     * @return tiempo estimado de entrega en minutos.
     *
     * */
    @Override
    public int calcularTiempoEntrega(){
        return (int) Math.round(20 + (1.5 * distanciaKm) + (0.5 * pesoPedido));
    }

    /**
     * Despacha el pedido de encomienda, siempre que no haya sido despachado previamente
     * y que el embalaje cuente con la validación necesaria para su transporte.
     * Si alguna de estas condiciones no se cumple, el despacho no se realiza.
     *
     * @return true si el despacho fue exitoso, false si el pedido ya estaba despachado
     * o si el embalaje no está validado.
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
        if (!embalajeValidado) {
            System.out.println("El pedido no puede ser despachado, el embalaje no está validado.");
            historial.add("Intento de despacho fallido: " + idPedido + ". Embalaje no validado.");
            return false;
        }
        despachado = true;
        System.out.println("Pedido de encomienda despachado correctamente.");
        historial.add("Pedido despachado: " + idPedido);
        return true;
    }

    /**
     * Cancela el pedido de encomienda, siempre que este no haya sido despachado previamente.
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
     * Obtiene el historial de eventos registrados para este pedido de encomienda
     * (creación, despacho, cancelaciones, entre otros).
     *
     * @return una copia de la lista de eventos del historial.
     * */
    @Override
    public List<String> verHistorial() {
        return new ArrayList<>(historial);
    }



}
