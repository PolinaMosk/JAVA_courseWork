package model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sales {
    private Integer id;
    private Goods good;
    private Integer good_count;
    private Date create_date;

    public Sales() {
    }

    public Sales(Goods good, Integer good_count, String d) {
        this.good = good;
        this.good_count = good_count;
        Date date = new Date();
        this.create_date = date;
    }

    public Integer getId() {
        return id;
    }

    public Goods getGood() {
        return good;
    }

    public void setGood(Goods good) {
        this.good = good;
    }

    public Integer getGood_count() {
        return good_count;
    }

    public void setGood_count(Integer good_count) {
        this.good_count = good_count;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String d) {
        Date date = new Date();
        //try {
        //  SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        // Date create = f.parse(d);
        this.create_date = date;
        //} catch (ParseException e) {
        //     e.printStackTrace();
        //}
    }

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", good=" + good +
                ", good_count=" + good_count +
                ", create_date=" + create_date.toString() +
                '}';
    }
}
