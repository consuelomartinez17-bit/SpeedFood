package cl.speedfood.interfaces;

/**
 * Contrato para validar si es posible el despacho.
 * Cualquier clase que implemente Despachable debe proporcionar su lógica de despacho.
 *
 * */
public interface Despachable {

    /**
     * Ejecuta el proceso de despacho de los pedidos.
     * @return true si el despacho fue exitoso, false en caso contrario.
     */
    boolean despachar();
}