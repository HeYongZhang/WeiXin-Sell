<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <script src="https://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
    <title></title>
</head>
<body>
<img src="" alt="" width="250" height="250">
<form id="uploadForm" enctype="multipart/form-data">
    文件:<input id="file" type="file" name="file"/>
</form>
<#--<button id="upload">上传文件</button>-->
</body>
<script type="text/javascript">
    $(function () {
        $("#file").change(function () {
            var formData = new FormData($('#uploadForm')[0]);
            $.ajax({
                type: 'post',
                url: "http://hyzgt.nat300.top/sell/wechat/upload", //上传文件的请求路径必须是绝对路劲
                data: formData,
                cache: false,
                dataType:"json",
                processData: false,
                contentType: false,
            }).success(function (data) {
                $("img").attr("src",data.path)
            }).error(function (XMLHttpRequest,textStatus,errorThrown) {
               alert("文件上传失败") //获取的信息即是异常中的Message
                /*// 状态码
                console.log(XMLHttpRequest.status);
                // 状态
                console.log(XMLHttpRequest.readyState);
                // 错误信息
                console.log(textStatus);*/
                /*alert("上传失败");*/
            });
            /*
            * error: function (XMLHttpRequest, textStatus, errorThrown) {
                    // 状态码
                    console.log(XMLHttpRequest.status);
                    // 状态
                    console.log(XMLHttpRequest.readyState);
                    // 错误信息
                    console.log(textStatus);
                }
            * */
        });
      /*  $("#upload").click(function () {
            var formData = new FormData($('#uploadForm')[0]);
            $.ajax({
                type: 'post',
                url: "http://hyzgt.nat300.top/sell/wechat/upload", //上传文件的请求路径必须是绝对路劲
                data: formData,
                cache: false,
                dataType:"json",
                processData: false,
                contentType: false,
            }).success(function (data) {
                alert(data.path);
            }).error(function () {
                alert("上传失败");
            });
        });*/
    });
</script>
</html>
