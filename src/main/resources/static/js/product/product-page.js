;

var PRODUCTPAGE = {};
var dropzoneObj;

$(document).ready(function(){
    $('#dropzonejs').dropzone({
        parallelUploads: 1,
        maxFilesize:     50000,
        filesizeBase:    1000,

        resize: function(file) {
            return {
                srcX:      0,
                srcY:      0,
                srcWidth:  file.width,
                srcHeight: file.height,
                trgWidth:  file.width,
                trgHeight: file.height,
            };
        },
        init: function() {
            dropzoneObj = this;
            this.on("success", function (file, message) {
                var fileMetaDatas = message.data;
                var fileMetas = $('#fileMetas').data('postData');
                if(!fileMetas) {
                    fileMetas = [];
                }
                for(var i=0; i<fileMetaDatas.length; i++) {
                    fileMetas.push(fileMetaDatas[i]);
                }
                $('#fileMetas').data('postData', fileMetas);
            });
            this.on("error", function (file, message) {
                console.log(message);
            });
            this.on('resetFiles', function() {
                this.removeAllFiles();
            });
        }
    });

    PRODUCTPAGE.refreshProductFiles();

    PRODUCTPAGE.loadAnnouncers();

    $("input[name='inputProductionState']").click(function () {
        var selectedVal = $(this).val();
        if(selectedVal == 4) {
            $(".announcer").show();
        }else {
            $(".announcer").hide();
        }
    });
});

PRODUCTPAGE.popUploadFileModal = function(type) {
    var productId = $('#productId').val();
    PRODUCTPAGE.clearUploadFileModal();
    $('#uploadFileType').val(type);
    $('#uploadFileProductId').val(productId);
    $('#uploadFileModal').modal('show');
};

PRODUCTPAGE.clearUploadFileModal = function() {
    dropzoneObj.emit("resetFiles");
    $('#fileMetas').removeData('postData');
    $('#uploadFileType').val('');
    $('#uploadFileProductId').val('');
    $('#uploadFileModal').modal('show');
};

PRODUCTPAGE.submitProductFiles = function() {
    var saveCopyrightFileUrl = '/copyright/saveCopyrightFiles';
    var saveProdPicUrl = '/product/savePics';
    var fileType = $('#uploadFileType').val();
    var productId = $('#uploadFileProductId').val();
    var fileMetas = $('#fileMetas').data('postData');
    var postUrl;
    for(var i=0; i<fileMetas.length; i++) {
        if(fileType == 'cpr_contract') {
            fileMetas[i].type = '1';
            postUrl = saveCopyrightFileUrl;
        }else if(fileType == 'cpr_copyright_page') {
            fileMetas[i].type = '2';
            postUrl = saveCopyrightFileUrl;
        }else if(fileType == 'cpr_grant_paper') {
            fileMetas[i].type = '3';
            postUrl = saveCopyrightFileUrl;
        }else if(fileType == 'cpr_author_id_card') {
            fileMetas[i].type = '4';
            postUrl = saveCopyrightFileUrl;
        }else if(fileType == 'cpr_publish_contract') {
            fileMetas[i].type = '5';
            postUrl = saveCopyrightFileUrl;
        }else if(fileType == 'cpr_contract_to_sale') {
            fileMetas[i].type = '6';
            postUrl = saveCopyrightFileUrl;
        }else if(fileType == 'prod_pic') {
            fileMetas[i].type = '0';
            postUrl = saveProdPicUrl;
        }else{
            fileMetas[i].type = '0';
        }
        fileMetas[i].productId = productId;
    }
    $.ajax(
        {
            url: postUrl,
            data: JSON.stringify(fileMetas),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("文件保存成功！");
                    $('#uploadFileModal').modal('hide');
                    if(fileType == 'cpr_contract') {
                        PRODUCTPAGE.refreshProductCopyrightContractFiles($('#productId').val());
                    }else if(fileType == 'cpr_copyright_page') {
                        PRODUCTPAGE.refreshProductCopyrightPageFiles($('#productId').val());
                    }else if(fileType == 'cpr_grant_paper') {
                        PRODUCTPAGE.refreshProductGrantPaperFiles($('#productId').val());
                    }else if(fileType == 'cpr_author_id_card') {
                        PRODUCTPAGE.refreshProductAuthorIdCardFiles($('#productId').val());
                    }else if(fileType == 'cpr_publish_contract') {
                        PRODUCTPAGE.refreshProductPublishContractFiles($('#productId').val());
                    }else if(fileType == 'cpr_contract_to_sale') {
                        PRODUCTPAGE.refreshProductToSaleContractFiles($('#productId').val());
                    }else if(fileType == 'prod_pic') {
                        PRODUCTPAGE.refreshProductPicFiles($('#productId').val());
                    }
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            },
            error: function(data) {

            }
        }
    );
};

PRODUCTPAGE.refreshProductFiles = function() {
    var productId = $('#productId').val();
    PRODUCTPAGE.refreshProductCopyrightContractFiles(productId);
    PRODUCTPAGE.refreshProductCopyrightPageFiles(productId);
    PRODUCTPAGE.refreshProductAuthorIdCardFiles(productId);
    PRODUCTPAGE.refreshProductGrantPaperFiles(productId);
    PRODUCTPAGE.refreshProductPublishContractFiles(productId);
    PRODUCTPAGE.refreshProductToSaleContractFiles(productId);
    PRODUCTPAGE.refreshProductPicFiles(productId);
};

PRODUCTPAGE.refreshProductCopyrightContractFiles = function(productId) {
    $.get(
        '/copyright/contractFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    PRODUCTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files, editable: true});
                $('#copyrightContractFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

PRODUCTPAGE.refreshProductCopyrightPageFiles = function(productId) {
    $.get(
        '/copyright/copyrightPageFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    PRODUCTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files, editable: true});
                $('#copyrightPageFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

PRODUCTPAGE.refreshProductAuthorIdCardFiles = function(productId) {
    $.get(
        '/copyright/authorIdCardFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    PRODUCTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files, editable: true});
                $('#copyrightAuthorIdCardFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

PRODUCTPAGE.refreshProductGrantPaperFiles = function(productId) {
    $.get(
        '/copyright/grantPaperFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    PRODUCTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files, editable: true});
                $('#copyrightGrantPaperFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

PRODUCTPAGE.refreshProductPublishContractFiles = function(productId) {
    $.get(
        '/copyright/publishContractFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    PRODUCTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files, editable: true});
                $('#copyrightPublishContractFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

PRODUCTPAGE.refreshProductToSaleContractFiles = function(productId) {
    $.get(
        '/copyright/toSaleContractFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    PRODUCTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files, editable: true});
                $('#contractToSaleFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

PRODUCTPAGE.convertTypeName = function(file) {
    if(file.type == '1') {
        file.typeName = 'cpr_contract';
    }else if(file.type == '2') {
        file.typeName = 'cpr_copyright_page';
    }else if(file.type == '3') {
        file.typeName = 'cpr_grant_paper';
    }else if(file.type == '4') {
        file.typeName = 'cpr_author_id_card';
    }else if(file.type == '5') {
        file.typeName = 'cpr_publish_contract';
    }else if(file.type == '6') {
        file.typeName = 'cpr_contract_to_sale';
    }
    return file;
};

PRODUCTPAGE.refreshProductPicFiles = function(productId) {
    $.get(
        '/product/productPicFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                var filesHtml = nunjucks.render('../../../js/product/product_pics.tmpl', {files: files});
                $('#prodPicFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

PRODUCTPAGE.deleteCopyrightFile = function(id, name, type) {
    var deleteUrl;
    if(type == 'cpr_contract'
        || type == 'cpr_copyright_page'
        || type == 'cpr_grant_paper'
        || type == 'cpr_author_id_card'
        || type == 'cpr_publish_contract'
        || type == 'cpr_contract_to_sale'
    ){
        deleteUrl = "/copyright/deleteCopyrightFile";
    }else if(type == 'prod_pic'){
        deleteUrl = "/product/deletePicture";
    }
    EMARS_COMMONS.showPrompt("您真的要删除文件[" + name + "]吗？", function() {
        $.post(
            "/copyright/deleteCopyrightFile",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    if(type == 'cpr_contract') {
                        PRODUCTPAGE.refreshProductCopyrightContractFiles($('#productId').val());
                    }else if(type == 'cpr_copyright_page') {
                        PRODUCTPAGE.refreshProductCopyrightPageFiles($('#productId').val());
                    }else if(type == 'cpr_grant_paper') {
                        PRODUCTPAGE.refreshProductGrantPaperFiles($('#productId').val());
                    }else if(type == 'cpr_author_id_card') {
                        PRODUCTPAGE.refreshProductAuthorIdCardFiles($('#productId').val());
                    }else if(type == 'cpr_publish_contract') {
                        PRODUCTPAGE.refreshProductPublishContractFiles($('#productId').val());
                    }else if(type == 'cpr_contract_to_sale') {
                        PRODUCTPAGE.refreshProductToSaleContractFiles($('#productId').val());
                    }else if(type == 'prod_pic'){
                        PRODUCTPAGE.refreshProductPicFiles($('#productId').val());
                    }
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
};

PRODUCTPAGE.popChangeProductionStateModel = function (id) {
    $.get(
        "/product/product",
        {id: id},
        function(data) {
            if (data.code == '0') {
                var prod = data.data;
                $('#inputEditId').val(prod.id);
                $("input[name='inputProductionState'][value='" + prod.productionState + "']").prop("checked", "checked");
                if(prod.productionState == 4) {
                    if(prod.reservationAnnouncers) {
                        var reservationAnnouncerIds = [];
                        for(var i=0; i<prod.reservationAnnouncers.length; i++) {
                            reservationAnnouncerIds.push(prod.reservationAnnouncers[i].announcerId);
                        }
                        $('#inputAnnouncerId').val(reservationAnnouncerIds).trigger('change');
                    }
                    $(".announcer").show();
                }else {
                    $(".announcer").hide();
                }
                $('#changeProductionStateModel').modal('show');

            }else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
};

PRODUCTPAGE.saveProductionState = function () {
    var id = $('#inputEditId').val();
    var state = $("input[name='inputProductionState']:checked").val();
    var announcerIds = $("#inputAnnouncerId").val();
    $.ajax({
        url: '/product/changeProductionState',
        data: {
            id: id, productionState: state, announcerIds : announcerIds
        },
        traditional: true,
        type: 'post',
        success: function (data) {
            if (data.code == '0') {
                var product = data.data;
                $("#productionStateText span").text(product.productionStateText);
                $("#productionStateText button").attr("onclick", "PRODUCTPAGE.popChangeProductionStateModel(" + product.id + ");");
                $('#changeProductionStateModel').modal('hide');
            } else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    })

};

PRODUCTPAGE.loadAnnouncers = function() {
    $.get(
        "/system/allAnnouncers",
        function(data) {
            if(data.code == '0') {
                var announcers = data.data;
                var html = '';
                for(var i = 0; i < announcers.length; i ++) {
                    var announcer = announcers[i];
                    var announcerName = announcer.name;
                    if(announcer.pseudonym) {
                        announcerName = announcerName + "(" + announcer.pseudonym + ")";
                    }
                    html += '<option value="' + announcer.id + '">' + announcerName + '</option>';
                }
                $('#inputAnnouncerId').empty().append(html);
                $('#inputAnnouncerId').select2({
                    dropdownParent: $("#changeProductionStateModel")
                });
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
};
