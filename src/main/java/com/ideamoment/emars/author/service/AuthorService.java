package com.ideamoment.emars.author.service;

import com.ideamoment.emars.model.Author;
import com.ideamoment.emars.utils.Page;

/**
 * Created by yukiwang on 2018/2/22.
 */
public interface AuthorService {

    Page<Author> listAuthors(String key, int offset, int pageSize);

    Author findAuthor(long id);

    Author createAuthor(String name, String desc, String pseudonym);

    int updateAuthor(long id, String name, String desc, String pseudonym);

    int deleteAuthor(long id);

    void batchDeleteAuthors(String[] idArray);

    int famousAuthor(long id);

    int unfamousAuthor(long id);
}
