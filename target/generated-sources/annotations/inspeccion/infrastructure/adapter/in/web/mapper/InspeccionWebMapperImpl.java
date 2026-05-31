package inspeccion.infrastructure.adapter.in.web.mapper;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.infrastructure.adapter.in.web.dto.DetalleInspeccionResponse;
import inspeccion.infrastructure.adapter.in.web.dto.InspeccionRequest;
import inspeccion.infrastructure.adapter.in.web.dto.InspeccionResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-31T04:46:09-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class InspeccionWebMapperImpl implements InspeccionWebMapper {

    @Autowired
    private DetalleInspeccionWebMapper detalleInspeccionWebMapper;

    @Override
    public InspeccionFitosanitaria toDomain(InspeccionRequest request) {
        if ( request == null ) {
            return null;
        }

        InspeccionFitosanitaria.InspeccionFitosanitariaBuilder inspeccionFitosanitaria = InspeccionFitosanitaria.builder();

        inspeccionFitosanitaria.fechaInspeccion( request.getFechaInspeccion() );
        inspeccionFitosanitaria.tipoInspeccion( request.getTipoInspeccion() );
        inspeccionFitosanitaria.estado( request.getEstado() );
        inspeccionFitosanitaria.idLote( request.getIdLote() );
        inspeccionFitosanitaria.nombreInspector( request.getNombreInspector() );
        inspeccionFitosanitaria.cedulaInspector( request.getCedulaInspector() );
        inspeccionFitosanitaria.observaciones( request.getObservaciones() );

        return inspeccionFitosanitaria.build();
    }

    @Override
    public InspeccionResponse toResponse(InspeccionFitosanitaria domain) {
        if ( domain == null ) {
            return null;
        }

        InspeccionResponse.InspeccionResponseBuilder inspeccionResponse = InspeccionResponse.builder();

        inspeccionResponse.detalles( detalleInspeccionListToDetalleInspeccionResponseList( domain.getDetalles() ) );
        inspeccionResponse.idInspeccion( domain.getIdInspeccion() );
        inspeccionResponse.numeroInspeccion( domain.getNumeroInspeccion() );
        inspeccionResponse.fechaInspeccion( domain.getFechaInspeccion() );
        inspeccionResponse.tipoInspeccion( domain.getTipoInspeccion() );
        inspeccionResponse.estado( domain.getEstado() );
        inspeccionResponse.idLote( domain.getIdLote() );
        inspeccionResponse.codigoLote( domain.getCodigoLote() );
        inspeccionResponse.nombreInspector( domain.getNombreInspector() );
        inspeccionResponse.cedulaInspector( domain.getCedulaInspector() );
        inspeccionResponse.observaciones( domain.getObservaciones() );
        inspeccionResponse.fechaCreacion( domain.getFechaCreacion() );
        inspeccionResponse.fechaActualizacion( domain.getFechaActualizacion() );

        return inspeccionResponse.build();
    }

    protected List<DetalleInspeccionResponse> detalleInspeccionListToDetalleInspeccionResponseList(List<DetalleInspeccion> list) {
        if ( list == null ) {
            return null;
        }

        List<DetalleInspeccionResponse> list1 = new ArrayList<DetalleInspeccionResponse>( list.size() );
        for ( DetalleInspeccion detalleInspeccion : list ) {
            list1.add( detalleInspeccionWebMapper.toResponse( detalleInspeccion ) );
        }

        return list1;
    }
}
