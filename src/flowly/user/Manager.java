package flowly.user;

import java.io.File;

public class Manager extends Account {
    private ManagerType type;

    public Manager(int id, String username, String pwd, String email, ManagerType type) {
        super(id, username, pwd, email);
        this.type = type;
    }

    public ManagerType getType() {
        return type;
    }

    public void addSite() {
    	
    }

    public void removeSite() {
    	
    }

    public void moveSite() {
    	
    }

    public void importSites(File file) {
    }

    public void exportMapBinary(String path) {
 
    }
}