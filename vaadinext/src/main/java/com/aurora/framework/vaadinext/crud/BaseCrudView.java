package com.aurora.framework.vaadinext.crud;

import com.aurora.framework.vaadinext.SimpleBaseView;
import com.aurora.framework.data.BaseEntity;
import com.aurora.framework.data.BaseRepository;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;

import javax.annotation.PostConstruct;

import java.text.SimpleDateFormat;

/**
 * Created by max on 22/08/17.
 */
public abstract class BaseCrudView<E extends BaseEntity> extends SimpleBaseView<E> implements View {


    @Autowired
    protected EventBus.ViewEventBus eventBus;

    @Autowired
    protected EntityForm<E> form;

    @Autowired
    protected JpaRepository<E,Long> repository;

    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");



    protected MTextField filterByName = new MTextField()
            .withPlaceholder("Filtro...");
    protected Button addNew = new MButton(FontAwesome.PLUS, this::add);
    protected Button edit = new MButton(FontAwesome.PENCIL, this::edit);
    protected Button delete = new ConfirmButton(FontAwesome.TRASH,
            "Esta seguro de eliminar el registro?", this::remove);



    public BaseCrudView()
    {
        this.getToolBar().addIzquierda(filterByName);
        this.getToolBar().addIzquierda(addNew);
        this.getToolBar().addIzquierda(edit);
        this.getToolBar().addIzquierda(delete);
        grid.asSingleSelect().addValueChangeListener(e -> adjustActionButtonState());
        filterByName.addValueChangeListener(e -> {
            listEntry(e.getValue());
        });
    }


    @PostConstruct
    private void init()
    {
        this.eventBus.subscribe(this);
    }

    public void add(Button.ClickEvent clickEvent) {
        edit(getNew());
    }

    public void edit(Button.ClickEvent e) {
        edit(grid.asSingleSelect().getValue());
    }

    public void remove() {
        try {
            repository.delete(grid.asSingleSelect().getValue());
            grid.deselectAll();
            listEntities();
        }
        catch (Exception ex)
        {
            Notification.show("Error","Error al eliminar:"+ ex.getMessage(), Notification.Type.HUMANIZED_MESSAGE);
        }


    }

    protected void edit(final E entry) {
        form.setEntity(entry);
        form.openInModalPopup();
        form.getPopup().getContent().setWidth("100%");
    }

    /*
    @EventBusListenerMethod(scope = EventScope.UI)
    public void onPersonModified(PersonModifiedEvent event) {
        listEntities();
        personForm.closePopup();
    }*/


    public void updateView()
    {
        listEntities();
        form.closePopup();
    }


    public abstract E getNew();

    //public listEntities();



    //private void listEntities(String nameFilter) {
        // A dead simple in memory listing would be:
        // list.setRows(repo.findAll());

        // But we want to support filtering, first add the % marks for SQL name query
        //String likeFilter = "%" + nameFilter + "%";
       // grid.setRows(repo.findByNameLikeIgnoreCase(likeFilter));

        // Lazy binding for better optimized connection from the Vaadin Table to
        // Spring Repository. This approach uses less memory and database
        // resources. Use this approach if you expect you'll have lots of data
        // in your table. There are simpler APIs if you don't need sorting.
        //list.setDataProvider(
        //        // entity fetching strategy
        //        (sortOrder, offset, limit) -> {
        //            final List<Person> page = repo.findByNameLikeIgnoreCase(likeFilter,
        //                    new PageRequest(
        //                            offset / limit,
        //                            limit,
        //                            sortOrder.isEmpty() || sortOrder.get(0).getDirection() == SortDirection.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC,
        //                            // fall back to id as "natural order"
        //                            sortOrder.isEmpty() ? "id" : sortOrder.get(0).getSorted()
        //                    )
        //            );
        //            return page.subList(offset % limit, page.size()).stream();
        //        },
        //        // count fetching strategy
        //        () -> (int) repo.countByNameLike(likeFilter)
        //);
       // adjustActionButtonState();

    //}

    public abstract void listEntry(String nameFilter);

    protected void adjustActionButtonState() {
        boolean hasSelection = !grid.getSelectedItems().isEmpty();
        edit.setEnabled(hasSelection);
        delete.setEnabled(hasSelection);
    }


    protected void listEntities() {
        listEntry(filterByName.getValue());
    }

    @EventBusListenerMethod(scope = EventScope.VIEW)
    public void onPersonModified(EntityModifiedEvent<E> event) {
        listEntities();
        form.closePopup();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        listEntities();
    }


}
