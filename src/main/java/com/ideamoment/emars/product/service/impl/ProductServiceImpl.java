package com.ideamoment.emars.product.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.ideamoment.emars.author.dao.AuthorMapper;
import com.ideamoment.emars.author.service.AuthorService;
import com.ideamoment.emars.constants.AliyunOSSConstants;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.make.dao.MakeContractMapper;
import com.ideamoment.emars.model.*;
import com.ideamoment.emars.model.enumeration.*;
import com.ideamoment.emars.product.dao.ProductCopyrightFileMapper;
import com.ideamoment.emars.product.dao.ProductMapper;
import com.ideamoment.emars.product.dao.ProductPictureMapper;
import com.ideamoment.emars.product.dao.ProductSampleMapper;
import com.ideamoment.emars.product.service.ProductService;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.StringUtils;
import com.ideamoment.emars.utils.UserContext;
import com.ideamoment.emars.utils.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by yukiwang on 2018/2/23.
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductPictureMapper productPictureMapper;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private ProductSampleMapper productSampleMapper;

    @Autowired
    private ProductCopyrightFileMapper productCopyrightFileMapper;

    @Autowired
    private MakeContractMapper makeContractMapper;

    @Autowired
    private OSSClient ossClient;

    @Override
    @Transactional
    public Page<ProductInfo> listProducts(ProductInfo condition, int offset, int pageSize) {
        long count = productMapper.listProductsCount(condition);
        int currentPage = offset/pageSize + 1;

        List<ProductInfo> products = productMapper.listProducts(condition, offset, pageSize);
        for(ProductInfo prod : products) {
            List<Author> authors = authorMapper.queryAuthorByProduct(prod.getId());
            prod.setAuthors(authors);
        }

        Page<ProductInfo> result = new Page<ProductInfo>();
        result.setCurrentPage(currentPage);
        result.setData(products);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public long listProductsCount(ProductInfo condition) {
        long count = productMapper.listProductsCount(condition);
        return count;
    }

    @Override
    @Transactional
    public ProductInfo findProduct(long id) {
        ProductInfo product = productMapper.findProduct(id);
        List<Author> authors = authorMapper.queryAuthorByProduct(id);
        product.setAuthors(authors);
        List<ReservationAnnouncer> reservationAnnouncers = productMapper.queryAnnouncerByProductId(id);
        product.setReservationAnnouncers(reservationAnnouncers);
        return product;
    }

    @Override
    @Transactional
    public String deleteProduct(long id) {
        boolean r = productMapper.existsCopyrightByProduct(id);
        if(r) {
            return ErrorCode.PRODUCT_CANNOT_DELETE;
        }
        boolean result = productMapper.deleteProduct(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public String createProduct(ProductInfo product) {
        ProductInfo existsProduct = productMapper.queryProduct(product.getName(), null);

        if(existsProduct != null) {
            return ErrorCode.PRODUCT_EXISTS;
        }
        boolean result = productMapper.insertProduct(product);
        return resultString(result);
    }

    @Override
    @Transactional
    public String updateProduct(ProductInfo product) {
        ProductInfo existsProduct = productMapper.queryProduct(product.getName(), null);

        if(existsProduct == null) {
            return ErrorCode.PRODUCT_NOT_EXISTS;
        }
        product.setModifier(UserContext.getUserId());
        product.setModifyTime(new Date());
        boolean result = productMapper.updateProduct(product);
        List<Author> existedAuthors = authorMapper.queryAuthorByProduct(product.getId());
        HashSet<Long> existedAuthorIdCache = new HashSet<Long>();
        for(Author existedAuthor : existedAuthors) {
            existedAuthorIdCache.add(existedAuthor.getId());
        }
        HashSet<Long> newAuthorIdCache = new HashSet<Long>();
        for(Author newAuthor : product.getAuthors()) {
            newAuthorIdCache.add(newAuthor.getId());
        }
        for(Author existedAuthor : existedAuthors) {
            if(!newAuthorIdCache.contains(existedAuthor.getId())) {
                productMapper.deleteProductAuthor(product.getId(), existedAuthor.getId());
            }
        }
        Date curDate = new Date();
        for(Author newAuthor : product.getAuthors()) {
            if(!existedAuthorIdCache.contains(newAuthor.getId())) {
                ProductAuthor prodAuthor = new ProductAuthor();
                prodAuthor.setProductId(product.getId());
                prodAuthor.setAuthorId(newAuthor.getId());
                prodAuthor.setCreator(UserContext.getUserId());
                prodAuthor.setCreateTime(curDate);
                productMapper.insertProductAuthor(prodAuthor);
            }
        }

        return resultString(result);
    }

    @Override
    @Transactional
    public String saveProduct(ProductInfo product, String submit, boolean withoutEva, String type) {
        boolean ret;
        String name = product.getName();
        if(name == null || "".equals(name.trim())) {
            return ErrorCode.NAME_REQUIED;
        }

        String checkDuplicated = checkDuplicated(product, product.getId());
        if(checkDuplicated != null) {
            return checkDuplicated;
        }

        Date curTime = new Date();

//        Author author = product.getAuthor();
//        Author productAuthor = findOrinsertAuthor(author.getName(), author.getPseudonym());
//        if(productAuthor == null) {
//            return ErrorCode.PRODUCT_AUTHOR_ERROR;
//        }
//        product.setAuthorId(productAuthor.getId());

        product.setModifier(UserContext.getUserId());
        product.setModifyTime(curTime);

//        product.setType(ProductType.TEXT);
//        if("1".equals(submit)) {
//            if(withoutEva) {
//                product.setState(ProductState.NEW_WITHOUT_EVA);
//            }else{
//                product.setState(ProductState.DRAFT);
//            }
//        }else {
//            if(withoutEva) {
//                product.setState(ProductState.NEW_WITHOUT_EVA);
//            }else{
//                product.setState(ProductState.APPROVE_WAITING);
//            }
//        }

        if(("0").equals(type)) {
            product.setCreateTime(curTime);
            product.setCreator(UserContext.getUserId());
            ret = productMapper.insertProduct(product);
        }else{
            ret = productMapper.updateProduct(product);
        }

//        List<ProductSample> samples = product.getSamples();
//        if(samples != null) {
//            for (ProductSample sample : samples) {
//                sample.setProductId(product.getId());
//                ret = productSampleMapper.insertProductSimpleMapper(sample);
//            }
//        }

        return resultString(ret);
    }

    @Override
    @Transactional
    public List<ProductCopyrightFile> listProductCopyrightFiles(long productId) {
        return productCopyrightFileMapper.listProductCopyrightFiles(productId);
    }

    @Override
    @Transactional
    public String saveProductPictures(ArrayList<ProductPicture> pics) {
        Long userId = UserContext.getUserId();
        Date curDate = new Date();

        boolean result = true;
        for(ProductPicture pic : pics) {
            pic.setCreator(userId);
            pic.setCreateTime(curDate);
            result = result && productPictureMapper.insertPicture(pic);
        }

        return resultString(result);
    }

    @Override
    @Transactional
    public List<ProductPicture> loadProductPictureFiles(String productId) {
        return productPictureMapper.queryProductPictures(productId);
    }

    @Override
    @Transactional
    public String deletePicture(String id) {
        boolean result = productPictureMapper.deleteProductPicture(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public String stockIn(long id) {
        ProductInfo product = productMapper.findProduct(id);

        if(product == null) {
            return ErrorCode.PRODUCT_NOT_EXISTS;
        }
        String validCopyrightResult = validateCopyrightValid(id);
        boolean validCopyright = false;
        if(SuccessCode.SUCCESS.equals(validCopyrightResult)) {
            validCopyright = true;
        }else{
            return ErrorCode.PRODUCT_COPYRIGHT_FILES_NOT_COMPLETE;
        }
        String validMalkeResult = validateMakeValid(id);
        boolean validMake = true;
        if(SuccessCode.SUCCESS.equals(validMalkeResult)) {
            validMake = true;
        }else{
            return ErrorCode.PRODUCT_MAKE_FILES_NOT_COMPLETE;
        }
        if(validCopyright && validMake) {
            product.setStockIn(StockInType.STOCK_IN);
            product.setModifier(UserContext.getUserId());
            product.setModifyTime(new Date());
            boolean result = productMapper.updateProduct(product);
            return resultString(result);
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }

    private String validateMakeValid(long id) {
        return SuccessCode.SUCCESS;
    }

    @Override
    @Transactional
    public Page<ProductInfo> listStockedInProducts(ProductInfo condition, int offset, int pageSize) {
        condition.setStockIn(StockInType.STOCK_IN);

        long count = productMapper.listProductsCount(condition);
        int currentPage = offset/pageSize + 1;

        List<ProductInfo> products = productMapper.listProducts(condition, offset, pageSize);
        for(ProductInfo prod : products) {
            List<Author> authors = authorMapper.queryAuthorByProduct(prod.getId());
            prod.setAuthors(authors);
        }

        Page<ProductInfo> result = new Page<ProductInfo>();
        result.setCurrentPage(currentPage);
        result.setData(products);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public String changeProductionState(long id, String productionState, Long[] announcerIds) {
        boolean ret = productMapper.changeProductionState(id, productionState);
        if(ret) {
            productMapper.deleteReservationAnnouncers(id);
            if(ProductionState.RESERVATION.equals(productionState)) {
                for(Long announcerId : announcerIds) {
                    productMapper.addReservationAnnouncer(id, announcerId);
                }
            }
        }

        return resultString(ret);
    }

    @Override
    @Transactional
    public String packageAllFiles(Long productId) {
        ProductInfo product = productMapper.findProduct(productId);

        String tempDir = makeTempDir(productId);

        downloadFiles(productId, tempDir, false);

        String systemTmpDir = System.getProperty("java.io.tmpdir");
        systemTmpDir = systemTmpDir.replaceAll("\\\\", "/");
        if(!systemTmpDir.endsWith("/")) {
            systemTmpDir = systemTmpDir + "/";
        }
        String zipPath = systemTmpDir + product.getName() + "_All.zip";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(zipPath));
            ZipUtils.toZip(tempDir, fos, true);
            fos.close();
            return zipPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void downloadFiles(Long productId, String tempDir, boolean toSale) {
        String cpFileDirPath = tempDir + "版权文件";
        File cpFileDir = new File(cpFileDirPath);
        cpFileDir.mkdir();

        List<CopyrightFile> copyrightFiles;
        if(toSale) {
            copyrightFiles = productCopyrightFileMapper.listCopyrightToSaleFiles(productId);
        }else{
            copyrightFiles = productCopyrightFileMapper.listCopyrightFiles(productId);
        }
        String cpContractDirPath = cpFileDirPath + "/合同";
        File cpContractDir = new File(cpContractDirPath);
        String cpCopyrightPageDirPath = cpFileDirPath + "/版权页";
        File cpCopyrightPageDir = new File(cpCopyrightPageDirPath);
        String cpGrantPaperDirPath = cpFileDirPath + "/授权书";
        File cpGrantPaperDir = new File(cpGrantPaperDirPath);
        String cpAuthorIdCardDirPath = cpFileDirPath + "/作者身份证";
        File cpAuthorIdCardDir = new File(cpAuthorIdCardDirPath);
        String cpPublishContractDirPath = cpFileDirPath + "/出版合同";
        File cpPublishContractDir = new File(cpPublishContractDirPath);
        String cpContractToSaleDirPath = cpFileDirPath + "/合同_给营销";
        File cpContractToSaleDir = new File(cpContractToSaleDirPath);
        for(CopyrightFile cpFile : copyrightFiles) {
            String path = cpFile.getPath();
            String key = StringUtils.getOssKeyFromUrl(path);
            String cpFilePath = null;
            if(CopyrightFileType.CONTRACT.equals(cpFile.getType())) {
                if(!cpContractDir.exists()) {
                    cpContractDir.mkdir();
                }
                cpFilePath = cpContractDirPath + "/" + cpFile.getName();
            }else if(CopyrightFileType.COPYRIGHT_PAGE.equals(cpFile.getType())){
                if(!cpCopyrightPageDir.exists()) {
                    cpCopyrightPageDir.mkdir();
                }
                cpFilePath = cpCopyrightPageDirPath + "/" + cpFile.getName();
            }else if(CopyrightFileType.GRANT_PAPER.equals(cpFile.getType())){
                if(!cpGrantPaperDir.exists()) {
                    cpGrantPaperDir.mkdir();
                }
                cpFilePath = cpGrantPaperDirPath + "/" + cpFile.getName();
            }else if(CopyrightFileType.AUTHOR_ID_CARD.equals(cpFile.getType())){
                if(!cpAuthorIdCardDir.exists()) {
                    cpAuthorIdCardDir.mkdir();
                }
                cpFilePath = cpAuthorIdCardDirPath + "/" + cpFile.getName();
            }else if(CopyrightFileType.PUBLISH_CONTRACT.equals(cpFile.getType())){
                if(!cpPublishContractDir.exists()) {
                    cpPublishContractDir.mkdir();
                }
                cpFilePath = cpPublishContractDirPath + "/" + cpFile.getName();
            }else if(CopyrightFileType.CONTRACT_TO_SALE.equals(cpFile.getType())){
                if(!cpContractToSaleDir.exists()) {
                    cpContractToSaleDir.mkdir();
                }
                cpFilePath = cpContractToSaleDirPath + "/" + cpFile.getName();
            }

            if(cpFilePath != null) {
                ossClient.getObject(new GetObjectRequest(AliyunOSSConstants.BUCKET_NAME, key), new File(cpFilePath));
            }
        }

        ArrayList<MakeContractDoc> mcFiles = makeContractMapper.listContractDocs(productId, null);
        String mcFileDirPath = tempDir + "制作文件";
        File mcFileDir = new File(mcFileDirPath);
        mcFileDir.mkdir();
        String mcContractDirPath = mcFileDirPath + "/合同";
        File mcContractDir = new File(mcContractDirPath);
        String mcBroadcasterFileDirPath = mcFileDirPath + "/委托制作权利声明";
        File mcBroadcasterFileDir = new File(mcBroadcasterFileDirPath);
        String mcTalentStationFileDirPath = mcFileDirPath + "/演播人权利声明";
        File mcTalentStationFileDir = new File(mcTalentStationFileDirPath);
        String mcTalentIdCardDirPath = mcFileDirPath + "/演播人身份证";
        File mcTalentIdCardDir = new File(mcTalentIdCardDirPath);
        String mcOperationFileDirPath = mcFileDirPath + "/运营权利授权书";
        File mcOperationFileDir = new File(mcOperationFileDirPath);
        for(MakeContractDoc mcFile : mcFiles) {
            String path = mcFile.getPath();
            String key = StringUtils.getOssKeyFromUrl(path);
            String mcFilePath = null;
            if(MakeContractDocType.CONTRACT.equals(mcFile.getType())) {
                if(!mcContractDir.exists()) {
                    mcContractDir.mkdir();
                }
                mcFilePath = mcContractDirPath + "/" + mcFile.getName();
            }else if(MakeContractDocType.BROADCASTER_FILE.equals(mcFile.getType())) {
                if(!mcBroadcasterFileDir.exists()) {
                    mcBroadcasterFileDir.mkdir();
                }
                mcFilePath = mcBroadcasterFileDirPath + "/" + mcFile.getName();
            }else if(MakeContractDocType.TALENT_STATION_FILE.equals(mcFile.getType())) {
                if(!mcTalentStationFileDir.exists()) {
                    mcTalentStationFileDir.mkdir();
                }
                mcFilePath = mcTalentStationFileDirPath + "/" + mcFile.getName();
            }else if(MakeContractDocType.TALENT_ID_CARD.equals(mcFile.getType())) {
                if(!mcTalentIdCardDir.exists()) {
                    mcTalentIdCardDir.mkdir();
                }
                mcFilePath = mcTalentIdCardDirPath + "/" + mcFile.getName();
            }else if(MakeContractDocType.OPERATION_FILE.equals(mcFile.getType())) {
                if(!mcOperationFileDir.exists()) {
                    mcOperationFileDir.mkdir();
                }
                mcFilePath = mcOperationFileDirPath + "/" + mcFile.getName();
            }
            if(mcFilePath != null) {
                ossClient.getObject(new GetObjectRequest(AliyunOSSConstants.BUCKET_NAME, key), new File(mcFilePath));
            }
        }

        String picFileDirPath = tempDir + "图片";
        File picFileDir = new File(picFileDirPath);
        picFileDir.mkdir();
        List<ProductPicture> pics = productPictureMapper.queryProductPictures(String.valueOf(productId));
        for(ProductPicture pic : pics) {
            String path = pic.getPath();
            String key = StringUtils.getOssKeyFromUrl(path);
            ossClient.getObject(new GetObjectRequest(AliyunOSSConstants.BUCKET_NAME, key), new File(picFileDirPath + "/" + pic.getName()));
        }
    }

    @Override
    @Transactional
    public String packageToSaleFiles(Long productId) {
        ProductInfo product = productMapper.findProduct(productId);

        String tempDir = makeTempDir(productId);

        downloadFiles(productId, tempDir, true);

        String systemTmpDir = System.getProperty("java.io.tmpdir");
        systemTmpDir = systemTmpDir.replaceAll("\\\\", "/");
        if(!systemTmpDir.endsWith("/")) {
            systemTmpDir = systemTmpDir + "/";
        }
        String zipPath = systemTmpDir + product.getName() + "_ToSale.zip";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(zipPath));
            ZipUtils.toZip(tempDir, fos, true);
            fos.close();
            return zipPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public List<Map> selectProductCountWithCopyrightType() {
        return productMapper.selectProductCountWithCopyrightType();
    }

    @Override
    @Transactional
    public List<Map> selectProductCountWhitSubject() {
        return productMapper.selectProductCountWhitSubject();
    }

    @Override
    public ProductPicture loadProductLogo(String productId) {
        List<ProductPicture> pics = productPictureMapper.queryProductPictures(productId);
        if(pics == null || pics.size() == 0) {
            return null;
        }else{
            return pics.get(0);
        }
    }

    private String makeTempDir(Long productId) {
        String tempDir = System.getProperty("java.io.tmpdir");
        tempDir = tempDir.replaceAll("\\\\", "/");
        if(!tempDir.endsWith("/")) {
            tempDir = tempDir + "/";
        }
        tempDir = tempDir + System.currentTimeMillis() + "_" + productId;
        File dir = new File(tempDir);
        if(!dir.exists()) {
            dir.mkdir();
        }
        return tempDir + "/";
    }

    private String validateCopyrightValid(long id) {
        List<CopyrightFile> copyrightFiles = productCopyrightFileMapper.listCopyrightFiles(id);
        boolean contract = false;
        boolean copyrightPage = false;
        boolean grantPaper = false;
        boolean authorIdCard = false;
        boolean publishContract = false;
        for(CopyrightFile file : copyrightFiles) {
            if(CopyrightFileType.CONTRACT.equals(file.getType())){
                contract = true;
            }
            if(CopyrightFileType.COPYRIGHT_PAGE.equals(file.getType())){
                copyrightPage = true;
            }
            if(CopyrightFileType.GRANT_PAPER.equals(file.getType())){
                grantPaper = true;
            }
            if(CopyrightFileType.AUTHOR_ID_CARD.equals(file.getType())){
                authorIdCard = true;
            }
            if(CopyrightFileType.PUBLISH_CONTRACT.equals(file.getType())){
                publishContract = true;
            }
        }
        boolean result = contract && copyrightPage && grantPaper && authorIdCard && publishContract;
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.PRODUCT_COPYRIGHT_FILES_NOT_COMPLETE;
        }
    }


    private String resultString(boolean result) {
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }

    private String checkDuplicated(ProductInfo product, long id) {
//        ProductInfo prods = productMapper.checkProductDuplicated(product.getName(), id);
//        if(prods != null) {
//            return ErrorCode.PRODUCT_DUPLICATED;
//        }

        if(product.getIsbn() != null && product.getIsbn().trim().length() > 0) {
            ProductInfo  prods = productMapper.checkIsbnDuplicated(product.getIsbn(), id);
            if(prods != null) {
                return ErrorCode.ISBN_DUPLICATED;
            }
        }
        return null;
    }

    private Author findOrinsertAuthor(String name, String pseudonym) {
        if(StringUtils.isEmpty(name)) {
            return null;
        }
        Author author = authorService.findAuthorByName(name);
        if(author == null) {
            String result = authorService.createAuthor(name, null, pseudonym);
            if(SuccessCode.SUCCESS.equals(result)) {
                Author newAuthor = authorService.findAuthorByName(name);
                return newAuthor;
            }else {
                return null;
            }
        }else {
            return author;
        }
    }
}
