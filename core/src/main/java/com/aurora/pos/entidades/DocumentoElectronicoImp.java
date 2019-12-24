package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import com.aurora.pos.servicios.Modulo11;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DocumentoElectronicoImp extends BaseEntity implements DocumentoElectronico{


    private static final Modulo11 MODULO_11 = new Modulo11();

    @Size(min = 13,max = 13)
    @NotNull
    @Column(nullable = false,name = "ruc_emisor")
    private String rucEmisor;
    @Enumerated(value = EnumType.STRING)
    private Ambiente ambiente = Ambiente.PRUEBAS;
    @Column(name = "numero_contribuyente_especial")
    private int numeroContribullenteEspecial = 0;
    @Column(name = "obligado_contabilidad")
    private boolean obligadoContabilidad = false;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_EMISION_ID")
    @PrivateOwned
    private EstadoEmision estadoEmision;


    private boolean anulada = false;

    public String getEstado()
    {
        if(estadoEmision == null)
            return "NO EMITIDA";

        if(anulada)
            return "ANULADO";

        return estadoEmision.getEstado();

    }



    public String getClaveAcceso(){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        StringBuilder sp = new StringBuilder();
        Date fechaEmision = getFechaEmision();
        sp.append(sdf.format(fechaEmision));
        sp.append(getCodigoDocumento());
        sp.append(rucEmisor);
        sp.append(ambiente == Ambiente.PRODUCCION ? "2":"1");
        sp.append(getSerie());
        sp.append(getSecuencial());
        String numero = getSecuencial().substring(1);
        sp.append(numero);
        sp.append(1);
        String x= sp.toString();
        int digito = MODULO_11.obtenerSumaPorDigitos(MODULO_11.invertirCadena(x));
        sp.append(  digito>=10?1     :digito     );
        return sp.toString();
    }


    public abstract Date getFechaEmision();
    public abstract String getCodigoDocumento();
    public abstract String getSerie();
    public abstract String getSecuencial();





    public enum Ambiente
    {
        PRUEBAS(1),
        PRODUCCION(2);


        private int value;
        Ambiente(int value)
        {
            this.value = value;
        }


        public int getValue()
        {
            return this.value;
        }
    }



}
