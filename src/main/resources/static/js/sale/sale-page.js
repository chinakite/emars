;

var SALECONTRACTPAGE = {};
var saleDropzoneObj;

var mcDocType = {
    1: 'saleContractFile',
    2: 'saleProductsList'
}

$(document).ready(function(){
    $('#mcdropzonejs').dropzone({
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
            mcDropzoneObj = this;
            this.on("success", function (file, message) {
                var fileMetaDatas = message.data;
                var mcFileMetas = $('#mcFileMetas').data('postData');
                if(!mcFileMetas) {
                    mcFileMetas = [];
                }
                for(var i=0; i<fileMetaDatas.length; i++) {
                    mcFileMetas.push(fileMetaDatas[i]);
                }
                $('#mcFileMetas').data('postData', mcFileMetas);
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

SALECONTRACTPAGE.loadMcProduct = function (mcProductId, editable) {
    SALECONTRACTPAGE.refreshProductDocs(mcProductId, editable);
}

SALECONTRACTPAGE.popUploadMcFileModal = function(mcProductId, type) {
    SALECONTRACTPAGE.clearUploadMcFileModal();
    $('#uploadMcFileType').val(type);
    $('#uploadFileMcProductId').val(mcProductId);
    $('#uploadMcFileModal').modal('show');
};

SALECONTRACTPAGE.clearUploadMcFileModal = function() {
    mcDropzoneObj.emit("resetFiles");
    $('#mcFileMetas').removeData('postData');
    $('#uploadMcFileType').val('');
    $('#uploadFileMcProductId').val('');
    $('#uploadMcFileModal').modal('show');
};

SALECONTRACTPAGE.submitMcProductFiles = function () {
    var saveCopyrightFileUrl = '/make/saveMcProductFiles';
    var fileType = $('#uploadMcFileType').val();
    var mcProductId = $('#uploadFileMcProductId').val();
    var mcFileMetas = $('#mcFileMetas').data('postData');
    var postUrl;
    for(var i=0; i<mcFileMetas.length; i++) {
        mcFileMetas[i].type = fileType;
        postUrl = saveCopyrightFileUrl;
        mcFileMetas[i].mcProductId = mcProductId;
    }
    $.ajax(
        {
            url: postUrl,
            data: JSON.stringify(mcFileMetas),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("文件保存成功！");
                    $('#uploadMcFileModal').modal('hide');
                    SALECONTRACTPAGE.refreshFilesList(mcProductId, fileType, true);
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            },
            error: function(data) {

            }
        }
    );
};

SALECONTRACTPAGE.refreshFilesList = function(mcProductId, type, editable) {
    $.get(
        '/make/getMcDocs',
        {mcProductId: mcProductId, type: type},
        function(data) {
            if(data.code == '0') {
                var docs = data.data;
                var filesHtml = nunjucks.render('../../../js/make/mc_docs.tmpl', {mcProductId: mcProductId, docs: docs, editable: editable});
                $('#' + mcProductId + '_' + mcDocType[type] + 'List').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

SALECONTRACTPAGE.deleteDoc = function (id, name, fileType, mcProductId) {
    EMARS_COMMONS.showPrompt("您真的要删除文件[" + name + "]吗？", function() {
        $.post(
            "/make/deleteDoc",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    SALECONTRACTPAGE.refreshFilesList(mcProductId, fileType, true);
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);

};

SALECONTRACTPAGE.refreshProductDocs = function (mcProductId, editable) {
    for(var key in mcDocType) {
        SALECONTRACTPAGE.refreshFilesList(mcProductId, key, editable);
    }
};