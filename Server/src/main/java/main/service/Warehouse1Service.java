package main.service;

import main.entity.Goods;
import main.entity.Warehouse1;

import java.util.List;

public interface Warehouse1Service {
    List<Warehouse1> listW1();
    List<Goods> listGoodsInW1();
    Warehouse1 findGood(Integer id);
    void removeBatch(Integer id);
    void removeGood(Integer id);
    void addWare(Warehouse1 ware);
    Goods findGoodByWareId(Integer id);
}
