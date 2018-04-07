package com.ideamoment.emars.grantee.service;

import com.ideamoment.emars.model.Grantee;
import com.ideamoment.emars.utils.Page;

import java.util.List;

public interface GranteeService {
    Page<Grantee> pageGrantees(int offset, int pageSize);

    List<Grantee> listGrantees();

    String createGrantee(String name, String desc);

    String deleteGrantee(long id);

    Grantee findGrantee(long id);

    String modifyGrantee(long id, String name, String desc);
}
