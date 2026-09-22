package cl.speedfood.modelo;

import cl.speedfood.interfaces.Despachable;
import cl.speedfood.interfaces.Cancelable;
import cl.speedfood.interfaces.Rastreable;

import java.util.List;
import java.util.ArrayList;


/**
 * Clase que representa el pedido de comida con los atributos heredados de la clase base pedido y ademas agregando los
 * suyos como lo son restaurante, la validacion si necesita mochila termica para el transporte, el historial de envio
 *
 * @author Consuelo
 * @version 1.0
 *
 * */
public class PedidoComida extends Pedido implements Despachable, Cancelable, Rastreable{

    private String restaurante;
    private boolean requiereMochilaTermica;
    private List<String> historial = new ArrayList<>();
    private boolean despachado = false;
    private boolean cancelado = false;


    /**
     * Constructor con parámetros.
     * Construye el pedido de comida con los parametros heredados de pedido y agregando los propios, restaurante y
     * requiere mochila termica.
     * @param idPedido          identificador único del pedido.
     * @param direccionEntrega  dirección donde debe entregarse el pedido.
     * @param distanciaKm       distancia en kilometros que debe recorrerse hasta el lugar de entrega.
     * @param restaurante       el nombre del restaurante que envia el pedido.
     * @param requiereMochilaTermica indica si el pedido necesita ser transportado en mochila termica.
     *
     * */

    public PedidoComida(String idPedido, String direccionEntrega, double distanciaKm, String restaurante, boolean requiereMochilaTermica) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.restaurante = restaurante;
        this.requiereMochilaTermica = requiereMochilaTermica;
        historial.add("Pedido de comida creado: " + idPedido);
    }

    /**
     * Obtiene el restaurante de donde se envia el pedido.
     * @return restaurante de donde se envia el pedido
     * */
    public String getRestaurante() {
        return restaurante;
    }

    /**
     * Obtiene la indicacion si el pedido necesita mochila termica para su transporte o no.
     * @return si necesita mochila termica o no
     *
     * */
    public boolean isRequiereMochilaTermica() {
        return requiereMochilaTermica;
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
     * Asigna un repartidor y ademas valida si se necesita mochila termica para el trasnporte del pedido.
     * */
    @Override
    public void asignarRepartidor(){
        System.out.println("[Pedido Comida]");
        System.out.println("Asignando repartidor...");
        if(requiereMochilaTermica){
            System.out.println("→ Verificando mochila térmica... OK");
        } else {
            System.out.println("→ No se requiere mochila térmica");
        }
    }

    /**
     * Asigna un repartidor específico al pedido de comida, una vez que la validación
     * de mochila térmica ya fue realizada.
     * @param nombreRepartidor entrega el nombre del repartidor responsable de la entrega
     *
     * */
    @Override
    public void asignarRepartidor(String nombreRepartidor){
        System.out.println("→ Pedido asignado a " + nombreRepartidor);
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido de comida.
     * Se consideran 15 minutos base más 2 minutos por cada kilómetro de distancia,
     * más 3 minutos adicionales si el pedido requiere mochila térmica.
     *
     * @return tiempo estimado de entrega en minutos.
     *
     * */
    @Override
    public int calcularTiempoEntrega(){
        int tiempo = (int)(15 + (2 * distanciaKm));
        if (requiereMochilaTermica) {
            tiempo += 3;
        }
        return tiempo;
    }

    /**
     * Despacha el pedido de comida, marcándolo como en ruta hacia el cliente.
     * Si el pedido ya fue despachado anteriormente, no se realiza ninguna acción.
     *
     * @return true si el despacho fue exitoso, false si el pedido ya estaba despachado.
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
        despachado = true;
        System.out.println("Pedido de comida despachado correctamente.");
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
     * Cancela el pedido de comida, siempre que este no haya sido despachado previamente.
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
     * Obtiene el historial de eventos registrados para este pedido de comida
     * (creación, despacho, cancelaciones, entre otros).
     *
     * @return una copia de la lista de eventos del historial.
     * */
    @Override
    public List<String> verHistorial() {
        return new ArrayList<>(historial);

    }
}
