package com.ideamoment.emars.copyright.controller;

import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.model.CopyrightContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yukiwang on 2018/2/24.
 */
@Controller
@RequestMapping("copyright")
public class CopyrightPageController {

    @Autowired
    private CopyrightService copyrightService;

    @RequestMapping(value = "/copyrightPage", method = RequestMethod.GET)
    public String copyrightPage() {
        return "copyright/copyrightPage";
    }

    @RequestMapping(value = "/copyrightDetail")
    public String copyrightDetail(long id, Model model) {
        CopyrightContract copyright = copyrightService.findCopyright(id);
        model.addAttribute("contract", copyright);

        return "copyright/copyrightDetail";
    }
}
