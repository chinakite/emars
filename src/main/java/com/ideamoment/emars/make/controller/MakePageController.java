package com.ideamoment.emars.make.controller;

import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.MakeContract;
import com.ideamoment.emars.model.MakeContractDoc;
import com.ideamoment.emars.model.MakeContractProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/makeContractDetail/{id}", method = RequestMethod.GET)
    public String makeContractDetail(@PathVariable("id") long id, Model model) {
        MakeContract contract = makeService.findMakeContract(id);
        model.addAttribute("contract", contract);

        return "make/makeContractDetail";
    }

}
