;

var SALELIST = {};
var mcTable;

$(document).ready(function(){
    SALELIST.initSaleContractTbl();
    SALELIST.loadProductList();
    $('#saleContractWizard').pxWizard();

    $('#inputSignDate').datepicker({
        format: 'yyyy-mm-dd',
        language: 'cn'
    }).on('changeDate', function(e){
        var type = $('#inputTargetType').val();
        $.get(
            '/sale/generateContractCode',
            {signDate: $('#inputSignDate').val(), type: type},
            function(data) {
                if(data.code == '0') {
                    $('#inputCode').val(data.data);
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    });

    //SALELIST.loadMakers();
});

SALELIST.productItem=function(id, mcProd, callback) {
    $.get(
        "/product/product",
        {id: id},
        function(data) {
            var productItem = data.data;

            var productItemHtml = nunjucks.render('../js/make/mc_product_listitem.tmpl', productItem);
            var productItemObj = $(productItemHtml).data('bindedData', productItem);
            productItemObj.find('.product-list-item-info').popover();

            $('#product-list-selected').append(productItemObj);
            MAKELIST.loadAnnouncers(callback, productItem.id);

            if(mcProd) {
                $('#' + id + "_inputSection").val(mcProd.section);
                $('#' + id + "_inputPrice").val(mcProd.price);
            }
        }
    )
}

SALELIST.loadProductList = function() {
    $.get(
        "/sale/products",
        function(data) {
            var result = data;
            if(result) {
                var optionsHtml = '<option></option>';
                for(var i=0; i<result.length; i++) {
                    optionsHtml += '<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>';
                }
                $('#product-list-select').html(optionsHtml);
                $('#product-list-select').select2({
                    placeholder: '选择作品',
                    dropdownParent: $('#contractModal')
                });
                $("#product-list-select").on("select2:select",function(e){
                    var id = e.params.data.id;
                    SALELIST.productItem(id);
                });
                $("#product-list-select").on("select2:unselect",function(e){
                    var id = e.params.data.id;
                    SALELIST.removeProduct(id);
                });
            }
        }
    )
}

SALELIST.removeProduct = function(id, syncSelect2) {
    $('.product-list-item[rel='+id+']').remove();
    if(syncSelect2) {
        var select2Datas = $('#product-list-select').select2('data');
        var selectIds = [];
        for(var i=0; i<select2Datas.length; i++) {
            if(select2Datas[i].id != id) {
                selectIds.push(select2Datas[i].id);
            }
        }
        $('#product-list-select').val(selectIds).trigger('change');
    }
}

SALELIST.initSaleContractTbl = function () {
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
            "url": '/sale/saleContracts',
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
                "render": function (data, type, full) {
                    var htmlText = full.code;
                    if(full.state == "1") {
                        htmlText += '<span class="label label-danger label-tag">作废</span>';
                    }else if(full.state == "2") {
                        htmlText += '<span class="label label-success label-tag">完结</span>';
                    }
                    return htmlText;
                }
            },
            {
                "data": "owner"
            },
            {
                "data": "totalPrice"
            },
            {
                "data": "createTime"
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="/make/makeContractDetail/' + full.id + '" target="_blank">查看</a>';
                    if(full.state == "1") {
                        htmlText += '&nbsp;&nbsp;|&nbsp;&nbsp;'
                            + '<a href="javascript:void(0);" onclick="MAKELIST.changeState(\'' + full.id + '\',\'' + full.code +'\', 0)">取消作废</a>';
                    }else if(full.state == "2") {

                    }else {
                        htmlText += '&nbsp;&nbsp;|&nbsp;&nbsp;'
                            + '<a href="javascript:void(0);" onclick="MAKELIST.popEditMakeContractModal(\'' + full.id + '\')">编辑</a>'
                            + '&nbsp;&nbsp;|&nbsp;&nbsp;'
                            + '<a href="javascript:void(0);" onclick="MAKELIST.changeState(\'' + full.id + '\',\'' + full.code +'\', 1)">作废</a>'
                            + '&nbsp;&nbsp;|&nbsp;&nbsp;'
                            + '<a href="javascript:void(0);" onclick="MAKELIST.changeState(\'' + full.id + '\',\'' + full.code +'\', 2)">完结</a>';
                    }
                    return htmlText;
                }
            }
        ]
    });
}

SALELIST.loadExtMakers = function () {
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

SALELIST.popTaskModal = function (productId) {
    SALELIST.clearTaskModal();
    $('#inputProductId').val(productId);
    $('#taskModal').modal('show');
}

SALELIST.popContractModal = function () {
    SALELIST.clearContractModal();
    $('#contractModal').modal('show');
}

SALELIST.popEditMakeContractModal = function (id) {
    MAKELIST.clearContractModal();
    $.get(
        '/make/makeContract',
        {id: id},
        function(data) {
            if (data.code == '0') {
                var makeContract = data.data;
                $('#inputId').val(makeContract.id);
                $('#inputCode').val(makeContract.code);
                $('#inputTargetType option[value=' + makeContract.targetType + ']').prop('selected', true);
                $('#inputOwner').val(makeContract.owner);
                $('#inputMakerId').val(makeContract.makerId).trigger('change');
                $('#inputTotalSection').val(makeContract.totalSection);
                $('#inputTotalPrice').val(makeContract.totalPrice);
                $('#inputSignDate').val(makeContract.signDate);
                var makeContractProducts = makeContract.mcProducts;
                var prodIds = [];
                for(var k in makeContractProducts) {
                    var productId = makeContractProducts[k].productId;
                    prodIds.push(productId);
                    productItem(productId, makeContractProducts[k], function(){
                        var announcers = makeContractProducts[k].announcers;
                        var announcerIds = [];
                        for(var c=0; c<announcers.length; c++) {
                            announcerIds.push(announcers[c].id);
                        }
                        console.log(announcerIds);
                        console.log($("#inputAnnouncerId_" + productId));
                        $("#inputAnnouncerId_" + productId).val(announcerIds).trigger('change');
                    });
                }
                $('#product-list-select').val(prodIds).trigger('change');
                $('#contractModal').modal('show');
            } else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
}

SALELIST.deleteMakeContract = function (id, code) {
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

SALELIST.popUploadContractDoc = function (contractId) {
    MAKELIST.clearDocModal();
    $('#docModal').modal('show');
}

SALELIST.clearDocModal = function () {

}

SALELIST.clearTaskModal = function () {
    $('#inputMaker option:first').prop("selected", 'selected');
    $('#inputContract option:first').prop("selected", 'selected');
    $('#inputTimePerSection').val('');
    $('#inputShowType option:first').prop("selected", 'selected');
    $('#inputShowExpection').val('');
    $('#inputMakeTime').val('');
    $('#inputDesc').val('');
}

SALELIST.clearContractModal = function () {
    $('#inputCode').val('');
    $('#inputTargetType option:first').prop("selected", 'selected');
    $('#inputOwner').val('北京悦库时光文化传媒有限公司');
    $('#inputTotalSection').val('');
    $('#inputMaker').val('');
    $('#inputTotalPrice').val('');
    $('#product-list-selected').empty();

    $('#makeContractWizard').pxWizard('reset');
}

SALELIST.submitTask = function () {
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

SALELIST.submitMakeContract = function () {
    var id = $('#inputId').val();
    // var maker = $('#inputMaker').val();
    var code = $('#inputCode').val();
    var makerId = $('#inputMakerId').val();
    var targetType = $('#inputTargetType').val();
    var owner = $('#inputOwner').val();
    var totalPrice = $('#inputTotalPrice').val();
    var totalSection = $('#inputTotalSection').val();
    var signDate = $('#inputSignDate').val();
    var productObjs = $('.product-list-item');
    var mcProducts = [];
    for(var i=0; i<productObjs.length; i++) {
        var productItem = $(productObjs[i]).data('bindedData');
        var section = $("#" + productItem.id + "_inputSection").val();
        var price = $("#" + productItem.id + "_inputPrice").val();
        var mcProduct = {
            productId: productItem.id,
            section: section,
            price: price,
            announcers: []
        };
        var announcerIds = $("#inputAnnouncerId_" + productItem.id).select2('data');
        for(var j=0; j<announcerIds.length; j++) {
            mcProduct.announcers.push({id: announcerIds[j].id});
        }
        mcProducts[i] = mcProduct;
    }

    var postData = {
        id: id,
        code: code,
        totalPrice: totalPrice,
        totalSection: totalSection,
        targetType: targetType,
        owner: owner,
        makerId: makerId,
        signDate: signDate,
        mcProducts: mcProducts
    };

    console.log(postData);

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

SALELIST.searchMakeContracts = function () {
    mcTable.api().ajax.reload();
}

SALELIST.refreshMakeContractTbl = function () {
    mcTable.api().ajax.reload(null, false);
}

SALELIST.loadMcProduct = function (id) {
    $.get(
        '/make/mcProduct',
        {id: id},
        function(data) {
            if (data.code == '0') {
                var mcProduct = data.data;
                console.log(mcProduct);
            } else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
};

SALELIST.showAddMakerPanel = function() {
    MAKELIST.clearAddMakerPanel();
    $('#makeContractWizard').hide();
    $('#addMakerPanel').show();
};

SALELIST.hideAddMakerPanel = function() {
    $('#addMakerPanel').hide();
    $('#makeContractWizard').show();
};

SALELIST.clearAddMakerPanel = function() {
    $('#inputMakerName').val('');
    $('#inputMakerContact').val('');
    $('#inputMakerPhone').val('');
    $('#inputMakerDesc').val('');
};

SALELIST.addMaker = function() {
    var gName = $('#inputMakerName').val();
    var gContact = $('#inputMakerContact').val();
    var gPhone = $('#inputMakerPhone').val();
    var gDesc = $('#inputMakerDesc').val();

    $.post(
        "/system/createMaker",
        {
            'name': gName,
            'contact': gContact,
            'phone': gPhone,
            'desc': gDesc
        },
        function (data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("制作方保存成功！");
                MAKELIST.hideAddMakerPanel();

                $('#inputMakerId').select2('destroy');
                $('#inputMakerId').empty();
                MAKELIST.loadMakers(function(){
                    var curId = $('#inputMakerId option').filter(function () {
                        return $(this).html() == gName;
                    }).val();
                    $('#inputMakerId').val(curId).trigger('change');
                });
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

SALELIST.loadMakers = function(callback) {
    $.get('/system/allMakers', {}, function(data){
        var defaultMakerId;
        if(data) {
            if(data.code == '0') {
                var result = data.data;
                var optionsHtml = '';
                for(var i=0; i<result.length; i++) {
                    defaultMakerId = result[i]['id'];
                    optionsHtml += '<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>';
                }
                $('#inputMakerId').html(optionsHtml);
            }
        }
        $('#inputMakerId').select2({
            dropdownParent: $("#contractModal"),
            placeholder : '请选择'
        });
        if(callback) {
            callback();
        }
    });
};

SALELIST.showAddAnnouncerPanel = function(prodId) {
    SALELIST.clearAddAnnouncerPanel();
    $('#inputAnnouncerProductId').val(prodId);
    $('#makeContractWizard').hide();
    $('#addAnnouncerPanel').show();
};

SALELIST.hideAddAnnouncerPanel = function() {
    $('#addAnnouncerPanel').hide();
    $('#makeContractWizard').show();
};

SALELIST.clearAddAnnouncerPanel = function() {
    $('#inputAnnouncerProductId').val('');
    $('#inputAnnouncerName').val('');
    $('#inputAnnouncerPseudonym').val('');
    $('#inputAnnouncerDesc').val('');
};

SALELIST.addAnnouncer = function() {
    var prodId = $('#inputAnnouncerProductId').val();
    var aName = $('#inputAnnouncerName').val();
    var aDesc = $('#inputAnnouncerDesc').val();
    var aPseudonym = $('#inputAnnouncerPseudonym').val();

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
                MAKELIST.hideAddAnnouncerPanel();
                var selectObj = $('#inputAnnouncerId_' + prodId);
                var selected = selectObj.select2('data');
                var selectedIds = [];
                for(var i=0; i<selected.length; i++) {
                    selectedIds.push(selected[i]['id']);
                }
                selectObj.select2('destroy');
                selectObj.empty();
                MAKELIST.loadAnnouncers(function(pid){
                    var curId = $('#inputAnnouncerId_'+ pid + ' option').filter(function () {
                        if(aPseudonym) {
                            return $(this).html() == aName+"("+aPseudonym+")";
                        }else{
                            return $(this).html() == aName;
                        }
                    }).val();
                    selectedIds.push(curId);
                    selectObj.val(selectedIds).trigger('change');
                }, prodId);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

SALELIST.loadAnnouncers = function(callback, prodId) {
    $.get(
        "/system/allAnnouncers",
        function(data) {
            if(data.code == '0') {
                var announcers = data.data;
                var html = '';
                for(var i = 0; i < announcers.length; i ++) {
                    var announcer = announcers[i];
                    var announcerName = announcer.name;
                    if(announcer.pseudonym) {
                        announcerName = announcerName + "(" + announcer.pseudonym + ")";
                    }
                    html += '<option value="' + announcer.id + '">' + announcerName + '</option>';
                }
                $('#inputAnnouncerId_' + prodId).empty().append(html);
                $('#inputAnnouncerId_' + prodId).select2({
                    dropdownParent: $("#contractModal")
                });
                if(callback) {
                    callback(prodId);
                }
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
};

SALELIST.autoSplit = function() {
    var totalSections = parseInt($('#inputTotalSection').val());
    var totalPrice = parseInt($('#inputTotalPrice').val());

    var selected = $('#product-list-select').select2('data');
    var length = selected.length;
    if(length == 0) {
        alert("您还没有选择作品。");
        return;
    }
    if(length == 1) {
        var selectedId = selected[0].id;
        $('#'+selectedId+'_inputSection').val(totalSections);
        $('#'+selectedId+'_inputPrice').val(totalPrice);
    }else{
        var avgSections = parseInt(totalSections/length);
        var avgPrice = parseInt(totalPrice/length);

        for(var i=0; i<length-1; i++) {
            var selectedId = selected[i].id;
            $('#'+selectedId+'_inputSection').val(avgSections);
            $('#'+selectedId+'_inputPrice').val(avgPrice);
        }
        var lastSelectedId = selected[length-1].id;
        $('#'+lastSelectedId+'_inputSection').val((totalSections - (avgSections*(length-1))));
        $('#'+lastSelectedId+'_inputPrice').val((totalPrice - (avgPrice*(length-1))));
    }
};

SALELIST.changeState = function(id, code, state) {
    var promptWords = "";
    if(state == '1') {
        promptWords = "您真的要作废合同号为[" + code + "]的制作合同吗？";
    }else if(state == '0') {
        promptWords = "您真的要取消作废合同号为[" + code + "]的制作合同吗？";
    }else if(state == '2') {
        promptWords = "您真的要完结合同号为[" + code + "]的制作合同吗？";
    }
    EMARS_COMMONS.showPrompt(promptWords, function() {
        $.post(
            "/make/changeMakeContractState",
            {'id': id, 'state': state},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("操作成功！");
                    MAKELIST.refreshMakeContractTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
};