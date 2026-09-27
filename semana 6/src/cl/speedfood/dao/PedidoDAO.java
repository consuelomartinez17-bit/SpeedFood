package cl.speedfood.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la tabla 'pedido': permite guardar pedidos nuevos
 * y consultar todos los pedidos almacenados en la base de datos.
 *
 * @author Consuelo
 * @version 1.0
 */
public class PedidoDAO {

    /**
     * Inserta un nuevo pedido en la base de datos.
     *
     * @param pedido datos del pedido a guardar (sin id, porque lo asigna MySQL).
     * @return el id generado por la base de datos para este pedido, o -1 si no se pudo obtener.
     * @throws SQLException si ocurre un error al guardar.
     */
    public int guardar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, pedido.getDireccion());
            sentencia.setString(2, pedido.getTipo());
            sentencia.setString(3, pedido.getEstado());
            sentencia.executeUpdate();

            try (ResultSet clavesGeneradas = sentencia.getGeneratedKeys()) {
                if (clavesGeneradas.next()) {
                    return clavesGeneradas.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * Obtiene todos los pedidos almacenados en la base de datos.
     *
     * @return lista con todos los pedidos registrados (puede estar vacía).
     * @throws SQLException si ocurre un error al consultar.
     */
    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedido";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                pedidos.add(new Pedido(
                        resultado.getInt("id"),
                        resultado.getString("direccion"),
                        resultado.getString("tipo"),
                        resultado.getString("estado")
                ));
            }
        }
        return pedidos;
    }
}