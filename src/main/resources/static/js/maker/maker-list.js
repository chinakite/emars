;

var MAKERLIST = {};
var makerTable;

$(document).ready(function(){
    MAKERLIST.initMakerTbl();
});

MAKERLIST.initMakerTbl = function(){
    makerTable = $('#makerTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/system/makers'
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
                    var htmlText = '<a href="javascript:void(0);" onclick="MAKERLIST.popEditMaker(' + full.id + ');">编辑</a>  ';
                    htmlText += '<span class="small">|</span> ' +
                        '<a href="javascript:void(0);" onclick="MAKERLIST.deleteMaker(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

MAKERLIST.popNewMakerModal = function(){
    MAKERLIST.clearMakerModal();
    $('#makerModal .modal-title').text("新建制作方");
    $('#makerModal').modal('show');
};

MAKERLIST.popEditMaker = function(id) {
    MAKERLIST.clearMakerModal();
    $('#makerModal .modal-title').text("编辑制作方");
    $('#makerModal').modal('show');
    $.get(
        "/system/maker",
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

MAKERLIST.clearMakerModal = function () {
    $('#id').val('');
    $('#name').val('');
    $('#contact').val('');
    $('#phone').val('');
    $('#desc').val('');
}

MAKERLIST.submitMaker = function() {
    var name = $('#name').val();
    var contact = $('#contact').val();
    var phone = $('#phone').val();
    var desc = $('#desc').val();
    var id = $('#id').val();
    if(id) {
        $.post(
            "/system/modifyMaker",
            {
                'name': name,
                'contact': contact,
                'phone': phone,
                'desc': desc,
                'id': id
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("制作方保存成功！");
                    $('#makerModal').modal('hide');
                    MAKERLIST.refreshMakerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }else {
        $.post(
            "/system/createMaker",
            {
                'name': name,
                'contact': contact,
                'phone': phone,
                'desc': desc
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("制作方保存成功！");
                    $('#makerModal').modal('hide');
                    MAKERLIST.refreshMakerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }
};

MAKERLIST.deleteMaker = function(id, name) {
    EMARS_COMMONS.showPrompt("您真的要删除制作方[" + name + "]吗？", function() {
        $.post(
            "/system/deleteMaker",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    MAKERLIST.refreshMakerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
}

MAKERLIST.refreshMakerTbl = function () {
    makerTable.api().ajax.reload(null, false);
}