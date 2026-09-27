package cl.speedfood.dao;

import java.sql.Date;
import java.sql.Time;

/**
 * Representa una fila de la tabla 'entrega' en la base de datos speedfast_db,
 * que asocia un pedido con el repartidor que lo entregó, en una fecha y hora.
 *
 * @author Consuelo
 * @version 1.0
 */
public class Entrega {

    private int id;
    private int idPedido;
    private int idRepartidor;
    private Date fecha;
    private Time hora;

    /**
     * Constructor para una entrega que todavía no existe en la base de datos
     * (sin id, porque MySQL se lo asigna automáticamente al guardarla).
     *
     * @param idPedido identificador del pedido entregado.
     * @param idRepartidor identificador del repartidor que hizo la entrega.
     * @param fecha fecha en que se realizó la entrega.
     * @param hora hora en que se realizó la entrega.
     */
    public Entrega(int idPedido, int idRepartidor, Date fecha, Time hora) {
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() { return id; }
    public int getIdPedido() { return idPedido; }
    public int getIdRepartidor() { return idRepartidor; }
    public Date getFecha() { return fecha; }
    public Time getHora() { return hora; }
}