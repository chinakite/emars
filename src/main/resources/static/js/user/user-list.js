;

var USERLIST = {};
var userTable;
var userInfoValid;

$(document).ready(function(){
    USERLIST.initUserTbl();
});

USERLIST.initUserTbl = function(){
    userTable = $('#userTbl').dataTable({
                    "processing": false,
                    "paging": true,
                    "lengthChange": false,
                    "searching": false,
                    "ordering": false,
                    "info": true,
                    "autoWidth": false,
                    "serverSide": true,
                    "ajax": {
                        "url": '/users',
                        "data": function(d) {
                            var key = $('#searchKey').val();
                            if(key) {
                                d.searchKey = key;
                            }
                            var status = $('#searchStatus').val();
                            if(status) {
                                d.searchStatus = status;
                            }
                        }
                    },
                    language: {
                        "emptyTable": "没有查询到相关数据",
                        "paginate": {
                            "first":      "首页",
                            "previous":   "上一页",
                            "next":       "下一页",
                            "last":       "尾页"
                        },
                        "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项"
                    },
                    "columns": [
                        {width:"40px"},
                        {},
                        {},
                        {},
                        {},
                        {},
                        {},
                        {}
                    ],
                    "columnDefs": [
                        {
                            "targets": [0],
                            "className": "text-center",
                            "render": function(data, type, full, meta) {
                                var checkboxHtml = '<label class="custom-control custom-checkbox custom-control-blank" for="userTblCheck_' + full.id + '">'
                                                 + '<input id="userTblCheck_' + full.id + '" type="checkbox" value="' + full.id + '" class="custom-control-input" onclick="USERLIST.checkRow();"/>'
                                                 + '<span class="custom-control-indicator"></span>'
                                                 + '</label>';
                                return checkboxHtml;
                            }
                        },
                        {
                            "targets": [1],
                            "render": function(data, type, full) {
                                return full.account;
                            }
                        },
                        {
                            "targets": [2],
                            "render": function(data, type, full) {
                                return full.name;
                            }
                        },
                        {
                            "targets": [3],
                            "render": function(data, type, full) {
                                return full.email;
                            }
                        },
                        {
                            "targets": [4],
                            "render": function(data, type, full) {
                                return full.mobile;
                            }
                        },
                        {
                            "targets": [5],
                            "render": function(data, type, full) {
                                return full.honorific;
                            }
                        },
                        {
                            "targets": [6],
                            "render": function(data, type, full) {
                                if(full.status == '1') {
                                    return '<span class="label label-primary">正常</span>';
                                }else{
                                    return '<span class="label label-danger">已禁用</span>';
                                }
                            }
                        },
                        {
                            "targets": [7],
                            "render": function(data, type, full) {
                                var html = '<a href="#" onclick="USERLIST.popEditUserModal(\''+ full.id +'\');">编辑</a>';
                                if(full.status == '1') {
                                    html += ' | <a href="#" onclick="USERLIST.disableUser(\''+ full.id +'\');">禁用</a>';
                                }else{
                                    html += ' | <a href="#" onclick="USERLIST.enableUser(\''+ full.id +'\');">启用</a>';
                                }
                                html += ' | <a href="#" onclick="USERLIST.deleteUser(\''+ full.id +'\');">删除</a>';
                                return html;
                            }
                        }
                    ]
                });
    //移除表格标题栏
    $('#userTbl_wrapper .table-header').hide();

    //初始化表单校验器
    // Initialize validator
    userInfoValid = $('#userInfoForm').pxValidate({
        rules: {
            'inputAccount': {
                required: true
            },
            'inputEmail': {
                required: true,
                email: true
            },
            'inputPassword': {
                required: true,
                minlength: 8,
                maxlength: 20
            },
            'inputName': {
                required: true
            }
        }
    });
};

USERLIST.searchUser = function(){
    userTable.api().ajax.reload();
};

USERLIST.checkAllRows = function(obj){
    var checkAll = $(obj).prop('checked');
    $('#userTbl tbody input[type=checkbox]').each(function(){
        if(checkAll) {
            $(this).prop('checked', true);
        }else{
            $(this).prop('checked', false);
        }
    });
};

USERLIST.checkRow = function() {
    var checkedAll = true;
    var checkboxes = $('#userTbl tbody input[type=checkbox]');
    for(var i=0; i<checkboxes.length; i++) {
        var checked = $(checkboxes[i]).prop('checked');
        checkedAll = checkedAll & checked;
    }
    $('#userTblCheckAllBtn').prop('checked', checkedAll);
};

USERLIST.popAddUserModal = function(){
    USERLIST.clearUserModal();

    if(!$('#inputPasswordLabel').hasClass('required')) {
        $('#inputPasswordLabel').addClass('required');
    }

    $('#userInfoModal').modal('show');
};

USERLIST.popEditUserModal = function(id){
    USERLIST.clearUserModal();

    if($('#inputPasswordLabel').hasClass('required')) {
        $('#inputPasswordLabel').removeClass('required');
    }

    $.get(
        '/user/' + id,
        {},
        function(data) {
            if(data.code == '0') {
                $('#inputId').val(data.data['id']);
                $('#inputAccount').val(data.data['account']);
                $('#inputName').val(data.data['name']);
                $('#inputPassword').val('');
                $('#inputEmail').val(data.data['email']);
                $('#inputMobile').val(data.data['mobile']);
                if(data.data['gender'] == '0') {
                    $('#inputGenderMale').prop('checked', false);
                    $('#inputGenderFemale').prop('checked', true);
                }else{
                    $('#inputGenderMale').prop('checked', true);
                    $('#inputGenderFemale').prop('checked', false);
                }
                $('#inputHonorific').val(data.data['honorific']);
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );

    $('#userInfoModal').modal('show');
};

USERLIST.clearUserModal = function(){
    $('#inputId').val('');
    $('#inputAccount').val('');
    $('#inputName').val('');
    $('#inputPassword').val('');
    $('#inputEmail').val('');
    $('#inputMobile').val('');
    $('#inputGenderMale').prop('checked', true);
    $('#inputGenderFemale').prop('checked', false);
    $('#inputHonorific').val('');
};

USERLIST.submitUserInfo = function(){
    var valid = $('#userInfoForm').validate();
    if(valid) {
        var account = $('#inputAccount').val();
        var name = $('#inputName').val();
        var password = $('#inputPassword').val();
        var email = $('#inputEmail').val();
        var mobile = $('#inputMobile').val();
        var gender = '1';
        if($('#inputGenderFemale').prop('checked')) {
            gender = '0';
        }
        var honorific = $('#inputHonorific').val();

        var id = $('#inputId').val();
        if(id) {
            $.ajax(
                {
                    "url": "/user/"+id,
                    "type": "POST",
                    "data": {
                        "id": id,
                        "account": account,
                        "name": name,
                        "password": password,
                        "email": email,
                        "mobile": mobile,
                        "gender": gender,
                        "honorific": honorific
                    },
                    "success": function(data) {
                        if(data.code == '0') {
                            EMARS_COMMONS.showSuccess("用户信息保存成功！");
                            $('#userInfoModal').modal('hide');
                            USERLIST.refreshUserTbl();
                        }else{
                            EMARS_COMMONS.showError(data.code, data.msg);
                        }
                    }
                }
            );
        }else{
            $.ajax(
                {
                    "url": "/user",
                    "type": "POST",
                    "data": {
                        "account": account,
                        "name": name,
                        "password": password,
                        "email": email,
                        "mobile": mobile,
                        "gender": gender,
                        "honorific": honorific
                    },
                    "success": function(data) {
                        if(data.code == '0') {
                            EMARS_COMMONS.showSuccess("用户信息保存成功！");
                            $('#userInfoModal').modal('hide');
                            USERLIST.refreshUserTbl();
                        }else{
                            EMARS_COMMONS.showError(data.code, data.msg);
                        }
                    }
                }
            );
        }
    }else{
        return;
    }
};

USERLIST.deleteUser = function(id) {
    EMARS_COMMONS.showPrompt("您确定要删除该用户吗？", function(){
        $.post(
            '/deleteUser/' + id,
            {},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("用户删除成功！");
                    USERLIST.refreshUserTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        )
    }, null);
};

USERLIST.enableUser = function(id) {
    $.post(
        '/enableUser/' + id,
        {},
        function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("用户启用成功！");
                USERLIST.refreshUserTbl();
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
};

USERLIST.disableUser = function(id) {
    $.post(
        '/disableUser/' + id,
        {},
        function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("用户禁用成功！");
                USERLIST.refreshUserTbl();
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    )
};

USERLIST.batchDeleteUser = function() {
    EMARS_COMMONS.showPrompt("您确定要删除这些用户吗？", function(){
        var ids = '';
        var checkedRows = $('#userTbl tbody input[type=checkbox]:checked');
        for(var i=0; i<checkedRows.length; i++) {
            if(i > 0) {
                ids += ',';
            }
            ids += $(checkedRows[i]).val();
        }

        $.post(
            '/batchDeleteUser',
            {"ids": ids},
            function(data) {
                if(data.code == '0') {
                    EMARS_COMMONS.showSuccess("用户删除成功！");
                    USERLIST.refreshUserTbl();
                }else{
                    EMARS_COMMONS.showError(data.code, data.msg);
                }
            }
        );
    }, null);
};

USERLIST.refreshUserTbl = function() {
    userTable.api().ajax.reload(null, false);
};
