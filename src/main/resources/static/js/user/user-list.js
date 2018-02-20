;

var USERLIST = {};
var userTable;

$(document).ready(function(){
    USERLIST.initUserTbl();
});

USERLIST.initUserTbl = function(){
    userTable = $('#userTbl').dataTable({
                    "processing": true,
                    "paging": true,
                    "lengthChange": false,
                    "searching": false,
                    "ordering": false,
                    "info": true,
                    "autoWidth": false,
                    "serverSide": true,
                    "ajax": {
                        "url": '/users?currentPage=1',

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
                        {},
                        {}
                    ],
                    "columnDefs": [
                        {
                            "targets": [0],
                            "render": function(data, type, full) {
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
                            }
                        },
                        {
                            "targets": [1],
                            "render": function(data, type, full) {
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
                            }
                        },
                        {
                            "targets": [2],
                            "render": function(data, type, full) {
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
                            }
                        },
                        {
                            "targets": [3],
                            "render": function(data, type, full) {
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
                            }
                        },
                        {
                            "targets": [4],
                            "render": function(data, type, full) {
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
                            }
                        },
                        {
                            "targets": [5],
                            "render": function(data, type, full) {
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
                            }
                        },
                        {
                            "targets": [6],
                            "render": function(data, type, full) {
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
                            }
                        },
                        {
                            "targets": [7],
                            "render": function(data, type, full) {
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
                            }
                        },
                        {
                            "targets": [8],
                            "render": function(data, type, full) {
                                return '<input type="checkbox" value="' + full.id + '" class="tblRowCheckbox"/>';
                            }
                        }
                    ]
                });
};