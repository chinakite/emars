;

var COPYRIGHTPAGE = {};
var dropzoneObj;

var mcDocType = {
    1: 'makeContractFile',
    2: 'makeContractBroadcasterFile',
    3: 'talentStationFile',
    4: 'talentIdCardFile',
    5: 'operationFile'
}

$(document).ready(function(){
    $('#dropzonejs').dropzone({
        parallelUploads: 1,
        maxFilesize:     20,
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

});

COPYRIGHTPAGE.loadProduct = function (productId) {
    COPYRIGHTPAGE.refreshProductDocs(productId);
}

COPYRIGHTPAGE.popUploadFileModal = function(productId, type) {
    COPYRIGHTPAGE.clearUploadFileModal();
    $('#uploadFileType').val(type);
    $('#uploadFileProductId').val(productId);
    $('#uploadFileModal').modal('show');
};

COPYRIGHTPAGE.clearUploadFileModal = function() {
    dropzoneObj.emit("resetFiles");
    $('#fileMetas').removeData('postData');
    $('#uploadFileType').val('');
    $('#uploadFileProductId').val('');
    $('#uploadFileModal').modal('show');
};

COPYRIGHTPAGE.submitProductFiles = function() {
    var saveCopyrightFileUrl = '/copyright/saveCopyrightFiles';
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
                        COPYRIGHTPAGE.refreshProductCopyrightContractFiles($('#uploadFileProductId').val());
                    }else if(fileType == 'cpr_copyright_page') {
                        COPYRIGHTPAGE.refreshProductCopyrightPageFiles($('#uploadFileProductId').val());
                    }else if(fileType == 'cpr_grant_paper') {
                        COPYRIGHTPAGE.refreshProductGrantPaperFiles($('#uploadFileProductId').val());
                    }else if(fileType == 'cpr_author_id_card') {
                        COPYRIGHTPAGE.refreshProductAuthorIdCardFiles($('#uploadFileProductId').val());
                    }else if(fileType == 'cpr_publish_contract') {
                        COPYRIGHTPAGE.refreshProductPublishContractFiles($('#uploadFileProductId').val());
                    }else if(fileType == 'cpr_contract_to_sale') {
                        COPYRIGHTPAGE.refreshProductToSaleContractFiles($('#uploadFileProductId').val());
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

COPYRIGHTPAGE.deleteCopyrightFile = function (productId, id, name, fileType) {
    EMARS_COMMONS.showPrompt("您真的要删除文件[" + name + "]吗？", function() {
        $.post(
            "/copyright/deleteCopyrightFile",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    if(fileType == 'cpr_contract') {
                        COPYRIGHTPAGE.refreshProductCopyrightContractFiles(productId);
                    }else if(fileType == 'cpr_copyright_page') {
                        COPYRIGHTPAGE.refreshProductCopyrightPageFiles(productId);
                    }else if(fileType == 'cpr_grant_paper') {
                        COPYRIGHTPAGE.refreshProductGrantPaperFiles(productId);
                    }else if(fileType == 'cpr_author_id_card') {
                        COPYRIGHTPAGE.refreshProductAuthorIdCardFiles(productId);
                    }else if(fileType == 'cpr_publish_contract') {
                        COPYRIGHTPAGE.refreshProductPublishContractFiles(productId);
                    }else if(fileType == 'cpr_contract_to_sale') {
                        COPYRIGHTPAGE.refreshProductToSaleContractFiles(productId);
                    }else if(fileType == 'prod_pic'){
                        COPYRIGHTPAGE.refreshProductPicFiles(productId);
                    }
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);

};

COPYRIGHTPAGE.refreshProductDocs = function (productId) {
    COPYRIGHTPAGE.refreshProductCopyrightContractFiles(productId);
    COPYRIGHTPAGE.refreshProductCopyrightPageFiles(productId);
    COPYRIGHTPAGE.refreshProductAuthorIdCardFiles(productId);
    COPYRIGHTPAGE.refreshProductGrantPaperFiles(productId);
    COPYRIGHTPAGE.refreshProductPublishContractFiles(productId);
    COPYRIGHTPAGE.refreshProductToSaleContractFiles(productId);
};

COPYRIGHTPAGE.refreshProductCopyrightContractFiles = function(productId) {
    $.get(
        '/copyright/contractFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;

                for(var i=0; i<files.length; i++) {
                    COPYRIGHTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/copyright/copyright_files.tmpl', {files: files, editable: true});
                $('#'+ productId +'_copyrightContractFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

COPYRIGHTPAGE.refreshProductCopyrightPageFiles = function(productId) {
    $.get(
        '/copyright/copyrightPageFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    COPYRIGHTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/copyright/copyright_files.tmpl', {files: files, editable: true});
                $('#'+productId+'_copyrightPageFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

COPYRIGHTPAGE.refreshProductAuthorIdCardFiles = function(productId) {
    $.get(
        '/copyright/authorIdCardFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    COPYRIGHTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/copyright/copyright_files.tmpl', {files: files, editable: true});
                $('#' + productId + '_copyrightAuthorIdCardFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

COPYRIGHTPAGE.refreshProductGrantPaperFiles = function(productId) {
    $.get(
        '/copyright/grantPaperFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    COPYRIGHTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/copyright/copyright_files.tmpl', {files: files, editable: true});
                $('#'+productId+'_copyrightGrantPaperFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

COPYRIGHTPAGE.refreshProductPublishContractFiles = function(productId) {
    $.get(
        '/copyright/publishContractFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    COPYRIGHTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/copyright/copyright_files.tmpl', {files: files, editable: true});
                $('#'+productId+'_copyrightPublishContractFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

COPYRIGHTPAGE.refreshProductToSaleContractFiles = function(productId) {
    $.get(
        '/copyright/toSaleContractFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                for(var i=0; i<files.length; i++) {
                    COPYRIGHTPAGE.convertTypeName(files[i]);
                }
                var filesHtml = nunjucks.render('../../../js/copyright/copyright_files.tmpl', {files: files, editable: true});
                $('#'+productId+'_contractToSaleFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

COPYRIGHTPAGE.convertTypeName = function(file) {
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
}