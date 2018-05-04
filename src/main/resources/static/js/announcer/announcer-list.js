;

var ANNOUNCERLIST = {};
var announcerTable;

$(document).ready(function(){
    ANNOUNCERLIST.initAnnouncerTbl();
});

ANNOUNCERLIST.initAnnouncerTbl = function(){
    announcerTable = $('#announcerTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/system/announcers',
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
                    var checkboxHtml = '<label class="custom-control custom-checkbox custom-control-blank" for="announcerTblCheck_' + data + '">'
                        + '<input id="announcerTblCheck_' + data + '" type="checkbox" value="' + data + '" class="custom-control-input tblRowCheckbox" onclick="ANNOUNCERLIST.checkRow();"/>'
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
                    var htmlText = '<a onclick="ANNOUNCERLIST.popEditAnnouncer(' + full.id + ');">编辑</a>  ';
                    htmlText += '<span class="small">|</span> ' +
                        '<a onclick="ANNOUNCERLIST.deleteAnnouncer(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

ANNOUNCERLIST.searchAnnouncers = function() {
    announcerTable.api().ajax.reload();
}

ANNOUNCERLIST.checkAllRows = function(obj){
    var checkAll = $(obj).prop('checked');
    $('#announcerTbl tbody input[type=checkbox]').each(function(){
        if(checkAll) {
            $(this).prop('checked', true);
        }else{
            $(this).prop('checked', false);
        }
    });
};

ANNOUNCERLIST.checkRow = function() {
    var checkedAll = true;
    var checkboxes = $('#announcerTbl tbody input[type=checkbox]');
    for(var i=0; i<checkboxes.length; i++) {
        var checked = $(checkboxes[i]).prop('checked');
        checkedAll = checkedAll & checked;
    }
    $('#announcerTblTblCheckAllBtn').prop('checked', checkedAll);
};


ANNOUNCERLIST.submitAnnouncer = function() {
    var aName = $('#name').val();
    var aDesc = $('#desc').val();
    var aPseudonym = $('#pseudonym').val();
    var aId = $('#id').val();
    if(aId) {
        $.post(
            "/system/announcer",
            {
                'pseudonym': aPseudonym,
                'name': aName,
                'desc': aDesc,
                'id': aId
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("演播人保存成功！");
                    $('#announcerModal').modal('hide');
                    ANNOUNCERLIST.refreshAnnouncerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }else {
        $.post(
            "/system/createAnnouncer",
            {
                'name': aName,
                'desc': aDesc,
                'pseudonym': aPseudonym
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("演播人保存成功！");
                    $('#announcerModal').modal('hide');
                    ANNOUNCERLIST.refreshAnnouncerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }
}

ANNOUNCERLIST.popNewAnnouncerModal = function(){
    ANNOUNCERLIST.clearAnnouncerModal();
    $('#announcerModal .modal-title').text("新建演播人");
    $('#announcerModal').modal('show');
};

ANNOUNCERLIST.popEditAnnouncer = function(id) {
    ANNOUNCERLIST.clearAnnouncerModal();
    $('#announcerModal .modal-title').text("编辑演播人");
    $('#announcerModal').modal('show');
    $.get(
        "/system/announcer",
        {id: id},
        function (data) {
            if(data.code == '0') {
                $('#id').val(data.data['id']);
                $('#name').val(data.data['name']);
                $('#desc').val(data.data['desc']);
                $('#pseudonym').val(data.data['pseudonym']);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}

ANNOUNCERLIST.deleteAnnouncer = function(id, name) {
    EMARS_COMMONS.showPrompt("您真的要删除演播人[" + name + "]吗？", function() {
        $.post(
            "/system/deleteAnnouncer",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    ANNOUNCERLIST.refreshAnnouncerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null)
}

ANNOUNCERLIST.batchDeleteAnnouncers = function() {
    var ids = [];
    $('.tblRowCheckbox:checked').each(function(){
        ids.push($(this).val());
    });

    if(ids.length == 0) {
        EMARS_COMMONS.showError("未选中", "您没有选择任何记录");
        return ;
    }

    EMARS_COMMONS.showPrompt("您真的要删除这些演播人吗？", function() {
        $.post(
            "/system/batchDeleteAnnouncer",
            {
                'ids': ids.join(',')
            },
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    ANNOUNCERLIST.refreshAnnouncerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null)
}


ANNOUNCERLIST.refreshAnnouncerTbl = function () {
    announcerTable.api().ajax.reload(null, false);
}

ANNOUNCERLIST.clearAnnouncerModal = function () {
    $('#id').val('');
    $('#name').val('');
    $('#desc').val('');
    $('#pseudonym').val('');
}