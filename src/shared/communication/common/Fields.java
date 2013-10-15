package shared.communication.common;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:59 PM
 */
public class Fields {

    private int id;
    private int number;
    private String title;
    private String helpUrl;
    private int xCoord;
    private int pixelWidth;

    public Fields(int id, int number,
                  String title, String helpUrl,
                  int xCoord, int pixelWidth) {

        this.id = id;
        this.number = number;
        this.title = title;
        this.helpUrl = helpUrl;
        this.xCoord = xCoord;
        this.pixelWidth = pixelWidth;

    }
}
