;

var PRODUCTLIST = {};
var productTable;

$(document).ready(function(){
    PRODUCTLIST.initProductTbl();
    PRODUCTLIST.loadCategories();
    PRODUCTLIST.loadAuthors();
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
                        if(full.stockIn == '0') {
                            htmlText += '<span class="small">&nbsp;|&nbsp;</span> ';
                            htmlText += '<a href="javascript:void(0);" onclick="PRODUCTLIST.stockInProduct(' + full.id + ',\''+full.name+'\');">入库</a>  ';
                        }
                    return htmlText;
                }
            }
        ]
    });
};

PRODUCTLIST.loadAuthors = function() {
    $.get(
        "/system/allAuthors",
        function(data) {
            if(data.code == '0') {
                var authors = data.data;
                var html = '';
                for(var i = 0; i < authors.length; i ++) {
                    var author = authors[i];
                    var authorName = author.name;
                    if(author.pseudonym) {
                        authorName = authorName + "(" + author.pseudonym + ")";
                    }
                    html += '<option value="' + author.id + '">' + authorName + '</option>';
                }
                $('#inputAuthorId').empty().append(html);
                $('#inputAuthorId').select2({
                    dropdownParent: $("#productModal")
                });
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
};

PRODUCTLIST.loadCategories = function() {
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
                $('#inputSubject').select2({
                    dropdownParent: $("#productModal")
                });
                $('#inputSearchSubject').empty()
                    .append('<option value="">全部</option>')
                    .append(html);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
};

PRODUCTLIST.popEditProduct = function (id) {
    PRODUCTLIST.clearProductModal();
    $('#productModal .modal-title').text("编辑作品信息");
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
                if(prod.authorId) {
                    $('#inputAuthorId').val(prod.authorId).trigger('change');
                }
                $('#inputWordCount').val(prod.wordCount);
                $('#inputSubject').val(prod.subjectId).trigger('change');
                var pubState = $('#inputPublishState').find('option[value=' + prod.publishState + ']').index();
                $('#inputPublishState')[0].selectedIndex = pubState;

                if(prod.publishState == 1) {
                    $('#inputPress').val(prod.press);
                    $('#inputIsbn').val(prod.isbn);
                }
                $('#inputDesc').val(prod.desc);

                $('#productModal').modal('show');

            }else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}


PRODUCTLIST.submitProduct = function() {

    var id = $('#inputId').val();
    var name = $('#inputName').val();
    var authorId = $('#inputAuthorId').val();
    var wordCount = $('#inputWordCount').val();
    var subject = $('#inputSubject').val();
    var publishState = $('#inputPublishState').val();
    var press = $('#inputPress').val();
    var isbn = $('#inputIsbn').val();
    var desc = $('#inputDesc').val();

    var postData = {
        'id': id,
        'name': name,
        'authorId': authorId,
        'wordCount': wordCount,
        'subjectId': subject,
        'publishState': publishState,
        'press': press,
        'isbn': isbn,
        'desc': desc
    };

    $.ajax({
        url: "/product/updateProduct",
        type: 'POST',
        data: JSON.stringify(postData),
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("作品保存成功！");
                $('#productModal').modal('hide');
                PRODUCTLIST.refreshProductTbl();
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    });
};

PRODUCTLIST.clearProductModal = function () {
    $('#inputId').val('');
    $('#inputName').val('');
    $('#inputAuthorId option:first').prop("selected", 'selected').trigger('change');
    $('#inputWordCount').val('');
    $("#inputSubject option:first").prop("selected", 'selected').trigger('change');
    $("#inputPublishState option:first").prop("selected", 'selected').trigger('change');
    $('#inputPress').val('');
    $('#inputIsbn').val('');
    $('#inputDesc').val('');
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
    }, null);
};

PRODUCTLIST.searchProducts = function () {
    productTable.api().ajax.reload();
};

PRODUCTLIST.refreshProductTbl = function () {
    productTable.api().ajax.reload(null, false);

};

PRODUCTLIST.stockInProduct = function(id, name) {
    EMARS_COMMONS.showPrompt("您真的对作品[" + name + "]执行入库操作吗？", function() {
        $.post(
            "/product/stockIn",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("入库成功！");
                    PRODUCTLIST.refreshProductTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
};