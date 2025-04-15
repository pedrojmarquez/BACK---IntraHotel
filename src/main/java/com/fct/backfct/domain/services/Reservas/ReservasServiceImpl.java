package com.fct.backfct.domain.services.Reservas;

import com.fct.backfct.domain.converters.ReservasMapper;
import com.fct.backfct.domain.dto.ReservasDTO;
import com.fct.backfct.domain.models.dao.IReservasDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservasServiceImpl implements IReservasService {

    @Autowired
    private IReservasDao reservasDao;

    @Autowired
    private ReservasMapper reservasMapper;

    @Override
    public List<ReservasDTO> findAll() {
        return reservasMapper.toListDtos(reservasDao.findAll());
    }
}
