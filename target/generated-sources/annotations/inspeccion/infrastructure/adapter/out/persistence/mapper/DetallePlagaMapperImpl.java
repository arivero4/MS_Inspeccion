package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-26T09:15:39-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class DetallePlagaMapperImpl implements DetallePlagaMapper {

    @Override
    public DetallePlaga entityToDomain(DetallePlagaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DetallePlaga.DetallePlagaBuilder detallePlaga = DetallePlaga.builder();

        detallePlaga.idDetallePlaga( entity.getIdDetallePlaga() );
        detallePlaga.idDetalle( entity.getIdDetalle() );
        detallePlaga.nombrePlaga( entity.getNombrePlaga() );
        detallePlaga.nombreCientifico( entity.getNombreCientifico() );
        detallePlaga.plantasAfectadas( entity.getPlantasAfectadas() );
        detallePlaga.nivelIncidencia( entity.getNivelIncidencia() );
        detallePlaga.nivelSeveridad( entity.getNivelSeveridad() );
        detallePlaga.areaAfectada( entity.getAreaAfectada() );
        detallePlaga.accionRecomendada( entity.getAccionRecomendada() );
        detallePlaga.fechaDeteccion( entity.getFechaDeteccion() );

        return detallePlaga.build();
    }

    @Override
    public DetallePlagaEntity domainToEntity(DetallePlaga domain) {
        if ( domain == null ) {
            return null;
        }

        DetallePlagaEntity.DetallePlagaEntityBuilder detallePlagaEntity = DetallePlagaEntity.builder();

        detallePlagaEntity.idDetallePlaga( domain.getIdDetallePlaga() );
        detallePlagaEntity.idDetalle( domain.getIdDetalle() );
        detallePlagaEntity.nombrePlaga( domain.getNombrePlaga() );
        detallePlagaEntity.nombreCientifico( domain.getNombreCientifico() );
        detallePlagaEntity.plantasAfectadas( domain.getPlantasAfectadas() );
        detallePlagaEntity.nivelIncidencia( domain.getNivelIncidencia() );
        detallePlagaEntity.nivelSeveridad( domain.getNivelSeveridad() );
        detallePlagaEntity.areaAfectada( domain.getAreaAfectada() );
        detallePlagaEntity.accionRecomendada( domain.getAccionRecomendada() );
        detallePlagaEntity.fechaDeteccion( domain.getFechaDeteccion() );

        return detallePlagaEntity.build();
    }
}
