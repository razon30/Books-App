package razon.nctbteachersguide.model;

import io.realm.RealmObject;

/**
 * Created by razon30 on 26-07-17.
 */

public class Book extends RealmObject{

    public Book(){}

    String name;
    String url;
    String writter;
    String size;
    String cls;
    String id;
    String fileName;

    public Book(String name, String url, String writter, String size, String cls, String id, String fileName) {
        this.name = name;
        this.url = url;
        this.writter = writter;
        this.size = size;
        this.cls = cls;
        this.id = id;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWritter() {
        return writter;
    }

    public void setWritter(String writter) {
        this.writter = writter;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
