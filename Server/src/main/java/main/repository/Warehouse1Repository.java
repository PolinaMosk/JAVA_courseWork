package main.repository;

import main.entity.Warehouse1;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface Warehouse1Repository extends CrudRepository<Warehouse1, Integer> {
    Optional<List<Warehouse1>> findAllById(Integer id);
}
