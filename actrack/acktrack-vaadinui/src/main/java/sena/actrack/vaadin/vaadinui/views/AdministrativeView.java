package sena.actrack.vaadin.vaadinui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/")
public class AdministrativeView extends VerticalLayout {

    public AdministrativeView() {
        add(new Button("Click me", e -> Notification.show("Hello, Spring+Vaadin user!")));
    }
}
