package com.ideamoment.emars.granter.service;

import com.ideamoment.emars.model.Granter;
import com.ideamoment.emars.utils.Page;

import java.util.List;

public interface GranterService {
    Page<Granter> pageGranters(int offset, int pageSize);

    List<Granter> listGranters();

    String createGranter(String name, String contact, String phone, String desc);

    String deleteGranter(long id);

    Granter findGranter(long id);

    String modifyGranter(long id, String name, String contact, String phone, String desc);
}
