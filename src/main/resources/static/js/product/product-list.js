;

var PRODUCTLIST = {};
var productTable;

$(document).ready(function(){
    PRODUCTLIST.initProductTbl();
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
            "url": '/products',
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
                "width": "20%",
                "render": function(data, type, full) {
                    var htmlText = '<a onclick="PRODUCTLIST.popEditProduct(' + full.id + ');">编辑</a>  ';
                    htmlText += '<span class="small">|</span> ' +
                        '<a onclick="PRODUCTLIST.deleteProduct(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

PRODUCTLIST.searchProducts = function () {
    productTable.api().ajax.reload(null, false);
}
