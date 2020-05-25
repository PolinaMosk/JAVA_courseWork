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
public class Warehouse2ServiceImp implements Warehouse2Service {
    @Autowired
    private Warehouse1Repository W1Repository;
    @Autowired
    private Warehouse2Repository W2Repository;
    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public List<Warehouse2> listW2() {
        return (List<Warehouse2>) W2Repository.findAll();
    }

    @Override
    public List<Goods> listGoodsInW2() {
        List <Goods> goods = new ArrayList<>();
        for (Warehouse2 w: W2Repository.findAll()) {
            for (Goods g: goodsRepository.findAll()) {
                if (w.getGood().getId().equals(g.getId())) goods.add(g);
            }
        }
        return goods;
    }
    @Override
    public Goods findGoodByWareId(Integer id){
        Optional<Warehouse2> ware = W2Repository.findById(id);
        if (ware.isPresent()) {
            Optional<Goods> good = goodsRepository.findById(ware.get().getGood().getId());
            if (good.isPresent()) return good.get();
            else throw new InvalidParameterException("No such id");
        } else throw new InvalidParameterException("No such id");
    }

    @Override
    public Warehouse2 findGood(Integer id) {
        Optional<Warehouse2> optionalGood = W2Repository.findById(id);
        if (optionalGood.isPresent()) {
            return optionalGood.get();
        } else {
            throw new InvalidParameterException("Good not found in warehouse2");
        }
    }

    @Override
    public void removeBatch(Integer id) {
        Optional<Warehouse2> removing = W2Repository.findById(id);
        if (removing.isPresent()) {
            Integer id_ = removing.get().getGood().getId();
            boolean flag = false;
            for (Warehouse1 w1: W1Repository.findAll())
                if (w1.getGood().getId().equals(id_)) flag = true;
            if (!flag) {
                goodsRepository.deleteById(id_);
                W2Repository.deleteById(id);
            } else {
                W2Repository.deleteById(id);
            }
        } else throw new InvalidParameterException("No product with such id in warehouse1");
    }

    @Override
    public void removeGood(Integer id) {
        for (Warehouse2 w2: W2Repository.findAll()) {
            if (w2.getGood().getId().equals(id)) {
                for (Warehouse1 w1: W1Repository.findAll())
                    if (w1.getGood().getId().equals(id)) {
                        W2Repository.deleteById(w2.getId());
                        return;
                    }
                goodsRepository.deleteById(id);
                W2Repository.deleteById(w2.getId());
                return;
            }
        }
        throw new InvalidParameterException("No good with such id");
    }

    @Override
    public void addWare(Warehouse2 ware) {
        if (ware.getGood_count() == null || ware.getGood_count() <= 0) throw new InvalidParameterException("Invalid number of goods");
        if (ware.getGood().getPriority() <= 0) throw new InvalidParameterException("Priority must be positive");
        Optional<List<Goods>> optionalGoods = goodsRepository.findByName(ware.getGood().getName());
        if (optionalGoods.isPresent()) {
            for (Warehouse2 w : W2Repository.findAll()) {
                if (ware.getGood().getName().equals(w.getGood().getName()))
                    if (ware.getGood().getPriority() == w.getGood().getPriority()) {
                        w.setGood_count(w.getGood_count() + ware.getGood_count());
                        W2Repository.save(w);
                        return;
                    }
            }
            for (Goods g: optionalGoods.get()) {
                if (g.getPriority() == ware.getGood().getPriority()) {
                    ware.setGood(g);
                    W2Repository.save(ware);
                    return;
                }
            }
            goodsRepository.save(ware.getGood());
            W2Repository.save(ware);
        } else {
            goodsRepository.save(ware.getGood());
            W2Repository.save(ware);
        }
    }
}
