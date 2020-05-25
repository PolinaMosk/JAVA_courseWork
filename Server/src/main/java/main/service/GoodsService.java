package main.service;

import main.entity.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> listGoods();
    Goods findGood(Integer id);
}
