;

var MAKELIST = {};
var productTable;
var pathname = window.location.pathname;
var mcTable;

$(document).ready(function(){
    // MAKELIST.initProductTbl();
    MAKELIST.initMakeContractTbl();
    MAKELIST.loadCategories();
    MAKELIST.loadExtMakers();
    MAKELIST.loadProductList();
    $('#makeContractWizard').pxWizard();
    $('#product-list-select').select2({
        placeholder: '选择作品',
        dropdownParent: $('#contractModal'),
        minimumSelectionLength: 1,
        allowClear: true,
        // templateSelection: productItem
    });
});

MAKELIST.loadProductList = function() {
    $.get(
        "/make/products",
        function(data) {
            var result = data;
            if(result) {
                var optionsHtml = '';
                for(var i=0; i<result.length; i++) {
                    optionsHtml += '<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>';
                }
                $('#product-list-select').html(optionsHtml);
            }
        }
    )
}

function productItem(selectProduct) {
    $("#product-list-selected").empty();
    $.get(
        "/product/product",
        {id: selectProduct.id},
        function(data) {
            var product = data.data;
            var html = '';
            html += '<div class="list-group-item">' +
                '<div class="product-list-item-info">' +
                '<h4 class="list-group-item-heading">' + product['name'] + '</h4>' +
                '<p class="list-group-item-text">' + product['authorName'] + '（' + product['authorPseudonym'] +'） | 10.6万 | ' + product['isbn'] + ' | 悬疑推理</p>' +
                '</div>' +
                // '<div class="product-list-item-toolbar">' +
                // '<a href="#" class="product-list-item-toolbar-btn bg-danger" onclick="MAKELIST.removeProduct(this);"><i class="fa fa-remove"></i></a>' +
                // '</div>' +
                '</div>';
            $("#product-list-selected").append(html);

        }
    )
    return selectProduct.text;

}


MAKELIST.removeProduct = function(obj) {
    $(obj).parent().parent().remove();
}

MAKELIST.initMakeContractTbl = function () {
    mcTable = $('#mcTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/make/makeContracts',
            "data": function(d) {
                var productName = $('#inputSearchProductName').val();
                if(productName && $.trim(productName).length > 0) {
                    d.productName = productName;
                }
                var code = $('#inputSearchCode').val();
                if(code && $.trim(code).length > 0) {
                    d.code = code;
                }
                var targetType = $('#inputSearchTargetType').val();
                if(targetType && $.trim(targetType).length > 0) {
                    d.targetType = targetType;
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
                "data": "worker"
            },
            {
                "data": "maker"
            },
            {
                "data": "totalPrice"
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="javascript:;" onclick="MAKELIST.popMakeContractDetailModal(' + full.id + ')">查看</a>';
                    return htmlText;
                }
            }
        ]
    });
}

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
                "render": function(data, type, full) {
                    var htmlText = '<a href="javascript:;" onclick="MAKELIST.popProductDetailModal(' + full.id + ')">查看</a>';
                    if (pathname == "/make/taskProductPage") {
                        if (!(full.taskCount) || full.taskCount == 0) {
                            htmlText += ' <span class="small">|</span> ';
                            htmlText += '<a href="javascript:;" onclick="MAKELIST.popTaskModal(' + full.id + ');">创建任务</a>';
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
                            htmlText += '<a href="javascript:;" onclick="MAKELIST.popMakeContractDetailModal(' + full.id +')">查看合同</a>';
                        }
                    }
                    return htmlText;
                }
            }
        ]
    });
};

MAKELIST.loadExtMakers = function () {
    $.get(
        "/system/extMakers",
        function(data) {
            if(data.code == '0') {
                var makers = data.data;
                var html = '';
                for(var i = 0; i < makers.length; i ++) {
                    var maker = makers[i];
                    html += '<option value="' + maker.id + '">' + maker.name + '</option>';
                }
                $('#inputMaker').empty().append(html);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
}

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

MAKELIST.popContractModal = function () {
    MAKELIST.clearContractModal();
    // $('#inputProductId').val(productId);
    $('#contractModal').modal('show');
}

MAKELIST.popMakeContractDetailModal = function (id) {
    $.get(
        "/make/makeContractDetail",
        {id: id},
        function(data) {
            $('#makeContractDetail').modal('show');
            $("#makeContractDetail .modal-body")
                .empty()
                .append(data);
        }
    )
}

MAKELIST.popUploadContractDoc = function (contractId) {
    MAKELIST.clearDocModal();
    $('#docModal').modal('show');
}

MAKELIST.clearDocModal = function () {
    
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
    $('#inputOwner').val();
    $('#inputWorker').val();
    $('#inputMaker').val();
    $('#inputTotalSection').val();
    $('#inputTotalPrice').val();
}

MAKELIST.submitTask = function () {
    var productId = $('#inputProductId').val();
    var makerId = $('#inputMaker').val();
    // var contractId = $('#inputContract').val();
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
    var products = $('#product-list-select').val();
    var maker = $('#inputMaker').val();
    var targetType = $('#inputTargetType').val();
    var owner = $('#inputOwner').val();
    var worker = $('#inputWorker').val();
    var totalPrice = $('#inputTotalPrice').val();
    var totalSection = $('#inputTotalSection').val();

    var postData = {
        productIds: products,
        maker: maker,
        targetType: targetType,
        owner: owner,
        worker: worker,
        totalPrice: totalPrice,
        totalSection: totalSection
    };

    $.ajax({
        url: "/make/makeContract",
        type: "POST",
        data: JSON.stringify(postData),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.code == '0') {
                EMARS_COMMONS.showSuccess("保存成功！");
                MAKELIST.clearContractModal();
                $('#contractModal').modal('hide');
                MAKELIST.refreshProductTbl();
            } else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    });
}

MAKELIST.searchProducts = function () {
    productTable.api().ajax.reload();
}

MAKELIST.refreshProductTbl = function () {
    productTable.api().ajax.reload(null, false);
}