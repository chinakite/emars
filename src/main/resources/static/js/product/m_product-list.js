;

var PRODUCTLIST = {};
var productTable;

$(document).ready(function(){
    PRODUCTLIST.searchProducts();
    PRODUCTLIST.loadCategories();
    PRODUCTLIST.loadAuthors();
});

var param = {
    draw:1,
    start: 0,
    "length": 10
};

PRODUCTLIST.searchProducts = function(){
    param.start = 0;
    var productName = $('#inputSearchProductName').val();
    if(productName && $.trim(productName).length > 0) {
        param.productName = productName;
    }else{
        delete param.productName;
    }
    var isbn = $('#inputSearchIsbn').val();
    if(isbn && $.trim(isbn).length > 0) {
        param.isbn = isbn;
    }else{
        delete param.isbn;
    }
    var subjectId = $('#inputSearchSubject').val();
    if(subjectId && $.trim(subjectId).length > 0) {
        param.subjectId = subjectId;
    }else{
        delete param.subjectId;
    }

    $.get(
        "/product/products",
        param,
        function(data) {
            var result = data.data;
            var prodsHtml = nunjucks.render('../../../js/product/m_product_list_item.tmpl', {prods: result});
            $('#prodlist').html(prodsHtml);
            if((param.start + param['length']) >= data.recordsTotal) {
                param.start = data.recordsTotal;
                $('#m_moreProds').hide();
                $('#m_nomoreInfo').show();
            }else{
                param.start = param.start + param['length'];
                $('#m_moreProds').show();
                $('#m_nomoreInfo').hide();
            }
        }
    );
};

PRODUCTLIST.loadMoreProducts = function(){
    $.get(
        "/product/products",
        param,
        function(data) {
            var result = data.data;
            var prodsHtml = nunjucks.render('../../../js/product/m_product_list_item.tmpl', {prods: result});
            $('#prodlist').append(prodsHtml);
            if((param.start + param['length']) >= data.recordsTotal) {
                param.start = data.recordsTotal;
                $('#m_moreProds').hide();
                $('#m_nomoreInfo').show();
            }else{
                param.start = param.start + param['length'];
                $('#m_moreProds').show();
                $('#m_nomoreInfo').hide();
            }
        }
    );
};

PRODUCTLIST.loadAuthors = function(callback) {
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
                if(callback) {
                    callback();
                }
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
                if(prod.authors) {
                    var authorIds = [];
                    for(var i=0; i<prod.authors.length; i++) {
                        authorIds.push(prod.authors[i].id);
                    }
                    $('#inputAuthorId').val(authorIds).trigger('change');
                }
                $('#inputWordCount').val(prod.wordCount);
                $('#inputSubject').val(prod.subjectId).trigger('change');
                var pubState = $('#inputPublishState').find('option[value=' + prod.publishState + ']').index();
                $('#inputPublishState')[0].selectedIndex = pubState;

                if(prod.publishState == 1) {
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
    var authorIds = $('#inputAuthorId').select2('data');
    var wordCount = $('#inputWordCount').val();
    var subject = $('#inputSubject').val();
    var publishState = $('#inputPublishState').val();
    var isbn = $('#inputIsbn').val();
    var desc = $('#inputDesc').val();

    var authors = [];
    for(var i=0; i<authorIds.length; i++) {
        authors.push({id: authorIds[i].id});
    }

    var postData = {
        'id': id,
        'name': name,
        'authors': authors,
        'wordCount': wordCount,
        'subjectId': subject,
        'publishState': publishState,
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

PRODUCTLIST.showAddAuthorPanel = function() {
    PRODUCTLIST.clearAddAuthorPanel();
    $('#productModalContent').hide();
    $('#authorModalContent').show();
};

PRODUCTLIST.hideAddAuthorPanel = function() {
    $('#authorModalContent').hide();
    $('#productModalContent').show();
};

PRODUCTLIST.clearAddAuthorPanel = function() {
    $('#inputAuthorName').val('');
    $('#inputAuthorPseudonym').val('');
    $('#inputAuthorDesc').val('');
};

PRODUCTLIST.addAuthor = function() {
    var aName = $('#inputAuthorName').val();
    var aDesc = $('#inputAuthorDesc').val();
    var aPseudonym = $('#inputAuthorPseudonym').val();

    $.post(
        "/system/createAuthor",
        {
            'name': aName,
            'desc': aDesc,
            'pseudonym': aPseudonym
        },
        function (data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("作者保存成功！");
                PRODUCTLIST.hideAddAuthorPanel();
                var selected = $('#inputAuthorId').select2('data');
                var selectedIds = [];
                for(var i=0; i<selected.length; i++) {
                    selectedIds.push(selected[i]['id']);
                }
                $('#inputAuthorId').select2('destroy');
                $('#inputAuthorId').empty();
                PRODUCTLIST.loadAuthors(function(){
                    var curId = $('#inputAuthorId option').filter(function () {
                        if(aPseudonym) {
                            return $(this).html() == aName+"("+aPseudonym+")";
                        }else{
                            return $(this).html() == aName;
                        }
                    }).val();
                    selectedIds.push(curId);
                    $('#inputAuthorId').val(selectedIds).trigger('change');
                });
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

PRODUCTLIST.viewProductDetail = function(id) {
    window.location.href = '/product/productDetail/' + id;
};