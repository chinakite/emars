package com.ideamoment.emars.maker.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.make.dao.MakeContractMapper;
import com.ideamoment.emars.maker.dao.MakerMapper;
import com.ideamoment.emars.maker.service.MakerService;
import com.ideamoment.emars.model.Maker;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MakerServiceImpl implements MakerService {
    @Autowired
    private MakerMapper makerMapper;

    @Autowired
    private MakeContractMapper makeContractMapper;

    @Override
    @Transactional
    public Page<Maker> pageMakers(int offset, int pageSize) {
        long count = makerMapper.countMaker();
        int currentPage = offset/pageSize + 1;

        List<Maker> granters = makerMapper.pageMakers(offset, pageSize);

        Page<Maker> result = new Page<Maker>();
        result.setCurrentPage(currentPage);
        result.setData(granters);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public List<Maker> listMakers() {
        return makerMapper.listMakers();
    }

    @Override
    @Transactional
    public String createMaker(String name, String contact, String phone, String desc) {
        Maker existsMaker = makerMapper.findMakerByName(name, null);

        if(existsMaker != null) {
            return ErrorCode.MAKER_EXISTS;
        }
        Maker maker = new Maker();
        maker.setName(name);
        maker.setContact(contact);
        maker.setPhone(phone);
        maker.setDesc(desc);
        maker.setCreateTime(new Date());
        maker.setCreator(UserContext.getUserId());

        boolean result = makerMapper.insertMaker(maker);
        return resultString(result);
    }

    @Override
    @Transactional
    public String deleteMaker(long id) {
        long r = makeContractMapper.countMakeContractByMaker(id);
        if(r > 0) {
            return ErrorCode.MAKER_CANNOT_DELETE;
        }
        boolean result = makerMapper.deleteMaker(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public Maker findMaker(long id) {
        return makerMapper.findMaker(id);
    }

    @Override
    @Transactional
    public String modifyMaker(long id, String name, String contact, String phone, String desc) {
        Maker maker = makerMapper.findMaker(id);

        if(maker == null) {
            return ErrorCode.MAKER_NOT_EXISTS;
        }
        Maker existsMaker = makerMapper.findMakerByName(name, id);
        if(existsMaker != null) {
            return ErrorCode.MAKER_EXISTS;
        }

        maker.setName(name);
        maker.setContact(contact);
        maker.setPhone(phone);
        maker.setModifyTime(new Date());
        maker.setModifier(UserContext.getUserId());
        maker.setDesc(desc);

        boolean result = makerMapper.updateMaker(maker);
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
