package com.ideamoment.emars.product.controller;

import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.product.service.ProductService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mediares")
public class MediaResourceController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public DataTableSource<ProductInfo> queryProducts(
            int draw,
            int start,
            int length,
            String productName,
            String isbn,
            Long subjectId
    ) {
        ProductInfo condition = new ProductInfo();
        condition.setName(productName);
        condition.setIsbn(isbn);
        condition.setSubjectId(subjectId);
        Page<ProductInfo> products = productService.listStockedInProducts(condition, start, length);
        DataTableSource<ProductInfo> dts = convertProductsToDataTableSource(draw, products);
        return dts;
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
