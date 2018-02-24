;

var COPYRIGHTLIST = {};
var copyrightTable;

$(document).ready(function(){
    COPYRIGHTLIST.initCopyrightTbl();
});

COPYRIGHTLIST.initCopyrightTbl = function(){
    copyrightTable = $('#copyrightTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/copyright/copyrights',
            "data": function(d) {
                var code = $('#inputSearchCode').val();
                if(code && $.trim(code).length > 0) {
                    d.code = code;
                }
                var owner = $('#inputSearchOwner').val();
                if(owner && $.trim(owner).length > 0) {
                    d.owner = owner;
                }
                var auditState = $('#inputSearchAudithState').val();
                if(auditState && $.trim(auditState).length > 0) {
                    d.auditState = auditState;
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
                    var checkboxHtml = '<label class="custom-control custom-checkbox custom-control-blank" for="copyrightTblCheck_' + data + '">'
                        + '<input id="copyrightTblCheck_' + data + '" type="checkbox" value="' + data + '" class="custom-control-input tblRowCheckbox" onclick="COPYRIGHTLIST.checkRow();"/>'
                        + '<span class="custom-control-indicator"></span>'
                        + '</label>';
                    return checkboxHtml;
                }
            },
            {
                "data": "code"
            },
            {
                "data": "owner"
            },
            {
                "data": "buyerContact"
            },
            {
                "render": function (data, type, full) {
                    return full.privilegesText.replace(/,/g, '<br/>');
                }
            },
            {
                "data": "privilegeTypeText"
            },
            {
                "data": "privilegeRangeText"
            },
            {
                "render": function(data, type, full) {
                    return full.privilegeDeadline + "年";
                }
            },
            {
                "data": "auditStateText",
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="/copyright/contractDetail?id=' + full.id + ' target="_blank">查看</a>  ';
                    return htmlText;
                }
            }
        ]
    });
};

COPYRIGHTLIST.searchCopyrights = function () {
    copyrightTable.api().ajax.reload();
}