package client.persistence;

import client.communication.Communicator;
import client.communication.errors.RemoteServerErrorException;
import client.communication.errors.UnauthorizedAccessException;
import client.modules.spellChecker.KnownData;
import shared.communication.common.Fields;
import shared.communication.params.DownloadBatch_Param;
import shared.communication.params.SubmitBatch_Param;
import shared.communication.responses.DownloadBatch_Res;
import shared.models.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 9:09 AM
 */
public class ImageState implements Serializable {

    private String[][] values;
    private String[] columns;

    private Cell selectedCell;
    private transient List<ImageStateListener> listeners;
    private transient List<NewProjectListener> projectListeners;
    private List<Fields> fieldsMetaData;
    private transient Communicator communicator;

    private String username;
    private String password;
    private int imageId = -1;
    private int firstYCoord = 0;
    private int recordHeight = 0;
    private int columnCount = 0;
    private int recordsPerImage = 0;
    private transient BufferedImage image;
    private boolean hasImage;
    private ArrayList<Integer> fieldXValues;
    private ArrayList<Integer> fieldWidthValues;
    private ArrayList<KnownData> knownDataValues;
    private Settings settings;


    public ImageState(Settings settings, Communicator communicator,
                      String username, String password) {
        this.settings = settings;
        this.communicator = communicator;
        this.username = username;
        this.password = password;

        // Init Listeners
        listeners = new ArrayList<>();
        projectListeners = new ArrayList<>();

        loadFromNoSettings();
    }

    public void loadFromNoSettings() {
        values = new String[0][0];

        firstYCoord = 0;
        recordHeight = 0;
        columnCount = 0;
        recordsPerImage = 0;
        hasImage = false;
        image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        knownDataValues = new ArrayList<>();

        columns = new String[0];
        values = new String[0][0];

        Cell initCell = new Cell();
        initCell.setField(0);
        initCell.setRecord(0);

        selectedCell = initCell;
    }

    public void initEvents() {
        setSelectedCell(selectedCell);
    }

    public void addListener(ImageStateListener imageStateListener) {
        listeners.add(imageStateListener);
    }

    public void addNewProjectListener(NewProjectListener npl) {
        projectListeners.add(npl);
    }

    public void setValue(Cell cell, String value) {
            values[cell.getRecord()][cell.getField()] = value;

        for(ImageStateListener isl : listeners) {
            isl.valueChanged(cell, value);
        }
    }

    public String getValue(Cell cell) {
        return values[cell.getRecord()][cell.getField()];
    }

    public void setSelectedCell(Cell cell) {
        this.selectedCell = cell;

        for(ImageStateListener isl : listeners) {
            isl.selectedCellChanged(cell);
        }
    }

    public void save() {
        try{
            // Create path
            File dest = new File("profiles/"+username);
            if(!dest.exists()) dest.mkdirs();

            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("profiles/"+username+"/settings.ser"));
            out.writeObject(settings);
            out.close();

            ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
            out = new ObjectOutputStream(bos) ;
            out.writeObject(settings);
            out.close();


            ObjectOutputStream out1 = new ObjectOutputStream(
                    new FileOutputStream("profiles/"+username+"/state.ser"));
            out1.writeObject(this);
            out1.close();

            ByteArrayOutputStream bos1 = new ByteArrayOutputStream() ;
            out1 = new ObjectOutputStream(bos1) ;
            out1.writeObject(this);
            out1.close();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(image, "png", out); // png is lossless
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = ImageIO.read(in);

        listeners = new ArrayList<>();
        projectListeners = new ArrayList<>();
    }

    private void initWithProject(DownloadBatch_Res downloadBatch) {
        hasImage = true;
        firstYCoord = downloadBatch.getFirstYCoord();
        recordHeight = downloadBatch.getRecordHeight();
        columnCount = downloadBatch.getNumberOfFields();
        recordsPerImage = downloadBatch.getRecordsPerImage();
        imageId = downloadBatch.getBatchId();

        values = new String[recordsPerImage][columnCount];
        columns = new String[columnCount];

        knownDataValues = new ArrayList<>();

        fieldsMetaData = downloadBatch.getFields();

        fieldXValues = new ArrayList<>();
        fieldWidthValues = new ArrayList<>();

        List<Fields> fields = downloadBatch.getFields();
        for(int i = 0; i < fields.size(); i++) {
            // Copy essential values and store the rest
            columns[i] = fields.get(i).getTitle();
            fieldXValues.add(fields.get(i).getxCoord());
            fieldWidthValues.add(fields.get(i).getPixelWidth());

            if(fields.get(i).getKnownData() != null) {
                knownDataValues.add(KnownData.getList(communicator.getServerPath() +
                        fields.get(i).getKnownData()));
            } else {
                knownDataValues.add(new KnownData());
            }

            for(int y = 0; y < recordsPerImage; y++) {
                // Ensure we have no null values.
                values[y][i] = "";
            }
        }

        String path = communicator.getServerPath() + downloadBatch.getImageUrl();
        try {
            image = ImageIO.read(new URL(path));
        } catch (Exception e1) {
            return;
        }

        for(NewProjectListener npl : projectListeners) {
            npl.hasNewProject();
        }

        Cell cell = new Cell();
        cell.setField(0);
        cell.setRecord(0);
        setSelectedCell(cell);
    }

    public void submitProject() {

        SubmitBatch_Param param = new SubmitBatch_Param();
        param.setImageId(imageId);
        param.setUsername(username);
        param.setPassword(password);

        for(String[] vs : values) {
            List<Value> valueList = new ArrayList<>();
            for(String v : vs) {
                Value model = new Value();
                model.setValue(v);
                model.setType("String");
                valueList.add(model);
            }
            param.addRecord(valueList);
        }

        try {
            communicator.submitBatch(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadFromNoSettings();
        save();

        for(NewProjectListener npl : projectListeners) {
            npl.hasNewProject();
        }
    }

    public void downloadProject(int projectId) {
        DownloadBatch_Param param = new DownloadBatch_Param();
        param.setUsername(this.username);
        param.setPassword(this.password);
        param.setProjectId(projectId);

        DownloadBatch_Res downloadBatchRes = null;
        try {
            downloadBatchRes = communicator.downloadBatch(param);
            initWithProject(downloadBatchRes);
        } catch (UnauthorizedAccessException e) {
            e.printStackTrace();
        } catch (RemoteServerErrorException e) {
            e.printStackTrace();
        }

    }

    /* ********************************************
                  Obligatory Fluff
     ******************************************** */

    public Cell getSelectedCell() {
        return this.selectedCell;
    }


    public Settings getSettings() {
        return settings;
    }

    public String[][] getModel() {
        return values;
    }

    public String[] getColumnNames() {
        return columns;
    }

    public ArrayList<Integer> getFieldWidthValues() {
        return fieldWidthValues;
    }

    public ArrayList<Integer> getFieldXValues() {
        return fieldXValues;
    }

    public int getRecordsPerImage() {
        return recordsPerImage;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRecordHeight() {
        return recordHeight;
    }

    public int getFirstYCoord() {
        return firstYCoord;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String[] getColumns() {
        return columns;
    }

    public String[][] getValues() {
        return values;
    }

    public List<Fields> getFieldsMetaData() {
        return fieldsMetaData;
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public BufferedImage getImage() {
        return image;
    }

    public ArrayList<KnownData> getKnownDataValues() {
        return knownDataValues;
    }
}
