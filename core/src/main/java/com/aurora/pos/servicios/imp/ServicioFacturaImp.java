package com.aurora.pos.servicios.imp;

import com.aurora.inventario.entidades.Servicio;
import com.aurora.pos.entidades.*;
import com.aurora.pos.repositorios.EmisorRepositorio;
import com.aurora.pos.repositorios.FacturaRepositorio;
import com.aurora.pos.repositorios.PuntoEmisionRepositorio;
import com.aurora.pos.servicios.ParametrosEmisor;
import com.aurora.pos.servicios.ServiceException;
import com.aurora.pos.servicios.ServicioEmisor;
import com.aurora.pos.servicios.ServicioFacturacion;
import com.aurora.pos.sri.api.ProcesadorDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ServicioFacturaImp implements ServicioFacturacion{

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    @Autowired
    private PuntoEmisionRepositorio puntoEmisionRepositorio;

    @Autowired
    private EmisorRepositorio emisorRepositorio;

    @Autowired
    @Lazy
    @Qualifier("procesadorFacturaProduccion")
    private ProcesadorDocumento<Factura> procesadorDocumentoProduccion;

    @Autowired
    @Lazy
    @Qualifier("procesadorFacturaDesarrollo")
    private ProcesadorDocumento<Factura> procesadorDocumentoDesarollo;


    @Autowired
    private ServicioEmisor servicioEmisor;



    //@Autowired
   // private ParametrosEmisor parametros;
   // @Autowired
   // private EmisorRepositorio emisorRepositorio;




    @Transactional(rollbackFor = Exception.class)
    @Override
    public void guardar(Factura factura) throws ServiceException {

        Emisor emisor = this.servicioEmisor.getEmisor();
        if(emisor == null)
            throw new ServiceException("No hay datos de emisor definido");

            factura.setAmbiente(emisor.getAmbiente().getValue() == 2 ? DocumentoElectronicoImp.Ambiente.PRODUCCION : DocumentoElectronicoImp.Ambiente.PRUEBAS);
            factura.setRucEmisor(emisor.getRucEmisor());
            factura.setObligadoContabilidad(emisor.isObligadoContabilidad());
            //puntoEmisionRepositorio.save(factura.getPuntoEmision());
            facturaRepositorio.save(factura);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Factura autorizarFactura(Factura factura) throws Exception {

      if(factura.getEstadoEmision()!=null
              && factura.getEstadoEmision().getEstadoAutorizacion()!=null
              && factura.getEstadoEmision().getEstadoAutorizacion().equals("AUTORIZADO"))
      {
            throw new ServiceException("Comprobante autorizado no se puede enviar nuevamente");

      }

      if(factura.getNumero() == 0)
      {
          PuntoEmision puntoEmision = factura.getPuntoEmision();
          factura.setPuntoEmision(puntoEmision);
          factura.setNumero(puntoEmision.getSiguienteNumero());
          puntoEmision.setNumeroUtimaFactura(factura.getNumero());
          this.puntoEmisionRepositorio.save(puntoEmision);
      }

        EstadoDocumento estadoDocumento = null;

      Emisor emisor = servicioEmisor.getEmisor();
      if(emisor ==null)
          throw new ServiceException("No hay datos de emisor");

       if(emisor.getAmbiente().getValue() == 1) {
           factura.setNumero(emisor.getSiguienteNumeroDesarrollo());
            estadoDocumento = procesadorDocumentoDesarollo.procesar(factura);
            emisor.setNumeroUltimaFacturaDesarrollo(emisor.getNumeroUltimaFacturaDesarrollo() + 1);
            this.emisorRepositorio.save(emisor);
        }
        else if (emisor.getAmbiente().getValue() == 2)
        {
            estadoDocumento =  procesadorDocumentoProduccion.procesar(factura);
        }


      EstadoEmision estadoEmision = new EstadoEmision();
      if(estadoDocumento!=null && estadoDocumento.getRespuestaRecepcion()!=null)
      {
          RespuestaRecepcion respuestaRecepcion = estadoDocumento.getRespuestaRecepcion();
          estadoEmision.setEstadoRecepcion(respuestaRecepcion.getEstado());
          for (MensajeRespuesta item : respuestaRecepcion.getMensajes())
          {
              MensajeProcesoEmision mensaje = new MensajeProcesoEmision();
              mensaje.setCodigo(item.getCodigo());
              mensaje.setInformacionAdcional(item.getInformacionAdiconal());
              mensaje.setMensaje(item.getMensaje());
              mensaje.setIdentificador(item.getIdentificador());
              estadoEmision.getMensajesRecepcion().add(mensaje);
              mensaje.setEstadoEmision(estadoEmision);
          }


      }

      if (estadoDocumento!=null && estadoDocumento.getRespuestaAutorizacion()!=null)
      {
          RespuestaAutorizacion respuestaAutorizacion = estadoDocumento.getRespuestaAutorizacion();
          estadoEmision.setEstadoAutorizacion(respuestaAutorizacion.getEstado());
          estadoEmision.setFechaAutorizacion(respuestaAutorizacion.getFechaAutorizacion());
          estadoEmision.setXml(respuestaAutorizacion.getComprobante());
          for(MensajeAutorizacion item : respuestaAutorizacion.getMensajes())
          {
              MensajeProcesoEmision mensaje = new MensajeProcesoEmision();
              mensaje.setCodigo(item.getIdentificador());
              mensaje.setInformacionAdcional(item.getInformacionAdicional());
              mensaje.setMensaje(item.getMensaje());
              mensaje.setIdentificador(item.getIdentificador());
              estadoEmision.getMensajesAutorizacion().add(mensaje);
              mensaje.setEstadoEmision(estadoEmision);
          }
      }
      factura.setEstadoEmision(estadoEmision);
      estadoEmision.setDocumentoElectronico(factura);

      Factura f = this.facturaRepositorio.save(factura);
      return f;

    }

/*
    private Emisor getEmisor()
    {
        List<Emisor> emisors = this.emisorRepositorio.findAll();
        if(emisors.size()== 0)
            return null;
        return emisors.get(0);
    }*/
}
