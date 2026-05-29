package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T03:30:55-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class DetalleInspeccionMapperImpl implements DetalleInspeccionMapper {

    @Override
    public DetalleInspeccion entityToDomain(DetalleInspeccionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DetalleInspeccion.DetalleInspeccionBuilder detalleInspeccion = DetalleInspeccion.builder();

        detalleInspeccion.idDetalle( entity.getIdDetalle() );
        detalleInspeccion.idInspeccion( entity.getIdInspeccion() );
        detalleInspeccion.nombreCultivo( entity.getNombreCultivo() );
        detalleInspeccion.areaInspeccionada( entity.getAreaInspeccionada() );
        detalleInspeccion.totalPlantas( entity.getTotalPlantas() );
        detalleInspeccion.plantasMuestreadas( entity.getPlantasMuestreadas() );
        detalleInspeccion.resultado( entity.getResultado() );
        detalleInspeccion.observaciones( entity.getObservaciones() );
        detalleInspeccion.fechaCreacion( entity.getFechaCreacion() );

        return detalleInspeccion.build();
    }

    @Override
    public DetalleInspeccionEntity domainToEntity(DetalleInspeccion domain) {
        if ( domain == null ) {
            return null;
        }

        DetalleInspeccionEntity.DetalleInspeccionEntityBuilder detalleInspeccionEntity = DetalleInspeccionEntity.builder();

        detalleInspeccionEntity.idDetalle( domain.getIdDetalle() );
        detalleInspeccionEntity.idInspeccion( domain.getIdInspeccion() );
        detalleInspeccionEntity.nombreCultivo( domain.getNombreCultivo() );
        detalleInspeccionEntity.areaInspeccionada( domain.getAreaInspeccionada() );
        detalleInspeccionEntity.totalPlantas( domain.getTotalPlantas() );
        detalleInspeccionEntity.plantasMuestreadas( domain.getPlantasMuestreadas() );
        detalleInspeccionEntity.resultado( domain.getResultado() );
        detalleInspeccionEntity.observaciones( domain.getObservaciones() );
        detalleInspeccionEntity.fechaCreacion( domain.getFechaCreacion() );

        return detalleInspeccionEntity.build();
    }
}
