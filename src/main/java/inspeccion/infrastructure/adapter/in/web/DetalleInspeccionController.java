package inspeccion.infrastructure.adapter.in.web;

import inspeccion.application.port.in.GestionarDetalleUseCase;
import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.in.web.dto.DetalleInspeccionRequest;
import inspeccion.infrastructure.adapter.in.web.dto.DetalleInspeccionResponse;
import inspeccion.infrastructure.adapter.in.web.dto.DetallePlagaRequest;
import inspeccion.infrastructure.adapter.in.web.dto.DetallePlagaResponse;
import inspeccion.infrastructure.adapter.in.web.mapper.DetalleInspeccionWebMapper;
import inspeccion.infrastructure.adapter.in.web.mapper.DetallePlagaWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Detalles de Inspección", description = "Gestión de detalles y plagas de inspecciones")
public class DetalleInspeccionController {

    private final GestionarDetalleUseCase gestionarDetalleUseCase;
    private final DetalleInspeccionWebMapper detalleMapper;
    private final DetallePlagaWebMapper plagaMapper;

    @PostMapping("/inspecciones/{idInspeccion}/detalles")
    @Operation(summary = "Agregar detalle a una inspección")
    public ResponseEntity<DetalleInspeccionResponse> agregar(
            @PathVariable Long idInspeccion,
            @Valid @RequestBody DetalleInspeccionRequest request) {
        DetalleInspeccion detalle = detalleMapper.toDomain(request);
        DetalleInspeccion creado = gestionarDetalleUseCase.agregarDetalle(idInspeccion, detalle);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleMapper.toResponse(creado));
    }

    @PutMapping("/detalles/{idDetalle}")
    @Operation(summary = "Actualizar un detalle de inspección")
    public ResponseEntity<DetalleInspeccionResponse> actualizar(
            @PathVariable Long idDetalle,
            @Valid @RequestBody DetalleInspeccionRequest request) {
        DetalleInspeccion detalle = detalleMapper.toDomain(request);
        DetalleInspeccion actualizado = gestionarDetalleUseCase.actualizarDetalle(idDetalle, detalle);
        return ResponseEntity.ok(detalleMapper.toResponse(actualizado));
    }

    @GetMapping("/detalles/{idDetalle}")
    @Operation(summary = "Buscar detalle por ID")
    public ResponseEntity<DetalleInspeccionResponse> buscarPorId(@PathVariable Long idDetalle) {
        return gestionarDetalleUseCase.buscarDetallePorId(idDetalle)
                .map(d -> ResponseEntity.ok(detalleMapper.toResponse(d)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/inspecciones/{idInspeccion}/detalles")
    @Operation(summary = "Listar detalles de una inspección")
    public ResponseEntity<List<DetalleInspeccionResponse>> listarPorInspeccion(@PathVariable Long idInspeccion) {
        List<DetalleInspeccionResponse> lista = gestionarDetalleUseCase.listarDetallesPorInspeccion(idInspeccion)
                .stream().map(detalleMapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/detalles/{idDetalle}")
    @Operation(summary = "Eliminar un detalle de inspección")
    public ResponseEntity<Void> eliminar(@PathVariable Long idDetalle) {
        gestionarDetalleUseCase.eliminarDetalle(idDetalle);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/detalles/{idDetalle}/plagas")
    @Operation(summary = "Agregar plaga a un detalle de inspección")
    public ResponseEntity<DetallePlagaResponse> agregarPlaga(
            @PathVariable Long idDetalle,
            @Valid @RequestBody DetallePlagaRequest request) {
        DetallePlaga plaga = plagaMapper.toDomain(request);
        DetallePlaga creada = gestionarDetalleUseCase.agregarDetallePlaga(idDetalle, plaga);
        return ResponseEntity.status(HttpStatus.CREATED).body(plagaMapper.toResponse(creada));
    }

    @PutMapping("/plagas/{idDetallePlaga}")
    @Operation(summary = "Actualizar detalle de plaga")
    public ResponseEntity<DetallePlagaResponse> actualizarPlaga(
            @PathVariable Long idDetallePlaga,
            @Valid @RequestBody DetallePlagaRequest request) {
        DetallePlaga plaga = plagaMapper.toDomain(request);
        DetallePlaga actualizada = gestionarDetalleUseCase.actualizarDetallePlaga(idDetallePlaga, plaga);
        return ResponseEntity.ok(plagaMapper.toResponse(actualizada));
    }

    @GetMapping("/detalles/{idDetalle}/plagas")
    @Operation(summary = "Listar plagas de un detalle")
    public ResponseEntity<List<DetallePlagaResponse>> listarPlagasPorDetalle(@PathVariable Long idDetalle) {
        List<DetallePlagaResponse> lista = gestionarDetalleUseCase.listarPlagasPorDetalle(idDetalle)
                .stream().map(plagaMapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/plagas/{idDetallePlaga}")
    @Operation(summary = "Eliminar una plaga")
    public ResponseEntity<Void> eliminarPlaga(@PathVariable Long idDetallePlaga) {
        gestionarDetalleUseCase.eliminarDetallePlaga(idDetallePlaga);
        return ResponseEntity.noContent().build();
    }
}
