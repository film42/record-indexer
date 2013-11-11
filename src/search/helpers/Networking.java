package search.helpers;

import client.communication.Communicator;
import client.communication.errors.RemoteServerErrorException;
import client.communication.errors.UnauthorizedAccessException;
import search.helpers.dataModels.ProjectContainer;
import shared.communication.common.Fields;
import shared.communication.common.Project_Res;
import shared.communication.params.Fields_Param;
import shared.communication.params.Projects_Param;
import shared.communication.params.Search_Param;
import shared.communication.responses.Fields_Res;
import shared.communication.responses.Projects_Res;
import shared.communication.responses.Search_Res;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/9/13
 * Time: 6:40 PM
 */
public class Networking {

    public static int DEFAULT = 1;

    private String serverPath;

    public Networking(String host, int port) {
        serverPath = "http://"+host+":"+port+"/";
        this.communicator = new Communicator(serverPath);
    }

    public Networking(int startCode) {
        if(startCode == 1) {
            serverPath = "http://localhost:39640/";
            this.communicator = new Communicator(serverPath);
        }
    }

    private Communicator communicator;

    public Projects_Res getProjects(String username, String password) {
        Projects_Param projectsParam = new Projects_Param();

        projectsParam.setUsername(username);
        projectsParam.setPassword(password);

        try {
            return communicator.getProjects(projectsParam);
        } catch (UnauthorizedAccessException e) {
            return null;
        } catch (RemoteServerErrorException e) {
            return null;
        }
    }

    public Fields_Res getFields(String username, String password, int projectId) {
        Fields_Param fieldsParam = new Fields_Param();

        fieldsParam.setUsername(username);
        fieldsParam.setPassword(password);
        fieldsParam.setProjectId(projectId);

        try {
            return communicator.getFields(fieldsParam);
        } catch (UnauthorizedAccessException e) {
            return null;
        } catch (RemoteServerErrorException e) {
            return null;
        }
    }

    public List<ProjectContainer> getProjectsWithFields(String username, String password) {
        // Get all project
        Projects_Res projectsRes = getProjects(username, password);
        // Get all fields
        Fields_Res allFieldsRes = getFields(username, password, -1);

        // Do we have valid data?
        if(projectsRes == null || allFieldsRes == null) return null;

        // Create container list
        List<ProjectContainer> projectContainerList = new ArrayList<ProjectContainer>();

        // Map all projects to their fields now
        for(Project_Res projectRes : projectsRes.getProjectsList()) {
            // Create a project container from a response project
            ProjectContainer projectContainer = new ProjectContainer(projectRes);
            // Search through fields
            for(Fields field : allFieldsRes.getFields()) {
                // Does match the project we're on?
                if(field.getProjectId() == projectRes.getId()) {
                    // Add to project container
                    projectContainer.addField(field);
                }
            }

            // Add project container
            projectContainerList.add(projectContainer);
        }

        return projectContainerList;
    }

    public Search_Res search(String username, String password, String fieldIds, String queryWords) {
        Search_Param searchParam = new Search_Param();
        searchParam.setUsername(username);
        searchParam.setPassword(password);
        // Add FieldIds
        for(String fieldId : parseCommaString(fieldIds)) {
            searchParam.addFieldId(Integer.parseInt(fieldId));
        }
        // Add Query Strings
        for(String query : parseCommaString(queryWords)) {
            searchParam.addSearchParam(query);
        }

        try {
            return communicator.search(searchParam);
        } catch (UnauthorizedAccessException e) {
            return null;
        } catch (RemoteServerErrorException e) {
            return null;
        }
    }

    public BufferedImage getImage(String imageUrl) {

        try {
            return ImageIO.read(new URL(serverPath+imageUrl));
        } catch (IOException e) {
            return null;
        }

    }

    // For search
    private String[] parseCommaString(String stringWithCommas) {
        return stringWithCommas.split(",");
    }


}
