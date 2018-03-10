package com.ideamoment.emars.make.controller;

import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.MakeContract;
import com.ideamoment.emars.model.MakeContractDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yukiwang on 2018/3/5.
 */
@Controller
@RequestMapping("make")
public class MakePageController {

    @Autowired
    private MakeService makeService;

    @RequestMapping(value = "/taskProductPage", method = RequestMethod.GET)
    public String taskProductPage() {
        return "make/taskProductPage";
    }

    @RequestMapping(value = "/contractProductPage", method = RequestMethod.GET)
    public String contractProductPage() {
        return "make/contractProductPage";
    }

    @RequestMapping(value = "/makeContractDetail", method = RequestMethod.GET)
    public String makeContractDetail(long productId, Model model) {
        MakeContract contract = makeService.findMakeContractByProduct(productId);
        List<MakeContractDoc> docs = makeService.listContractDocs(contract.getId());
        model.addAttribute("contract", contract);
        model.addAttribute("docs", docs);

        return "make/makeContractDetail";
    }

}
