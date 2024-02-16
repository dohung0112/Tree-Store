package com.datamining.controller;

import com.datamining.entity.Account;
import com.datamining.entity.Product;
import com.datamining.service.AccountService;
import com.datamining.service.CategoryService;
import com.datamining.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    AccountService aService;

    @Autowired
    ProductService pService;

    @Autowired
    CategoryService cService;

    @GetMapping("/{cate}")
    public String list(Model model, @PathVariable("cate") Optional<String> cate,
                       @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 6); // 6 product/1 page
        if (cate.isPresent()) {
            String id = cService.findIdByUrlEquals(cate.get());
            Page<Product> list = pService.findByCategoryIdByPage(id, pageable);
            model.addAttribute("items", list);
            model.addAttribute("url", cate.get());
        } else {
            Page<Product> list = pService.findAllByPage(pageable);
            model.addAttribute("items", list);

        }
        List<Product> bestSale = pService.findTop5Seller();

        model.addAttribute("bestSale", bestSale);
        return "user/layout/index";
    }

    @RequestMapping("/product/{url}")
    public String detail(Model model, @PathVariable("url") String url, HttpServletRequest req) {
        Product item = pService.findByUrlEquals(url);
        model.addAttribute("item", item);
        if (req.getRemoteUser() != null) {
            Account us = aService.findByTk(req.getRemoteUser());
            int usId = us.getId();
            model.addAttribute("user_id", usId);
        }
        return "user/product/product-detail";
    }

    @RequestMapping("/product/search")
    public String search(Model model, @RequestParam("keyword") String keyword,
                         @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 6); // 6 product/1 page
        Page<Product> list = pService.findByKeywordPage(keyword, pageable);
        model.addAttribute("items", list);
        model.addAttribute("search", keyword);
        List<Product> bestSale = pService.findTop5Seller();
        model.addAttribute("bestSale", bestSale);
        return "user/layout/index";
    }

//    @GetMapping("/filter/{cate}")
//    public String filter(Model model, @RequestParam("count") String price,
//                          @RequestParam("page") Optional<Integer> page,
//                         @PathVariable("cate") String cate) {
//        Pageable pageable = PageRequest.of(page.orElse(0), 6); // 6 product/1 page
//        String[] arr = price.split(" ");
//        Double price1 = Double.parseDouble(arr[0]);
//        Double price2 = Double.parseDouble(arr[1]);
//
//        Page<Product> list = pService.findByPriceBetweenByCate(price1, price2, cate, pageable);
//
//        model.addAttribute("filtCate", price);
//        model.addAttribute("urlCate",cate);
//        model.addAttribute("items", list);
//
//
//        return "user/layout/index";
//    }

    @GetMapping("/filter")
    public String filters(Model model, @RequestParam("count") String price,
                          @RequestParam("page") Optional<Integer> page,
                            HttpServletRequest req) {
        Pageable pageable = PageRequest.of(page.orElse(0), 6); // 6 product/1 page
        String urlHead = req.getHeader("Referer");
        String url = urlHead.substring(urlHead.lastIndexOf("/") + 1); // lấy url của category
        System.out.println(price);
        System.out.println(url);
        String[] arr = price.split(" ");
        Double price1 = Double.parseDouble(arr[0]);
        Double price2 = Double.parseDouble(arr[1]);
        if (url.equals("home") ) {
            Page<Product> list = pService.findByPriceBetweenPage(price1, price2, pageable);
            model.addAttribute("items", list);
            model.addAttribute("filt", price);

            return "user/layout/index";
        }

        List<Product> list = pService.findByPriceBetweenByCate(price1, price2, url);
//
        model.addAttribute("filtCate", price);
        model.addAttribute("urlCate",url);
        model.addAttribute("items", list);


        return "user/layout/index";
    }
}