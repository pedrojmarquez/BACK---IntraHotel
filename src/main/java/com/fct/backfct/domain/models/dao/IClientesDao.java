package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IClientesDao extends JpaRepository<Clientes, Long> {

    @Query(value = "SELECT c.* " +
            "FROM reservas r " +
            "JOIN clientes c " +
            "ON r.id_cliente = c.id_cliente " +
            "WHERE r.id_habitacion = :idHabitacion " +
            "AND c.activo = 1 " +
            "ORDER BY r.fecha_reserva DESC LIMIT 3", nativeQuery = true)
    List<Clientes> findLas5ClientesDeHabitacion(@Param("idHabitacion") Long idHabitacion);


    Clientes findBydni(String dni);
}
