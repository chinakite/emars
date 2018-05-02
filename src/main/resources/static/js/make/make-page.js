;

var MAKECONTRACTPAGE = {};
var dropzoneObj;

var mcDocType = {
    1: 'makeContractFile',
    2: 'makeContractBroadcasterFile',
    3: 'talentStationFile',
    4: 'talentIdCardFile',
    5: 'operationFile',
}

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

});

MAKECONTRACTPAGE.loadMcProducts = function (mcProductIds) {
    var ids = mcProductIds.split(',');
    for(var k in ids) {
        MAKECONTRACTPAGE.refreshProductDocs(ids[k]);
    }
}

MAKECONTRACTPAGE.popUploadFileModal = function(mcProductId, type) {
    MAKECONTRACTPAGE.clearUploadFileModal();
    $('#uploadFileType').val(type);
    $('#uploadFileMcProductId').val(mcProductId);
    $('#uploadFileModal').modal('show');
};

MAKECONTRACTPAGE.clearUploadFileModal = function() {
    dropzoneObj.emit("resetFiles");
    $('#uploadFileType').val('');
    $('#uploadFileMcProductId').val('');
    $('#uploadFileModal').modal('show');
};

MAKECONTRACTPAGE.submitMcProductFiles = function () {
    var saveCopyrightFileUrl = '/make/saveMcProductFiles';
    var fileType = $('#uploadFileType').val();
    var mcProductId = $('#uploadFileMcProductId').val();
    var fileMetas = $('#fileMetas').data('postData');
    var postUrl;
    for(var i=0; i<fileMetas.length; i++) {
        fileMetas[i].type = fileType;
        postUrl = saveCopyrightFileUrl;
        fileMetas[i].mcProductId = mcProductId;
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
                    MAKECONTRACTPAGE.refreshFilesList(mcProductId, fileType);
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            },
            error: function(data) {

            }
        }
    );
}

MAKECONTRACTPAGE.clearUploadFileModal = function () {
    dropzoneObj.emit("resetFiles");
    $('#uploadFileType').val('');
    $('#uploadFileMcProductId').val('');
    $('#uploadFileModal').modal('show');
}

MAKECONTRACTPAGE.refreshFilesList = function(mcProductId, type) {
    $.get(
        '/make/getMcDocs',
        {mcProductId: mcProductId, type: type},
        function(data) {
            console.log(data.data);
            if(data.code == '0') {
                var docs = data.data;
                var filesHtml = nunjucks.render('../../../js/make/mc_docs.tmpl', {docs: docs, editable: true});
                console.log(filesHtml);
                $('#' + mcProductId + '_' + mcDocType[type] + 'List').html(filesHtml);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

MAKECONTRACTPAGE.deleteDoc = function (id, name, type) {
    
};

MAKECONTRACTPAGE.refreshProductDocs = function (mcProductId) {
    for(var key in mcDocType) {
        MAKECONTRACTPAGE.refreshFilesList(mcProductId, key);
    }
};