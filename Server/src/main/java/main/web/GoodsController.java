package main.web;

import main.entity.Goods;
import main.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/wholesaledb/goods")
public class GoodsController {
    private GoodsService goodsService;

    @GetMapping("/goods")
    public ResponseEntity<List<Goods>> getAllTGoods() {
        List<Goods> list = goodsService.listGoods();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/getGood/{id}")
    public ResponseEntity<Goods> getGood(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity(goodsService.findGood(id), HttpStatus.OK);
        } catch (InvalidParameterException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good not found");
        }
    }

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
}
