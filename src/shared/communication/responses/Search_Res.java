package shared.communication.responses;

import shared.communication.common.Tuple;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:27 PM
 */
public class Search_Res {

    private List<Tuple> tuples;

    public List<Tuple> getSearchResults() {
        return tuples;
    }

}
