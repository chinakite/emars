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
    $('#uploadFileType').val('');
    $('#uploadFileProductId').val('');
    $('#uploadFileModal').modal('show');
};

PRODUCTPAGE.submitProductFiles = function() {
    var fileType = $('#uploadFileType').val();
    var productId = $('#uploadFileProductId').val();
    var fileMetas = $('#fileMetas').data('postData');
    for(var i=0; i<fileMetas.length; i++) {
        if(fileType == 'cpr_contract') {
            fileMetas[i].type = '1';
        }else if(fileType == 'cpr_copyright_page') {
            fileMetas[i].type = '2';
        }else if(fileType == 'cpr_grant_paper') {
            fileMetas[i].type = '3';
        }else if(fileType == 'cpr_author_id_card') {
            fileMetas[i].type = '4';
        }else if(fileType == 'cpr_publish_contract') {
            fileMetas[i].type = '5';
        }else{
            fileMetas[i].type = '0';
        }
        fileMetas[i].productId = productId;
    }
    $.ajax(
        {
            url: '/copyright/saveCopyrightFiles',
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
};

PRODUCTPAGE.refreshProductCopyrightContractFiles = function(productId) {
    $.get(
        '/copyright/contractFiles',
        {productId: productId},
        function(data) {
            if(data.code == '0') {
                var files = data.data;
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files});
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
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files});
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
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files});
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
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files});
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
                var filesHtml = nunjucks.render('../../../js/product/product_files.tmpl', {files: files});
                $('#copyrightPublishContractFileList').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

PRODUCTPAGE.deleteCopyrightFile = function(id, name, type) {
    EMARS_COMMONS.showPrompt("您真的要删除文件[" + name + "]吗？", function() {
        $.post(
            "/copyright/deleteCopyrightFile",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    if(type == 'cpr_contract') {
                        PRODUCTPAGE.refreshProductCopyrightContractFiles($('#productId').val());
                    }else if(fileType == 'cpr_copyright_page') {
                        PRODUCTPAGE.refreshProductCopyrightPageFiles($('#productId').val());
                    }else if(fileType == 'cpr_grant_paper') {
                        PRODUCTPAGE.refreshProductGrantPaperFiles($('#productId').val());
                    }else if(fileType == 'cpr_author_id_card') {
                        PRODUCTPAGE.refreshProductAuthorIdCardFiles($('#productId').val());
                    }else if(fileType == 'cpr_publish_contract') {
                        PRODUCTPAGE.refreshProductPublishContractFiles($('#productId').val());
                    }
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
};