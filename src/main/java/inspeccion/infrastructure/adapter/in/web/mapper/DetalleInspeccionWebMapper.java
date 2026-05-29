package inspeccion.infrastructure.adapter.in.web.mapper;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.infrastructure.adapter.in.web.dto.DetalleInspeccionRequest;
import inspeccion.infrastructure.adapter.in.web.dto.DetalleInspeccionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {DetallePlagaWebMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetalleInspeccionWebMapper {

    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "idInspeccion", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "plagas", ignore = true)
    DetalleInspeccion toDomain(DetalleInspeccionRequest request);

    @Mapping(target = "porcentajeMuestreado", expression = "java(domain.calcularPorcentajeMuestreado())")
    @Mapping(target = "plagas", source = "plagas")
    DetalleInspeccionResponse toResponse(DetalleInspeccion domain);
}
