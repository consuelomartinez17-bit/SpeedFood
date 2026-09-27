package cl.speedfood.interfaces;

import java.util.List;

/**
 * Contrato para obtener un historial de eventos o registros asociados a los pedidos.
 * @author Consuelo
 * @version 1.0
 * */

public interface Rastreable {
    /**
     * Devuelve una lista de eventos o registros que conforman el historial del objeto rastreado.
     *
     * @return Lista de strings que representan el historial.
     * */

    List<String> verHistorial();
}
