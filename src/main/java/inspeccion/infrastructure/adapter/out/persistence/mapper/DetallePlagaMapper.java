package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetallePlagaMapper {

    DetallePlaga entityToDomain(DetallePlagaEntity entity);

    DetallePlagaEntity domainToEntity(DetallePlaga domain);
}
