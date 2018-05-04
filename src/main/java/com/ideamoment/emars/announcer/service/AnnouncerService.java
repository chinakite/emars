package com.ideamoment.emars.announcer.service;

import com.ideamoment.emars.model.Announcer;
import com.ideamoment.emars.utils.Page;

import java.util.List;

public interface AnnouncerService {
    Page<Announcer> listAnnouncer(String key, int offset, int pageSize);

    Announcer findAnnouncer(long id);

    Announcer findAnnouncerByName(String name);

    String createAnnouncer(String name, String desc, String pseudonym);

    String updateAnnouncer(long id, String name, String desc, String pseudonym);

    String deleteAnnouncer(long id);

    List<Announcer> listAllAnnouncers();

    String batchDeleteAnnouncers(long[] ids);
}
