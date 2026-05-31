package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {DetallePlagaMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper MapStruct entre {@link inspeccion.domain.model.DetalleInspeccion}
 * y {@link inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity}.
 */
public interface DetalleInspeccionMapper {

    @Mapping(target = "plagas", ignore = true)
    @Mapping(target = "idLote", source = "idLote")
    DetalleInspeccion entityToDomain(DetalleInspeccionEntity entity);

    @Mapping(target = "idLote", source = "idLote")
    DetalleInspeccionEntity domainToEntity(DetalleInspeccion domain);
}
