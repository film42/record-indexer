package server;

import org.junit.* ;
import server.db.common.Database;
import server.errors.ServerException;

import static org.junit.Assert.* ;

public class ServerUnitTests {

    public static void main(String[] args) throws ServerException {
        
        String[] testClasses = new String[] {
            "server.db.Common",
            "server.db.accessors.FieldAccessorTest",
            "server.db.accessors.ImageAccessorTest",
            "server.db.accessors.ProjectAccessorTest",
            "server.db.accessors.RecordAccessorTest",
            "server.db.accessors.UserAccessorTest",
            "server.db.accessors.ValueAccessorTest",
        };

        org.junit.runner.JUnitCore.main(testClasses);
    }
    
}

