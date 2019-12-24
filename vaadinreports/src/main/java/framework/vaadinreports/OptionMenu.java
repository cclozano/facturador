package framework.vaadinreports;

import com.vaadin.server.FontAwesome;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by max on 15/06/17.
 */
@Getter @Setter
public class OptionMenu {
    private String id;
    private String nombre;
    private FontAwesome icon;

    public OptionMenu(FontAwesome icon, String id, String nombre) {
        this.icon = icon;
        this.id = id;
        this.nombre = nombre;
    }


}