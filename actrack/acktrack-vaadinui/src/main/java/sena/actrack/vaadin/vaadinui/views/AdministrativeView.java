package sena.actrack.vaadin.vaadinui.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.services.UserService;

import java.util.Set;

@Route("")
public class AdministrativeView extends VerticalLayout {

    private final UserService userService;

    public AdministrativeView(UserService userService) {
        super();
        this.userService = userService;

        // Have some data
        Set<User> people = userService.findAll();

        Grid<User> grid = new Grid<>();

        grid.setItems(people);
        grid.addColumn(User::getId).setHeader("Id");
        grid.addColumn(User::getGivenName).setHeader("First name");
        grid.addColumn(User::getFamilyName).setHeader("Family Name");
        grid.addColumn(User::getUid).setHeader("User id");

        this.add(grid);
    }

}
