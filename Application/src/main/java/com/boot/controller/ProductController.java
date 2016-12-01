package com.boot.controller;

/**
 * Created by Abdeldjallil on 13/11/2016.
 */

import com.alma.fournisseur.infra.factory.Product;
import com.alma.fournisseur.infra.repository.ProductRepository;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @RequestMapping(value = "products", method = RequestMethod.GET)
    public List<Product> list() {
        return productRepository.findAll();
    }

    @RequestMapping(value = "products", method = RequestMethod.POST)
    public Product create(@RequestBody Product product) {
        return productRepository.saveAndFlush(product);
    }

    @RequestMapping(value = "products/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable long id) {
        return productRepository.findOne(id);
    }

    @RequestMapping(value = "products/{id}", method = RequestMethod.PUT)
    public Product update(@PathVariable long id, @RequestBody Product product) {
        Product existingProduct = productRepository.findOne(id);
        BeanUtils.copyProperties(product, existingProduct);
        return productRepository.saveAndFlush(product);
    }

    @RequestMapping(value = "products/{id}", method = RequestMethod.DELETE)
    public Product delete(@PathVariable long id) {
        Product existingProduct = productRepository.findOne(id);
        productRepository.delete(existingProduct);
        return existingProduct;
    }
    @RequestMapping(value = "/product-totalprice" , method=RequestMethod.GET, produces="application/json")
    public String productrequest(@RequestParam(value="id", required=true) Long id, @RequestParam(value="quantity", required=true) int quantity) {
        JSONObject jsonResponse = new JSONObject();
        Product existingProduct = productRepository.findOne(id);

        jsonResponse.put("total price",quantity*existingProduct.getPrice());




        return jsonResponse.toString();
    }
}
