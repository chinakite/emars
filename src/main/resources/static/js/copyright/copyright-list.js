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
                    var htmlText = '<a href="javascript:;" onclick="COPYRIGHTLIST.popCopyrightDetailModal(' + full.id + ')">查看</a>';
                    return htmlText;
                }
            }
        ]
    });
};

COPYRIGHTLIST.popCopyrightDetailModal = function (id) {
    $.get(
        "/copyright/copyrightDetail",
        {id: id},
        function(data) {
            $('#copyrightDetail').modal('show');
            $("#copyrightDetail .modal-body")
                .empty()
                .append(data);
        }
    )
}

COPYRIGHTLIST.popNewCopyrightModal = function () {
    COPYRIGHTLIST.clearCopyrightModal();
    $('#copyrightModal .modal-title').text("新建合同");
    $('#copyrightModal').modal('show');
}

COPYRIGHTLIST.submitCopyright = function () {
    var contractId = $('#inputContractId').val();
    var totalPrice = $('#inputTotalPrice').val();

    var priceEles = $('input[id^=inputPrice_]');
    var idarr = [];
    var pricearr = [];
    for(var i=0; i<priceEles.length; i++) {
        var ele = priceEles[i];
        var prodId = $(ele).attr('rel');
        idarr.push(prodId);
        pricearr.push($(ele).val());
    }
    var contractProductIds = idarr.join(',');
    var prices = pricearr.join(',');

    var owner = $('#inputOwner').val();
    var ownerContact = $('#inputOwnerContact').val();
    var ownerContactPhone = $('#inputOwnerContactPhone').val();
    var ownerContactAddress = $('#inputOwnerContactAddress').val();
    var ownerContactEmail = $('#inputOwnerContactEmail').val();

    var buyer = $('#inputBuyer').val();
    var buyerContact = $('#inputBuyerContact').val();
    var buyerContactPhone = $('#inputBuyerContactPhone').val();
    var buyerContactAddress = $('#inputBuyerContactAddress').val();
    var buyerContactEmail = $('#inputBuyerContactEmail').val();

    var checkPrivgs = $('input[name=inputPrivileges]:checked');
    var privgArr = [];
    for(var j=0; j<checkPrivgs.length; j++) {
        privgArr.push($(checkPrivgs[j]).val());
    }
    var privileges = privgArr.join(',');
    var privilegeType = $('#inputPrivilegeType').val();
    var privilegeRange = $('#inputPrivilegeRange').val();
    var privilegeDeadline = $('#inputPrivilegeDeadline').val();

    var bankAccountName = $('#inputBankAccountName').val();
    var bankAccountNo = $('#inputBankAccountNo').val();
    var bank = $('#inputBank').val();

    var type;
    if(contractId) {
        type = "1";
    }else {
        type = "0";
    }

    $.post(
        "/copyright/createCopyrightContract",
        {
            'contractId': contractId,
            'totalPrice': totalPrice,
            'contractProductIds' : contractProductIds,
            'prices': prices,
            'owner': owner,
            'ownerContact': ownerContact,
            'ownerContactPhone': ownerContactPhone,
            'ownerContactAddress': ownerContactAddress,
            'ownerContactEmail': ownerContactEmail,
            'buyer': buyer,
            'buyerContact': buyerContact,
            'buyerContactPhone': buyerContactPhone,
            'buyerContactAddress': buyerContactAddress,
            'buyerContactEmail': buyerContactEmail,
            'privileges': privileges,
            'privilegeType': privilegeType,
            'privilegeRange': privilegeRange,
            'privilegeDeadline': privilegeDeadline,
            'bankAccountName': bankAccountName,
            'bankAccountNo': bankAccountNo,
            'bank': bank,
            'submit': 1,
            'type': type
        },
        function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("合同保存成功！");
                $('#copyrightModal').modal('hide');
                COPYRIGHTLIST.refreshAuthorTbl();
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}


COPYRIGHTLIST.searchCopyrights = function () {
    copyrightTable.api().ajax.reload();
}

COPYRIGHTLIST.clearCopyrightModal = function () {
    $("#inputContractId").val('');
    $("#inputTotalPrice").val('');
    $("#inputOwner").val('');
    $("#inputOwnerContact").val('');
    $("#inputOwnerContactPhone").val('');
    $("#inputOwnerContactAddress").val('');
    $("#inputOwnerContactEmail").val('');
    $("#inputBuyer").val('');
    $("#inputBuyerContact").val('');
    $("#inputBuyerContactPhone").val('');
    $("#inputBuyerContactAddress").val('');
    $("#inputBuyerContactEmail").val('');
    $("#audioEditPrg").prop('checked', false);
    $("#broadcastPrg").prop('checked', false);
    $("#netcastPrg").prop('checked', false);
    $("#grantPrg").prop('checked', false);
    $("#inputPrivilegeType option:first").prop("selected", 'selected');
    $("#inputPrivilegeRange option:first").prop("selected", 'selected');
    $("#inputPrivilegeDeadline option:first").prop("selected", 'selected');
    $("#inputBankAccountName").val('');
    $("#inputBank").val('');
    $("#inputAccountNo").val('');
}

COPYRIGHTLIST.refreshAuthorTbl = function () {
    copyrightTable.api().ajax.reload(null, false);
}