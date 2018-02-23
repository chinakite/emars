;
var EMARS_COMMONS = {};

EMARS_COMMONS.showSuccess = function(msg, callbackFn) {
    var successDom =   '<div id="successModal" class="modal fade modal-alert modal-success">' +
        '  <div class="modal-dialog">' +
        '    <div class="modal-content">' +
        '      <div class="modal-header"><i class="fa fa-times-circle"></i></div>' +
        '      <div class="modal-title">恭喜您</div>' +
        '      <div class="modal-body">操作成功</div>' +
        '      <div class="modal-footer">' +
        '        <button type="button" class="btn btn-danger" data-dismiss="modal">确定</button>' +
        '      </div>' +
        '    </div>' +
        '  </div>' +
        '</div>';

    var successModal = $('#successModal');
    if(successModal.length == 0) {
        $('body').append(successDom);
        successModal = $('#successModal');
    }

    successModal.find('.modal-body').text(msg);
    successModal.modal('show');
    if(callbackFn) {
        successModal.one('hidden.bs.modal', function (e) {
            callbackFn();
        })
    }
};

EMARS_COMMONS.showError = function(title, msg, callbackFn) {
    var errorDom =   '<div id="errorModal" class="modal fade modal-alert modal-danger">' +
                '  <div class="modal-dialog">' +
                '    <div class="modal-content">' +
                '      <div class="modal-header"><i class="fa fa-times-circle"></i></div>' +
                '      <div class="modal-title">错误</div>' +
                '      <div class="modal-body">操作失败</div>' +
                '      <div class="modal-footer">' +
                '        <button type="button" class="btn btn-danger" data-dismiss="modal">确定</button>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';

    var errorModal = $('#errorModal');
    if(errorModal.length == 0) {
        $('body').append(errorDom);
        errorModal = $('#errorModal');
    }

    errorModal.find('.modal-title').text("错误：" + title);
    errorModal.find('.modal-body').text(msg);
    errorModal.modal('show');

    if(callbackFn) {
        errorModal.one('hidden.bs.modal', function (e) {
            callbackFn();
        })
    }
};

EMARS_COMMONS.showPrompt = function(msg, callbackOk, callbackCancel) {
    var promptDom =   '<div id="promptModal" class="modal fade modal-alert modal-warning">' +
        '  <div class="modal-dialog">' +
        '    <div class="modal-content">' +
        '      <div class="modal-header"><i class="fa fa-times-circle"></i></div>' +
        '      <div class="modal-title">操作提醒</div>' +
        '      <div class="modal-body">您确定要执行该操作吗？</div>' +
        '      <div class="modal-footer">' +
        '        <button type="button" class="btn btn-danger cancel-btn" data-dismiss="modal">取消</button>' +
        '        <button type="button" class="btn btn-danger ok-btn">确定</button>' +
        '      </div>' +
        '    </div>' +
        '  </div>' +
        '</div>';

    var promptModal = $('#promptModal');
    if(promptModal.length == 0) {
        $('body').append(promptDom);
        promptModal = $('#promptModal');
    }

    promptModal.find('.modal-body').text(msg);
    promptModal.modal('show');
    if(callbackOk) {
        promptModal.find('.ok-btn').one('click', function (e) {
            callbackOk();
            promptModal.modal('hide');
        });
    }
    if(callbackCancel) {
        promptModal.find('.cancel-btn').one('click', function (e) {
            callbackCancel();
        })
    }
};