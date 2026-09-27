package cl.speedfood.dao;

/**
 * Representa una fila de la tabla 'repartidor' en la base de datos speedfast_db.
 *
 * @author Consuelo
 * @version 1.0
 */
public class Repartidor {

    private int id;
    private String nombre;

    /**
     * Constructor para un repartidor que todavía no existe en la base de datos
     * (sin id, porque MySQL se lo asigna automáticamente al guardarlo).
     *
     * @param nombre nombre del repartidor.
     */
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor para un repartidor que ya existe en la base de datos
     * (por ejemplo, al leerlo con un SELECT).
     *
     * @param id identificador del repartidor en la base de datos.
     * @param nombre nombre del repartidor.
     */
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
}