;

var SUBJECTLIST = {};
var subjectTable;

$(document).ready(function(){
    SUBJECTLIST.initSubjectTbl();

    $('#newSubject').pxValidate({
        rules: {
            'name': {
                required: true
            }
        }
    });
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
            "data": function(d) {
                var key = $('#searchKey').val();
                if(key && $.trim(key).length > 0) {
                    d.key = key;
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
                "data": "id",
                "width": "4%",
                "render": function(data, type) {
                    return '<input type="checkbox" value="' + data + '" class="tblRowCheckbox"/>';
                }
            },
            {
                "data": "name"
            },
            {
                "data": "desc"
            },
            {
                "width": "20%",
                "render": function(data, type, full, meta) {
                    var htmlText = '<a onclick="popEditSubject(' + full.id + ');">编辑</a>  ';
                    if(full.order != 1) {
                        htmlText += '<span class="small">|</span> ' +
                            '<a onclick="upSubject(' + full.id + ');">上移</a> ';
                    }
                    if(full.order != meta.settings.json.recordsTotal) {
                        htmlText += '<span class="small">|</span> ' +
                            '<a onclick="downSubject(' + full.id + ');">下移</a> ';
                    }
                    htmlText += '<span class="small">|</span> ' +
                        '<a onclick="deleteSubject(' + full.id + ', \'' + full.name + '\');">删除</a>';
                    return htmlText;
                }
            }
        ]
    });
};

function searchSubjects() {
    subjectTable.api().ajax.reload();
}

function submitSubject() {

}