module.exports = {
    connect: function(successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "rfr900", "connect", [null]);
    },
    regReadCallback: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "rfr900", "readCallback", [null]);	
    },
    read: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "rfr900", "read", [null]);	
    }
};







