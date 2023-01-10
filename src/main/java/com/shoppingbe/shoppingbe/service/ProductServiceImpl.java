package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.Product;
import com.shoppingbe.shoppingbe.repository.ProductDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAllProducts() throws Exception {
        List<Product> allProducts = new ArrayList<>();
        productDao.findAll().forEach(allProducts::add);
        return allProducts;
    }

    @Override
    public List<String> getCategories() throws Exception {
        List<Product> products = new ArrayList<>();
        productDao.findAll().forEach(products::add);
        List<String> categories =
                products.stream().distinct().map(Product::getCategory).sorted().collect(Collectors.toList());
        return categories;
    }

    @Override
    public List<Product> getAllSlug(String slug) throws Exception {
        String slugDecode = URLDecoder.decode(slug, StandardCharsets.UTF_8.name());
        List<Product> allSlug = productDao.findBySlug(slugDecode);
        return allSlug;
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws Exception {
        List<Product> productList = productDao.findByCategory(category);
        return productList;
    }

    @Override
    public Page<Product> findAllWithSpecification(String name, String category, String price, String rating,
                                                  PageRequest pageRequest) throws UnsupportedEncodingException {

        category = URLDecoder.decode(category, StandardCharsets.UTF_8.name());
        Specification<Product> specification = buildCondition(name, category, price, rating);
        Page<Product> productList = productDao.findAll(specification, pageRequest);
        return productList;
    }

    private Specification<Product> buildCondition(String name, String category, String price, String rating) {
        //重写toPredicate方法
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            List<Predicate> predicateList = new ArrayList<>();

            if (StringUtils.isNotBlank(name) && !"all".equalsIgnoreCase(name)) {
                // 本处我都转为小写，进行模糊匹配
                predicateList.add(cb.like(cb.lower(root.get("name").as(String.class)), "%" + name.toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(category) && !"all".equalsIgnoreCase(category)) {
                predicateList.add(cb.equal(root.get("category").as(String.class), category));
            }

            if (StringUtils.isNotBlank(price) && !"all".equalsIgnoreCase(price)) {
                String[] priceRang = price.split("-");
                predicateList.add(cb.between(root.get("price").as(Integer.class),Integer.parseInt(priceRang[0]),
                        Integer.parseInt(priceRang[1])));
            }

            if (StringUtils.isNotBlank(rating) && !"all".equalsIgnoreCase(rating)) {
                predicateList.add(cb.gt(root.get("rating").as(Double.class), Double.parseDouble(rating)));
            }
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }

}
