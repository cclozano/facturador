package com.aurora.framework.vaadinext;

import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.text.SimpleDateFormat;

/**
 * Created by max on 22/08/17.
 */
public abstract class SimpleBaseView<E> extends MVerticalLayout {
    private ToolBar toolBar;
    protected MGrid<E> grid;

    protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


    public SimpleBaseView()
    {
        this.grid = getGrid();
        this.grid.setWidth("100%");
        this.grid.setHeight("100%");
        add(getToolBar(),grid)
                .expand(grid)
                .withWidth("100%")
                .withHeight("100%");


    }






    public ToolBar getToolBar()
    {
        if(toolBar ==null)
            toolBar = new ToolBar();
        return toolBar;
    }

    public abstract MGrid<E> getGrid();








}
