package cl.speedfood.interfaces;

/**
 * Contrato para validar si se puede cancelar un pedido.
 * Cualquier clase que implemente Cancelable debe proporcionar su lógica para cancelar.
 *
 * */
public interface Cancelable {

    /**
     * Ejecuta el proceso de cancelar los pedidos.
     * @param motivo cadena que explica el motivo de la cancelación.
     * @return true si la cancelación fue exitosa, false en caso contrario.
     */
    boolean cancelar(String motivo);
}