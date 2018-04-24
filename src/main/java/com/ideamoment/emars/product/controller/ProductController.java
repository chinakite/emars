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

import java.math.BigDecimal;
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
        ProductInfo productResultVo = productService.findProduct(id);
        return JsonData.success(productResultVo);
    }

    @RequestMapping(value = "/createProduct", method = RequestMethod.POST)
    public JsonData<String> createProduct(
            Long id,
            String name,
            String authorName,
            String authorPseudonym,
            String wordCount,
            long subject,
            String publishState,
            String publishYear,
            String press,
            String finishYear,
            String website,
            String summary,
            boolean hasAudio,
            boolean audioCopyright,
            String audioDesc,
            String samples,
            String isbn,
            String copyrights,
            String logoUrl,
            String submit,
            String type
    ) {
        ProductInfo product;
        if(id == null) {
            product = new ProductInfo();
        }else{
            product = productService.findProduct(id);
        }

        product.setName(name);

        Author author = new Author();
        author.setName(authorName);
        author.setPseudonym(authorPseudonym);

        String result = productService.saveProduct(product, submit, true, type);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public JsonData<Boolean> updateProduct(ProductInfo product) {
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

    private DataTableSource<ProductInfo> convertProductsToDataTableSource(int draw, Page<ProductInfo> productsPage) {
        DataTableSource<ProductInfo> dts = new DataTableSource<ProductInfo>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }
}
