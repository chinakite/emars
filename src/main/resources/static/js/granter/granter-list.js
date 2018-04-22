;

var GRANTERLIST = {};
var granterTable;

$(document).ready(function(){
    GRANTERLIST.initGranterTbl();
});

GRANTERLIST.initGranterTbl = function(){
    granterTable = $('#granterTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/system/granters'
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
                "data": "contact"
            },
            {
                "data": "phone"
            },
            {
                "data": "desc"
            },
            {
                "width": "20%",
                "render": function(data, type, full) {
                    var htmlText = '<a href="javascript:void(0);" onclick="GRANTERLIST.popEditGranter(' + full.id + ');">编辑</a>  ';
                    htmlText += '<span class="small">|</span> ' +
                        '<a href="javascript:void(0);" onclick="GRANTERLIST.deleteGranter(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

GRANTERLIST.popNewGranterModal = function(){
    GRANTERLIST.clearGranterModal();
    $('#granterModal .modal-title').text("新建授权方");
    $('#granterModal').modal('show');
};

GRANTERLIST.popEditGranter = function(id) {
    GRANTERLIST.clearGranterModal();
    $('#granterModal .modal-title').text("编辑签约方");
    $('#granterModal').modal('show');
    $.get(
        "/system/granter",
        {id: id},
        function (data) {
            if(data.code == '0') {
                $('#id').val(data.data['id']);
                $('#name').val(data.data['name']);
                $('#contact').val(data.data['contact']);
                $('#phone').val(data.data['phone']);
                $('#desc').val(data.data['desc']);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}

GRANTERLIST.clearGranterModal = function () {
    $('#id').val('');
    $('#name').val('');
    $('#contact').val('');
    $('#phone').val('');
    $('#desc').val('');
}

GRANTERLIST.submitGranter = function() {
    var name = $('#name').val();
    var contact = $('#contact').val();
    var phone = $('#phone').val();
    var desc = $('#desc').val();
    var id = $('#id').val();
    if(id) {
        $.post(
            "/system/modifyGranter",
            {
                'name': name,
                'contact': contact,
                'phone': phone,
                'desc': desc,
                'id': id
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("授权方保存成功！");
                    $('#granterModal').modal('hide');
                    GRANTERLIST.refreshGranterTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }else {
        $.post(
            "/system/createGranter",
            {
                'name': name,
                'contact': contact,
                'phone': phone,
                'desc': desc
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("授权方保存成功！");
                    $('#granterModal').modal('hide');
                    GRANTERLIST.refreshGranterTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }
};

GRANTERLIST.deleteGranter = function(id, name) {
    EMARS_COMMONS.showPrompt("您真的要删除授权方[" + name + "]吗？", function() {
        $.post(
            "/system/deleteGranter",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    GRANTERLIST.refreshGranterTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
}

GRANTERLIST.refreshGranterTbl = function () {
    granterTable.api().ajax.reload(null, false);
}