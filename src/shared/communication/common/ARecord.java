package shared.communication.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import shared.models.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/1/13
 * Time: 12:10 PM
 */
@XStreamAlias("record")
public class ARecord {

    private List<Value> values = new ArrayList<Value>();

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

}
