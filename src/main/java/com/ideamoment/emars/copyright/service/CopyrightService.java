package com.ideamoment.emars.copyright.service;

import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.utils.Page;

/**
 * Created by yukiwang on 2018/2/24.
 */
public interface CopyrightService {

    Page<Copyright> listCopyrights(Copyright condition, int offset, int pageSize);
}
