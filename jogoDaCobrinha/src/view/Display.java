package view;

import java.awt.Graphics2D;
import java.util.HashMap;

import model.Physical;
import view.factories.DisplayFactory;
import view.interfaces.IDisplayableObject;

/** An object displayable into the screen */
public class Display {
    /* Hashmap linking the display object to their physical reference */
    static HashMap<Physical, Display> displayableMap = new HashMap<>();

    /* Reference of the physical object */
    Physical reference;

    /* Displayable used to draw the object */
    IDisplayableObject displayableObject;

    /* POSITION */
    double x, y;

    /* SIZE */
    int width, height;

    public Display(
            Physical ref,
            IDisplayableObject display,
            int x, int y,
            int width, int height
    ) {
        this.reference = ref;
        this.displayableObject = display;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /** Change the displayable used to display the object */
    public void setDisplayable(IDisplayableObject d) {
        this.displayableObject = d;
    }

    /** Display the object */
    public void display(Graphics2D g) {
        displayableObject.display(g);
    }

    /** Add a display object into the disposable's list object of the game */
    public static void add(Physical p) {
        Display d = DisplayFactory.create(p);
        displayableMap.put(p, d);
    }

    /** Remove a display object from the displayable list object of the game */
    public static void remove(Physical p) {
        displayableMap.remove(p);
    }

    /** Remove all the physical objects */
    public static void clear() {
        displayableMap = new HashMap<>();
    }
}
