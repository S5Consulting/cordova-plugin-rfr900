module.exports = {
    test: function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "rfr900", "test", [1]);
    }
};







