package com.aurora.pos.server.ui.view.components;

import com.aurora.inventario.entidades.Item;
import com.aurora.inventario.repositorios.ItemRepositorio;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringComponent
@PrototypeScope
public class ItemsCombo extends ComboBox<Item> {


    private ItemRepositorio itemRepositorio;

    @Autowired
    public ItemsCombo(ItemRepositorio itemRepositorio)
    {
        this.itemRepositorio = itemRepositorio;
        this.setWidth("100%");
        this.setItemCaptionGenerator(item -> item.getNombreCompleto());
        List<Item> items = itemRepositorio.findAll();
        this.setItems(items);
    }


    public void removeItems(List<Item> itemsRemove)
    {
        List<Item> listaItems = itemRepositorio.findAll();
        for (Item item : itemsRemove)
        {
            if(item!=null) {
                Optional<Item> optional = listaItems.stream().filter(x -> x.getId() == item.getId()).findAny();
                if (optional.isPresent())
                    listaItems.remove(optional.get());
            }
        }
        setItems(listaItems);
    }





}

