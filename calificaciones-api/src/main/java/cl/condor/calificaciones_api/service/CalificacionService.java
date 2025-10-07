package cl.condor.calificaciones_api.service;

import cl.condor.calificaciones_api.model.Calificacion;
import cl.condor.calificaciones_api.repository.CalificacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    // Para entregarnos la lista de calificaciones
    public List<Calificacion> findAll() {
        return calificacionRepository.findAll();
    }

    public Calificacion findById(Integer id) {
        return calificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calificacion no encontrada"));

    }

    //
}
