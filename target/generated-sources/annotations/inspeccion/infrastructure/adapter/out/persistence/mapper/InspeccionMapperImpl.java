package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.enums.TipoInspeccion;
import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.infrastructure.adapter.out.persistence.entity.InspeccionEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T03:30:55-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class InspeccionMapperImpl implements InspeccionMapper {

    @Override
    public InspeccionFitosanitaria entityToDomain(InspeccionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        InspeccionFitosanitaria.InspeccionFitosanitariaBuilder inspeccionFitosanitaria = InspeccionFitosanitaria.builder();

        inspeccionFitosanitaria.idInspeccion( entity.getIdInspeccion() );
        inspeccionFitosanitaria.numeroInspeccion( entity.getNumeroInspeccion() );
        inspeccionFitosanitaria.fechaInspeccion( entity.getFechaInspeccion() );
        if ( entity.getTipoInspeccion() != null ) {
            inspeccionFitosanitaria.tipoInspeccion( Enum.valueOf( TipoInspeccion.class, entity.getTipoInspeccion() ) );
        }
        if ( entity.getEstado() != null ) {
            inspeccionFitosanitaria.estado( Enum.valueOf( EstadoInspeccion.class, entity.getEstado() ) );
        }
        inspeccionFitosanitaria.idLote( entity.getIdLote() );
        inspeccionFitosanitaria.codigoLote( entity.getCodigoLote() );
        inspeccionFitosanitaria.nombreInspector( entity.getNombreInspector() );
        inspeccionFitosanitaria.cedulaInspector( entity.getCedulaInspector() );
        inspeccionFitosanitaria.observaciones( entity.getObservaciones() );
        inspeccionFitosanitaria.fechaCreacion( entity.getFechaCreacion() );
        inspeccionFitosanitaria.fechaActualizacion( entity.getFechaActualizacion() );

        return inspeccionFitosanitaria.build();
    }

    @Override
    public InspeccionEntity domainToEntity(InspeccionFitosanitaria domain) {
        if ( domain == null ) {
            return null;
        }

        InspeccionEntity.InspeccionEntityBuilder inspeccionEntity = InspeccionEntity.builder();

        inspeccionEntity.idInspeccion( domain.getIdInspeccion() );
        inspeccionEntity.numeroInspeccion( domain.getNumeroInspeccion() );
        inspeccionEntity.fechaInspeccion( domain.getFechaInspeccion() );
        if ( domain.getTipoInspeccion() != null ) {
            inspeccionEntity.tipoInspeccion( domain.getTipoInspeccion().name() );
        }
        if ( domain.getEstado() != null ) {
            inspeccionEntity.estado( domain.getEstado().name() );
        }
        inspeccionEntity.idLote( domain.getIdLote() );
        inspeccionEntity.codigoLote( domain.getCodigoLote() );
        inspeccionEntity.nombreInspector( domain.getNombreInspector() );
        inspeccionEntity.cedulaInspector( domain.getCedulaInspector() );
        inspeccionEntity.observaciones( domain.getObservaciones() );
        inspeccionEntity.fechaCreacion( domain.getFechaCreacion() );
        inspeccionEntity.fechaActualizacion( domain.getFechaActualizacion() );

        return inspeccionEntity.build();
    }
}
