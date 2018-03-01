package com.ideamoment.emars.product.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.ProductQueryVo;
import com.ideamoment.emars.model.ProductResultVo;
import com.ideamoment.emars.product.service.ProductService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yukiwang on 2018/2/23.
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public DataTableSource<ProductResultVo> queryProducts(
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
        ProductQueryVo condition = new ProductQueryVo();
        condition.setProductName(productName);
        condition.setAuthorName(authorName);
        condition.setIsbn(isbn);
        condition.setSubjectId(subjectId);
        condition.setPublishState(publishState);
        condition.setState(state);
        Page<ProductResultVo> products = productService.listProducts(condition, start, length);
        DataTableSource<ProductResultVo> dts = convertProductsToDataTableSource(draw, products);
        return dts;
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public JsonData findProduct(long id) {
        ProductResultVo productResultVo = productService.findProduct(id);
        return JsonData.success(productResultVo);
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


    private DataTableSource<ProductResultVo> convertProductsToDataTableSource(int draw, Page<ProductResultVo> productsPage) {
        DataTableSource<ProductResultVo> dts = new DataTableSource<ProductResultVo>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }
}
