;

var PRODUCTCOMMONS = {};

PRODUCTCOMMONS.removeProduct = function(obj) {
    $(obj).parent().parent().remove();
};