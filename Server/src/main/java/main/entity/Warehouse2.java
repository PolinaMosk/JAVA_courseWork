package main.entity;

import javax.persistence.*;

@Entity
public class Warehouse2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="good_count")
    private Integer good_count;

    @OneToOne(cascade=CascadeType.ALL)
    private Goods good;

    public Warehouse2() {
    }
    public Warehouse2(Goods good, Integer good_count) {
        this.good = good;
        this.good_count = good_count;
    }

    public Goods getGood() {
        return good;
    }

    public void setGood(Goods good) {
        this.good = good;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGood_count() {
        return good_count;
    }

    public void setGood_count(Integer good_count) {
        this.good_count = good_count;
    }

    @Override
    public String toString() {
        return "Warehouse1{" +
                "id=" + id +
                ", good_id=" + good.getId() +
                ", good_count=" + good_count +
                '}';
    }
}
