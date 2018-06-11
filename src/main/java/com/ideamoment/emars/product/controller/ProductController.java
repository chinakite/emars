package com.ideamoment.emars.product.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.*;
import com.ideamoment.emars.product.service.ProductService;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukiwang on 2018/2/23.
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public DataTableSource<ProductInfo> queryProducts(
            int draw,
            int start,
            int length,
            String productName,
            String authorName,
            String isbn,
            Long subjectId,
            String publishState,
            String state
    ) {
        ProductInfo condition = new ProductInfo();
        condition.setName(productName);
        condition.setIsbn(isbn);
        condition.setSubjectId(subjectId);
        Page<ProductInfo> products = productService.listProducts(condition, start, length);
        DataTableSource<ProductInfo> dts = convertProductsToDataTableSource(draw, products);
        return dts;
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public JsonData findProduct(long id) {
        ProductInfo product = productService.findProduct(id);
        return JsonData.success(product);
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public JsonData<Boolean> updateProduct(@RequestBody ProductInfo product) {
        String result = productService.updateProduct(product);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public JsonData<Boolean> deleteProduct(long id) {
        String result = productService.deleteProduct(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/savePics", method = RequestMethod.POST)
    public JsonData<String> savePics(@RequestBody ArrayList<ProductPicture> pics) {
        String result = productService.saveProductPictures(pics);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/productPicFiles", method = RequestMethod.GET)
    public JsonData<List<ProductPicture>> loadProductPictureFiles(String productId) {
        List<ProductPicture> pics = productService.loadProductPictureFiles(productId);
        return JsonData.success(pics);
    }

    @RequestMapping(value="/productLogo", method = RequestMethod.GET)
    public JsonData<ProductPicture> loadProductLogo(String productId) {
        ProductPicture pic = productService.loadProductLogo(productId);
        return JsonData.success(pic);
    }

    @RequestMapping(value="/deletePicture", method = RequestMethod.POST)
    public JsonData<String> deletePicture(String id) {
        String result = productService.deletePicture(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/stockIn", method = RequestMethod.POST)
    public JsonData stockIn(long id) {
        String result = productService.stockIn(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/changeProductionState", method = RequestMethod.POST)
    public JsonData changeProductionState(long id, String productionState, Long[] announcerIds) {
        String result = productService.changeProductionState(id, productionState, announcerIds);
        if(result.equals(SuccessCode.SUCCESS)) {
            ProductInfo product = productService.findProduct(id);
            return JsonData.success(product);
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "downloadAllFiles", method = RequestMethod.GET)
    public StreamingResponseBody downloadAllFiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productIdStr = request.getParameter("id");
        if(StringUtils.isEmpty(productIdStr)) {
            return null;
        }

        Long productId = Long.parseLong(productIdStr);
        String filePath = productService.packageAllFiles(productId);
        if(filePath != null) {
            response.setContentType("application/octet-stream");
            String fileName = StringUtils.getFileNameFromPath(filePath);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");

            InputStream in = new FileInputStream(new File(filePath));

            return outputStream -> {
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = in.read(data, 0, data.length)) != -1) {
                    outputStream.write(data, 0, nRead);
                }
                in.close();
            };
        }

        return null;
    }

    @RequestMapping(value = "downloadToSaleFiles", method = RequestMethod.GET)
    public StreamingResponseBody downloadToSaleFiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productIdStr = request.getParameter("id");
        if(StringUtils.isEmpty(productIdStr)) {
            return null;
        }

        Long productId = Long.parseLong(productIdStr);
        String filePath = productService.packageToSaleFiles(productId);
        if(filePath != null) {
            response.setContentType("application/octet-stream");
            String fileName = StringUtils.getFileNameFromPath(filePath);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");

            InputStream in = new FileInputStream(new File(filePath));

            return outputStream -> {
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = in.read(data, 0, data.length)) != -1) {
                    outputStream.write(data, 0, nRead);
                }
                in.close();
            };
        }

        return null;
    }

    private DataTableSource<ProductInfo> convertProductsToDataTableSource(int draw, Page<ProductInfo> productsPage) {
        DataTableSource<ProductInfo> dts = new DataTableSource<ProductInfo>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }
}
