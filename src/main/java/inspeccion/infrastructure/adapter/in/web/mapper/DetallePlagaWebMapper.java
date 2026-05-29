package inspeccion.infrastructure.adapter.in.web.mapper;

import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.in.web.dto.DetallePlagaRequest;
import inspeccion.infrastructure.adapter.in.web.dto.DetallePlagaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetallePlagaWebMapper {

    @Mapping(target = "idDetallePlaga", ignore = true)
    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "nivelIncidencia", ignore = true)
    @Mapping(target = "nivelSeveridad", ignore = true)
    DetallePlaga toDomain(DetallePlagaRequest request);

    @Mapping(target = "requiereAccionInmediata", expression = "java(domain.requiereAccionInmediata())")
    DetallePlagaResponse toResponse(DetallePlaga domain);
}
