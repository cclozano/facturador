package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Setter @Getter
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,name = "Type")
public abstract class FormaPago extends BaseEntity {

  @NotNull
  @Column(scale = 2,precision = 10)
  private BigDecimal valor;

  @NotNull
  private TipoFormaPago tipo;
  public int plazo;

  //Tabla 24 ficha tecnica del sri
  public abstract String getCodigoFormaPago();


  public abstract String getFormaPago();



  public enum  TipoFormaPago
  {
      CONTADO,CREDITO
  }
}
