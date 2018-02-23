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
                    var checkboxHtml = '<label class="custom-control custom-checkbox custom-control-blank" for="authorTblCheck_' + data + '">'
                        + '<input id="authorTblCheck_' + data + '" type="checkbox" value="' + data + '" class="custom-control-input tblRowCheckbox" onclick="AUTHORLIST.checkRow();"/>'
                        + '<span class="custom-control-indicator"></span>'
                        + '</label>';
                    return checkboxHtml;
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
                    var htmlText = '<a onclick="AUTHORLIST.popEditAuthor(' + full.id + ');">编辑</a>  ';
                    htmlText += '<span class="small">|</span> ' +
                        '<a onclick="AUTHORLIST.deleteAuthor(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

AUTHORLIST.searchAuthors = function() {
    authorTable.api().ajax.reload();
}

AUTHORLIST.checkAllRows = function(obj){
    var checkAll = $(obj).prop('checked');
    $('#authorTbl tbody input[type=checkbox]').each(function(){
        if(checkAll) {
            $(this).prop('checked', true);
        }else{
            $(this).prop('checked', false);
        }
    });
};

AUTHORLIST.checkRow = function() {
    var checkedAll = true;
    var checkboxes = $('#authorTbl tbody input[type=checkbox]');
    for(var i=0; i<checkboxes.length; i++) {
        var checked = $(checkboxes[i]).prop('checked');
        checkedAll = checkedAll & checked;
    }
    $('#authorTblTblCheckAllBtn').prop('checked', checkedAll);
};


AUTHORLIST.submitAuthor = function() {
    var aName = $('#name').val();
    var aDesc = $('#desc').val();
    var aPseudonym = $('#pseudonym').val();
    var aId = $('#id').val();
    if(aId) {
        $.post(
            "/system/author",
            {
                'pseudonym': aPseudonym,
                'name': aName,
                'desc': aDesc,
                'id': aId
            },
            function (data) {
                if(data.code == '0') {
                    alert('作者保存成功！');
                    $('#authorModal').modal('hide');
                    AUTHORLIST.refreshAuthorTbl();
                }else{
                    alert(data.msg);
                }
            }
        );
    }else {
        $.post(
            "/system/createAuthor",
            {
                'name': aName,
                'desc': aDesc,
                'pseudonym': aPseudonym
            },
            function (data) {
                if(data.code == '0') {
                    alert('作者保存成功！');
                    $('#authorModal').modal('hide');
                    AUTHORLIST.refreshAuthorTbl();
                }else{
                    alert(data.msg);
                }
            }
        );
    }
}

AUTHORLIST.popNewAuthorModal = function(){
    AUTHORLIST.clearAuthorModal();
    $('#authorModal .modal-title').text("新建作者");
    $('#authorModal').modal('show');
};

AUTHORLIST.popEditAuthor = function(id) {
    AUTHORLIST.clearAuthorModal();
    $('#authorModal .modal-title').text("编辑作者");
    $('#authorModal').modal('show');
    $.get(
        "/system/author",
        {id: id},
        function (data) {
            if(data.code == '0') {
                $('#id').val(data.data['id']);
                $('#name').val(data.data['name']);
                $('#desc').val(data.data['desc']);
                $('#pseudonym').val(data.data['pseudonym']);
            }else{
                alert(data.msg);
            }
        }
    );
}

AUTHORLIST.deleteAuthor = function(id, name) {
    var r = confirm("您真的要删除作者[" + name + "]吗？");
    if(r) {
        $.post(
            "/system/deleteAuthor",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    alert('删除成功');
                    AUTHORLIST.refreshAuthorTbl();
                }else{
                    alert(data.msg);
                }
            }
        );
    }
}

AUTHORLIST.batchDeleteAuthors = function() {
    var ids = [];
    $('.tblRowCheckbox:checked').each(function(){
        ids.push($(this).val());
    });

    if(ids.length == 0) {
        alert('您没有选择任何记录。');
        return ;
    }

    var r = confirm("您真的要删除这些作者吗？");
    if(r) {
        $.post(
            "/system/batchDeleteAuthor",
            {
                'ids': ids.join(',')
            },
            function(data) {
                if(data.code == '0') {
                    alert('删除成功');
                    AUTHORLIST.refreshAuthorTbl();
                }else{
                    alert(data.msg);
                }
            }
        );
    }
}


AUTHORLIST.refreshAuthorTbl = function () {
    authorTable.api().ajax.reload(null, false);
}

AUTHORLIST.clearAuthorModal = function () {
    $('#id').val('');
    $('#name').val('');
    $('#desc').val('');
    $('#pseudonym').val('');
}