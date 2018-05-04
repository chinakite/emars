package com.ideamoment.emars.maker.service;

import com.ideamoment.emars.model.Maker;
import com.ideamoment.emars.utils.Page;

import java.util.List;

public interface MakerService {
    Page<Maker> pageMakers(int offset, int pageSize);

    List<Maker> listMakers();

    String createMaker(String name, String contact, String phone, String desc);

    String deleteMaker(long id);

    Maker findMaker(long id);

    String modifyMaker(long id, String name, String contact, String phone, String desc);
}
