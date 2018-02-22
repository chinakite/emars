;

var AUTHORLIST = {};
var authorTable;

$(document).ready(function(){
    AUTHORLIST.initAuthorTbl();
});

AUTHORLIST.initAuthorTbl = function(){
    authorTable = $('#authorTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/system/authors',
            "data": function(d) {
                var key = $('#searchKey').val();
                if(key && $.trim(key).length > 0) {
                    d.key = key;
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
                    return '<input type="checkbox" value="' + data + '" />';
                }
            },
            {
                "data": "name"
            },
            {
                "data": "pseudonym"
            },
            {
                "data": "desc"
            },
            {
                "width": "20%",
                "render": function(data, type, full) {
                    var htmlText = '<a onclick="popEditAuthor(' + full.id + ');">编辑</a>  ';
                    htmlText += '<span class="small">|</span> ' +
                        '<a onclick="deleteAuthor(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

function searchAuthors() {
    authorTable.api().ajax.reload();
}

function submitSubject() {
    var sbjName = $('#name').val();
    var sbjDesc = $('#desc').val();
    $.post(
        "/system/textSubject",
        {
            'name': sbjName,
            'desc': sbjDesc
        },
        function (json) {
            console.log(json);
        }
    );
}

function popEditAuthor(id) {
    $.get(
        "/system/author",
        {id: id},
        function (json) {

        }
    );
}

function deleteAuthor(id, name) {
    var r = confirm("您真的要删除作者[" + name + "]吗？");
    if(r) {
        $.post(
            "/system/author/",
            {'_method': "delete", 'id': id},
            function(json) {
                var result = IDEA.parseJSON(json);
                if(result.type == 'success') {
                    alert('删除成功');
                    loadAuthors();
                }
            }
        );
    }
}
