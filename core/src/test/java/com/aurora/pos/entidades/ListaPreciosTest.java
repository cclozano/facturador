package com.aurora.pos.entidades;

import com.aurora.inventario.entidades.Producto;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ListaPreciosTest  {


    @Test
    public void testSetDetalles() throws Exception {

        ListaPrecios listaPrecios = new ListaPrecios();

        Producto producto = new Producto();
        Producto producto2 = new Producto();

        DetalleListaPrecios detalle1 = new DetalleListaPrecios();
        detalle1.setItem(producto);

        DetalleListaPrecios detalle2 = new DetalleListaPrecios();
        detalle2.setItem(producto2);

        List<DetalleListaPrecios> detalles = new ArrayList<>();
        detalles.add(detalle1);
        detalles.add(detalle2);

        listaPrecios.setDetalles(detalles);

        assertEquals(2,listaPrecios.getDetalles().size());
        assertEquals(listaPrecios,listaPrecios.getDetalles().stream().findAny().get().getLista());

    }

    @Test
    public void testGetItems() throws Exception {
        ListaPrecios listaPrecios = new ListaPrecios();

        Producto producto = new Producto();
        Producto producto2 = new Producto();
        Producto producto3 = new Producto();

        DetalleListaPrecios detalle1 = new DetalleListaPrecios();
        detalle1.setItem(producto);

        DetalleListaPrecios detalle2 = new DetalleListaPrecios();
        detalle2.setItem(producto2);

        DetalleListaPrecios detalle3 = new DetalleListaPrecios();
        detalle3.setItem(producto3);

        listaPrecios.agregarDetalle(detalle1);
        listaPrecios.agregarDetalle(detalle2);
        listaPrecios.agregarDetalle(detalle3);

        assertEquals(3,listaPrecios.getItems().size());


    }


    @Test
    public void testAgregarDetalle() throws Exception {

        ListaPrecios listaPrecios = new ListaPrecios();
        Producto producto = new Producto();
        DetalleListaPrecios detalle1 = new DetalleListaPrecios();
        detalle1.setItem(producto);
        listaPrecios.agregarDetalle(detalle1);
        assertEquals(detalle1.getLista(), listaPrecios);
    }

}