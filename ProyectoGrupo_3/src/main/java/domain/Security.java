package domain;

public class Security {
    private String user;
    private String passWord;

    private String rol;

    public Security(String user, String passWord, String rol) {
        this.user = user;
        this.passWord = passWord;
        this.rol = rol;
    }

    public String getUser() {
        return user;
    }


    public String getPassWord() {
        return passWord;
    }


    public void setUser(String user) {
        this.user = user;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Security{" +
                "user='" + user + '\'' +
                ", passWord='" + passWord + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
