package pro.edu.model;

import java.util.Date;

public class Students {
    private String id;
    private String name;
    private Date birth_date;
    private Integer classId;

    public Students() {
    }

    public Students(String id, String name, Date birth_date, Integer classId) {
        this.id = id;
        this.name = name;
        this.birth_date = birth_date;
        this.classId = classId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birth_date;
    }

    public void setBirthDate(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}
