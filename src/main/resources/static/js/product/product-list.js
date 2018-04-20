;

var PRODUCTLIST = {};
var productTable;
var dropzoneObj;

$(document).ready(function(){
    PRODUCTLIST.initProductTbl();
    PRODUCTLIST.loadCategories();

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
                console.log(message);
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

PRODUCTLIST.initProductTbl = function(){
    productTable = $('#productTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/product/products',
            "data": function(d) {
                var productName = $('#inputSearchProductName').val();
                if(productName && $.trim(productName).length > 0) {
                    d.productName = productName;
                }
                var isbn = $('#inputSearchIsbn').val();
                if(isbn && $.trim(isbn).length > 0) {
                    d.isbn = isbn;
                }
                var subjectId = $('#inputSearchSubject').val();
                if(subjectId && $.trim(subjectId).length > 0) {
                    d.subjectId = subjectId;
                }
            }

        },
        language: {
            "paginate": {
                "first":      "首页",
                "previous":   "上一页",
                "next":       "下一页",
                "last":       "尾页"
            },
            "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项"
        },
        "columns": [
            {
                "data": "name"
            },
            {
                "render": function (data, type, full) {
                    if(full.authorPseudonym) {
                        return full.authorName + "（" + full.authorPseudonym + "）";
                    }else{
                        return full.authorName;
                    }

                }
            },
            {
                "data": "isbn"
            },
            {
                "data": "subjectName"
            },
            {
                "render": function(data, type, full) {
                    if(full.type == 'wz') {
                        return full.wordCount + "万字";
                    }else{
                        return full.wordCount + "集";
                    }
                }
            },
            {
                "data": "stockInText"
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="/product/productDetail/' + full.id + '" target="_blank">查看</a>';
                        htmlText += '<span class="small">&nbsp;|&nbsp;</span> ';
                        htmlText += '<a onclick="PRODUCTLIST.popEditProduct(' + full.id + ');">编辑</a>  ';
                    return htmlText;
                }
            }
        ]
    });
};

PRODUCTLIST.loadCategories = function () {
    $.get(
        "/system/textSubjects",
        function(data) {
            if(data.code == '0') {
                var subjects = data.data;
                var html = '';
                for(var i = 0; i < subjects.length; i ++) {
                    var subject = subjects[i];
                    html += '<option value="' + subject.id + '">' + subject.name + '</option>';
                }
                $('#inputSubject').empty().append(html);
                $('#inputSearchSubject').empty()
                    .append('<option value="">全部</option>')
                    .append(html);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
}

PRODUCTLIST.popEditProduct = function (id) {
    PRODUCTLIST.clearProductModal();
    $('#productModal .modal-title').text("编辑作品");
    $('#productModal').modal('show');
    $.get(
        "/product/product",
        {id: id},
        function(data) {
            if(data.code == '0') {
                PRODUCTLIST.clearProductModal();
                var prod = data.data;

                $('#inputId').val(prod.id);
                $('#inputName').val(prod.name);
                if(prod.authorName) {
                    $('#inputAuthorName').val(prod.authorName);
                    $('#inputAuthorPseudonym').val(prod.authorPseudonym);
                }else{
                    $('#inputAuthorName').val('');
                    $('#inputAuthorPseudonym').val('');
                }
                $('#inputWordCount').val(prod.wordCount);
                var subj = $('#inputSubject').find('option[value=' + prod.subjectId + ']').index();
                $('#inputSubject')[0].selectedIndex = subj;
                var pubState = $('#inputPublishState').find('option[value=' + prod.publishState + ']').index();
                $('#inputPublishState')[0].selectedIndex = pubState;

                if(prod.publishState == 0) {
                    var pubYear = $('#inputPublishYear').find('option[value=' + prod.publishYear + ']').index;
                    $('#inputPublishYear')[0].selectedIndex = pubYear;
                    $('#inputPress').val(prod.press);
                    $('#inputIsbn').val(prod.isbn);
                }else{
                    $('#publishYearDiv').hide();
                    $('#pressDiv').hide();
                    $('#isbnDiv').hide();
                    var finishYear = $('#inputFinishYear').find('option[value=' + prod.finishYear + ']').index();
                    $('#inputFinishYear')[0].selectedIndex = finishYear;
                    $('#finishYearDiv').show();
                    if(prod.publishState == 1) {
                        $('#inputWebsite').val(prod.website);
                        $('#websiteDiv').show();
                    }
                }
                $('#inputSummary').val(prod.summary);
                if(prod.samples && prod.samples.length > 0) {
                    $('#samplesShowDiv a').attr('href', prod.samples[0].fileUrl);
                    $('#samplesUploadDiv').hide();
                    $('#samplesShowDiv').show();
                    $('#inputSamples').val(prod.samples[0].fileUrl);
                }
                if(prod.logoUrl) {
                    $('#coverShowDiv a').attr('href', prod.logoUrl);
                    $('#coverUploadDiv').hide();
                    $('#coverShowDiv').show();
                    $('#inputCover').val(prod.logoUrl);
                }
                if(prod.copyrightFiles) {
                    $('#copyrightsShowDiv').empty();
                    var copyrightFiles = '';
                    for(var i=0; i<prod.copyrightFiles.length; i++) {
                        var html = "<li style='margin-bottom: 2px;'><a href='" + prod.copyrightFiles[i].fileUrl + "' class='label bg-gray'>" + prod.copyrightFiles[i].name + "</a></li>";
                        $('#copyrightsShowDiv').append(html);
                        if(i > 0) {
                            copyrightFiles += ",";
                        }
                        copyrightFiles += prod.copyrightFiles[i].fileUrl;
                    }
                    $('#copyrightsUploadDiv').hide();
                    $('#copyrightsShowDiv').show();

                    $('#inputCopyrights').val(copyrightFiles);
                }

                if(prod.hasAudio == '1') {
                    $('#hasAudio').attr('checked', true);
                    $('#inputAudioCopyright').find('option[value=' + prod.audioCopyright + ']').prop('selected', true);
                    $('#inputAudioDesc').val(prod.audioDesc);
                    $('#audioCopyrightDiv').show();
                    $('#audioDescDiv').show();
                }else{
                    $('#noAudio').prop('checked', true);
                    $('#inputAudioDesc').val('');
                    $('#audioCopyrightDiv').hide();
                    $('#audioDescDiv').hide();
                }

                $('#productModal').modal('show');

            }else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}


PRODUCTLIST.submitProduct = function(submit) {

    var id = $('#inputId').val();
    var name = $('#inputName').val();
    var authorName = $('#inputAuthorName').val();
    var authorPseudonym = $('#inputAuthorPseudonym').val();
    var wordCount = $('#inputWordCount').val();
    var subject = $('#inputSubject').val();
    var publishState = $('#inputPublishState').val();
    var publishYear = $('#inputPublishYear').val();
    var press = $('#inputPress').val();
    var finishYear = $('#inputFinishYear').val();
    var website = $('#inputWebsite').val();
    var summary = $('#inputSummary').val();
    var hasAudio = $('input[name=hasAudio]:checked').val();
    var audioCopyright = $('#inputAudioCopyright').val();
    var audioDesc = $('#inputAudioDesc').val();
    var samples = $('#inputSamples').val();
    var isbn = $('#inputIsbn').val();
    //var copyrights = $('#inputCopyrights').val();
    var cover = $('#inputCover').val();
    
    var type;
    if(id) {
        type = "1";
    }else {
        type = "0";
    }
    

    $.post(
        "/product/createProduct",
        {
            'id': id,
            'name': name,
            'authorName': authorName,
            'authorPseudonym': authorPseudonym,
            'wordCount': wordCount,
            'subject': subject,
            'publishState': publishState,
            'publishYear': publishYear,
            'press': press,
            'finishYear': finishYear,
            'website': website,
            'summary': summary,
            'hasAudio': hasAudio,
            'audioCopyright': audioCopyright,
            'audioDesc': audioDesc,
            'samples': samples,
            'submit': submit,
            'isbn': isbn,
            'logoUrl' : cover,
            'type': type
        },
        function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("作者保存成功！");
                $('#authorModal').modal('hide');
                AUTHORLIST.refreshAuthorTbl();
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}


PRODUCTLIST.clearProductModal = function () {
    $('#inputId').val('');
    $('#inputName').val('');
    $('#inputAuthorName').val('');
    $('#inputAuthorPseudonym').val('');
    $('#inputWordCount').val('');
    $("#inputSubject option:first").prop("selected", 'selected');
    $("#inputPublishState option:first").prop("selected", 'selected');
    $("#inputPublishYear option:first").prop("selected", 'selected');
    $('#inputPress').val('');
    $("#inputFinishYear option:first").prop("selected", 'selected');
    $('#inputWebsite').val('');
    $('#inputSummary').val('');
    $('#hasAudio').prop('checked', false);
    $('#inputIsbn').val('');
    $("#inputAudioCopyright option:first").prop("selected", 'selected');
    $('#inputAudioDesc').val('');

    $('#inputSamples').val('');
    $('#uploadedFile').empty();
    $('#samplesShowDiv').hide();

    $('#inputCover').val('');
    $('#uploadedCoverFile').empty();
    $('#coverShowDiv').hide();

    $('#inputCopyrights').val('');
    $('#uploadedCopyrightFiles').empty();
    $('#copyrightsShowDiv').hide();

}

PRODUCTLIST.popProductDetailModal = function(id){
    $.get(
        "/product/productDetail",
        {id: id},
        function(data) {
            $('#productDetail').modal('show');
            $("#productDetail .modal-body")
                .empty()
                .append(data);
        }
    )
};

PRODUCTLIST.deleteProduct = function (id, name) {
    EMARS_COMMONS.showPrompt("您真的要删除作品[" + name + "]吗？", function() {
        $.post(
            "/product/deleteProduct",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    PRODUCTLIST.refreshProductTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null)
};

PRODUCTLIST.searchProducts = function () {
    productTable.api().ajax.reload();
};

PRODUCTLIST.refreshProductTbl = function () {
    productTable.api().ajax.reload(null, false);

};

PRODUCTLIST.popUploadFileModal = function(type) {
    PRODUCTLIST.clearUploadFileModal();
    $('#uploadFileType').val(type);
    $('#uploadFileModal').modal('show');
};

PRODUCTLIST.clearUploadFileModal = function() {
    dropzoneObj.emit("resetFiles");
    $('#uploadFileType').val('');
    $('#uploadFileModal').modal('show');
};