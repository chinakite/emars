;

var SUBJECTLIST = {};
var subjectTable;

$(document).ready(function(){
    SUBJECTLIST.initSubjectTbl();
});

SUBJECTLIST.initSubjectTbl = function(){
    subjectTable = $('#subjectTbl').dataTable({
        "processing": true,
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": '/system/subjects',

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
            {}
        ]
    });
};