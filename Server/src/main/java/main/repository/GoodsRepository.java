package main.repository;

import main.entity.Goods;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends CrudRepository<Goods, Long> {
    Optional<List<Goods>> findByName(String wareName);
    Optional<List<Goods>> findAllByName(String wareName);
}
