package cl.condor.calificaciones_api.controller;

import cl.condor.calificaciones_api.model.Calificacion;
import cl.condor.calificaciones_api.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// get y post funcionan
@RequestMapping("/api/v1/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    // 🔹 Obtener todas las calificaciones
    @GetMapping
    public ResponseEntity<List<Calificacion>> getAllCalificaciones() {
        List<Calificacion> lista = calificacionService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // 🔹 Obtener una calificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> getCalificacionById(@PathVariable Integer id) {
        try {
            Calificacion calificacion = calificacionService.findById(id);
            return ResponseEntity.ok(calificacion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 Crear una nueva calificación
    @PostMapping
    public ResponseEntity<Calificacion> createCalificacion(@RequestBody Calificacion calificacion) {
        Calificacion saved = calificacionService.save(calificacion);
        return ResponseEntity.status(201).body(saved);
    }
}
