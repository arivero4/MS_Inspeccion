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
    date = "2026-05-26T09:14:49-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
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

        detalleInspeccion.nombreCultivo( request.getNombreCultivo() );
        detalleInspeccion.areaInspeccionada( request.getAreaInspeccionada() );
        detalleInspeccion.totalPlantas( request.getTotalPlantas() );
        detalleInspeccion.plantasMuestreadas( request.getPlantasMuestreadas() );
        detalleInspeccion.resultado( request.getResultado() );
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
