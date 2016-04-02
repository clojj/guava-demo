package jwin.pojo;

import java.util.Date;

public class SomeValue {

    private Date date;
    private String str;

    public SomeValue(Date date, String str) {
        this.date = date;
        this.str = str;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
