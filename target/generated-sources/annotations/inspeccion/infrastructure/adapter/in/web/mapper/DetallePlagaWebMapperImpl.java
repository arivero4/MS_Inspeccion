package inspeccion.infrastructure.adapter.in.web.mapper;

import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.in.web.dto.DetallePlagaRequest;
import inspeccion.infrastructure.adapter.in.web.dto.DetallePlagaResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T21:20:00-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class DetallePlagaWebMapperImpl implements DetallePlagaWebMapper {

    @Override
    public DetallePlaga toDomain(DetallePlagaRequest request) {
        if ( request == null ) {
            return null;
        }

        DetallePlaga.DetallePlagaBuilder detallePlaga = DetallePlaga.builder();

        detallePlaga.plantasAfectadas( request.getPlantasAfectadas() );
        detallePlaga.nombrePlaga( request.getNombrePlaga() );
        detallePlaga.nombreCientifico( request.getNombreCientifico() );
        detallePlaga.areaAfectada( request.getAreaAfectada() );
        detallePlaga.accionRecomendada( request.getAccionRecomendada() );
        detallePlaga.fechaDeteccion( request.getFechaDeteccion() );

        return detallePlaga.build();
    }

    @Override
    public DetallePlagaResponse toResponse(DetallePlaga domain) {
        if ( domain == null ) {
            return null;
        }

        DetallePlagaResponse.DetallePlagaResponseBuilder detallePlagaResponse = DetallePlagaResponse.builder();

        detallePlagaResponse.idDetallePlaga( domain.getIdDetallePlaga() );
        detallePlagaResponse.idDetalle( domain.getIdDetalle() );
        detallePlagaResponse.nombrePlaga( domain.getNombrePlaga() );
        detallePlagaResponse.nombreCientifico( domain.getNombreCientifico() );
        detallePlagaResponse.plantasAfectadas( domain.getPlantasAfectadas() );
        detallePlagaResponse.nivelIncidencia( domain.getNivelIncidencia() );
        detallePlagaResponse.nivelSeveridad( domain.getNivelSeveridad() );
        detallePlagaResponse.areaAfectada( domain.getAreaAfectada() );
        detallePlagaResponse.accionRecomendada( domain.getAccionRecomendada() );
        detallePlagaResponse.fechaDeteccion( domain.getFechaDeteccion() );

        detallePlagaResponse.requiereAccionInmediata( domain.requiereAccionInmediata() );

        return detallePlagaResponse.build();
    }
}
