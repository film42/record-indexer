package search.elements;

import shared.communication.common.Fields;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/9/13
 * Time: 9:17 PM
 */
public class FieldButton extends JButton {

    private Fields field;

    public FieldButton(Fields field) {
        super(field.getTitle());

        this.field = field;

        setupView();
    }

    public void setupView() {

    }
}
