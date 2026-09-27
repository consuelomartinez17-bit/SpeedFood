package cl.speedfood.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Acceso a datos para la tabla 'entrega': registra la relación entre un
 * pedido y el repartidor que lo entrega, en una fecha y hora determinadas.
 *
 * @author Consuelo
 * @version 1.0
 */
public class EntregaDAO {

    /**
     * Inserta una nueva entrega en la base de datos, relacionando un pedido
     * ya existente con un repartidor ya existente.
     *
     * @param entrega datos de la entrega a guardar (sin id, porque lo asigna MySQL).
     * @return true si la entrega se guardó correctamente, false en caso contrario.
     * @throws SQLException si ocurre un error al guardar (por ejemplo, si el
     *         id de pedido o de repartidor no existen en sus tablas).
     */
    public boolean guardar(Entrega entrega) throws SQLException {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, entrega.getIdPedido());
            sentencia.setInt(2, entrega.getIdRepartidor());
            sentencia.setDate(3, entrega.getFecha());
            sentencia.setTime(4, entrega.getHora());

            return sentencia.executeUpdate() > 0;
        }
    }
}