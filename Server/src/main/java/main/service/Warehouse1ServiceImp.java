package main.service;

import main.entity.Goods;
import main.entity.Warehouse1;
import main.entity.Warehouse2;
import main.repository.GoodsRepository;
import main.repository.Warehouse1Repository;
import main.repository.Warehouse2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Warehouse1ServiceImp implements Warehouse1Service {
    @Autowired
    private Warehouse1Repository W1Repository;
    @Autowired
    private Warehouse2Repository W2Repository;
    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public List<Warehouse1> listW1() {
        return (List<Warehouse1>) W1Repository.findAll();
    }

    @Override
    public List<Goods> listGoodsInW1() {
        List <Goods> goods = new ArrayList<>();
        for (Warehouse1 w: W1Repository.findAll()) {
            for (Goods g: goodsRepository.findAll()) {
                if (w.getGood().getId().equals(g.getId())) goods.add(g);
            }
        }
        return goods;
    }

    @Override
    public Goods findGoodByWareId(Integer id){
        Optional<Warehouse1> ware = W1Repository.findById(id);
        if (ware.isPresent()) {
            Optional<Goods> good = goodsRepository.findById(ware.get().getGood().getId());
            if (good.isPresent()) return good.get();
            else throw new InvalidParameterException("No such id");
        } else throw new InvalidParameterException("No such id");
    }

    @Override
    public Warehouse1 findGood(Integer id) {
        Optional<Warehouse1> optionalGood = W1Repository.findById(id);
        if (optionalGood.isPresent()) {
            return optionalGood.get();
        } else {
            throw new InvalidParameterException("Good not found in warehouse1");
        }
    }

    @Override
    public void removeBatch(Integer id) {
        Optional<Warehouse1> removing = W1Repository.findById(id);
        if (removing.isPresent()) {
           W1Repository.deleteById(id);
        } else throw new InvalidParameterException("No product with such id in warehouse1");
    }

    @Override
    public void removeGood(Integer id) {
        for (Warehouse1 w1: W1Repository.findAll()) {
            if (w1.getGood().getId().equals(id)) {
                for (Warehouse2 w2: W2Repository.findAll())
                    if (w2.getGood().getId().equals(id)) {
                        W1Repository.deleteById(w1.getId());
                        return;
                    }
                goodsRepository.deleteById(id);
                W1Repository.deleteById(w1.getId());
                return;
            }
        }
        throw new InvalidParameterException("No good with such id");
    }

    @Override
    public void addWare(Warehouse1 ware) {
        if (ware.getGood_count() == null || ware.getGood_count() <= 0) throw new InvalidParameterException("Invalid number of goods");
        if (ware.getGood().getPriority() <= 0) throw new InvalidParameterException("Priority must be positive");
        Optional<List<Goods>> optionalGoods = goodsRepository.findByName(ware.getGood().getName());
        if (optionalGoods.isPresent()) {
            for (Warehouse1 w : W1Repository.findAll()) {
                if (ware.getGood().getName().equals(w.getGood().getName()))
                    if (ware.getGood().getPriority() == w.getGood().getPriority()) {
                        w.setGood_count(w.getGood_count() + ware.getGood_count());
                        W1Repository.save(w);
                        return;
                    }
            }
            for (Goods g: optionalGoods.get()) {
                if (g.getPriority() == ware.getGood().getPriority()) {
                    ware.setGood(g);
                    W1Repository.save(ware);
                    return;
                }
            }
            goodsRepository.save(ware.getGood());
            W1Repository.save(ware);
        } else {
            goodsRepository.save(ware.getGood());
            W1Repository.save(ware);
        }
    }
}
