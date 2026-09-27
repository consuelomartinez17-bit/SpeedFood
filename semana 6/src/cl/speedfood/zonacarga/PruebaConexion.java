package cl.speedfood.zonacarga;

import cl.speedfood.dao.ConexionDB;

import java.sql.Connection;

public class PruebaConexion {
    public static void main(String[] args) {
        try (Connection conexion = ConexionDB.conectar()) {
            System.out.println("¡Conexión exitosa a la base de datos!");
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}