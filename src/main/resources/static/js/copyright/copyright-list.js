;

var COPYRIGHTLIST = {};
var copyrightTable;

$(document).ready(function(){
    COPYRIGHTLIST.initCopyrightTbl();

    $('#copyrightWizard').pxWizard();
    $('.product-list-item').popover({});
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
                "data": "type"
            },
            {
                "data": "owner"
            },
            {
                "data": "buyerContact"
            },
            {
                "data": "privilegeTypeText"
            },
            {
                "data": "privilegeRangeText"
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
    var contractId = $('#inputContactId').val();
    var contractCode = $('#inputContactCode').val();
    var contractType = $('#inputContactCode').val();
    var owner = $('#inputGranter').val();
    var buyer = $('#inputGrantee').val();
    var signDate = $('#inputSignDate').val();
    var operator = $('#inputOperator').val();



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

COPYRIGHTLIST.showAddProductPanel = function() {
    $('#copyrightWizard').hide();
    $('#addProductPanel').show();
}

COPYRIGHTLIST.hideAddProductPanel = function() {
    $('#copyrightWizard').show();
    $('#addProductPanel').hide();
}

COPYRIGHTLIST.addProduct = function() {

}

COPYRIGHTLIST.removeProduct = function(obj) {
    $(obj).parent().parent().remove();
}

COPYRIGHTLIST.editProduct = function(obj) {
    $('#copyrightWizard').hide();
    $('#addProductPanel').show();
}