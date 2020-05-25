package model;

public class Warehouse1 {
    private Integer id;
    private Goods good;
    private Integer good_count;

    public Warehouse1(){}
    public Warehouse1(Goods good, Integer good_count) {
        this.good = good;
        this.good_count = good_count;
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

    @Override
    public String toString() {
        return "Warehouse1{" +
                "id=" + id +
                ", good=" + good +
                ", good_count=" + good_count +
                '}';
    }
}
