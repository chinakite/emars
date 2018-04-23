;

var GRANTEELIST = {};
var granteeTable;

$(document).ready(function(){
    GRANTEELIST.initGranteeTbl();
});

GRANTEELIST.initGranteeTbl = function(){
    granteeTable = $('#granteeTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/system/grantees'
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
                "data": "desc"
            },
            {
                "width": "20%",
                "render": function(data, type, full) {
                    var htmlText = '<a href="javascript:void(0);" onclick="GRANTEELIST.popEditGrantee(' + full.id + ');">编辑</a>  ';
                    htmlText += '<span class="small">|</span> ' +
                        '<a href="javascript:void(0);" onclick="GRANTEELIST.deleteGrantee(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

GRANTEELIST.popNewGranteeModal = function(){
    GRANTEELIST.clearGranteeModal();
    $('#granteeModal .modal-title').text("新建签约主体");
    $('#granteeModal').modal('show');
};

GRANTEELIST.popEditGrantee = function(id) {
    GRANTEELIST.clearGranteeModal();
    $('#granteeModal .modal-title').text("编辑签约主体");
    $('#granteeModal').modal('show');
    $.get(
        "/system/grantee",
        {id: id},
        function (data) {
            if(data.code == '0') {
                $('#id').val(data.data['id']);
                $('#name').val(data.data['name']);
                $('#desc').val(data.data['desc']);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}

GRANTEELIST.clearGranteeModal = function () {
    $('#id').val('');
    $('#name').val('');
    $('#desc').val('');
}

GRANTEELIST.submitGrantee = function() {
    var name = $('#name').val();
    var desc = $('#desc').val();
    var id = $('#id').val();
    if(id) {
        $.post(
            "/system/modifyGrantee",
            {
                'name': name,
                'desc': desc,
                'id': id
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("签约主体保存成功！");
                    $('#granteeModal').modal('hide');
                    GRANTEELIST.refreshGranteeTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }else {
        $.post(
            "/system/createGrantee",
            {
                'name': name,
                'desc': desc
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("签约主体保存成功！");
                    $('#granteeModal').modal('hide');
                    GRANTEELIST.refreshGranteeTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }
};

GRANTEELIST.deleteGrantee = function(id, name) {
    EMARS_COMMONS.showPrompt("您真的要删除签约主体[" + name + "]吗？", function() {
        $.post(
            "/system/deleteGrantee",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    GRANTEELIST.refreshGranteeTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
}

GRANTEELIST.refreshGranteeTbl = function () {
    granteeTable.api().ajax.reload(null, false);
}