package com.ideamoment.emars.make.service;

import com.ideamoment.emars.model.MakeContract;
import com.ideamoment.emars.model.MakeTask;
import com.ideamoment.emars.model.ProductQueryVo;
import com.ideamoment.emars.model.ProductResultVo;
import com.ideamoment.emars.utils.Page;

/**
 * Created by yukiwang on 2018/3/5.
 */
public interface MakeService {

    Page<ProductResultVo> pageProducts(ProductQueryVo condition, int offset, int pageSize);

    String saveMakeTask(MakeTask makeTask);

    MakeContract findCopyContract(long id);

    String saveMakeContract(MakeContract makeContract, String type);

}
