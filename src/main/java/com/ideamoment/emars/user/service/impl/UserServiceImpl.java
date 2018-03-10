package com.ideamoment.emars.user.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.PwdSalt;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.User;
import com.ideamoment.emars.model.enumeration.StatusType;
import com.ideamoment.emars.user.dao.UserMapper;
import com.ideamoment.emars.user.service.UserService;
import com.ideamoment.emars.utils.MD5Utils;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public User login(String account, String password) {
        User user = userMapper.findUser(account, StatusType.ENABLED);
        String md5 = MD5Utils.MD5(password + PwdSalt.USER_PWD_SALT);
        if(user == null || !md5.equals(user.getPassword())) {
            return null;
        }else{
            return user;
        }
    }

    @Override
    @Transactional
    public Page<User> pageUsers(int offset, int pageSize, String searchKey, String searchStatus) {
        long count = userMapper.countUser();
        int currentPage = offset/pageSize + 1;
        if(searchKey != null && searchKey.trim().length() > 0) {
            searchKey = "%" + searchKey + "%";
        }
        List<User> users = userMapper.pageUsers(offset, pageSize, searchKey, searchStatus);

        Page<User> result = new Page<User>();
        result.setCurrentPage(currentPage);
        result.setData(users);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public String addUser(User user) {
        User existsUser = userMapper.queryUser(user.getAccount());
        if(existsUser != null) {
            return ErrorCode.USER_EXISTS;
        }

        String encryptPwd = MD5Utils.MD5(user.getPassword() + PwdSalt.USER_PWD_SALT);
        user.setPassword(encryptPwd);
        user.setStatus(StatusType.ENABLED);
        user.setCreator(UserContext.getUserId());
        user.setCreateTime(new Date());

        boolean result = userMapper.insertUser(user);
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }

    @Override
    @Transactional
    public User loadUser(Long id) {
        User user = userMapper.findUserById(id);
        user.setPassword("");
        return user;
    }

    @Override
    @Transactional
    public String modifyUser(Long id, User user) {
        User dbUser = userMapper.findUserById(id);
        if(dbUser == null) {
            return ErrorCode.USER_NOT_EXISTS;
        }else{
            String newPwd = user.getPassword();
            if(newPwd != null && newPwd.trim().length() > 0) {
                dbUser.setPassword(MD5Utils.MD5(user.getPassword() + PwdSalt.USER_PWD_SALT));
            }
            dbUser.setAccount(user.getAccount());
            dbUser.setEmail(user.getEmail());
            dbUser.setMobile(user.getMobile());
            dbUser.setGender(user.getGender());
            dbUser.setHonorific(user.getHonorific());
            dbUser.setModifier(UserContext.getUserId());
            dbUser.setModifyTime(new Date());

            boolean result = userMapper.updateUser(dbUser);
            if(result) {
                return SuccessCode.SUCCESS;
            }else{
                return ErrorCode.UNKNOWN_ERROR;
            }
        }
    }

    @Override
    @Transactional
    public String deleteUser(Long id) {
        if(id == 1L) {
            return ErrorCode.SUPERADMIN_CANT_OPERATE;
        }

        User dbUser = userMapper.findUserById(id);
        if(dbUser == null) {
            return ErrorCode.USER_NOT_EXISTS;
        }else{
            boolean result = userMapper.deleteUser(id);
            if(result) {
                return SuccessCode.SUCCESS;
            }else{
                return ErrorCode.UNKNOWN_ERROR;
            }
        }
    }

    @Override
    @Transactional
    public String enableUser(Long id) {
        if(id == 1L) {
            return ErrorCode.SUPERADMIN_CANT_OPERATE;
        }

        User dbUser = userMapper.findUserById(id);
        if(dbUser == null) {
            return ErrorCode.USER_NOT_EXISTS;
        }else{
            dbUser.setStatus(StatusType.ENABLED);
            boolean result = userMapper.updateUser(dbUser);
            if(result) {
                return SuccessCode.SUCCESS;
            }else{
                return ErrorCode.UNKNOWN_ERROR;
            }
        }
    }

    @Override
    @Transactional
    public String disableUser(Long id) {
        if(id == 1L) {
            return ErrorCode.SUPERADMIN_CANT_OPERATE;
        }

        User dbUser = userMapper.findUserById(id);
        if(dbUser == null) {
            return ErrorCode.USER_NOT_EXISTS;
        }else{
            dbUser.setStatus(StatusType.DISABLED);
            boolean result = userMapper.updateUser(dbUser);
            if(result) {
                return SuccessCode.SUCCESS;
            }else{
                return ErrorCode.UNKNOWN_ERROR;
            }
        }
    }

    @Override
    @Transactional
    public String batchDeleteUser(String ids) {
        if(ids != null && ids.trim().length() > 0) {
            String[] arr = ids.split(",");
            Long[] longIds = new Long[arr.length];
            for(int i=0; i<arr.length; i++) {
                longIds[i] = Long.parseLong(arr[i]);
            }
            boolean result = userMapper.batchDeleteUser(longIds);
            if(result) {
                return SuccessCode.SUCCESS;
            }else{
                return ErrorCode.UNKNOWN_ERROR;
            }
        }

        return ErrorCode.UNKNOWN_ERROR;
    }

    @Override
    @Transactional
    public List<User> listExtMakers() {
        return userMapper.listExtMakers();
    }
}
