;

var SALECONTRACTPAGE = {};
var saleDropzoneObj;

var saleDocType = {
    1: 'saleContractFile',
    2: 'saleProductsList'
}

$(document).ready(function(){
    $('#saledropzonejs').dropzone({
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
            saleDropzoneObj = this;
            this.on("success", function (file, message) {
                var fileMetaDatas = message.data;
                var saleFileMetas = $('#saleFileMetas').data('postData');
                if(!saleFileMetas) {
                    saleFileMetas = [];
                }
                for(var i=0; i<fileMetaDatas.length; i++) {
                    saleFileMetas.push(fileMetaDatas[i]);
                }
                $('#saleFileMetas').data('postData', saleFileMetas);
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

SALECONTRACTPAGE.loadSaleProduct = function (saleProductId, editable) {
    SALECONTRACTPAGE.refreshProductDocs(saleProductId, editable);
}

SALECONTRACTPAGE.popUploadSaleFileModal = function(saleProductId, type) {
    SALECONTRACTPAGE.clearUploadSaleFileModal();
    $('#uploadSaleFileType').val(type);
    $('#uploadFileSaleProductId').val(saleProductId);
    $('#uploadSaleFileModal').modal('show');
};

SALECONTRACTPAGE.clearUploadSaleFileModal = function() {
    saleDropzoneObj.emit("resetFiles");
    $('#saleFileMetas').removeData('postData');
    $('#uploadSaleFileType').val('');
    $('#uploadFileSaleProductId').val('');
    $('#uploadSaleFileModal').modal('show');
};

SALECONTRACTPAGE.submitSaleProductFiles = function () {
    var saveCopyrightFileUrl = '/sale/saveSaleProductFiles';
    var fileType = $('#uploadSaleFileType').val();
    var saleProductId = $('#uploadFileSaleProductId').val();
    var saleFileMetas = $('#saleFileMetas').data('postData');
    var postUrl;
    for(var i=0; i<saleFileMetas.length; i++) {
        saleFileMetas[i].type = fileType;
        postUrl = saveCopyrightFileUrl;
        saleFileMetas[i].saleProductId = saleProductId;
    }
    $.ajax(
        {
            url: postUrl,
            data: JSON.stringify(saleFileMetas),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("文件保存成功！");
                    $('#uploadSaleFileModal').modal('hide');
                    SALECONTRACTPAGE.refreshFilesList(saleProductId, fileType, true);
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            },
            error: function(data) {

            }
        }
    );
};

SALECONTRACTPAGE.refreshFilesList = function(saleProductId, type, editable) {
    $.get(
        '/make/getSaleDocs',
        {saleProductId: saleProductId, type: type},
        function(data) {
            if(data.code == '0') {
                var docs = data.data;
                var filesHtml = nunjucks.render('../../../js/sale/sale_docs.tmpl', {saleProductId: saleProductId, docs: docs, editable: editable});
                $('#' + saleProductId + '_' + saleDocType[type] + 'List').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

SALECONTRACTPAGE.deleteDoc = function (id, name, fileType, saleProductId) {
    EMARS_COMMONS.showPrompt("您真的要删除文件[" + name + "]吗？", function() {
        $.post(
            "/make/deleteDoc",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    SALECONTRACTPAGE.refreshFilesList(saleProductId, fileType, true);
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);

};

SALECONTRACTPAGE.refreshProductDocs = function (saleProductId, editable) {
    for(var key in saleDocType) {
        SALECONTRACTPAGE.refreshFilesList(saleProductId, key, editable);
    }
};