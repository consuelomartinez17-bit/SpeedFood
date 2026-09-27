package cl.speedfood.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión entre la aplicación Java y la base de datos
 * speedfast_db en MySQL, mediante JDBC.
 *
 * @author Consuelo
 * @version 1.0
 */
public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Consuelo2026!";

    /**
     * Abre y retorna una nueva conexión a la base de datos speedfast_db.
     * Quien llame a este método es responsable de cerrar la conexión
     * cuando termine de usarla (por ejemplo, con try-with-resources).
     *
     * @return una conexión activa a la base de datos.
     * @throws SQLException si no se puede establecer la conexión
     *         (por ejemplo, si MySQL no está corriendo o la contraseña es incorrecta).
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
