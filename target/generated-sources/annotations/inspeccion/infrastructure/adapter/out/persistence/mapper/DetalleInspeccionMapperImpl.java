package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-26T09:15:39-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
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
