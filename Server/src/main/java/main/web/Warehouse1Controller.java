package main.web;

import main.entity.Goods;
import main.entity.Warehouse1;
import main.repository.Warehouse1Repository;
import main.service.Warehouse1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/wholesaledb/warehouse1")
public class Warehouse1Controller {
    private Warehouse1Service service;
    @PostMapping(value = "/addWare", consumes = "application/json")
    public void addWare(@RequestBody Warehouse1 newWare) {
         service.addWare(newWare);
    }
    @GetMapping("/listWare")
    public ResponseEntity<Warehouse1> getAllWares() {
        List<Warehouse1> list = service.listW1();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/listGoodsInWare")
    public ResponseEntity<Goods> getGoods() {
        List<Goods> list = service.listGoodsInW1();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping("/getGoodByWareId/{id}")
    public ResponseEntity<Goods> getGoodsById(@PathVariable("id") Long id) {
        return new ResponseEntity(service.findGoodByWareId(id), HttpStatus.OK);
    }
    @GetMapping("/getGoodInWare/{id}")
    public ResponseEntity<Warehouse1> getGood(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity(service.findGood(id), HttpStatus.OK);
        } catch (InvalidParameterException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good not found in warehouse1");
        }
    }
    @DeleteMapping("/removeByBatchId/{id}")
    public void removeGoodbyBatchId(@PathVariable("id") Long id){
        service.removeBatch(id);
    }

    @DeleteMapping("/removeByGoodId/{id}")
    public void removeGoodbyId(@PathVariable("id") Long id){
        service.removeGood(id);
    }
    @Autowired
    public void setWareService(Warehouse1Service wareService) {
        this.service = wareService;
    }
}
