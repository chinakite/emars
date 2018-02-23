package com.ideamoment.emars.author.service;

import com.ideamoment.emars.model.Author;
import com.ideamoment.emars.utils.Page;

/**
 * Created by yukiwang on 2018/2/22.
 */
public interface AuthorService {

    Page<Author> listAuthors(String key, int offset, int pageSize);

    Author findAuthor(long id);

    String createAuthor(String name, String desc, String pseudonym);

    String updateAuthor(long id, String name, String desc, String pseudonym);

    String deleteAuthor(long id);

    String batchDeleteAuthors(long[] idArray);

    String famousAuthor(long id);

    String unfamousAuthor(long id);
}
