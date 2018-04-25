package com.ideamoment.emars.author.controller;

import com.ideamoment.emars.author.service.AuthorService;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.Author;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/22.
 */
@RestController
@RequestMapping("system")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public DataTableSource<Author> queryAuthors(int draw, int start, int length, String key) {
        Page<Author> authors = authorService.listAuthors(key, start, length);
        DataTableSource<Author> dts = convertProductsToDataTableSource(draw, authors);
        return dts;
    }

    @RequestMapping(value = "/allAuthors", method = RequestMethod.GET)
    public JsonData<List<Author>> listAllAuthors() {
        List<Author> authors = authorService.listAllAuthors();
        return JsonData.success(authors);
    }

    @RequestMapping(value = "/author", method = RequestMethod.GET)
    public JsonData findAuthor(long id) {
        Author author = authorService.findAuthor(id);
        return JsonData.success(author);
    }

    @RequestMapping(value = "/createAuthor", method = RequestMethod.POST)
    public JsonData<Boolean> createAuthor(String name, String desc, String pseudonym) {
        String result = authorService.createAuthor(name, desc, pseudonym);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public JsonData<Boolean> updateAuthor(long id, String name, String desc, String pseudonym) {
        String result = authorService.updateAuthor(id, name, desc, pseudonym);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/deleteAuthor", method = RequestMethod.POST)
    public JsonData<Boolean> deleteAuthor(long id) {
        String result = authorService.deleteAuthor(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/batchDeleteAuthor", method = RequestMethod.POST)
    public JsonData<Boolean> batchDeleteAuthor(long[] ids) {
        String result =  authorService.batchDeleteAuthors(ids);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/famousAuthor", method = RequestMethod.POST)
    public JsonData<Boolean> famousAuthor(long id) {
        String result = authorService.famousAuthor(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/unfamousAuthor", method=RequestMethod.POST)
    public JsonData<Boolean> unfamousAuthor(long id) {
        String result = authorService.unfamousAuthor(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
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
