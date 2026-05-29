package inspeccion.application.port.in;

import inspeccion.domain.model.InspeccionFitosanitaria;

public interface RegistrarInspeccionUseCase {
    InspeccionFitosanitaria registrar(InspeccionFitosanitaria inspeccion);
    InspeccionFitosanitaria actualizar(Long idInspeccion, InspeccionFitosanitaria inspeccion);
    InspeccionFitosanitaria iniciar(Long idInspeccion);
    InspeccionFitosanitaria completar(Long idInspeccion);
    InspeccionFitosanitaria cancelar(Long idInspeccion, String motivo);
    InspeccionFitosanitaria enviarARevision(Long idInspeccion);
}
