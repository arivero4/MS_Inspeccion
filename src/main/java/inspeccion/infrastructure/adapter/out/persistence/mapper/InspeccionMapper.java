package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.infrastructure.adapter.out.persistence.entity.InspeccionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {DetalleInspeccionMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InspeccionMapper {

    @Mapping(target = "detalles", ignore = true)
    InspeccionFitosanitaria entityToDomain(InspeccionEntity entity);

    @Mapping(target = "detalles", ignore = true)
    InspeccionEntity domainToEntity(InspeccionFitosanitaria domain);
}
