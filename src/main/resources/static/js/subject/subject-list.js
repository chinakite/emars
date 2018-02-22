;

var SUBJECTLIST = {};
var subjectTable;

$(document).ready(function(){
    SUBJECTLIST.initSubjectTbl();

    // $('#newSubject').pxValidate({
    //     rules: {
    //         'name': {
    //             required: true
    //         }
    //     }
    // });
});

SUBJECTLIST.initSubjectTbl = function(){
    subjectTable = $('#subjectTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/system/subjects',
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
                    var checkboxHtml = '<label class="custom-control custom-checkbox custom-control-blank" for="subjectTblCheck_' + data + '">'
                        + '<input id="subjectTblCheck_' + data + '" type="checkbox" value="' + data + '" class="custom-control-input tblRowCheckbox" onclick="SUBJECTLIST.checkRow();"/>'
                        + '<span class="custom-control-indicator"></span>'
                        + '</label>';
                    return checkboxHtml;
                }
            },
            {
                "data": "name"
            },
            {
                "data": "desc"
            },
            {
                "width": "20%",
                "render": function(data, type, full, meta) {
                    var i = meta.settings.oAjaxData.start + meta.row;

                    var htmlText = '<a onclick="SUBJECTLIST.popEditSubject(' + full.id + ');">编辑</a>  ';
                    if(i != 0) {
                        htmlText += '<span class="small">|</span> ' +
                            '<a onclick="SUBJECTLIST.upSubject(' + full.id + ');">上移</a> ';
                    }
                    if(i != meta.settings.json.recordsTotal - 1) {
                        htmlText += '<span class="small">|</span> ' +
                            '<a onclick="SUBJECTLIST.downSubject(' + full.id + ');">下移</a> ';
                    }
                    htmlText += '<span class="small">|</span> ' +
                        '<a onclick="SUBJECTLIST.deleteSubject(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

SUBJECTLIST.searchSubjects = function () {
    subjectTable.api().ajax.reload();
}

SUBJECTLIST.checkAllRows = function(obj){
    var checkAll = $(obj).prop('checked');
    $('#subjectTbl tbody input[type=checkbox]').each(function(){
        if(checkAll) {
            $(this).prop('checked', true);
        }else{
            $(this).prop('checked', false);
        }
    });
};

SUBJECTLIST.checkRow = function() {
    var checkedAll = true;
    var checkboxes = $('#subjectTbl tbody input[type=checkbox]');
    for(var i=0; i<checkboxes.length; i++) {
        var checked = $(checkboxes[i]).prop('checked');
        checkedAll = checkedAll & checked;
    }
    $('#subjectTblCheckAllBtn').prop('checked', checkedAll);
};

SUBJECTLIST.submitSubject = function () {
    var sbjName = $('#name').val();
    var sbjDesc = $('#desc').val();
    var sbjId = $('#id').val();
    if(sbjId) {
        $.post(
            "/system/subject",
            {
                'id': sbjId,
                'name': sbjName,
                'desc': sbjDesc
            },
            function (data) {
                if(data.code == '0') {
                    alert('作品题材保存成功！');
                    $('#subjectModal').modal('hide');
                    SUBJECTLIST.refreshSubjectTbl();
                }else{
                    alert(data.msg);
                }
            }
        );
    }else {
        $.post(
            "/system/textSubject",
            {
                'name': sbjName,
                'desc': sbjDesc
            },
            function (data) {
                if(data.code == '0') {
                    alert('作品题材保存成功！');
                    $('#subjectModal').modal('hide');
                    SUBJECTLIST.refreshSubjectTbl();
                }else{
                    alert(data.msg);
                }
            }
        );
    }
    
}

SUBJECTLIST.popNewSubjectModal = function(){
    SUBJECTLIST.clearSubjectModal();
    $('#subjectModal').modal('show');
};

SUBJECTLIST.popEditSubject = function(id) {
    SUBJECTLIST.clearSubjectModal();
    $('#subjectModal').modal('show');
    $.get(
        "/system/subject",
        {id: id},
        function (data) {
            if(data.code == '0') {
                $('#id').val(data.data['id']);
                $('#name').val(data.data['name']);
                $('#desc').val(data.data['desc']);
            }else{
                alert(data.msg);
            }
        }
    );
}

SUBJECTLIST.upSubject = function(id) {
    $.post(
        "/system/upSubject",
        {"id": id},
        function(data) {
            if(data.code == '0') {
                alert('移动成功');
                SUBJECTLIST.refreshSubjectTbl();
            }else{
                alert(data.msg);
            }
        }
    );
}

SUBJECTLIST.downSubject = function(id) {
    $.post(
        "/system/downSubject",
        {"id": id},
        function(data) {
            if(data.code == '0') {
                alert('移动成功');
                SUBJECTLIST.refreshSubjectTbl();
            }else{
                alert(data.msg);
            }
        }
    );
}

SUBJECTLIST.deleteSubject = function(id, name) {
    var r = confirm("您真的要删除作品题材[" + name + "]吗？");
    if(r) {
        $.post(
            "/system/deleteSubject",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    alert('删除成功');
                    SUBJECTLIST.refreshSubjectTbl();
                }else{
                    alert(data.msg);
                }
            }
        );
    }
}

SUBJECTLIST.batchDeleteSubjects = function() {
    var ids = [];
    $('.tblRowCheckbox:checked').each(function(){
        ids.push($(this).val());
    });

    if(ids.length == 0) {
        alert('您没有选择任何记录。');
        return ;
    }

    var r = confirm("您真的要删除这些作品题材吗？");
    if(r) {
        $.post(
            "/system/batchDeleteSubject",
            {
                'ids': ids.join(',')
            },
            function(data) {
                if(data.code == '0') {
                    alert('删除成功');
                    SUBJECTLIST.refreshSubjectTbl();
                }else{
                    alert(data.msg);
                }
            }
        );
    }
}

SUBJECTLIST.clearSubjectModal = function(){
    $('#id').val('');
    $('#name').val('');
    $('#desc').val('');
};

SUBJECTLIST.refreshSubjectTbl = function() {
    subjectTable.api().ajax.reload(null, false);
};
