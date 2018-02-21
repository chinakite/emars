;

var USERLIST = {};
var userTable;

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
                        "paginate": {
                            "first":      "首页",
                            "previous":   "上一页",
                            "next":       "下一页",
                            "last":       "尾页"
                        },
                        "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项"
                    },
                    "columns": [
                        {},
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
                            "render": function(data, type, full, meta) {
                                console.log(meta);
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
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
                                return '';
                            }
                        }
                    ]
                });
    $('#userTbl_wrapper .table-header').hide();
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