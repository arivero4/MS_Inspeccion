package inspeccion.infrastructure.adapter.in.web.mapper;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.in.web.dto.DetalleInspeccionRequest;
import inspeccion.infrastructure.adapter.in.web.dto.DetalleInspeccionResponse;
import inspeccion.infrastructure.adapter.in.web.dto.DetallePlagaResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-30T20:17:18-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class DetalleInspeccionWebMapperImpl implements DetalleInspeccionWebMapper {

    @Autowired
    private DetallePlagaWebMapper detallePlagaWebMapper;

    @Override
    public DetalleInspeccion toDomain(DetalleInspeccionRequest request) {
        if ( request == null ) {
            return null;
        }

        DetalleInspeccion.DetalleInspeccionBuilder detalleInspeccion = DetalleInspeccion.builder();

        detalleInspeccion.totalPlantas( request.getTotalPlantas() );
        detalleInspeccion.idLote( request.getIdLote() );
        detalleInspeccion.nombreCultivo( request.getNombreCultivo() );
        detalleInspeccion.areaInspeccionada( request.getAreaInspeccionada() );
        detalleInspeccion.plantasMuestreadas( request.getPlantasMuestreadas() );
        detalleInspeccion.observaciones( request.getObservaciones() );

        return detalleInspeccion.build();
    }

    @Override
    public DetalleInspeccionResponse toResponse(DetalleInspeccion domain) {
        if ( domain == null ) {
            return null;
        }

        DetalleInspeccionResponse.DetalleInspeccionResponseBuilder detalleInspeccionResponse = DetalleInspeccionResponse.builder();

        detalleInspeccionResponse.plagas( detallePlagaListToDetallePlagaResponseList( domain.getPlagas() ) );
        detalleInspeccionResponse.idLote( domain.getIdLote() );
        detalleInspeccionResponse.idDetalle( domain.getIdDetalle() );
        detalleInspeccionResponse.idInspeccion( domain.getIdInspeccion() );
        detalleInspeccionResponse.nombreCultivo( domain.getNombreCultivo() );
        detalleInspeccionResponse.areaInspeccionada( domain.getAreaInspeccionada() );
        detalleInspeccionResponse.totalPlantas( domain.getTotalPlantas() );
        detalleInspeccionResponse.plantasMuestreadas( domain.getPlantasMuestreadas() );
        detalleInspeccionResponse.resultado( domain.getResultado() );
        detalleInspeccionResponse.observaciones( domain.getObservaciones() );
        detalleInspeccionResponse.fechaCreacion( domain.getFechaCreacion() );

        detalleInspeccionResponse.porcentajeMuestreado( domain.calcularPorcentajeMuestreado() );

        return detalleInspeccionResponse.build();
    }

    protected List<DetallePlagaResponse> detallePlagaListToDetallePlagaResponseList(List<DetallePlaga> list) {
        if ( list == null ) {
            return null;
        }

        List<DetallePlagaResponse> list1 = new ArrayList<DetallePlagaResponse>( list.size() );
        for ( DetallePlaga detallePlaga : list ) {
            list1.add( detallePlagaWebMapper.toResponse( detallePlaga ) );
        }

        return list1;
    }
}
