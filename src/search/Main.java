package search;

import search.elements.FieldButton;
import search.elements.ProjectGroup;
import search.forms.InputField;
import search.helpers.Networking;
import search.helpers.dataModels.ProjectContainer;
import shared.communication.common.Fields;
import shared.communication.common.Tuple;
import shared.communication.responses.Search_Res;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/9/13
 * Time: 6:13 PM
 */
public class Main extends JFrame {

    private JPanel menuPanel;
    private JPanel sidebarPanel;
    private JPanel mainBody;
    private JPanel searchResults;

    private JSplitPane searchArea;
    private JSplitPane searchForm;

    public Main() {


        this.setTitle("Search Program");
        this.setSize(800, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupMenu();
        setupSidebar();
        setupBody();
        setupSearchResults();

        searchArea = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainBody, new JScrollPane());
        searchArea.setOneTouchExpandable(false);
        searchArea.setEnabled(false);
        searchArea.setDividerLocation(80);
        searchArea.setDividerSize(0);

        searchForm = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebarPanel, searchArea);
        searchForm.setOneTouchExpandable(false);
        searchForm.setDividerLocation(120);
        searchForm.setEnabled(false);
        searchForm.setDividerSize(1);
        searchForm.setPreferredSize(new Dimension(120, 500));


        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, menuPanel, searchForm);
        jSplitPane.setOneTouchExpandable(false);
        jSplitPane.setDividerLocation(40);
        jSplitPane.setDividerSize(1);
        jSplitPane.setEnabled(false);

        this.add(jSplitPane);

        this.setVisible(true);
    }

    private final InputField usernameField = new InputField("Username", 15);
    private final JTextField passwordField = new JTextField("Password", 15);
    private final JTextField hostField = new JTextField("localhost", 7);
    private final JTextField portField = new JTextField("39640", 4);

    private void setupMenu() {
        // Add buttons
        FlowLayout flowLayout = new FlowLayout();
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(flowLayout);
        flowLayout.setAlignment(FlowLayout.LEFT);

        JButton button = new JButton("Get Projects");

        jPanel.add(hostField);
        jPanel.add(portField);
        jPanel.add(usernameField);
        jPanel.add(passwordField);
        jPanel.add(button);
        jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Networking networking = new Networking(
                        hostField.getText(), Integer.parseInt(portField.getText()));

                String username = usernameField.getText();
                String password = passwordField.getText();

                List<ProjectContainer> projectContainerList =
                        networking.getProjectsWithFields(username, password);

                JPanel search = new JPanel();

                if(projectContainerList != null)
                for(ProjectContainer projectContainer : projectContainerList) {
                    search.add(new ProjectGroup(projectContainer));

                    for(final Fields field : projectContainer.getFieldsList()) {
                        FieldButton fieldButton = new FieldButton(field);
                        fieldButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String fieldId = Integer.toString(field.getId());

                                updateFieldsField(fieldId);
                            }
                        });
                        search.add(fieldButton);
                    }
                }

                searchForm.setLeftComponent(search);
                searchForm.setDividerLocation(120);
                searchForm.validate();
                searchForm.repaint();


                String fieldName = "";
                if(projectContainerList != null)
                    fieldName = projectContainerList.get(0).getFieldsList().get(0).getTitle();

                System.out.println(fieldName);
            }
        });

        menuPanel = jPanel;
    }

    private void setupSidebar() {
        final JPanel jPanel = new JPanel();


        jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        sidebarPanel = jPanel;
    }

    private final JTextField fieldsField = new JTextField("",48);

    private void updateFieldsField(String fieldId) {

        String text = fieldsField.getText();

        String[] options = text.split(",");

        if(options.length > 0 && !text.isEmpty())
            text += ",";

        fieldsField.setText(text + fieldId);
    }

    private void setupBody() {
        final JPanel jPanel = new JPanel();

        jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        final JButton searchButton = new JButton("Search");
        final JTextField queryField = new JTextField("",41);

        jPanel.add(new JLabel("Fields: "));
        jPanel.add(fieldsField);
        jPanel.add(new JLabel("Terms: "));
        jPanel.add(queryField);
        jPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel results = new JPanel();
                Networking networking = new Networking(
                        hostField.getText(), Integer.parseInt(portField.getText()));

                searchResults.setVisible(false);

                String username = usernameField.getText();
                String password = passwordField.getText();

                Search_Res searchRes = networking.search(username,password,
                        fieldsField.getText(), queryField.getText());

                if(searchRes != null)
                for(Tuple searchResult : searchRes.getSearchResults()) {
                    String imageUrl = searchResult.getImageUrl();

                    final BufferedImage image = networking.getImage(imageUrl);

                    if(image == null) return;

                    BufferedImage scaledImage = resize(image, 200, 150);

                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

                    imageLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JFrame frame2 = new JFrame();
                            JLabel bigImage = new JLabel(new ImageIcon(image));
                            frame2.add(bigImage);
                            frame2.setSize(bigImage.getPreferredSize());
                            frame2.setVisible(true);
                        }
                    });

                    imageLabel.setPreferredSize(new Dimension(200, 150));
                    results.add(imageLabel);
                }

                JScrollPane scrollPane = new JScrollPane();
                results.setPreferredSize(new Dimension(600, 2000));
                scrollPane.getViewport().add(results);
                searchArea.setRightComponent(scrollPane);
                searchArea.validate();
                searchArea.setDividerLocation(80);
                searchArea.repaint();
            }
        });

        mainBody = jPanel;
    }

    private void setupSearchResults() {
        JPanel jPanel = new JPanel();

        searchResults = jPanel;
    }

    public  BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }

}
