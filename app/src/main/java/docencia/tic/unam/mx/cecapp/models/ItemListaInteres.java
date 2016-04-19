package docencia.tic.unam.mx.cecapp.models;

public class ItemListaInteres {
    int id;
    String subject;
    boolean check;

    public ItemListaInteres(int id, String subject, boolean status){
        this.id = id;
        this.subject = subject;
        this.check = status;
    }

    public int getId() {
        return id;
    }
    public String getSubject() {
        return subject;
    }
    public boolean isChecked() {
        return check;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setCheck(boolean check) {
        this.check = check;
    }
}
