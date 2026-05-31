package inspeccion.infrastructure.adapter.in.web.mapper;

import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.in.web.dto.DetallePlagaRequest;
import inspeccion.infrastructure.adapter.in.web.dto.DetallePlagaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
/**
 * Mapper MapStruct entre {@link inspeccion.domain.model.DetallePlaga}
 * y los DTOs de plaga detectada.
 * Mapea: {@code plagaId} del request -> {@code idPlaga} del dominio.
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetallePlagaWebMapper {

    @Mapping(target = "idDetallePlaga", ignore = true)
    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "nivelSeveridad", ignore = true)
    // Mapear plagaId → idPlaga (nombres distintos en request y domain)
    @Mapping(target = "idPlaga", source = "plagaId")
    // Mapear incidencia del request → nivelIncidencia del domain
    @Mapping(target = "nivelIncidencia", source = "incidencia")
    DetallePlaga toDomain(DetallePlagaRequest request);

    @Mapping(target = "requiereAccionInmediata", expression = "java(domain.requiereAccionInmediata())")
    DetallePlagaResponse toResponse(DetallePlaga domain);
}
