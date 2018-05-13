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
    });
    $("#product-list-select").on("select2:unselect",function(e){
        var id = e.params.data.id;
        MAKELIST.removeProduct(id);
    });


    $('#inputSignDate').datepicker({
        format: 'yyyy-mm-dd',
        language: 'cn'
    }).on('changeDate', function(e){
        var type = $('#inputTargetType').val();
        $.get(
            '/make/generateContractCode',
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

    MAKELIST.loadMakers();
});

function productItem(id, mcProd, callback) {
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

MAKELIST.removeProduct = function(id, syncSelect2) {
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
                "data": "createTime"
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="/make/makeContractDetail/' + full.id + '" target="_blank">查看</a>';
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
    $('#contractModal').modal('show');
}

MAKELIST.popEditMakeContractModal = function (id) {
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
    $('#inputCode').val('');
    $('#inputTargetType option:first').prop("selected", 'selected');
    $('#inputOwner').val('北京悦库时光文化传媒有限公司');
    $('#inputTotalSection').val('');
    $('#inputMaker').val('');
    $('#inputTotalPrice').val('');
    $('#product-list-selected').empty();

    $('#makeContractWizard').pxWizard('reset');
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

MAKELIST.searchMakeContracts = function () {
    mcTable.api().ajax.reload();
}

MAKELIST.refreshMakeContractTbl = function () {
    mcTable.api().ajax.reload(null, false);
}

MAKELIST.loadMcProduct = function (id) {
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

MAKELIST.showAddMakerPanel = function() {
    MAKELIST.clearAddMakerPanel();
    $('#makeContractWizard').hide();
    $('#addMakerPanel').show();
};

MAKELIST.hideAddMakerPanel = function() {
    $('#addMakerPanel').hide();
    $('#makeContractWizard').show();
};

MAKELIST.clearAddMakerPanel = function() {
    $('#inputMakerName').val('');
    $('#inputMakerContact').val('');
    $('#inputMakerPhone').val('');
    $('#inputMakerDesc').val('');
};

MAKELIST.addMaker = function() {
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

MAKELIST.loadMakers = function(callback) {
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

MAKELIST.showAddAnnouncerPanel = function(prodId) {
    MAKELIST.clearAddAnnouncerPanel();
    $('#inputAnnouncerProductId').val(prodId);
    $('#makeContractWizard').hide();
    $('#addAnnouncerPanel').show();
};

MAKELIST.hideAddAnnouncerPanel = function() {
    $('#addAnnouncerPanel').hide();
    $('#makeContractWizard').show();
};

MAKELIST.clearAddAnnouncerPanel = function() {
    $('#inputAnnouncerProductId').val('');
    $('#inputAnnouncerName').val('');
    $('#inputAnnouncerPseudonym').val('');
    $('#inputAnnouncerDesc').val('');
};

MAKELIST.addAnnouncer = function() {
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

MAKELIST.loadAnnouncers = function(callback, prodId) {
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

MAKELIST.autoSplit = function() {
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