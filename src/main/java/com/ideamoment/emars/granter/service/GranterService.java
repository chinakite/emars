package com.ideamoment.emars.granter.service;

import com.ideamoment.emars.model.Granter;
import com.ideamoment.emars.utils.Page;

public interface GranterService {
    Page<Granter> listGranters(int offset, int pageSize);

    String createGranter(String name, String contact, String phone, String desc);

    String deleteGranter(long id);

    Granter findGranter(long id);

    String modifyGranter(long id, String name, String contact, String phone, String desc);
}
