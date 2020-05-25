package main.entity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Min(value = 1, message = "No less than 1 item can be sold")
    @Column(name = "good_count")
    private Integer good_count;

    @Column(name = "create_date", nullable = false)
    private Date create_date;

    @ManyToOne(cascade=CascadeType.ALL)
    private Goods good;

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

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getGood_count() {
        return good_count;
    }

    public void setGood_count(Integer good_count) {
        this.good_count = good_count;
    }

    public Goods getGood() {
        return good;
    }

    public void setGood(Goods good) {
        this.good = good;
    }

    public Date getCreate_date() {
        return create_date;
    }
    public void setCreate_date(String d) {
        Date date = new Date();
        this.create_date = date;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", good_id=" + good.getId() +
                ", good_count=" + good_count +
                ", create_date=" + create_date.toString() +
                '}';
    }
}
