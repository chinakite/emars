;

var MAKELIST = {};
var productTable;
var pathname = window.location.pathname;

$(document).ready(function(){
    MAKELIST.initProductTbl();
    MAKELIST.loadCategories();
});

MAKELIST.initProductTbl = function(){
    productTable = $('#productTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/make/dtProducts',
            "data": function(d) {
                var productName = $('#inputSearchProductName').val();
                if(productName && $.trim(productName).length > 0) {
                    d.productName = productName;
                }
                var authorName = $('#inputSearchAuthorName').val();
                if(authorName && $.trim(authorName).length > 0) {
                    d.authorName = authorName;
                }
                var isbn = $('#inputSearchIsbn').val();
                if(isbn && $.trim(isbn).length > 0) {
                    d.isbn = isbn;
                }
                var subjectId = $('#inputSearchSubject').val();
                if(subjectId && $.trim(subjectId).length > 0) {
                    d.subjectId = subjectId;
                }
                var publishState = $('#inputSearchPublishState').val();
                if(publishState && $.trim(publishState).length > 0) {
                    d.publishState = publishState;
                }
                var state = $('#inputSearchState').val();
                if(state && $.trim(state).length > 0) {
                    d.state = state;
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
                "data": "name"
            },
            {
                "data": "authorName"
            },
            {
                "data": "publishStateText"
            },
            {
                "render": function (data, type, full) {
                    if(full.publishYear) {
                        return full.publishYear;
                    }else if(full.finishYear){
                        return full.finishYear;
                    }else{
                        return '';
                    }
                }
            },
            {
                "data": "subjectName"
            },
            {
                "data": "stateText"
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="javascript:;" onclick="MAKELIST.popProductDetailModal(' + full.id + ')">查看</a>';
                    if (pathname == "/make/taskProductPage") {
                        if (!(full.taskCount) || full.taskCount == 0) {
                            htmlText += ' <span class="small">|</span> ';
                            htmlText += '<a onclick="MAKELIST.popTaskModal(' + full.id + ');">创建任务</a>';
                        } else {
                            htmlText += ' <span class="small">|</span> ';
                            htmlText += '<a href="/make/taskPage?productId=' + full.id + '">查看任务</a>';
                        }
                    } else if(pathname == "/make/contractProductPage") {
                        if(full.state == '10'){
                            htmlText += ' <span class="small">|</span> ';
                            htmlText += '<a onclick="MAKELIST.popContractModal(' + full.id + ');">创建合同</a>';
                        }else if(full.state == '11' || full.state == '12' || full.state == '13' || full.state == '14'){
                            htmlText += ' <span class="small">|</span> ';
                            htmlText += '<a href="/make/contractDetail?productId=' + full.id +'" target="_blank">查看合同</a>';
                        }
                    }
                    return htmlText;
                }
            }
        ]
    });
};

MAKELIST.loadCategories = function () {
    $.get(
        "/system/textSubjects",
        function(data) {
            if(data.code == '0') {
                var subjects = data.data;
                var html = '';
                for(var i = 0; i < subjects.length; i ++) {
                    var subject = subjects[i];
                    html += '<option value="' + subject.id + '">' + subject.name + '</option>';
                }
                $('#inputSubject').empty().append(html);
                $('#inputSearchSubject').empty()
                    .append('<option value="">全部</option>')
                    .append(html);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
}

MAKELIST.popTaskModal = function (productId) {
    MAKELIST.clearTaskModal();
    $('#inputProductId').val(productId);
    $('#taskModal').modal('show');
}

MAKELIST.popContractModal = function (productId) {
    MAKELIST.clearContractModal();
    $('#inputProductId').val(productId);
    $('#contractModal').modal('show');
}

MAKELIST.clearTaskModal = function () {
    $('#inputProductId').val('');
    $('#inputMaker option:first').prop("selected", 'selected');
    $('#inputContract option:first').prop("selected", 'selected');
    $('#inputTimePerSection').val('');
    $('#inputShowType option:first').prop("selected", 'selected');
    $('#inputShowExpection').val('');
    $('#inputMakeTime').val('');
    $('#inputDesc').val('');
}

MAKELIST.clearContractModal = function () {
    $('#inputProductId').val('');
    $('#inputTargetType option:first').prop("selected", 'selected');
    $('#inputMaker option:first').prop("selected", 'selected');
    $('#inputOwner').val();
    $('#inputOwnerContact').val();
    $('#inputOwnerContactPhone').val();
    $('#inputOwnerContactAddress').val();
    $('#inputOwnerContactEmail').val();
    $('#inputWorker').val();
    $('#inputWorkerContact').val();
    $('#inputWorkerContactPhone').val();
    $('#inputWorkerContactAddress').val();
    $('#inputWorkerContactEmail').val();
    $('#inputTotalSection').val();
    $('#inputPrice').val();
    $('#inputTotalPrice').val();
    $('#inputPenalty').val();
    $('#inputBankAccountName').val();
    $('#inputBank').val();
    $('#inputAccountNo').val();

}

MAKELIST.submitTask = function () {
    var productId = $('#inputProductId').val();
    // var makerId = $('#inputMaker').val();
    // var contractId = $('#inputContract').val();
    var makerId = 1;
    var contractId = 1;

    var timePerSection = $('#inputTimePerSection').val();
    var showType = $('#inputShowType').val();
    var showExpection = $('#inputShowExpection').val();
    var makeTime = $('#inputMakeTime').val();
    var desc = $('#inputDesc').val();

    $.post(
        "/make/makeTask",
        {
            'productId': productId,
            'makerId': makerId,
            'contractId': contractId,
            'timePerSection': timePerSection,
            'showType': showType,
            'showExpection': showExpection,
            'makeTime': makeTime,
            'desc': desc
        },
        function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("任务制作成功！");
                MAKELIST.clearTaskModal();
                $('#taskModal').modal('hide');
                MAKELIST.refreshProductTbl();
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}

MAKELIST.submitMakeContract = function () {
    // var productId = $('#inputProductId').val();
    // var makerId = $('#inputMaker').val();
    var makerId = 1;
    var productId = 1;
    var targetType = $('#inputTargetType').val();

    var owner = $('#inputOwner').val();
    var ownerContact = $('#inputOwnerContact').val();
    var ownerContactPhone = $('#inputOwnerContactPhone').val();
    var ownerContactAddress = $('#inputOwnerContactAddress').val();
    var ownerContactEmail = $('#inputOwnerContactEmail').val();

    var worker = $('#inputWorker').val();
    var workerContact = $('#inputWorkerContact').val();
    var workerContactPhone = $('#inputWorkerContactPhone').val();
    var workerContactAddress = $('#inputWorkerContactAddress').val();
    var workerContactEmail = $('#inputWorkerContactEmail').val();

    var totalPrice = $('#inputTotalPrice').val();
    var price = $('#inputPrice').val();
    var totalSection = $('#inputTotalSection').val();
    var penalty = $('#inputPenalty').val();

    var bankAccountName = $('#inputBankAccountName').val();
    var bankAccountNo = $('#inputBankAccountNo').val();
    var bank = $('#inputBank').val();
    $.post(
        "/make/makeContract",
        {
            'productId': productId,
            'makerId': makerId,
            'targetType': targetType,
            'totalPrice': totalPrice,
            'price': price,
            'totalSection': totalSection,
            'penalty': penalty,
            'owner': owner,
            'ownerContact': ownerContact,
            'ownerContactPhone': ownerContactPhone,
            'ownerContactAddress': ownerContactAddress,
            'ownerContactEmail': ownerContactEmail,
            'worker': worker,
            'workerContact': workerContact,
            'workerContactPhone': workerContactPhone,
            'workerContactAddress': workerContactAddress,
            'workerContactEmail': workerContactEmail,
            'bankAccountName': bankAccountName,
            'bankAccountNo': bankAccountNo,
            'bank': bank,
            'type': "0"
        },
        function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("保存成功！");
                MAKELIST.clearContractModal();
                $('#contractModal').modal('hide');
                MAKELIST.refreshProductTbl();
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}

MAKELIST.searchProducts = function () {
    productTable.api().ajax.reload();
}

MAKELIST.refreshProductTbl = function () {
    productTable.api().ajax.reload(null, false);
}