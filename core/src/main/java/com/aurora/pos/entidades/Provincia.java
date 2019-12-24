package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 10/08/17.
 */
@Entity
@Setter @Getter
public class Provincia extends BaseEntity{
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL)
    @PrivateOwned
    private List<Ciudad> ciudades = new ArrayList<Ciudad>();
}
