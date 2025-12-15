package cl.condor.calificaciones_api.controller;

import cl.condor.calificaciones_api.model.Calificacion;
import cl.condor.calificaciones_api.service.CalificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(
        name = "Calificaciones",
        description = """
            Controlador del microservicio de Calificaciones.
            Gestiona la creación, consulta por ID y listado de calificaciones.
            """
)
@RestController
@RequestMapping("/api/v1/calificaciones")
public class    CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @Operation(
        summary = "Listar todas las calificaciones",
        description = "Retorna la lista completa de calificaciones registradas.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de calificaciones obtenida exitosamente."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "No hay calificaciones registradas.")
        }
    )
    @GetMapping
    public ResponseEntity<List<Calificacion>> getAllCalificaciones() {
        List<Calificacion> lista = calificacionService.findAll();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(
        summary = "Buscar calificación por ID",
        description = "Devuelve una calificación específica según su identificador.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Calificación encontrada."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Calificación no encontrada.")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> getCalificacionById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(calificacionService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Crear una nueva calificación",
        description = "Registra una nueva calificación en el sistema.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "JSON con los datos de la calificación a registrar.",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"id_usuario\": 1,\n  \"id_ruta\": 2,\n  \"valor\": 4.5,\n  \"comentario\": \"Muy buena ruta\"\n}"
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Calificación creada exitosamente."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes.")
        }
    )
    @PostMapping
    public ResponseEntity<Calificacion> createCalificacion(@RequestBody Calificacion calificacion) {
        Calificacion saved = calificacionService.save(calificacion);
        return ResponseEntity.status(201).body(saved);
    }

    @Operation(
        summary = "Listar calificaciones por ruta",
        description = "Devuelve todas las calificaciones asociadas a una ruta específica.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de calificaciones obtenida exitosamente."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "No se encontraron calificaciones para la ruta especificada."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor.")
        }
    )
    @GetMapping("/ruta/{idRuta}")
    public ResponseEntity<List<Calificacion>> getCalificacionesByIdRuta(@PathVariable Integer idRuta) {
        try {
            List<Calificacion> lista = calificacionService.findByIdRuta(idRuta);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("No se encontraron calificaciones para la ruta especificada")) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Obtener promedio de calificaciones por ruta",
        description = "Devuelve el promedio de todas las calificaciones asociadas a una ruta específica.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Promedio calculado exitosamente.")
        }
    )
    @GetMapping("/ruta/{idRuta}/promedio")
    public ResponseEntity<Double> getPromedioCalificacionesByIdRuta(@PathVariable Integer idRuta) {
        Double promedio = calificacionService.calcularPromedioPorIdRuta(idRuta);
        return ResponseEntity.ok(promedio);
    }

}
