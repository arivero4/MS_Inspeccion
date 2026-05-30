package inspeccion.infrastructure.adapter.out.persistence.mapper;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.infrastructure.adapter.out.persistence.entity.InspeccionEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-30T11:37:15-0500",
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

        inspeccionFitosanitaria.numeroInspeccion( entity.getCodigoIca() );
        inspeccionFitosanitaria.idInspeccion( entity.getIdInspeccion() );
        inspeccionFitosanitaria.fechaInspeccion( entity.getFechaInspeccion() );
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

        inspeccionFitosanitaria.tipoInspeccion( entity.getTipo() != null ? inspeccion.domain.enums.TipoInspeccion.valueOf(entity.getTipo()) : null );

        return inspeccionFitosanitaria.build();
    }

    @Override
    public InspeccionEntity domainToEntity(InspeccionFitosanitaria domain) {
        if ( domain == null ) {
            return null;
        }

        InspeccionEntity.InspeccionEntityBuilder inspeccionEntity = InspeccionEntity.builder();

        inspeccionEntity.codigoIca( domain.getNumeroInspeccion() );
        inspeccionEntity.idInspeccion( domain.getIdInspeccion() );
        inspeccionEntity.fechaInspeccion( domain.getFechaInspeccion() );
        if ( domain.getEstado() != null ) {
            inspeccionEntity.estado( domain.getEstado().name() );
        }
        inspeccionEntity.fechaActualizacion( domain.getFechaActualizacion() );
        inspeccionEntity.observaciones( domain.getObservaciones() );
        inspeccionEntity.numeroInspeccion( domain.getNumeroInspeccion() );
        inspeccionEntity.idLote( domain.getIdLote() );
        inspeccionEntity.codigoLote( domain.getCodigoLote() );
        inspeccionEntity.nombreInspector( domain.getNombreInspector() );
        inspeccionEntity.cedulaInspector( domain.getCedulaInspector() );
        if ( domain.getTipoInspeccion() != null ) {
            inspeccionEntity.tipoInspeccion( domain.getTipoInspeccion().name() );
        }
        inspeccionEntity.fechaCreacion( domain.getFechaCreacion() );

        inspeccionEntity.tipo( domain.getTipoInspeccion() != null ? domain.getTipoInspeccion().name() : null );
        inspeccionEntity.idGrupo( 1L );

        return inspeccionEntity.build();
    }
}
