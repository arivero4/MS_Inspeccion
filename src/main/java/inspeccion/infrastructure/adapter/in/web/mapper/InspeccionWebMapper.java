package inspeccion.infrastructure.adapter.in.web.mapper;

import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.infrastructure.adapter.in.web.dto.InspeccionRequest;
import inspeccion.infrastructure.adapter.in.web.dto.InspeccionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {DetalleInspeccionWebMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper MapStruct entre {@link inspeccion.domain.model.InspeccionFitosanitaria}
 * y los DTOs HTTP {@link inspeccion.infrastructure.adapter.in.web.dto.InspeccionRequest}
 * / {@link inspeccion.infrastructure.adapter.in.web.dto.InspeccionResponse}.
 */
public interface InspeccionWebMapper {

    @Mapping(target = "idInspeccion", ignore = true)
    @Mapping(target = "numeroInspeccion", ignore = true)
    @Mapping(target = "codigoLote", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    InspeccionFitosanitaria toDomain(InspeccionRequest request);

    @Mapping(target = "detalles", source = "detalles")
    InspeccionResponse toResponse(InspeccionFitosanitaria domain);
}
