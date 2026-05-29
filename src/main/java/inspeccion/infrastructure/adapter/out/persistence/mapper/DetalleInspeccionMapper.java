package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {DetallePlagaMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetalleInspeccionMapper {

    @Mapping(target = "plagas", ignore = true)
    DetalleInspeccion entityToDomain(DetalleInspeccionEntity entity);

    DetalleInspeccionEntity domainToEntity(DetalleInspeccion domain);
}
