package com.aurora.pos.server.ui.view.components;

import com.aurora.inventario.entidades.Item;
import com.aurora.inventario.entidades.Producto;
import com.aurora.inventario.repositorios.ItemRepositorio;
import com.vaadin.data.provider.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ItemsComboTest {

    @Mock
    private ItemRepositorio itemRepositorio;

    @InjectMocks
    private ItemsCombo itemsCombo;

    @Before
    public void init()
    {
        Producto producto1 = new Producto();
        producto1.setId(1l);

        Producto producto2 = new Producto();
        producto2.setId(2l);

        Producto producto3 = new Producto();
        producto3.setId(3l);

        List<Item> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);
        productos.add(producto3);

        when(itemRepositorio.findAll()).thenReturn(productos);
    }




    @Test
    public void removeItems() throws Exception {

        Producto producto1 = new Producto();
        producto1.setId(1l);

        List<Item> items = new ArrayList<>();
        items.add(producto1);

        itemsCombo.removeItems(items);
        int actual = itemsCombo.getDataProvider().size(new Query<>());
        assertEquals(2,actual);

        producto1.setId(10l);
        itemsCombo.removeItems(items);
        actual = itemsCombo.getDataProvider().size(new Query<>());
        assertEquals(2,actual);

    }




}