package com.ideamoment.emars.sale.controller;

import com.ideamoment.emars.model.Sale;
import com.ideamoment.emars.sale.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Chinakite on 2018/6/16.
 */
@Controller
@RequestMapping("sale")
public class SalePageController {

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "/salePage", method = RequestMethod.GET)
    public String salePage() {
        return "sale/salePage";
    }

    @RequestMapping(value = "/saleContractDetail/{id}", method = RequestMethod.GET)
    public String makeContractDetail(@PathVariable("id") long id, Model model) {
        Sale sale = saleService.findSaleContract(id);
        model.addAttribute("contract", sale);

        return "sale/saleContractDetail";
    }

}
