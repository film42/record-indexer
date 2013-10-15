package server.db;

import server.db.common.DatabaseAccessor;
import server.db.common.Transaction;
import shared.models.Image;
import shared.models.Project;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:42 PM
 */
public class ImageAccessor extends Image implements DatabaseAccessor {

    /**
     * Make ImageAccessor wrapper around Image model
     *
     * @param image - an Image model
     */
    public ImageAccessor(Image image) {
        super();
    }

    /**
     * Find an Image with id
     *
     * @param id - and Image id
     * @return ImageAccessor(image)
     */
    public static ImageAccessor find(int id) {
        Image image = null;
        return new ImageAccessor(image);
    }

    /**
     * Get an Image model's associated project
     *
     * @return ProjectAccessor(Project) or null
     */
    public ProjectAccessor getProject() {
        return ProjectAccessor.find(super.getProjectId());
    }

    @Override
    public boolean save() {
        try {
            Transaction.save((Image)super.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean destroy() {
        return false;
    }
}
