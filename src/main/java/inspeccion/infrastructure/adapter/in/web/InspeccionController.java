package inspeccion.infrastructure.adapter.in.web;

import inspeccion.application.port.in.ConsultarInspeccionUseCase;
import inspeccion.application.port.in.RegistrarInspeccionUseCase;
import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.infrastructure.adapter.in.web.dto.InspeccionRequest;
import inspeccion.infrastructure.adapter.in.web.dto.InspeccionResponse;
import inspeccion.infrastructure.adapter.in.web.mapper.InspeccionWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Controlador REST que expone los endpoints del ciclo de vida de inspecciones.
 *
 * <p>Base URL: {@code /api/v1/inspecciones}. Incluye:</p>
 * <ul>
 *   <li>CRUD de inspecciones (POST, GET, PUT).</li>
 *   <li>Transiciones de estado: PATCH /iniciar, /completar, /cancelar, /revision, /aprobar, /devolver.</li>
 *   <li>Consultas por estado, lote, inspector y periodo.</li>
 * </ul>
 *
 * <p>Todos los endpoints requieren autenticacion JWT.
 * Las transiciones de aprobacion estan restringidas a rol ADMINISTRADOR.</p>
 */

@Slf4j
@RestController
@RequestMapping("/inspecciones")
@RequiredArgsConstructor
@Tag(name = "Inspecciones", description = "Gestión de inspecciones fitosanitarias")
public class InspeccionController {

    private final RegistrarInspeccionUseCase registrarUseCase;
    private final ConsultarInspeccionUseCase consultarUseCase;
    private final InspeccionWebMapper mapper;

    @PostMapping
    @Operation(summary = "Registrar una nueva inspección fitosanitaria")
    public ResponseEntity<InspeccionResponse> registrar(@Valid @RequestBody InspeccionRequest request) {
        log.info("POST /inspecciones - Registrando inspeccion para lote: {}", request.getIdLote());
        InspeccionFitosanitaria inspeccion = mapper.toDomain(request);
        InspeccionFitosanitaria creada = registrarUseCase.registrar(inspeccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una inspección existente")
    public ResponseEntity<InspeccionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody InspeccionRequest request) {
        InspeccionFitosanitaria inspeccion = mapper.toDomain(request);
        InspeccionFitosanitaria actualizada = registrarUseCase.actualizar(id, inspeccion);
        return ResponseEntity.ok(mapper.toResponse(actualizada));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una inspección por ID")
    public ResponseEntity<InspeccionResponse> buscarPorId(@PathVariable Long id) {
        return consultarUseCase.buscarPorId(id)
                .map(i -> ResponseEntity.ok(mapper.toResponse(i)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numero}")
    @Operation(summary = "Consultar una inspección por número")
    public ResponseEntity<InspeccionResponse> buscarPorNumero(@PathVariable String numero) {
        return consultarUseCase.buscarPorNumero(numero)
                .map(i -> ResponseEntity.ok(mapper.toResponse(i)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todas las inspecciones")
    public ResponseEntity<List<InspeccionResponse>> listarTodas() {
        List<InspeccionResponse> lista = consultarUseCase.listarTodas().stream()
                .map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar inspecciones por estado")
    public ResponseEntity<List<InspeccionResponse>> listarPorEstado(@PathVariable EstadoInspeccion estado) {
        List<InspeccionResponse> lista = consultarUseCase.listarPorEstado(estado).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/lote/{idLote}")
    @Operation(summary = "Listar inspecciones por lote")
    public ResponseEntity<List<InspeccionResponse>> listarPorLote(@PathVariable Long idLote) {
        List<InspeccionResponse> lista = consultarUseCase.listarPorLote(idLote).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/inspector/{cedula}")
    @Operation(summary = "Listar inspecciones por inspector")
    public ResponseEntity<List<InspeccionResponse>> listarPorInspector(@PathVariable String cedula) {
        List<InspeccionResponse> lista = consultarUseCase.listarPorInspector(cedula).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar inspecciones por periodo de fechas")
    public ResponseEntity<List<InspeccionResponse>> listarPorPeriodo(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        List<InspeccionResponse> lista = consultarUseCase.listarPorPeriodo(fechaInicio, fechaFin).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}/iniciar")
    @Operation(summary = "Iniciar una inspección programada")
    public ResponseEntity<InspeccionResponse> iniciar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(registrarUseCase.iniciar(id)));
    }

    @PatchMapping("/{id}/completar")
    @Operation(summary = "Completar una inspección en proceso")
    public ResponseEntity<InspeccionResponse> completar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(registrarUseCase.completar(id)));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar una inspección")
    public ResponseEntity<InspeccionResponse> cancelar(
            @PathVariable Long id,
            @RequestParam(defaultValue = "Cancelada por el usuario") String motivo) {
        return ResponseEntity.ok(mapper.toResponse(registrarUseCase.cancelar(id, motivo)));
    }

    @PatchMapping("/{id}/revision")
    @Operation(summary = "Enviar inspección a revisión")
    public ResponseEntity<InspeccionResponse> enviarARevision(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(registrarUseCase.enviarARevision(id)));
    }

    @PatchMapping("/{id}/aprobar")
    @Operation(summary = "Aprobar inspección revisada (Admin ICA)")
    public ResponseEntity<InspeccionResponse> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(registrarUseCase.aprobar(id)));
    }

    @PatchMapping("/{id}/devolver")
    @Operation(summary = "Devolver inspección para corrección (Admin ICA)")
    public ResponseEntity<InspeccionResponse> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(registrarUseCase.devolverParaCorreccion(id)));
    }
}
