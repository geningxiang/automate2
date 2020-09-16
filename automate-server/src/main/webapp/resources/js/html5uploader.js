(function (window, $) {
    var formatFileSize = function (size) {
        if (size > 1024 * 1024) {
            size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
        }
        else {
            size = (Math.round(size * 100 / 1024) / 100).toString() + 'KB';
        }
        return size;
    }
    /**
     *
     * @param option
     * {
     *   url: '',
     *   data: formData
     *   onUploadStart: function () {},
     *   onUploadProgress: function() {},
     *   onUploadSuccess: function () {},
     *   onUploadComplete: function () {},
     *   onUploadError: function () {}
     * }
     */
    var html5uploader = function(option){
        var xhr = new XMLHttpRequest();
        if (xhr.upload) {
            //进度监听
            xhr.upload.addEventListener("progress", function (e) {
                if(option.onUploadProgress && typeof option.onUploadProgress === "function") {
                    option.onUploadProgress(e.loaded, e.total, formatFileSize(e.loaded), formatFileSize(e.total));
                }
            }, false);
            //状态监听
            xhr.onreadystatechange = function (e) {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        option.onUploadSuccess && option.onUploadSuccess(xhr.responseText);
                        option.onUploadComplete && option.onUploadComplete();
                    } else {
                        option.onUploadError && option.onUploadError(xhr.responseText);
                    }
                }
            };

            option.onUploadStart && option.onUploadStart();

            xhr.open("POST", option.url, true);
            //xhr.setRequestHeader("X_FILENAME", "a.jpg");

            xhr.send(option.data);
        } else {
            alert("当前浏览器不支持html5文件上传");
        }
    };
    window.html5uploader = html5uploader;
    $.html5uploader = html5uploader;
})(window, jQuery || $)