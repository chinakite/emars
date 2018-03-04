package com.ideamoment.emars.author.service.impl;

import com.ideamoment.emars.author.dao.AuthorMapper;
import com.ideamoment.emars.author.service.AuthorService;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.Author;
import com.ideamoment.emars.model.enumeration.YesOrNo;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yukiwang on 2018/2/22.
 */
@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    @Transactional
    public Page<Author> listAuthors(String key, int offset, int pageSize) {
        long count = authorMapper.listAuthorsCount(key);
        int currentPage = offset/pageSize + 1;

        List<Author> authors = authorMapper.listAuthors(key, offset, pageSize);

        Page<Author> result = new Page<Author>();
        result.setCurrentPage(currentPage);
        result.setData(authors);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public Author findAuthor(long id) {
        return authorMapper.findAuthor(id);
    }

    @Override
    @Transactional
    public Author findAuthorByName(String name) {
        Author author = authorMapper.queryAuthor(name, null);
        return author;
    }

    @Override
    @Transactional
    public String createAuthor(String name, String desc, String pseudonym) {
        Author existsAuthor = authorMapper.queryAuthor(name, null);

        if(existsAuthor != null) {
            return ErrorCode.AUTHOR_EXISTS;
        }
        Author author = new Author();
        author.setName(name);
        author.setPseudonym(pseudonym);
        author.setDesc(desc);
        author.setCreateTime(new Date());
        author.setCreator(UserContext.getUserId());

        boolean result = authorMapper.insertAuthor(author);
        return resultString(result);
    }

    @Override
    @Transactional
    public String updateAuthor(long id, String name, String desc, String pseudonym) {
        Author author = authorMapper.findAuthor(id);

        if(author == null) {
            return ErrorCode.AUTHOR_NOT_EXISTS;
        }
        Author existsAuthor = authorMapper.queryAuthor(name, id);
        if(existsAuthor != null) {
            return ErrorCode.AUTHOR_EXISTS;
        }

        author.setName(name);
        author.setPseudonym(pseudonym);
        author.setModifyTime(new Date());
        author.setModifier(UserContext.getUserId());
        author.setDesc(desc);

        boolean result = authorMapper.updateAuthor(author);
        return resultString(result);

    }

    @Override
    @Transactional
    public String deleteAuthor(long id) {
        boolean r = authorMapper.existsProductsByAuthor(id);
        if(r) {
            return ErrorCode.AUTHOR_CANNOT_DELETE;
        }
        boolean result = authorMapper.deleteAuthor(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public String batchDeleteAuthors(long[] idArray) {
        boolean result = authorMapper.batchDeleteAuthors(idArray);
        return resultString(result);
    }

    @Override
    @Transactional
    public String famousAuthor(long id) {
        Author author = authorMapper.findAuthor(id);

        if(author == null) {
            return ErrorCode.AUTHOR_NOT_EXISTS;
        }
        author.setFamous(YesOrNo.YES);
        boolean result = authorMapper.updateAuthor(author);
        return resultString(result);
    }

    @Override
    @Transactional
    public String unfamousAuthor(long id) {
        Author author = authorMapper.findAuthor(id);

        if(author == null) {
            return ErrorCode.AUTHOR_NOT_EXISTS;
        }
        author.setFamous(YesOrNo.NO);
        boolean result = authorMapper.updateAuthor(author);
        return resultString(result);
    }

    private String resultString(boolean result) {
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }
}
