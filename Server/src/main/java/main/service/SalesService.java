package main.service;

import main.entity.Goods;
import main.entity.Sales;

import java.util.List;

public interface SalesService {
    List<Sales> listSales();
    Sales findSale(Integer id);
    void addSale(Sales sale);
    Goods findGoodBySale(Integer id);
    List<Goods> listGoodsInSales();
}
