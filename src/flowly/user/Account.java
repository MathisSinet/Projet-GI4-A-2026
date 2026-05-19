package flowly.user;

public abstract class Account {
    protected int id;
    protected String username;
    protected String pwd; 
    protected String email;

    public Account(int id, String username, String pwd, String email) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.email = email;
    }

    public boolean login(String username, String pwd) {
        return this.username.equals(username) && this.pwd.equals(pwd);
    }

    public void logout() {
        System.out.println(username + " déconnection");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}