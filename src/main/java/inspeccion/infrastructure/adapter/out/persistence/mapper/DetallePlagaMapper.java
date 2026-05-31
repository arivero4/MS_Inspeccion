package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
/**
 * Mapper MapStruct entre {@link inspeccion.domain.model.DetallePlaga}
 * y {@link inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity}.
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetallePlagaMapper {

    @Mapping(target = "nivelIncidencia", source = "incidencia")
    @Mapping(target = "idPlaga", source = "idPlaga")
    DetallePlaga entityToDomain(DetallePlagaEntity entity);

    @Mapping(target = "incidencia", source = "nivelIncidencia")
    @Mapping(target = "idPlaga", source = "idPlaga")
    DetallePlagaEntity domainToEntity(DetallePlaga domain);
}
