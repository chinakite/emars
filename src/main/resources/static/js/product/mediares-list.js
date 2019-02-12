;

var MEDIARESLIST = {};
var productTable;

$(document).ready(function(){
    MEDIARESLIST.initProductTbl();
    MEDIARESLIST.loadCategories();
});

MEDIARESLIST.initProductTbl = function(){
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
            "url": '/mediares/products',
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
                    var authors = full.authors;
                    var content = '';
                    for(var i=0; i<authors.length; i++) {
                        if(i > 0) {
                            content += '<br/>';
                        }
                        if(authors[i].pseudonym) {
                            content += authors[i].name + "（" + authors[i].pseudonym + "）";
                        }else{
                            content += authors[i].name;
                        }
                    }
                    return content;

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
                    if(full.wordCount) {
                        return full.wordCount + "万字";
                    }else{
                        return "";
                    }
                }
            },
            {
                "render": function(data, type, full) {
                    if(full.section) {
                        return full.section + "集";
                    }else{
                        return "";
                    }
                }
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="/mediares/productDetail/' + full.id + '" target="_blank">查看</a>';
                    return htmlText;
                }
            }
        ]
    });
};

MEDIARESLIST.loadCategories = function () {
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


MEDIARESLIST.searchProducts = function () {
    productTable.api().ajax.reload();
};