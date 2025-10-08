package cl.condor.calificaciones_api.service;

import cl.condor.calificaciones_api.model.Calificacion;
import cl.condor.calificaciones_api.repository.CalificacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.condor.calificaciones_api.webclient.UsuarioClient;
import cl.condor.calificaciones_api.webclient.RutaClient;


import java.util.List;

@Service
@Transactional
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;
    @Autowired
    private UsuarioClient usuarioClient;
    @Autowired
    private RutaClient rutaClient;

    // Para entregarnos la lista de calificaciones
    public List<Calificacion> findAll() {
        return calificacionRepository.findAll();
    }

    public Calificacion findById(Integer id) {
        return calificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calificacion no encontrada"));

    }

    // para guardar ruta y que tenga los datos requeridos
    public Calificacion save(Calificacion calificacion) {
        if (calificacion.getIdUsuario() == null)
            throw new RuntimeException("id_usuario es obligatorio");
        if (calificacion.getIdRuta() == null)
            throw new RuntimeException("id_ruta es obligatorio");

        usuarioClient.getUsuarioById(calificacion.getIdUsuario());
        rutaClient.getRutaById(calificacion.getIdRuta());


        return calificacionRepository.save(calificacion);
    }

}
