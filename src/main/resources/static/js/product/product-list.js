;

var PRODUCTLIST = {};
var productTable;

$(document).ready(function(){
    PRODUCTLIST.initProductTbl();
    PRODUCTLIST.loadCategories();
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
                var authorName = $('#inputSearchAuthorName').val();
                if(authorName && $.trim(authorName).length > 0) {
                    d.authorName = authorName;
                }
                var isbn = $('#inputSearchIsbn').val();
                if(isbn && $.trim(isbn).length > 0) {
                    d.isbn = isbn;
                }
                var subjectId = $('#inputSearchSubject').val();
                if(subjectId && $.trim(subjectId).length > 0) {
                    d.subjectId = subjectId;
                }
                var publishState = $('#inputSearchPublishState').val();
                if(publishState && $.trim(publishState).length > 0) {
                    d.publishState = publishState;
                }
                var state = $('#inputSearchState').val();
                if(state && $.trim(state).length > 0) {
                    d.state = state;
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
                "data": "id",
                "width": "4%",
                "render": function(data, type) {
                    var checkboxHtml = '<label class="custom-control custom-checkbox custom-control-blank" for="productTblCheck_' + data + '">'
                        + '<input id="productTblCheck_' + data + '" type="checkbox" value="' + data + '" class="custom-control-input tblRowCheckbox" onclick="AUTHORLIST.checkRow();"/>'
                        + '<span class="custom-control-indicator"></span>'
                        + '</label>';
                    return checkboxHtml;
                }
            },
            {
                "data": "name"
            },
            {
                "data": "authorName"
            },
            {
                "data": "publishStateText"
            },
            {
                "render": function (data, type, full) {
                    if(full.publishYear) {
                        return full.publishYear;
                    }else if(full.finishYear){
                        return full.finishYear;
                    }else{
                        return '';
                    }
                }
            },
            {
                "data": "subjectName"
            },
            {
                "data": "stateText"
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="javascript:;" onclick="PRODUCTLIST.popProductDetailModal(' + full.id + ')">查看</a>';
                    // var htmlText = '<a href="/product/productDetail?id=' + full.id + ' target="_blank">查看</a> ';

                    if(full.state == '0' || full.state == '101') {
                        htmlText += '<span class="small">|</span> ';
                        htmlText += '<a onclick="PRODUCTLIST.popEditProduct(' + full.id + ');">编辑</a>  ';
                        htmlText += '<span class="small">|</span> ' +
                            '<a onclick="PRODUCTLIST.deleteProduct(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    }
                    return htmlText;
                }
            }
        ]
    });
};

PRODUCTLIST.popNewProductModal = function () {
    PRODUCTLIST.clearProductModal();
    $('#productModal .modal-title').text("新建作品题材");
    $('#productModal').modal('show');
}

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
                if(prod.author) {
                    $('#inputAuthorName').val(prod.author.name);
                    $('#inputAuthorPseudonym').val(prod.author.pseudonym);
                }else{
                    $('#inputAuthorName').val('');
                    $('#inputAuthorPseudonym').val('');
                }
                $('#inputWordCount').val(prod.wordCount);
                var subj = $('#inputSubject').find('option[value=' + prod.subject.id + ']').index();
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
}

PRODUCTLIST.searchProducts = function () {
    productTable.api().ajax.reload();
}


PRODUCTLIST.refreshProductTbl = function () {
    productTable.api().ajax.reload(null, false);

}