package com.ideamoment.emars.author.service.impl;

import com.ideamoment.emars.author.dao.AuthorMapper;
import com.ideamoment.emars.author.service.AuthorService;
import com.ideamoment.emars.model.Author;
import com.ideamoment.emars.model.User;
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
    public Author createAuthor(String name, String desc, String pseudonym) {
//        UserContext uc = UserContext.getCurrentContext();
//        User user = (User)uc.getContextAttribute(UserContext.SESSION_USER);
//
//        Author existsAuthor = authorMapper.queryAuthor(name, null);
//
//        if(existsAuthor != null) {
//            throw duplicateException(name);
//        }else{
//            Author author = new Author();
//            author.setName(name);
//            author.setPseudonym(pseudonym);
//            author.setDesc(desc);
//            author.setCreateTime(new Date());
//            author.setCreator(user.getId());
//            author.setModifyTime(new Date());
//            author.setModifier(user.getId());
//
//            authorMapper.insertAuthor(author);
//
//            return author;
//        }
        return null;
    }

    @Override
    public int updateAuthor(long id, String name, String desc, String pseudonym) {
//        UserContext uc = UserContext.getCurrentContext();
//        User optUser = (User)uc.getContextAttribute(UserContext.SESSION_USER);
//
//        Author author = authorMapper.findAuthor(id);
//
//        if(author == null) {
//            throw dataNotFoundException(id);
//        }else{
//            Author existsAuthor = authorMapper.queryAuthor(name, id);
//            if(existsAuthor != null) {
//                duplicateException(name);
//            }
//
//            author.setName(name);
//            author.setPseudonym(pseudonym);
//            author.setModifyTime(new Date());
//            author.setModifier(optUser.getId());
//            author.setDesc(desc);
//
//            authorMapper.updateAuthor(author);
//            return 1;
//        }
        return 0;
    }

    @Override
    @Transactional
    public int deleteAuthor(long id) {
        boolean r = authorMapper.existsProductsByAuthor(id);
//        if(r) {
//            throw canNotDeleteException(id);
//        }else{
//            authorMapper.deleteAuthor(id);
//            return 1;
//        }
        return 1;
    }

    @Override
    @Transactional
    public void batchDeleteAuthors(String[] idArray) {
        for(String id : idArray) {
            deleteAuthor(Long.parseLong(id));
        }
    }

    @Override
    @Transactional
    public int famousAuthor(long id) {
        Author author = authorMapper.findAuthor(id);

//        if(author == null) {
//            throw dataNotFoundException(id);
//        }else{
//            author.setFamous(YesOrNo.YES);
//            authorMapper.updateAuthor(author);
//        }

        return 0;
    }

    @Override
    @Transactional
    public int unfamousAuthor(long id) {
//        Author author = authorMapper.findAuthor(id);
//
//        if(author == null) {
//            throw dataNotFoundException(id);
//        }else{
//            author.setFamous(YesOrNo.NO);
//            authorMapper.updateAuthor(author);
//        }

        return 0;
    }
}
