package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-30T11:37:15-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class DetallePlagaMapperImpl implements DetallePlagaMapper {

    @Override
    public DetallePlaga entityToDomain(DetallePlagaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DetallePlaga.DetallePlagaBuilder detallePlaga = DetallePlaga.builder();

        detallePlaga.nivelIncidencia( entity.getIncidencia() );
        detallePlaga.idPlaga( entity.getIdPlaga() );
        detallePlaga.idDetallePlaga( entity.getIdDetallePlaga() );
        detallePlaga.idDetalle( entity.getIdDetalle() );
        detallePlaga.plantasAfectadas( entity.getPlantasAfectadas() );
        detallePlaga.nombrePlaga( entity.getNombrePlaga() );
        detallePlaga.nombreCientifico( entity.getNombreCientifico() );
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

        detallePlagaEntity.incidencia( domain.getNivelIncidencia() );
        detallePlagaEntity.idPlaga( domain.getIdPlaga() );
        detallePlagaEntity.idDetallePlaga( domain.getIdDetallePlaga() );
        detallePlagaEntity.idDetalle( domain.getIdDetalle() );
        detallePlagaEntity.plantasAfectadas( domain.getPlantasAfectadas() );
        detallePlagaEntity.nombrePlaga( domain.getNombrePlaga() );
        detallePlagaEntity.nombreCientifico( domain.getNombreCientifico() );
        detallePlagaEntity.nivelIncidencia( domain.getNivelIncidencia() );
        detallePlagaEntity.nivelSeveridad( domain.getNivelSeveridad() );
        detallePlagaEntity.areaAfectada( domain.getAreaAfectada() );
        detallePlagaEntity.accionRecomendada( domain.getAccionRecomendada() );
        detallePlagaEntity.fechaDeteccion( domain.getFechaDeteccion() );

        return detallePlagaEntity.build();
    }
}
