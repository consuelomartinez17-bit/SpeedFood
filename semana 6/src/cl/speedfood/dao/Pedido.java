package cl.speedfood.dao;

/**
 * Representa una fila de la tabla 'pedido' en la base de datos speedfast_db.
 * A diferencia del Pedido de cl.speedfood.modelo, esta clase es solo un
 * contenedor simple de datos (sin lógica de negocio), pensado para leer y
 * escribir directamente en la base de datos mediante JDBC.
 *
 * @author Consuelo
 * @version 1.0
 */
public class Pedido {

    private int id;
    private String direccion;
    private String tipo;
    private String estado;

    /**
     * Constructor para un pedido que todavía no existe en la base de datos
     * (sin id, porque MySQL se lo asigna automáticamente al guardarlo).
     */
    public Pedido(String direccion, String tipo, String estado) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    /**
     * Constructor para un pedido que ya existe en la base de datos
     * (por ejemplo, al leerlo con un SELECT).
     */
    public Pedido(int id, String direccion, String tipo, String estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getDireccion() { return direccion; }
    public String getTipo() { return tipo; }
    public String getEstado() { return estado; }
}