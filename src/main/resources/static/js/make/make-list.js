;

var MAKELIST = {};
var mcTable;

$(document).ready(function(){
    MAKELIST.initMakeContractTbl();
    MAKELIST.loadExtMakers();
    MAKELIST.loadProductList();
    $('#makeContractWizard').pxWizard();
    $('#product-list-select').select2({
        placeholder: '选择作品',
        dropdownParent: $('#contractModal')
    });
    $("#product-list-select").on("select2:select",function(e){
        var id = e.params.data.id;
        productItem(id);
    })
});

function productItem(id) {
    $.get(
        "/product/product",
        {id: id},
        function(data) {
            var productItem = data.data;

            var productItemHtml = nunjucks.render('../js/make/mc_product_listitem.tmpl', productItem);
            var productItemObj = $(productItemHtml).popover().data('bindedData', productItem);

            $('#product-list-selected').append(productItemObj);

            // var html = '';
            // html += '<div class="list-group-item">' +
            //     '<div class="product-list-item-info">' +
            //     '<h4 class="list-group-item-heading">' + product['name'] + '</h4>' +
            //     '<p class="list-group-item-text">' + product['authorName'] + '（' + product['authorPseudonym'] +'） | 10.6万 | ' + product['isbn'] + ' | 悬疑推理</p>' +
            //     '</div>' +
            //     '<div class="product-list-item-toolbar">' +
            //     '<a href="#" class="product-list-item-toolbar-btn bg-danger" onclick="MAKELIST.removeProduct(this);"><i class="fa fa-remove"></i></a>' +
            //     '</div>' +
            //     '</div>';
            // $("#product-list-selected").append(html);

        }
    )
}

MAKELIST.loadProductList = function() {
    $.get(
        "/make/products",
        function(data) {
            var result = data;
            if(result) {
                var optionsHtml = '<option></option>';
                for(var i=0; i<result.length; i++) {
                    optionsHtml += '<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>';
                }
                $('#product-list-select').html(optionsHtml);
            }
        }
    )
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
                "data": "totalPrice"
            },
            {
                "data": ""  
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="javascript:;" onclick="MAKELIST.popMakeContractDetailModal(' + full.id + ')">查看</a>';
                    htmlText += ' <span class="small">|</span> ';
                    htmlText += '<a href="javascript:;" onclick="MAKELIST.popEditMakeContractModal(' + full.id + ')">编辑</a>';
                    htmlText += ' <span class="small">|</span> ';
                    htmlText += '<a href="javascript:;" onclick="MAKELIST.deleteMakeContract(' + full.id + ',\'' + full.code +'\')">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
}

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

MAKELIST.popEditMakeContractModal = function (id) {
    MAKELIST.clearContractModal();
    $.get(
        '/make/makeContract',
        {id: id},
        function(data) {
            if (data.code == '0') {
                var makeContract = data.data;
                $('#inputTargetType option:first').prop("selected", 'selected');
                $('#inputOwner').val(makeContract.owner);
                $('#inputWorker').val(makeContract.worker);
                $('#inputMaker').val(makeContract.maker);
                $('#inputTotalSection').val(makeContract.totalSection);
                $('#inputTotalPrice').val(makeContract.totalPrice);
                $('#contractModal').modal('show');
            } else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
}

MAKELIST.deleteMakeContract = function (id, code) {
    EMARS_COMMONS.showPrompt("您真的要合同号为[" + code + "]的版权合同吗？", function() {
        $.get(
            "/make/deleteMakeContract",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    MAKELIST.refreshMakeContractTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
}

MAKELIST.popUploadContractDoc = function (contractId) {
    MAKELIST.clearDocModal();
    $('#docModal').modal('show');
}

MAKELIST.clearDocModal = function () {
    
}

MAKELIST.clearTaskModal = function () {
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
                MAKELIST.refreshMakeContractTbl();
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
                MAKELIST.refreshMakeContractTbl();
            } else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    });
}

MAKELIST.searchMakeContracts = function () {
    mcTable.api().ajax.reload();
}

MAKELIST.refreshMakeContractTbl = function () {
    mcTable.api().ajax.reload(null, false);
}