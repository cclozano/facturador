package com.aurora.inventario.ui.view;


import com.aurora.inventario.entidades.Servicio;
import com.aurora.inventario.repositorios.ServicioRepositorio;
import com.aurora.inventario.ui.view.crud.ServiciosCrudView;
import com.aurora.pos.entidades.ListaPrecios;
import com.aurora.pos.server.seguridad.UserService;
import com.aurora.seguridad.entidades.Usuario;
import com.vaadin.data.provider.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.viritin.grid.MGrid;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ServicioCrudViewTest {

    @Mock
    private JpaRepository<Servicio,Long> repository;

    @Mock
    private ServicioRepositorio servicioRepositorio;

    @Mock
    private UserService userService;

    @InjectMocks
    ServiciosCrudView serviciosCrudView;

    @Before
    public void init()
    {
        Usuario user = new Usuario();

        when(userService.findByEmail(anyString())).thenReturn(user);
        when(userService.getCurrentUser()).thenReturn(user);

        ArrayList<Servicio> servicios = new ArrayList<>();
        servicios.add(new Servicio());
        when(repository.findAll()).thenReturn(servicios);



        //when(servicioRepositorio.fiter(user.getContribuyente().getId())).thenReturn(servicios);
    }


    @Test
    public void getGridTest()
    {
        ServiciosCrudView serviciosCrudView = new ServiciosCrudView();
        MGrid<Servicio> grid = serviciosCrudView.getGrid();

        assertNotNull(grid);
        assertEquals(3,grid.getColumns().size());
    }


    @Test
    public void getNewTest()
    {
        assertNotNull(serviciosCrudView.getNew());
       // assertNotNull(serviciosCrudView.getNew().getContribuyente());
    }


    @Test
    public void listTest()
    {

        serviciosCrudView.listEntry("xxx");


        assertEquals(0,serviciosCrudView.getGrid().getDataProvider().size(new Query<>()));
    }
}



