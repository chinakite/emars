package com.ideamoment.emars.subject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yukiwang on 2018/2/15.
 */
@Controller
@RequestMapping("system")
public class SubjectController {
    private final Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @RequestMapping(value = "/subjectPage", method = RequestMethod.GET)
    public String subjectPage() {
        return "subject/subjectPage";
    }

}
