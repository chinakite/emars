;
var MAILSETTING = {};

MAILSETTING.popTestEmailModal = function() {
    MAILSETTING.clearTestEmailModal();
    $('#testEmailModal').modal('show');
};

MAILSETTING.clearTestEmailModal = function() {
    $('#inputTestEmail').val('');
};

MAILSETTING.saveMailSetting = function() {
    var hostName = $('#inputHostName').val();
    var port = $('#inputPort').val();
    var fromEmail = $('#inputFromEmail').val();
    var fromName = $('#inputFromName').val();
    var userName = $('#inputUserName').val();
    var password = $('#inputPassword').val();
    var ssl = '0';
    if($('#inputSslYes').prop('checked')) {
        ssl = '1';
    }

    $.post(
        '/mail/1',
        {
            "hostName": hostName,
            "port": port,
            "fromEmail": fromEmail,
            "fromName": fromName,
            "userName": userName,
            "password": password,
            "ssl": ssl
        },
        function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("邮件设置保存成功");
                $('#testEmailModal').modal('hide');
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};

MAILSETTING.sendTestEmail = function() {
    var hostName = $('#inputHostName').val();
    var port = $('#inputPort').val();
    var fromEmail = $('#inputFromEmail').val();
    var fromName = $('#inputFromName').val();
    var userName = $('#inputUserName').val();
    var password = $('#inputPassword').val();
    var ssl = '0';
    if($('#inputSslYes').prop('checked')) {
        ssl = '1';
    }
    var testEmail = $('#inputTestEmail').val();

    $.post(
        '/testmail/1',
        {
            "hostName": hostName,
            "port": port,
            "fromEmail": fromEmail,
            "fromName": fromName,
            "userName": userName,
            "password": password,
            "ssl": ssl,
            "testEmail": testEmail
        },
        function(data) {
            if(data.code == '0') {
                EMARS_COMMONS.showSuccess("邮件发送成功，请收件人注意查收");
                $('#testEmailModal').modal('hide');
            }else{
                EMARS_COMMONS.showError(data.code, data.msg);
            }
        }
    );
};