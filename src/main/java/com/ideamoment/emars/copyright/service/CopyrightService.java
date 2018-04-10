package com.ideamoment.emars.copyright.service;

import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.model.CopyrightContract;
import com.ideamoment.emars.utils.Page;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/24.
 */
public interface CopyrightService {

    Page<CopyrightContract> listCopyrights(CopyrightContract condition, int offset, int pageSize);

    CopyrightContract findCopyright(long id);

    String saveCopyrightContract(Copyright cc, long[] productIdArr, String[] priceArr, int submit, String type);

    List<Copyright> listProductContracts(long productId);

    String createCopyrightContract(CopyrightContract copyrightContract);

    String removeCopyright(Long id);

    String updateCopyrightContract(CopyrightContract copyrightContract);
}
