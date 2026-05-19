package flowly.user;

public class Tourist extends Account {

    public Tourist(int id, String username, String pwd, String email) {
        super(id, username, pwd, email);
    }

    public void viewMap() {
    	
    }

    public void calculateDensityAwareRoute() {
        System.out.println("Calcul d'un trajet évitant la foule " + username);
    }
}