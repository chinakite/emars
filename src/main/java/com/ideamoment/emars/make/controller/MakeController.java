package com.ideamoment.emars.make.controller;

import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.ProductQueryVo;
import com.ideamoment.emars.model.ProductResultVo;
import com.ideamoment.emars.product.service.ProductService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yukiwang on 2018/3/5.
 */
@RestController
@RequestMapping("make")
public class MakeController {

    @Autowired
    private MakeService makeService;

    @RequestMapping(value = "/dtProducts", method = RequestMethod.GET)
    public DataTableSource<ProductResultVo> dtProducts(
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
        Page<ProductResultVo> products = makeService.pageProducts(condition, start, length);
        DataTableSource<ProductResultVo> dts = convertProductsToDataTableSource(draw, products);
        return dts;
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
