package com.kodilla.stockpricemonitorwithalert.views;

import com.kodilla.stockpricemonitorwithalert.entity.UserEty;
import com.kodilla.stockpricemonitorwithalert.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Route("/users")
@Component
public class UserView extends VerticalLayout {

    public UserView(@Autowired UserService userService) {
        add(new Button("Users"));

        Grid<UserEty> grid = new Grid<>(UserEty.class);
        grid.setColumns("id", "name", "email", "wallet");
        add(grid);
        setSizeFull();

        List<UserEty> all = userService.findAll();

        grid.setItems(all);
    }
}
