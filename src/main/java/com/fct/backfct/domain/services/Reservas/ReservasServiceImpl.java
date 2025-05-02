package com.fct.backfct.domain.services.Reservas;

import com.fct.backfct.domain.converters.EstadosReservasMapper;
import com.fct.backfct.domain.converters.ReservasMapper;
import com.fct.backfct.domain.dto.EstadosReservaDTO;
import com.fct.backfct.domain.dto.ReservasDTO;
import com.fct.backfct.domain.models.dao.IEstadoReservaDao;
import com.fct.backfct.domain.models.dao.IReservasDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservasServiceImpl implements IReservasService {

    @Autowired
    private IReservasDao reservasDao;

    @Autowired
    private IEstadoReservaDao estadoReservaDao;

    @Autowired
    private ReservasMapper reservasMapper;

    @Autowired
    private EstadosReservasMapper estadosMapper;

    @Override
    public List<ReservasDTO> findAll() {
        return reservasMapper.toListDtos(reservasDao.findAll());
    }

    @Override
    public List<EstadosReservaDTO> getEstadosReservas() {
        return estadosMapper.toListDtos(estadoReservaDao.findAll());
    }

    @Override
    public ReservasDTO update(ReservasDTO reservasDTO) {
        return reservasMapper.toDto(reservasDao.save(reservasMapper.toEntity(reservasDTO)));
    }

    @Override
    public ReservasDTO save(ReservasDTO reservasDTO) {
        return reservasMapper.toDto(reservasDao.save(reservasMapper.toEntity(reservasDTO)));
    }
}
