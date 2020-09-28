var container = [];
$(function () {

    // $.ajax({
    //     type: 'GET',
    //     url: getRealPath() + "/canCode/info/selectAllStore",
    //     data: '',
    //     contentType: 'application/x-www-form-urlencoded',
    //     processData: true,//这个很有必要，不然不行
    //     dataType: "json",
    //     success: function(data){
    //     },
    //     error: function () {
    //         alert("异常了！！！");
    //     }
    // });

    //上传图片
    var tgaUpload = $('#goodsUpload').diyUpload({
        url: "/provider/provider/getPic",
        success: function (data) {
            alert('上传成功！')
        },
        error: function (err) {
            alert('上传失败！')
        },
        buttonText: '',
        accept: {
            title: "Images",
            extensions: 'gif,jpg,jpeg,bmp,png'
        },
        fileNumLimit: '2',
        thumb: {
            width: 120,
            height: 90,
            quality: 100,
            allowMagnify: true,
            crop: true,
            type: "image/jpeg"
        }
    });
    container.push(tgaUpload);


    // $("#yzm_img").click(function () {
    //     var timestamp = (new Date()).valueOf();
    //     $(this).attr("src",getRealPath() + "/msgContrller/code");
    // })

    $("#button").click(function () {
        var num = $("#num").val().toLowerCase();
        var text = $.cookie("text").toString().toLowerCase();
        if (num == text) {
            alert("成功")
        }
        console.log("cookie:" + text)
    })

    $("#eWMa").click(function () {
        var num = Math.ceil(Math.random() * 100);
        $("#eWMa_img").attr("src", "/provider/provider/createQRCode?num=" + num);
    })

    $("#subFileEWMa").click(function () {
        uploadParseFile();
    })

    $("#subFileImg").click(function () {
        uploadImgFile();
    })


    $("#saveBtn").click(function () {
        // var parms = encodeURI($("#editForm").serialize());
        $("#editForm").ajaxSubmit({
            type: "POST",
            // data: parms,
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            url: "/provider/provider/ceshiForm",
            success: function (msg) {
                $.each(container, function (index, value) {
                    value.upload();
                })
                $(this).resetForm();
            }
        })
        return false;//阻止表单提交
    });


})

function getVerifiCode() {
    console.log("getVerifiCode")
    var num = Math.ceil(Math.random() * 100);
    $("#yzm_img").attr("src", getBaseUrl() + "/provider/provider/code?num=" + num);
}

function uploadParseFile() {
    var formData = new FormData();
    formData.append("fileEWMa", $("#fileEWMa")[0].files[0]);

    $.ajax({
        type: 'POST',
        url: getBaseUrl() + "/provider/provider/uploadParseEWMa",
        data: formData,
        contentType: false,
        processData: false,//这个很有必要，不然不行
        dataType: "json",
        mimeType: "multipart/form-data",
        success: function (data) {
            if (data.succ) {
                alert("成功了！！！");
                var path = data.path;
                $("#imgHidden").css("display", 'block');
                $("#imgHidden").attr("src", getRealPath() + path);
                $("#showResult").css("display", 'block');
                $("#showResult").html(data.result)
            } else {
                alert("失败了！！！");
            }
        },
        error: function () {
            alert("异常了！！！");
        }
    });
}

function uploadImgFile() {
    var formData = new FormData();
    var input = document.getElementById("fileImg");
    var imgFiles = input.files;
    var size = imgFiles.length;
    var temp = 0;
    var flag = 1;

    if (imgFiles) {
        for (var i = 0; i < size; i++) {
            //读取图片数据
            var f = input.files[i];
            var reader = new FileReader();
            reader.onload = function (e) {
                var data = e.target.result;
                //加载图片获取图片真实宽度和高度
                var image = new Image();
                image.onload = function () {
                    var width = image.width;
                    var height = image.height;
                    console.log('第' + i + '张：' + width + '===' + height + "===" + f.size);
                    if (width < 200 || height < 200) {
                        console.log("图片太小！");
                        flag = 0;
                    }
                    ++temp;
                    if (size == temp && flag == 1) {
                        console.log("图片符合要求，走进方法了！");
                        ajaxFileUpload(formData);
                    } else if (size == temp && flag == 0) {
                        alert("图片太小，不符合规范！图片的宽、高像素必须大于200！");
                    }
                };
                image.src = data;
            };
            reader.readAsDataURL(f);
            formData.append("fileImg", imgFiles[i]);
        }

    } else {
        var image = new Image();
        image.onload = function () {
            var width = image.width;
            var height = image.height;
            var fileSize = image.fileSize;
            alert(width + '===2===' + height + "===2==" + fileSize);
        }
        image.src = input.value;
    }
}

function ajaxFileUpload(formData) {
    $.ajax({
        type: 'POST',
        url: getBaseUrl() + "/provider/msgController/uploadImgFile",
        data: formData,
        contentType: false,
        processData: false,//这个很有必要，不然不行
        dataType: "json",
        asyn: false,
        mimeType: "multipart/form-data",
        success: function (data) {
            if (data.succ) {
                alert("成功了！！！");
                // var path = data.path;
                // $("#uploadImgHidden").css("display", 'block');
                // $("#uploadImgHidden").attr("src", path);
            } else {
                alert("失败了！！！");
            }
        },
        error: function () {
            alert("异常了！！！");
        }
    });
}