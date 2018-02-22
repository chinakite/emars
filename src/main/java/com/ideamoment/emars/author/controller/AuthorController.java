package com.ideamoment.emars.author.controller;

import com.ideamoment.emars.author.service.AuthorService;
import com.ideamoment.emars.model.Author;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yukiwang on 2018/2/22.
 */
@RestController
@RequestMapping("system")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public DataTableSource<Author> querySubjects(int draw, int start, int length, String key) {
        Page<Author> authors = authorService.listAuthors(key, start, length);
        DataTableSource<Author> dts = convertProductsToDataTableSource(draw, authors);
        return dts;
    }

    @RequestMapping(value = "/author", method = RequestMethod.GET)
    public JsonData findAuthor(long id) {
        Author author = authorService.findAuthor(id);
        return JsonData.success(author);
    }

    @RequestMapping(value = "/createAuthor", method = RequestMethod.POST)
    public JsonData createAuthor(String name, String desc, String pseudonym) {
        Author author = authorService.createAuthor(name, desc, pseudonym);
        return JsonData.SUCCESS;
    }

    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public JsonData updateAuthor(long id, String name, String desc, String pseudonym) {
        int r = authorService.updateAuthor(id, name, desc, pseudonym);
        return JsonData.SUCCESS;
    }

    @RequestMapping(value = "/author", method = RequestMethod.DELETE)
    public JsonData deleteAuthor(long id) {
        int r = authorService.deleteAuthor(id);
        return JsonData.SUCCESS;
    }

    @RequestMapping(value = "/batchDeleteAuthor", method = RequestMethod.DELETE)
    public JsonData batchDeleteAuthor(String ids) {
        String[] idArray = ids.split(",");

        authorService.batchDeleteAuthors(idArray);

        return JsonData.SUCCESS;
    }

    @RequestMapping(value = "/famousAuthor", method = RequestMethod.POST)
    public JsonData famousAuthor(long id) {
        int r = authorService.famousAuthor(id);
        return JsonData.SUCCESS;
    }

    @RequestMapping(value="/unfamousAuthor", method=RequestMethod.POST)
    public JsonData unfamousAuthor(long id) {
        int r = authorService.unfamousAuthor(id);
        return JsonData.SUCCESS;
    }

    private DataTableSource<Author> convertProductsToDataTableSource(int draw, Page<Author> productsPage) {
        DataTableSource<Author> dts = new DataTableSource<Author>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }

}
