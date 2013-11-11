package search.elements;

import search.helpers.dataModels.ProjectContainer;
import shared.communication.common.Fields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/9/13
 * Time: 9:14 PM
 */
public class ProjectGroup extends JPanel {

    private ProjectContainer projectContainer;

    public ProjectGroup(ProjectContainer projectContainer) {
        this.projectContainer = projectContainer;

        setupView();

        //setPreferredSize(new Dimension(100, 40 + (30 * projectContainer.getFieldsList().size())));
    }

    private void setupView() {
        add(new JLabel(projectContainer.getTitle()));
    }

}
