package main.service;

import main.entity.Goods;
import main.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsServiceImp implements GoodsService {
    @Autowired
    private GoodsRepository goodsRepository;
    @Override
    public List<Goods> listGoods() {
        return (List<Goods>) goodsRepository.findAll();
    }

    @Override
    public Goods findGood(Integer id) {
        Optional<Goods> optionalGood = goodsRepository.findById(id);
        if (optionalGood.isPresent()) {
            return optionalGood.get();
        } else {
            throw new InvalidParameterException("Good not found");
        }
    }
}
