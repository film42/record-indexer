package client.components.downloadModal;

import client.communication.Communicator;
import client.communication.errors.RemoteServerErrorException;
import client.communication.errors.UnauthorizedAccessException;
import client.persistence.ImageState;
import shared.communication.common.Project_Res;
import shared.communication.params.Projects_Param;
import shared.communication.params.SampleImage_Param;
import shared.communication.responses.Projects_Res;
import shared.communication.responses.SampleImage_Res;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/10/13
 * Time: 2:50 AM
 */
public class DownloadModal extends JDialog {

    //private JDialog dialog;
    //private JFrame modalFrame;
    private ImageState imageState;
    private Communicator communicator;
    private List<Project_Res> projects;
    private JComboBox batchSelect;


    public DownloadModal(ImageState imageState, Communicator communicator) {
        this.imageState = imageState;
        this.communicator = communicator;

        setupView();
    }

    private void setupView() {
        this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        this.setTitle("Download Image");
        this.setSize(350, 100);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());

        // TODO: Make this block the main window

        JLabel label = new JLabel("Project: ");
        this.add(label);

        projects = getProjects().getProjectsList();

        List<String> values = getProjectNames();

        batchSelect = new JComboBox(values.toArray());
        this.add(batchSelect);

        JButton sampleImageButton = new JButton("Sample Image?");
        sampleImageButton.addActionListener(getSampleImageListener);
        this.add(sampleImageButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(closeListener);
        this.add(cancelButton);

        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(downloadListener);
        this.add(downloadButton);
    }

    private Projects_Res getProjects() {
        Projects_Param param = new Projects_Param();
        param.setUsername(imageState.getUsername());
        param.setPassword(imageState.getPassword());

        try {
            return communicator.getProjects(param);
        } catch (Exception e) {
            // TODO: Remove
            e.printStackTrace();
        }

        return null;
    }

    private SampleImage_Res getSampleImage(int projectId) {
        SampleImage_Param param = new SampleImage_Param();
        param.setUsername(imageState.getUsername());
        param.setPassword(imageState.getPassword());
        param.setProjectId(projectId);

        try {
            return communicator.getSampleImage(param);
        } catch (Exception e) {

        }
        return null;
    }

    private ActionListener getSampleImageListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String projectTitle = (String)batchSelect.getSelectedItem();
            int projectId = getProjectIdForName(projectTitle);
            SampleImage_Res res = getSampleImage(projectId);

            String fullPath = communicator.getServerPath() + res.getUrl();

            SampleImageModal sampleImageModal = new SampleImageModal(fullPath);
            sampleImageModal.setVisible(true);

        }
    };

    private ActionListener closeListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispatchEvent(new WindowEvent(DownloadModal.this, WindowEvent.WINDOW_CLOSING));
        }
    };

    private ActionListener downloadListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String projectTitle = (String)batchSelect.getSelectedItem();
            int projectId = getProjectIdForName(projectTitle);

            imageState.downloadProject(projectId);

            setVisible(false);
            dispatchEvent(new WindowEvent(DownloadModal.this, WindowEvent.WINDOW_CLOSING));
        }
    };

    private int getProjectIdForName(String title) {

        for(Project_Res project : projects) {

            if(title.equals(project.getTitle())) {
                return project.getId();
            }
        }

        return -1;
    }

    private List<String>  getProjectNames() {
        ArrayList<String> titles = new ArrayList<>();

        for(Project_Res project : projects) {
            titles.add(project.getTitle());
        }

        return titles;
    }
}
