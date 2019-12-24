package com.aurora.inventario.ui.view;

import com.aurora.inventario.entidades.Producto;
import com.aurora.inventario.repositorios.ProductoRepositorio;
import com.aurora.inventario.ui.view.crud.ProductoCrudView;
import com.aurora.pos.server.seguridad.UserService;
import com.vaadin.data.provider.HierarchicalQuery;
import com.vaadin.data.provider.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.viritin.grid.MGrid;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProductoCrudViewTest {

    @Mock
    private JpaRepository<Producto,Long> productoRepositorio;

    @Mock
    private UserService userService;

    @InjectMocks
    ProductoCrudView productoCrudView;


    @Test
    public void getGridTest()
    {
        ProductoCrudView productoCrudView = new ProductoCrudView();
        MGrid<Producto> productoMGrid = productoCrudView.getGrid();

        assertNotNull(productoMGrid);
        assertEquals(3,productoMGrid.getColumns().size());
    }


    @Test
    public void getNewTest()
    {
       // productoCrudView = new ProductoCrudView();
        //assertNotNull(productoCrudView.getNew());
    }


    @Test
    public void listTest()
    {

        //productoCrudView.listEntry("xxx");
        //assertEquals(3,productoCrudView.getGrid().getDataProvider().size(new Query<>()));
    }
}
