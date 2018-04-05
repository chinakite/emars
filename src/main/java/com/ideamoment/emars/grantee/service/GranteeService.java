package com.ideamoment.emars.grantee.service;

import com.ideamoment.emars.model.Grantee;
import com.ideamoment.emars.utils.Page;

public interface GranteeService {
    Page<Grantee> listGrantees(int offset, int pageSize);

    String createGrantee(String name, String desc);

    String deleteGrantee(long id);

    Grantee findGrantee(long id);

    String modifyGrantee(long id, String name, String desc);
}
