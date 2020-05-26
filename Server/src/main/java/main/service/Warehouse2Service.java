package main.service;

import main.entity.Goods;
import main.entity.Warehouse2;

import java.util.List;

public interface Warehouse2Service {
    List<Warehouse2> listW2();
    List<Goods> listGoodsInW2();
    Warehouse2 findGood(Long id);
    void removeBatch(Long id);
    void removeGood(Long id);
    void addWare(Warehouse2 ware);
    Goods findGoodByWareId(Long id);
}
