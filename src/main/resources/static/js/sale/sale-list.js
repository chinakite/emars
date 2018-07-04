;

var SALELIST = {};
var saleTable;
var customerSelect2Obj;

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

    SALELIST.loadGranters();
    SALELIST.loadCustomers();

    $.get('/enabledUsers', {}, function(data){
        if(data) {
            if(data.code == '0') {
                var result = data.data;
                var optionsHtml = '';
                for(var i=0; i<result.length; i++) {
                    optionsHtml += '<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>';
                }
                $('#inputOperator').html(optionsHtml);
            }
        }
        $('#inputOperator').select2({
            dropdownParent: $("#contractModal")
        });
    });

    $('#inputPlatformId').select2({dropdownParent: $("#contractModal")});

    $('#inputBegin').datepicker({
        format: 'yyyy-mm-dd',
        language: 'cn'
    });

    $('#inputEnd').datepicker({
        format: 'yyyy-mm-dd',
        language: 'cn'
    });
});

SALELIST.loadGranters = function() {
    $.get('/system/allGrantees', {}, function(data){
        if(data) {
            if(data.code == '0') {
                var result = data.data;
                var optionsHtml = '';
                for(var i=0; i<result.length; i++) {
                    optionsHtml += '<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>';
                }
                $('#inputGranterId').html(optionsHtml);
            }
        }
        $('#inputGranterId').select2({
            dropdownParent: $("#contractModal"),
            placeholder : '请选择'
        });
    });
};

SALELIST.productItem=function(id, mcProd, callback) {
    $.get(
        "/product/product",
        {id: id},
        function(data) {
            var productItem = data.data;

            var productItemHtml = nunjucks.render('../js/sale/sale_product_listitem.tmpl', productItem);
            var productItemObj = $(productItemHtml).data('bindedData', productItem);
            productItemObj.find('.product-list-item-info').popover();

            $('#product-list-selected').append(productItemObj);

            if(mcProd) {
                $('#' + id + "_inputSection").val(mcProd.section);
                $('#' + id + "_inputPrice").val(mcProd.price);
            }
        }
    )
};

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
    saleTable = $('#mcTbl').dataTable({
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
                "data": "typeText"
            },
            {
                "data": "customer.name"
            },
            {
                "data": "totalSection"
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
                            + '<a href="javascript:void(0);" onclick="SALELIST.changeState(\'' + full.id + '\',\'' + full.code +'\', 0)">取消作废</a>';
                    }else if(full.state == "2") {

                    }else {
                        htmlText += '&nbsp;&nbsp;|&nbsp;&nbsp;'
                            + '<a href="javascript:void(0);" onclick="SALELIST.popEditSaleContractModal(\'' + full.id + '\')">编辑</a>'
                            + '&nbsp;&nbsp;|&nbsp;&nbsp;'
                            + '<a href="javascript:void(0);" onclick="SALELIST.changeState(\'' + full.id + '\',\'' + full.code +'\', 1)">作废</a>'
                            + '&nbsp;&nbsp;|&nbsp;&nbsp;'
                            + '<a href="javascript:void(0);" onclick="SALELIST.changeState(\'' + full.id + '\',\'' + full.code +'\', 2)">完结</a>';
                    }
                    return htmlText;
                }
            }
        ]
    });
};

SALELIST.popContractModal = function () {
    SALELIST.clearContractModal();
    $('#contractModal').modal('show');
}

SALELIST.popEditSaleContractModal = function (id) {
    SALELIST.clearContractModal();
    $.get(
        '/sale/saleContract',
        {id: id},
        function(data) {
            if (data.code == '0') {
                var saleContract = data.data;
                $('#inputId').val(saleContract.id);
                $('#inputCode').val(saleContract.code);
                $('#inputTargetType option[value=' + saleContract.type + ']').prop('selected', true);
                $('#inputGranterId').val(saleContract.granterId).trigger('change');
                $('#inputCustomerId').val(saleContract.customerId).trigger('change');
                $('#inputTotalSection').val(saleContract.totalSection);
                $('#inputTotalPrice').val(saleContract.totalPrice);
                $('#inputSignDate').val(saleContract.signDate);
                $('#inputBegin').val(saleContract.begin);
                $('#inputEnd').val(saleContract.end);
                $('#inputOperator').val(saleContract.operator).trigger('change');
                $('#inputProjectCode').val(saleContract.projectCode);

                var privileges = saleContract.privileges;
                if(privileges.charAt(0) == '1') {
                    $('#inputPrivilege0').prop('checked', true);
                }else{
                    $('#inputPrivilege0').prop('checked', false);
                }
                if(privileges.charAt(1) == '1') {
                    $('#inputPrivilege1').prop('checked', true);
                }else{
                    $('#inputPrivilege1').prop('checked', false);
                }

                $.get(
                    '/system/platforms?customerId=' + saleContract.customerId,
                    {},
                    function(platformData) {
                        if(platformData.code == '0') {
                            var platforms = platformData.data;
                            var platformOptionsHtml = '';
                            for(var i=0; i<platforms.length; i++) {
                                platformOptionsHtml += '<option value="' + platforms[i]['id'] + '">' + platforms[i]['name'] + '</option>';
                            }
                            $('#inputPlatformId').html(platformOptionsHtml);
                            var platformIds = [];
                            for(var j=0; j<saleContract.platforms.length; j++) {
                                platformIds.push(saleContract.platforms[j].platformId);
                            }
                            $('#inputPlatformId').val(platformIds);
                            $('#inputPlatformId').select2('destroy');
                            $('#inputPlatformId').select2({
                                dropdownParent: $('#contractModal')
                            });
                        }
                    }
                );

                var saleProducts = saleContract.products;
                var prodIds = [];
                for(var k in saleProducts) {
                    var productId = saleProducts[k].productId;
                    prodIds.push(productId);
                    SALELIST.productItem(productId, saleProducts[k]);
                }
                $('#product-list-select').val(prodIds).trigger('change');
                $('#contractModal').modal('show');
            } else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

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
    $('#inputId').val('');
    $('#inputCode').val('');
    $('#inputTargetType option:first').prop("selected", 'selected');
    $('#inputTotalSection').val('');
    $('#inputCustomerId').val('');
    $('#inputTotalPrice').val('');
    $('#inputPrivilege0').prop('checked', false);
    $('#inputPrivilege1').prop('checked', false);
    $('#product-list-selected').empty();

    $('#saleContractWizard').pxWizard('reset');
};

SALELIST.submitSaleContract = function () {
    var id = $('#inputId').val();
    var signDate = $('#inputSignDate').val();
    var code = $('#inputCode').val();
    var customerId = $('#inputCustomerId').val();
    var type = $('#inputTargetType').val();
    var platformIds = $('#inputPlatformId').select2('data');
    var granterId = $('#inputGranterId').val();
    var operator = $('#inputOperator').val();
    var projectCode = $('#inputProjectCode').val();
    var totalPrice = $('#inputTotalPrice').val();
    var totalSection = $('#inputTotalSection').val();
    var beginDate = $('#inputBegin').val();
    var endDate = $('#inputEnd').val();
    var privileges = '';
    if($('#inputPrivilege0').prop('checked')) {
        privileges += '1';
    }else{
        privileges += '0';
    }
    if($('#inputPrivilege1').prop('checked')) {
        privileges += '1';
    }else{
        privileges += '0';
    }

    var platforms = [];
    for(var i=0; i<platformIds.length; i++) {
        platforms.push({platformId: platformIds[i].id, customerId: customerId});
    }


    var productObjs = $('.product-list-item');
    var saleProducts = [];
    for(var i=0; i<productObjs.length; i++) {
        var productItem = $(productObjs[i]).data('bindedData');
        var section = $("#" + productItem.id + "_inputSection").val();
        var price = $("#" + productItem.id + "_inputPrice").val();
        var saleProduct = {
            productId: productItem.id,
            section: section,
            price: price
        };
        saleProducts[i] = saleProduct;
    }

    var postData = {
        id: id,
        code: code,
        totalPrice: totalPrice,
        totalSection: totalSection,
        type: type,
        granterId: granterId,
        customerId: customerId,
        platforms: platforms,
        signDate: signDate,
        begin: beginDate,
        end: endDate,
        operator: operator,
        projectCode: projectCode,
        privileges: privileges,
        products: saleProducts
    };

    console.log(postData);

    $.ajax({
        url: "/sale/saleContract",
        type: "POST",
        data: JSON.stringify(postData),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.code == '0') {
                EMARS_COMMONS.showSuccess("保存成功！");
                SALELIST.clearContractModal();
                $('#contractModal').modal('hide');
                SALELIST.refreshSaleContractTbl();
            } else {
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    });
};

SALELIST.searchMakeContracts = function () {
    saleTable.api().ajax.reload();
}

SALELIST.refreshSaleContractTbl = function () {
    saleTable.api().ajax.reload(null, false);
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

SALELIST.loadCustomers = function(callback) {
    $.get('/system/allCustomers', {}, function(data){
        var defaultMakerId;
        if(data) {
            if(data.code == '0') {
                var result = data.data;
                var optionsHtml = '<option value="-1">请选择客户</option>';
                for(var i=0; i<result.length; i++) {
                    optionsHtml += '<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>';
                }
                $('#inputCustomerId').html(optionsHtml);
            }
        }
        if(customerSelect2Obj) {
            $('#inputCustomerId').select2('destroy');
        }

        customerSelect2Obj = $('#inputCustomerId').select2({
            dropdownParent: $("#contractModal")
        });

        $("#inputCustomerId").on("select2:select",function(e){
            var selectedCustomerId = e.params.data.id;
            if(selectedCustomerId == -1) {
                $('#inputPlatformId').empty().prepend('<option value="-1">请选择平台</option>');
            }else{
                $.get(
                    '/system/platforms?customerId=' + selectedCustomerId,
                    {},
                    function(platformData) {
                        if(platformData.code == '0') {
                            var platforms = platformData.data;
                            var platformOptionsHtml = '';
                            for(var i=0; i<platforms.length; i++) {
                                defaultMakerId = platforms[i]['id'];
                                platformOptionsHtml += '<option value="' + platforms[i]['id'] + '">' + platforms[i]['name'] + '</option>';
                            }
                            $('#inputPlatformId').html(platformOptionsHtml);
                            $('#inputPlatformId').select2('destroy');
                            $('#inputPlatformId').select2({
                                dropdownParent: $('#contractModal')
                            });
                        }
                    }
                );
            }
        });

        if(callback) {
            callback();
        }
    });
};

SALELIST.showAddCustomerPanel = function(prodId) {
    SALELIST.clearAddCustomerPanel();
    $('#saleContractWizard').hide();
    $('#addCustomerPanel').show();
};

SALELIST.hideAddCustomerPanel = function() {
    $('#addCustomerPanel').hide();
    $('#saleContractWizard').show();
};

SALELIST.clearAddCustomerPanel = function() {
    $('#inputAddCustomerName').val('');
    $('#inputAddCustomerContact').val('');
    $('#inputAddCustomerPhone').val('');
    $('#inputAnnouncerDesc').val('');
};

SALELIST.addCustomer = function() {
    var customerName = $('#inputAddCustomerName').val();
    var customerContact = $('#inputAddCustomerContact').val();
    var customerPhone = $('#inputAddCustomerPhone').val();
    var customerDesc = $('#inputAddCustomerDesc').val();

    $.post(
        "/system/createCustomer",
        {
            'name': customerName,
            'contact': customerContact,
            'phone': customerPhone,
            'desc': customerDesc
        },
        function (data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("客户保存成功！");
                SALELIST.hideAddCustomerPanel();
                SALELIST.loadCustomers(function(){
                    $('#inputCustomerId').find('option[text=' + customerName + ']').attr('selected', true);
                    $('#inputCustomerId').trigger('change');
                });
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
        promptWords = "您真的要作废合同号为[" + code + "]的营销合同吗？";
    }else if(state == '0') {
        promptWords = "您真的要取消作废合同号为[" + code + "]的营销合同吗？";
    }else if(state == '2') {
        promptWords = "您真的要完结合同号为[" + code + "]的营销合同吗？";
    }
    EMARS_COMMONS.showPrompt(promptWords, function() {
        $.post(
            "/sale/changeSaleContractState",
            {'id': id, 'state': state},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("操作成功！");
                    SALELIST.refreshSaleContractTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
};