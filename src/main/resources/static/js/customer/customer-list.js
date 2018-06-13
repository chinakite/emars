;

var CUSTOMERLIST = {};
var customerTable;

$(document).ready(function(){
    CUSTOMERLIST.initCustomerTbl();
});

CUSTOMERLIST.initCustomerTbl = function(){
    customerTable = $('#customerTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/system/customers'
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
                "data": "contact"
            },
            {
                "data": "phone"
            },
            {
                "data": "desc"
            },
            {
                "width": "20%",
                "render": function(data, type, full) {
                    var htmlText = '<a href="javascript:void(0);" onclick="CUSTOMERLIST.popEditCustomer(' + full.id + ');">编辑</a>  ';
                    htmlText += '<span class="small">|</span> ' +
                        '<a href="javascript:void(0);" onclick="CUSTOMERLIST.deleteCustomer(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

CUSTOMERLIST.popNewCustomerModal = function(){
    CUSTOMERLIST.clearCustomerModal();
    $('#customerModal .modal-title').text("新建客户");
    $('#customerModal').modal('show');
};

CUSTOMERLIST.popEditCustomer = function(id) {
    CUSTOMERLIST.clearCustomerModal();
    $('#customerModal .modal-title').text("编辑客户");
    $('#customerModal').modal('show');
    $.get(
        "/system/customer",
        {id: id},
        function (data) {
            if(data.code == '0') {
                $('#id').val(data.data['id']);
                $('#name').val(data.data['name']);
                $('#contact').val(data.data['contact']);
                $('#phone').val(data.data['phone']);
                $('#desc').val(data.data['desc']);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
}

CUSTOMERLIST.clearCustomerModal = function () {
    $('#id').val('');
    $('#name').val('');
    $('#contact').val('');
    $('#phone').val('');
    $('#desc').val('');
}

CUSTOMERLIST.submitCustomer = function() {
    var name = $('#name').val();
    var contact = $('#contact').val();
    var phone = $('#phone').val();
    var desc = $('#desc').val();
    var id = $('#id').val();
    if(id) {
        $.post(
            "/system/modifyCustomer",
            {
                'name': name,
                'contact': contact,
                'phone': phone,
                'desc': desc,
                'id': id
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("客户保存成功！");
                    $('#customerModal').modal('hide');
                    CUSTOMERLIST.refreshCustomerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }else {
        $.post(
            "/system/createCustomer",
            {
                'name': name,
                'contact': contact,
                'phone': phone,
                'desc': desc
            },
            function (data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("客户保存成功！");
                    $('#customerModal').modal('hide');
                    CUSTOMERLIST.refreshCustomerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }
};

CUSTOMERLIST.deleteCustomer = function(id, name) {
    EMARS_COMMONS.showPrompt("您真的要删除客户[" + name + "]吗？", function() {
        $.post(
            "/system/deleteCustomer",
            {'id': id},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("删除成功！");
                    CUSTOMERLIST.refreshCustomerTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
}

CUSTOMERLIST.refreshCustomerTbl = function () {
    customerTable.api().ajax.reload(null, false);
}