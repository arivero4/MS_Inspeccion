package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.infrastructure.adapter.out.persistence.entity.InspeccionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {DetalleInspeccionMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper MapStruct entre {@link inspeccion.domain.model.InspeccionFitosanitaria}
 * y {@link inspeccion.infrastructure.adapter.out.persistence.entity.InspeccionEntity}.
 */
public interface InspeccionMapper {

    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "numeroInspeccion", source = "codigoIca")
    @Mapping(target = "tipoInspeccion", expression = "java(entity.getTipo() != null ? inspeccion.domain.enums.TipoInspeccion.valueOf(entity.getTipo()) : null)")
    InspeccionFitosanitaria entityToDomain(InspeccionEntity entity);

    @Mapping(target = "codigoIca", source = "numeroInspeccion")
    @Mapping(target = "tipo", expression = "java(domain.getTipoInspeccion() != null ? domain.getTipoInspeccion().name() : null)")
    @Mapping(target = "idGrupo", constant = "1L")
    InspeccionEntity domainToEntity(InspeccionFitosanitaria domain);
}
