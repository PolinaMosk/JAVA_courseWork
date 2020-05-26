package main.service;

import main.entity.Goods;
import main.entity.Sales;
import main.entity.Warehouse1;
import main.entity.Warehouse2;
import main.repository.GoodsRepository;
import main.repository.SalesRepository;
import main.repository.Warehouse1Repository;
import main.repository.Warehouse2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImp implements SalesService {
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private Warehouse1Repository W1Repository;
    @Autowired
    private Warehouse2Repository W2Repository;

    @Override
    public List<Sales> listSales() {
        return (List<Sales>) salesRepository.findAll();
    }

    @Override
    public Sales findSale(Long id) {
        Optional<Sales> optionalApp = salesRepository.findById(id);
        if (optionalApp.isPresent()) {
            return optionalApp.get();
        } else {
            throw new InvalidParameterException("Application not found");
        }
    }

    @Override
    public Goods findGoodBySale(Long id) {
        Optional<Sales> sale = salesRepository.findById(id);
        if (sale.isPresent()) return sale.get().getGood();
        else
        throw new InvalidParameterException("Invalid id");
    }

    @Override
    public List<Goods> listGoodsInSales() {
        List<Goods> goods = new ArrayList<>();
        for (Sales s: salesRepository.findAll()) {
            for (Goods g: goodsRepository.findAll()) {
                if (s.getGood().getId().equals(g.getId())) {
                    goods.add(g);
                }
            }
        }
        return goods;
    }

    @Override
    public void addSale(Sales app) {
        if (app.getGood_count() <= 0) throw new InvalidParameterException("Number of goods must be positive");
        Integer cnt = app.getGood_count();
        Optional<List<Goods>> optionalGood = goodsRepository.findByName(app.getGood().getName());
        if (optionalGood.isPresent()) {
            Integer sum = 0;
            Warehouse1 w1 = null;
            Warehouse2 w2 = null;
            for (Goods g : optionalGood.get()) {
                for (Warehouse1 w: W1Repository.findAll())
                    if (w.getGood().getId().equals(g.getId()))
                        w1 = w;
                for (Warehouse2 w: W2Repository.findAll())
                    if (w.getGood().getId().equals(g.getId()))
                        w2 = w;
                if (w1 != null && w2 != null) {
                    sum += w1.getGood_count() + w2.getGood_count();
                    continue;
                }
                if (w1 != null) {
                    sum += w1.getGood_count();
                    continue;
                }
                if (w2 != null) {
                    sum += w2.getGood_count();
                }
            }
            if (sum < app.getGood_count()) throw new InvalidParameterException ("Not enough goods for this sale");
            optionalGood.get().sort(Comparator.comparing(Goods::getPriority).reversed());
            for (Goods g : optionalGood.get()) {
                for (Warehouse1 w: W1Repository.findAll())
                    if (w.getGood().getId().equals(g.getId()))
                        w1 = w;
                for (Warehouse2 w: W2Repository.findAll())
                    if (w.getGood().getId().equals(g.getId()))
                        w2 = w;
                if (w1 != null && w2 != null) {
                    if (w1.getGood_count() + w2.getGood_count() >= cnt) {
                        if (w1.getGood_count() >= cnt) {
                            w1.setGood_count(w1.getGood_count() - cnt);
                            app.setGood(g);
                            salesRepository.save(app);
                            return;
                        } else if (w2.getGood_count() >= cnt) {
                            w2.setGood_count(w2.getGood_count() - cnt);
                            app.setGood(g);
                            salesRepository.save(app);
                            return;
                        } else if (w1.getGood_count() >= w2.getGood_count()) {
                            Integer diff = cnt - w1.getGood_count();
                            w1.setGood_count(0);
                            w2.setGood_count(w2.getGood_count() - diff);
                            app.setGood(g);
                            salesRepository.save(app);
                            return;
                        } else if (w2.getGood_count() > w1.getGood_count()) {
                            Integer diff = cnt - w2.getGood_count();
                            w2.setGood_count(0);
                            w1.setGood_count(w1.getGood_count() - diff);
                            app.setGood(g);
                            salesRepository.save(app);
                            return;
                        }
                    } else {
                        cnt = cnt - w2.getGood_count() - w1.getGood_count();
                        w2.setGood_count(0);
                        w1.setGood_count(0);
                        if (cnt == 0)  {
                            app.setGood(g);
                            salesRepository.save(app);
                            return;
                        }
                    }
                } else if (w1 != null) {
                    if (w1.getGood_count() >= cnt) {
                        w1.setGood_count(w1.getGood_count() - cnt);
                        app.setGood(g);
                        salesRepository.save(app);
                        return;
                    } else {
                        cnt = cnt - w1.getGood_count();
                        w1.setGood_count(0);
                        if (cnt == 0)  {
                            app.setGood(g);
                            salesRepository.save(app);
                            return;
                        }
                    }
                } else if (w2 != null) {
                    if (w2.getGood_count() >= cnt) {
                        w2.setGood_count(w2.getGood_count() - cnt);
                        app.setGood(g);
                        salesRepository.save(app);
                        return;
                    } else {
                        cnt = cnt - w2.getGood_count();
                        w2.setGood_count(0);
                        if (cnt == 0)  {
                            app.setGood(g);
                            salesRepository.save(app);
                            return;
                        }
                    }
                }
            }
        }
        throw new InvalidParameterException("No good with such name");
    }
}
