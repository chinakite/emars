package com.ideamoment.emars.copyright.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.ideamoment.emars.constants.AliyunOSSConstants;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.model.CopyrightContract;
import com.ideamoment.emars.model.CopyrightFile;
import com.ideamoment.emars.model.CopyrightProductInfo;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukiwang on 2018/2/24.
 */
@RestController
@RequestMapping("copyright")
public class CopyrightController {

    @Autowired
    private CopyrightService copyrightService;

    @Autowired
    private OSSClient ossClient;

    @RequestMapping(value = "/copyrights", method = RequestMethod.GET)
    public DataTableSource<CopyrightContract> queryCopyrights(
            int draw,
            int start,
            int length,
            String code,
            String owner,
            String auditState
    ) {
        CopyrightContract condition = new CopyrightContract();
//        condition.setCode(code);
//        condition.setOwner(owner);
//        condition.setAuditState(auditState);
        Page<CopyrightContract> copyrights = copyrightService.listCopyrights(condition, start, length);
        DataTableSource<CopyrightContract> dts = convertProductsToDataTableSource(draw, copyrights);
        return dts;
    }

    @RequestMapping(value = "/copyright", method = RequestMethod.GET)
    public JsonData findCopyright(long id) {
        CopyrightContract copyright = copyrightService.findCopyright(id);
        return JsonData.success(copyright);
    }

    @RequestMapping(value = "/saveCopyrightContract", method = RequestMethod.POST)
    public JsonData<String> saveCopyrightContract(
            @RequestBody CopyrightContract copyrightContract
    ) {
        if(copyrightContract.getId() > 0) {
            copyrightService.updateCopyrightContract(copyrightContract);
        }else{
            copyrightService.createCopyrightContract(copyrightContract);
        }

        return JsonData.SUCCESS;
    }

    @RequestMapping(value="/productCopyright", method = RequestMethod.GET)
    public JsonData<CopyrightProductInfo> productCopyright(Long productId) {
        CopyrightProductInfo copyrightProductInfo = copyrightService.queryProductCopyright(productId);
        return JsonData.success(copyrightProductInfo);
    }

    @RequestMapping(value = "/removeCopyright", method = RequestMethod.POST)
    public JsonData<String> removeCopyright(Long id) {
        String result = copyrightService.removeCopyright(id);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/saveCopyrightFiles", method = RequestMethod.POST)
    public JsonData<String> saveCopyrightFiles(@RequestBody ArrayList<CopyrightFile> copyrightFiles) {
        String result = copyrightService.saveCopyrightFiles(copyrightFiles);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/contractFiles", method = RequestMethod.GET)
    public JsonData<List<CopyrightFile>> loadCopyrightContractFiles(String productId) {
        List<CopyrightFile> files = copyrightService.loadCopyrightContractFiles(productId);
        return JsonData.success(files);
    }

    @RequestMapping(value="/copyrightPageFiles", method = RequestMethod.GET)
    public JsonData<List<CopyrightFile>> loadCopyrightPageFiles(String productId) {
        List<CopyrightFile> files = copyrightService.loadCopyrightPageFiles(productId);
        return JsonData.success(files);
    }

    @RequestMapping(value="/authorIdCardFiles", method = RequestMethod.GET)
    public JsonData<List<CopyrightFile>> loadAuthorIdCardFiles(String productId) {
        List<CopyrightFile> files = copyrightService.loadAuthorIdCardFiles(productId);
        return JsonData.success(files);
    }

    @RequestMapping(value="/grantPaperFiles", method = RequestMethod.GET)
    public JsonData<List<CopyrightFile>> loadGrantPaperFiles(String productId) {
        List<CopyrightFile> files = copyrightService.loadGrantPaperFiles(productId);
        return JsonData.success(files);
    }

    @RequestMapping(value="/publishContractFiles", method = RequestMethod.GET)
    public JsonData<List<CopyrightFile>> loadPublishContractFiles(String productId) {
        List<CopyrightFile> files = copyrightService.loadPublishContractFiles(productId);
        return JsonData.success(files);
    }

    @RequestMapping(value="/toSaleContractFiles", method = RequestMethod.GET)
    public JsonData<List<CopyrightFile>> loadToSaleContractFiles(String productId) {
        List<CopyrightFile> files = copyrightService.loadToSaleContractFiles(productId);
        return JsonData.success(files);
    }

    @RequestMapping(value="/deleteCopyrightFile", method = RequestMethod.POST)
    public JsonData<String> deleteCopyrightFile(String id) {
        String result = copyrightService.deleteCopyrightFile(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "downloadCopyrightFile", method = RequestMethod.GET)
    public StreamingResponseBody getSteamingFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileIdStr = request.getParameter("id");
        if(StringUtils.isEmpty(fileIdStr)) {
            return null;
        }

        Long fileId = Long.parseLong(fileIdStr);
        CopyrightFile fileInfo = copyrightService.findCopyrightFile(fileId);
        String filePath = fileInfo.getPath();
        String key = StringUtils.getOssKeyFromUrl(filePath);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileInfo.getName(), "UTF-8") + "\"");

        OSSObject ossObject = ossClient.getObject(AliyunOSSConstants.BUCKET_NAME, key);

        InputStream in = ossObject.getObjectContent();

        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
            in.close();
        };

    }

    @RequestMapping(value = "generateContractCode", method = RequestMethod.GET)
    public JsonData generateContractCode(String signDate, String type) {
        String code = copyrightService.generateContractCode(signDate, type);
        if(code == null) {
            return JsonData.error(ErrorCode.UNKNOWN_ERROR, "生成合同号时发生错误");
        }else{
            return JsonData.success(code);
        }
    }

    @RequestMapping(value = "changeCopyrightState", method = RequestMethod.POST)
    public JsonData<String> changeCopyrightState(long id, String state) {
        String result = copyrightService.changeCopyrightState(id, state);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    private DataTableSource<CopyrightContract> convertProductsToDataTableSource(int draw, Page<CopyrightContract> productsPage) {
        DataTableSource<CopyrightContract> dts = new DataTableSource<CopyrightContract>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }


}
