;

var COPYRIGHTLIST = {};
var copyrightTable;

$(document).ready(function(){
    var env = new nunjucks.Environment(new nunjucks.WebLoader('/'));

    COPYRIGHTLIST.initCopyrightTbl();

    $('#copyrightWizard').pxWizard();
    $('#copyrightWizard').on('finish.px.wizard', function(_e) {
        COPYRIGHTLIST.submitCopyright(_e);
    });


    $('.product-list-item').popover({});

    $('#inputPublishState').change(function(){
        var pubState = $(this).val();
        if(pubState == 0) {
            $('#pressCol').hide();
            $('#isbnCol').hide();
        }else{
            $('#pressCol').show();
            $('#isbnCol').show();
        }
    });

    $('#inputContractType').select2({
        dropdownParent: $("#copyrightModal"),
        minimumResultsForSearch: -1
    });

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
            dropdownParent: $("#copyrightModal")
        });
    });

    $.get('/system/textSubjects', {}, function(data){
        if(data) {
            if(data.code == '0') {
                var result = data.data;
                var optionsHtml = '';
                for(var i=0; i<result.length; i++) {
                    optionsHtml += '<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>';
                }
                $('#inputSubject').html(optionsHtml);
            }
        }
        $('#inputSubject').select2({
            dropdownParent: $("#copyrightModal")
        });
    });

    $.get('/system/allGrantees', {}, function(data){
        if(data) {
            if(data.code == '0') {
                var result = data.data;
                var optionsHtml = '';
                for(var i=0; i<result.length; i++) {
                    optionsHtml += '<option value="' + result[i]['id'] + '">' + result[i]['name'] + '</option>';
                }
                $('#inputGranteeId').html(optionsHtml);
            }
        }
        $('#inputGranteeId').select2({
            dropdownParent: $("#copyrightModal")
        });
    });

    $.get('/system/allGranters', {}, function(data){
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
            dropdownParent: $("#copyrightModal")
        });
    });

    $('#inputSignDate').datepicker({
        format: 'yyyy-mm-dd'
    });

    $('#inputCopyrightBegin').datepicker({
        format: 'yyyy-mm-dd'
    });

    $('#inputCopyrightEnd').datepicker({
        format: 'yyyy-mm-dd'
    });
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
                "data": "contractCode"
            },
            {
                "data": "contractTypeText"
            },
            {
                "data": "granter"
            },
            {
                "data": "grantee"
            },
            {
                "data": "signDate"
            },
            {
                "data": "operatorName"
            },
            {
                "render": function(data, type, full) {
                    var htmlText = '<a href="javascript:void(0);" onclick="COPYRIGHTLIST.popCopyrightDetailModal(' + full.id + ')">查看</a>'
                                 + '&nbsp;&nbsp;|&nbsp;&nbsp;'
                                 + '<a href="javascript:void(0);" onclick="COPYRIGHTLIST.popEditCopyrightModal(\'' + full.id + '\')">编辑</a>'
                                 + '&nbsp;&nbsp;|&nbsp;&nbsp;'
                                 + '<a href="javascript:void(0);" onclick="COPYRIGHTLIST.deleteCopyright(\'' + full.id + '\',\'' + full.code +'\')">删除</a>';
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
};

COPYRIGHTLIST.popEditCopyrightModal = function (id) {
    $.get(
        '/copyright/copyright',
        {id: id},
        function(data){
            if(data.code == '0') {
                var copyright = data.data;
                COPYRIGHTLIST.clearCopyrightModal();
                $('#copyrightModal .modal-title').text("编辑合同");

                $('#inputContractId').val(copyright.id);
                $('#inputContractCode').val(copyright.contractCode);
                $('#inputContractType').val(copyright.contractType).trigger('change');
                $('#inputGranterId').val(copyright.granterId).trigger('change');
                $('#inputGranteeId').val(copyright.granteeId).trigger('change');
                $('#inputSignDate').val(copyright.signDate);
                $('#inputOperator').val(copyright.operator).trigger('change');
                $('#inputProjectCode').val(copyright.projectCode);

                var products = copyright.products;
                if(products && products.length > 0) {
                    for(var i=0; i<products.length; i++) {
                        var productItem = products[i];
                        if(productItem.publishState != '1') {
                            productItem.isbn = '未出版';
                            productItem.publishStateText = '未出版';
                        }else{
                            productItem.publishStateText = '已出版';
                        }
                        var privilege1 = (productItem.privileges.charAt(0) == '1');
                        var privilege2 = (productItem.privileges.charAt(1) == '1');
                        var privilege3 = (productItem.privileges.charAt(2) == '1');
                        var privilege4 = (productItem.privileges.charAt(3) == '1');
                        var privilegeText;
                        if(privilege1) {
                            privilegeText = '音频改编权';
                        }
                        if(privilege2) {
                            if(privilegeText) privilegeText += '、';
                            privilegeText += '广播权';
                        }
                        if(privilege3) {
                            if(privilegeText) privilegeText += '、';
                            privilegeText += '信息网络传播权';
                        }
                        if(privilege4) {
                            if(privilegeText) privilegeText += '、';
                            privilegeText += '广播剧改编权';
                        }
                        productItem.privilegeText = privilegeText;
                        if(productItem.grant == '1') {
                            productItem.grantText = '有';
                        }else{
                            productItem.grantText = '无';
                        }
                        if(productItem.copyrightType == '1') {
                            productItem.copyrightTypeText = '专有许可使用';
                        }else{
                            productItem.copyrightTypeText = '非专有许可使用';
                        }
                        if(productItem.settlementType == '1') {
                            productItem.settlementTypeText = '是';
                        }else{
                            productItem.settlementTypeText = '否';
                        }

                        var productItemHtml = nunjucks.render('../js/copyright/copyright_product_listitem.tmpl', productItem);
                        var productItemObj = $(productItemHtml).popover().data('bindedData', productItem);

                        $('#copyrightProductList').append(productItemObj);
                    }
                }

                $('#copyrightModal').modal('show');
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

COPYRIGHTLIST.submitCopyright = function (_e) {
    var contractId = $('#inputContractId').val();
    var contractCode = $('#inputContractCode').val();
    var contractType = $('#inputContractType').val();
    var granterId = $('#inputGranterId').val();
    var granteeId = $('#inputGranteeId').val();
    var signDate = $('#inputSignDate').val();
    var operator = $('#inputOperator').val();
    var projectCode = $('#inputProjectCode').val();

    var productObjs = $('.product-list-item');

    var products = [];
    for(var i=0; i<productObjs.length; i++) {
        var productItem = $(productObjs[i]).data('bindedData');
        products[i] = productItem;
    }

    console.log(products);
    var postData = {
        'id': contractId,
        'contractCode': contractCode,
        'contractType' : contractType,
        'granterId': granterId,
        'granteeId': granteeId,
        'signDate': signDate,
        'operator': operator,
        'projectCode': projectCode,
        'products': products
    };

    $.ajax({
        url: "/copyright/saveCopyrightContract",
        type: "POST",
        data: JSON.stringify(postData),
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("合同保存成功！");
                $('#copyrightModal').modal('hide');
                COPYRIGHTLIST.refreshCopyrightTbl();
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
                _e.preventDefault();
            }
        }
    });
}


COPYRIGHTLIST.searchCopyrights = function () {
    copyrightTable.api().ajax.reload();
}

COPYRIGHTLIST.clearCopyrightModal = function () {
    $("#inputContractCode").val('');
    $("#inputContractType").val('wz').trigger('change');
    $("#inputGranterId").val('43').trigger('change');
    $("#inputGranteeId").val('43').trigger('change');
    $("#inputSignDate").val('');
    $("#inputSignMonth").text('');
    $("#inputOperator").val('1').trigger('change');
    $("#inputProjectCode").val('');

    $('#copyrightProductList').empty();

    COPYRIGHTLIST.resetProduct();
    $('#copyrightWizard').pxWizard('reset');
}

COPYRIGHTLIST.refreshCopyrightTbl = function () {
    copyrightTable.api().ajax.reload(null, false);
}

COPYRIGHTLIST.showAddProductPanel = function() {
    COPYRIGHTLIST.resetProduct();
    $('#copyrightWizard').hide();
    $('#addProductPanel').show();
}

COPYRIGHTLIST.hideAddProductPanel = function() {
    $('#copyrightWizard').show();
    $('#addProductPanel').hide();
}

COPYRIGHTLIST.addProduct = function() {
    var name = $('#inputName').val();
    var author = $('#inputAuthor').val();
    var authorPseudonym = $('#inputAuthorPseudonym').val();
    var wordCount = $('#inputWordCount').val();
    var subjectData = $('#inputSubject').select2('data');
    var publishState = $('#inputPublishState').val();
    var press = $('#inputPress').val();
    var isbn = $('#inputIsbn').val();
    var privilege1 = $('#inputPrivilege1').prop('checked');
    var privilege2 = $('#inputPrivilege2').prop('checked');
    var privilege3 = $('#inputPrivilege3').prop('checked');
    var privilege4 = $('#inputPrivilege4').prop('checked');
    var grant = $('#inputGrant').val();
    var copyrightType = $('#inputCopyrightType').val();
    var copyrightPrice = $('#inputCopyrightPrice').val();
    var settlementType = $('#inputSettlementType').val();
    var copyrightBegin = $('#inputCopyrightBegin').val();
    var copyrightEnd = $('#inputCopyrightEnd').val();
    var desc = $('#inputDesc').val();

    var privilegeText;
    if(privilege1) {
        privilegeText = '音频改编权';
    }
    if(privilege2) {
        if(privilegeText) privilegeText += '、';
        privilegeText += '广播权';
    }
    if(privilege3) {
        if(privilegeText) privilegeText += '、';
        privilegeText += '信息网络传播权';
    }
    if(privilege4) {
        if(privilegeText) privilegeText += '、';
        privilegeText += '广播剧改编权';
    }

    var productItem = {
        name : name,
        author: author,
        authorPseudonym: authorPseudonym,
        wordCount: wordCount,
        isbn: publishState == '1' ? isbn : "未出版",
        subjectText: subjectData[0].text,
        subjectId: subjectData[0].id,
        press: press,
        publishState: publishState,
        publishStateText: publishState == '1' ? "已出版" : "未出版",
        privilege1: privilege1,
        privilege2: privilege2,
        privilege3: privilege3,
        privilege4: privilege4,
        privilegeText: privilegeText,
        grant: grant,
        grantText: grant == '1' ? "有" : "无",
        copyrightType: copyrightType,
        copyrightTypeText: copyrightType == '1' ? '专有许可使用' : '非专有许可使用',
        copyrightPrice: copyrightPrice,
        settlementType: settlementType,
        settlementTypeText: settlementType == '1' ? '是' : '否',
        copyrightBegin: copyrightBegin,
        copyrightEnd: copyrightEnd,
        desc: desc
    };

    var productItemHtml = nunjucks.render('../js/copyright/copyright_product_listitem.tmpl', productItem);
    var productItemObj = $(productItemHtml).popover().data('bindedData', productItem);

    $('#copyrightProductList').append(productItemObj);
    COPYRIGHTLIST.hideAddProductPanel();
}

COPYRIGHTLIST.removeProduct = function(obj) {
    $(obj).parent().parent().remove();
}

COPYRIGHTLIST.editProduct = function(obj) {
    COPYRIGHTLIST.resetProduct();

    var productItem = $(obj).parent().parent().data('bindedData');
    $('#inputName').val(productItem.name);
    $('#inputAuthor').val(productItem.author);
    $('#inputAuthorPseudonym').val(productItem.authorPseudonym);
    $('#inputWordCount').val(productItem.wordCount);
    $('#inputSubject').select2('val', productItem.subjectId);
    $('#inputPublishState').val(productItem.publishState);
    $('#inputPress').val(productItem.press);
    $('#inputIsbn').val(productItem.isbn);
    if(productItem.privilege1)
        $('#inputPrivilege1').prop('checked', true);
    if(productItem.privilege2)
        $('#inputPrivilege2').prop('checked', true);
    if(productItem.privilege3)
        $('#inputPrivilege3').prop('checked', true);
    if(productItem.privilege4)
        $('#inputPrivilege4').prop('checked', true);
    $('#inputGrant').val(productItem.grant);
    $('#inputCopyrightType').val(productItem.copyrightType);
    $('#inputCopyrightPrice').val(productItem.copyrightPrice);
    $('#inputSettlementType').val(productItem.settlementType);
    $('#inputCopyrightBegin').val(productItem.copyrightBegin);
    $('#inputCopyrightEnd').val(productItem.copyrightEnd);
    $('#inputDesc').val(productItem.desc);

    $('#copyrightWizard').hide();
    $('#addProductPanel').show();
}

COPYRIGHTLIST.resetProduct = function() {
    $('#inputName').val('');
    $('#inputAuthor').val('');
    $('#inputAuthorPseudonym').val('');
    $('#inputWordCount').val('');
    $('#inputSubject').select2('val', '1');
    $('#inputPublishState').val('1');
    $('#inputPress').val('');
    $('#inputIsbn').val('');
    $('#inputPrivilege1').prop('checked', false);
    $('#inputPrivilege2').prop('checked', false);
    $('#inputPrivilege3').prop('checked', false);
    $('#inputPrivilege4').prop('checked', false);
    $('#inputGrant').val('1');
    $('#inputCopyrightType').val('1');
    $('#inputCopyrightPrice').val('');
    $('#inputSettlementType').val('0');
    $('#inputCopyrightBegin').val('');
    $('#inputCopyrightEnd').val('');
    $('#inputDesc').val('');
};

COPYRIGHTLIST.deleteCopyright = function(id, code) {
    EMARS_COMMONS.showPrompt("您真的要合同号为[" + name + "]的版权合同吗？", function() {
        $.post(
            "/copyright/removeCopyright",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    GRANTEELIST.refreshCopyrightTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
};