package main.repository;

import main.entity.Warehouse2;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface Warehouse2Repository extends CrudRepository<Warehouse2, Long> {
    Optional<List<Warehouse2>> findAllById(Integer id);
}
