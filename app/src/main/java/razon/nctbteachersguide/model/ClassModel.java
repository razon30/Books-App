package razon.nctbteachersguide.model;

/**
 * Created by razon30 on 28-07-17.
 */

public class ClassModel {

    String cls;
    String clsIdentifier;

    public ClassModel(String cls, String clsIdentifier) {
        this.cls = cls;
        this.clsIdentifier = clsIdentifier;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getClsIdentifier() {
        return clsIdentifier;
    }

    public void setClsIdentifier(String clsIdentifier) {
        this.clsIdentifier = clsIdentifier;
    }
}
