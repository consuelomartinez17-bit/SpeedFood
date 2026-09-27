package cl.speedfood.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la tabla 'repartidor': permite guardar repartidores
 * nuevos y consultar todos los repartidores almacenados en la base de datos.
 *
 * @author Consuelo
 * @version 1.0
 */
public class RepartidorDAO {

    /**
     * Inserta un nuevo repartidor en la base de datos.
     *
     * @param repartidor datos del repartidor a guardar (sin id, porque lo asigna MySQL).
     * @return el id generado por la base de datos para este repartidor, o -1 si no se pudo obtener.
     * @throws SQLException si ocurre un error al guardar.
     */
    public int guardar(Repartidor repartidor) throws SQLException {
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, repartidor.getNombre());
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
     * Obtiene todos los repartidores almacenados en la base de datos.
     *
     * @return lista con todos los repartidores registrados (puede estar vacía).
     * @throws SQLException si ocurre un error al consultar.
     */
    public List<Repartidor> listarTodos() throws SQLException {
        List<Repartidor> repartidores = new ArrayList<>();
        String sql = "SELECT id, nombre FROM repartidor";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                repartidores.add(new Repartidor(
                        resultado.getInt("id"),
                        resultado.getString("nombre")
                ));
            }
        }
        return repartidores;
    }
}