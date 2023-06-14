package app.ui.gui;

import javafx.fxml.Initializable;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class MenuItem {

    private String description;
    private Menu ui;

    public MenuItem(String description,  Menu ui)
    {
        if (StringUtils.isBlank(description))
            throw new IllegalArgumentException("MenuItem description cannot be null or empty.");
        if (Objects.isNull(ui))
            throw new IllegalArgumentException("MenuItem does not support a null UI.");

        this.description = description;
        this.ui = ui;
    }

    public MenuItem(String description, ClientMenuUI ui){
        if (StringUtils.isBlank(description))
            throw new IllegalArgumentException("MenuItem description cannot be null or empty.");
        if (Objects.isNull(ui))
            throw new IllegalArgumentException("MenuItem does not support a null UI.");

        this.description = description;
        this.ui = ui;
    }

    public void run()
    {
    }

    public boolean hasDescription(String description)
    {
        return this.description.equals(description);
    }

    public Menu getUi() {
        return ui;
    }

    public String toString()
    {
        return this.description;
    }
}
