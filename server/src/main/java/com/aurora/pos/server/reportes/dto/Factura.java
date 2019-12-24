package com.aurora.pos.server.reportes.dto;


import com.aurora.pos.entidades.DetalleFactura;
import com.aurora.pos.entidades.DocumentoElectronicoImp;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Getter @Setter
public class Factura {

    private RespuestaAutorizacion respuestaAutorizacion = new RespuestaAutorizacion();

    private InformacionTributaria informacionTributaria = new InformacionTributaria();

    private String numeroContribuyente;

    private String numeroAutorizacion;
    private String llevaContabilidad = "NO";
    private String numeroFactura;
    private String direccionEstablecimiento;
    private String direccionComprador;
    private String claveAcceso="2009201701130941044500110010020000000540000005413";
    private String correoElectronicoNotificacion;
    private String moneda = "DOLAR";
    private Date fechaRecepcion= new Date();
    private Date fechaEmision = new Date();
    private Date fechaAutorizacion = new Date();
    private String identificacionComprador;
    private BigDecimal importeTotal = BigDecimal.ZERO;
    private BigDecimal subtotalX = BigDecimal.ZERO;
    private BigDecimal subtotal0 = BigDecimal.ZERO;
    private BigDecimal subtotalSinIVA = BigDecimal.ZERO;
    private BigDecimal valorIVA = BigDecimal.ZERO;
    private BigDecimal totalSinImpuestos = BigDecimal.ZERO;
    private BigDecimal totalDescuento;
    private BigDecimal propina;
    private String razonSocialComprador;
    private boolean produccion;
    private BigDecimal porcentajeIva;
    private String estado;
    private ArrayList<Detalle> detalles = new ArrayList<>();

    private ArrayList<CampoAdicional> camposAdicionales = new ArrayList<>();

    private ArrayList<FormaPago> pagos  =new ArrayList<>();





    public Factura(com.aurora.pos.entidades.Factura source)
    {
        claveAcceso = source.getClaveAcceso();
        numeroFactura = source.getNumeroCompleto();
        direccionEstablecimiento = source.getPuntoEmision().getEstablecimiento().getDireccion();
        direccionComprador = source.getCliente().getDireccion();
        correoElectronicoNotificacion = source.getCliente().getCorreo();
        fechaEmision = source.getFechaEmision();
        identificacionComprador = source.getCliente().getIdentificacion();
        importeTotal = source.getTotalFactura();
        //subtotalSinIVA = source.getTotalSinImpuestos();
        valorIVA = source.getTotalIva12();
        subtotalX = source.getSubTotalIva12();
        subtotal0 =source.getSubTotalZero();
        produccion = source.getAmbiente() == DocumentoElectronicoImp.Ambiente.PRODUCCION;
        numeroContribuyente = (source.getNumeroContribullenteEspecial() == 0) ? "" :
                String.valueOf(source.getNumeroContribullenteEspecial());
        razonSocialComprador = source.getCliente().getNombre();
        fechaAutorizacion = source.getEstadoEmision() !=null ? source.getEstadoEmision().getFechaAutorizacion() : null;

        for(DetalleFactura detalleFactura : source.getDetalles())
        {
            Detalle detalle = new Detalle();
            detalle.setCantidad(detalleFactura.getCantidad());
            detalle.setPrecioUnitario(detalleFactura.getPrecioUnitario());
            detalle.setCodigoAuxiliar(" ");
            detalle.setDetalleAdicional1(" ");
            detalle.setDetalleAdicional2(" ");
            if(detalleFactura.getDescripcion()!=null&& !detalleFactura.getDescripcion().isEmpty()) {
                detalle.setDescripcion(detalleFactura.getDescripcion());
            }
            else if(detalleFactura.getItem()!=null)
            {
                detalle.setDescripcion(detalleFactura.getItem().getNombreCompleto());
            }
            detalle.setCodigoPrincipal(detalleFactura.getItem()!=null ? detalleFactura.getItem().getCodigo():"");
            detalle.setDescuento(detalleFactura.getDescuento());
            detalle.setPrecioTotalSinImpuestos(detalleFactura.getSubTotal());
            this.detalles.add(detalle);
        }

        CampoAdicional campoAdicional  = new CampoAdicional();
        campoAdicional.setNombre("Correo");
        campoAdicional.setValor(source.getCliente().getCorreo());
        camposAdicionales.add(campoAdicional);

        CampoAdicional campoAdicional2  = new CampoAdicional();
        campoAdicional2.setNombre("Direccion Cliente");
        campoAdicional2.setValor(source.getCliente().getDireccion());
        camposAdicionales.add(campoAdicional2);


        for (com.aurora.pos.entidades.FormaPago fp :   source.getFormaPagos())
        {
            FormaPago formaPago = new FormaPago();
            formaPago.setFormaPagoEnum(fp.getFormaPago());
            formaPago.setTotal(fp.getValor());
            pagos.add(formaPago);
        }


    }











}
