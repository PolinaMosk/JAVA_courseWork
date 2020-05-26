package main.service;

import main.entity.Goods;
import main.entity.Warehouse1;

import java.util.List;

public interface Warehouse1Service {
    List<Warehouse1> listW1();
    List<Goods> listGoodsInW1();
    Warehouse1 findGood(Long id);
    void removeBatch(Long id);
    void removeGood(Long id);
    void addWare(Warehouse1 ware);
    Goods findGoodByWareId(Long id);
}
