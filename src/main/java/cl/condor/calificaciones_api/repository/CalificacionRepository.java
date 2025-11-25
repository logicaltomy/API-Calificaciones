package cl.condor.calificaciones_api.repository;

import cl.condor.calificaciones_api.model.Calificacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {

    Optional<List<Calificacion>> findByIdRuta(Integer idRuta);
}
