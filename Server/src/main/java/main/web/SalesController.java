package main.web;

import main.entity.Goods;
import main.entity.Sales;
import main.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/wholesalecomp/sales")
public class SalesController {
    private SalesService salesService;
    @PostMapping(value = "/addSale", consumes = "application/json", produces = "application/json")
    public void addSale(@RequestBody Sales newSale) {
        salesService.addSale(newSale);
    }
    @GetMapping("/sales")
    public ResponseEntity<Sales> getAllSales() {
        List<Sales> list = salesService.listSales();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping("/getGoodBySaleId/{id}")
    public ResponseEntity<Goods> getGoodsBySale(@PathVariable("id") Long id){
        Goods good = salesService.findGoodBySale(id);
        return new ResponseEntity(good, HttpStatus.OK);
    }
    @GetMapping("/listGoodsInSales")
    public ResponseEntity<List<Goods>> listGoodsInSales(){
        List<Goods> goods = salesService.listGoodsInSales();
        return new ResponseEntity(goods, HttpStatus.OK);
    }

    @GetMapping("/getSale/{id}")
    public ResponseEntity<Sales> getSale(@PathVariable("id") Long id) {
          try {
              return new ResponseEntity(salesService.findSale(id), HttpStatus.OK);
          } catch (InvalidParameterException ex){
              throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
          }
    }

    @Autowired
    public void setSalesService(SalesService salesService) {
        this.salesService = salesService;
    }


}
