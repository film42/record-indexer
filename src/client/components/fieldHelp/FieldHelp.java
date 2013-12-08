package client.components.fieldHelp;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 8:20 PM
 */
public class FieldHelp extends JPanel {

    public FieldHelp() {
        setupView();
    }

    private void setupView() {
        String html = "<!DOCTYPE html><html><body><h1>Testing</h1>This should be helpful to you.</body></html>";
        JEditorPane editorPane = new JEditorPane();

        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.setText(html);

//        FOR LATER ;)
//        try {
//            editorPane.setPage("http://google.com/");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(editorPane, BorderLayout.CENTER);
    }
}
