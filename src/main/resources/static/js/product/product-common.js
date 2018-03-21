;

var PRODUCTCOMMONS = {};

PRODUCTCOMMONS.removeProduct = function(obj) {
    $(obj).parent().parent().remove();
};

PRODUCTCOMMONS.popAddProduct = function(obj) {
    $(obj).parent().parent().remove();
};