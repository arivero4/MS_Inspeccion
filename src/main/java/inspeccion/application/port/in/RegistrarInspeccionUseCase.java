package inspeccion.application.port.in;

import inspeccion.domain.model.InspeccionFitosanitaria;
/**
 * Puerto de entrada para el ciclo de vida de inspecciones.
 *
 * <p>Define las operaciones de escritura: registro, actualizacion y
 * todas las transiciones de estado del flujo fitosanitario.
 * Implementado por {@link inspeccion.application.service.InspeccionService}.</p>
 */

public interface RegistrarInspeccionUseCase {
    InspeccionFitosanitaria registrar(InspeccionFitosanitaria inspeccion);
    InspeccionFitosanitaria actualizar(Long idInspeccion, InspeccionFitosanitaria inspeccion);
    InspeccionFitosanitaria iniciar(Long idInspeccion);
    InspeccionFitosanitaria completar(Long idInspeccion);
    InspeccionFitosanitaria cancelar(Long idInspeccion, String motivo);
    InspeccionFitosanitaria enviarARevision(Long idInspeccion);
    InspeccionFitosanitaria aprobar(Long idInspeccion);
    InspeccionFitosanitaria devolverParaCorreccion(Long idInspeccion);
}
