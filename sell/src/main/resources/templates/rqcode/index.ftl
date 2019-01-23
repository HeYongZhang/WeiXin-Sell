<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title><script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            setTimeout(donw,60000)
            var uuid;
            $.get("/sell/rqcoder", function(data, status) {
                var obj = eval("(" + data + ")");
                //存储UUID
                uuid = obj.uuid;
                //显示二维码
                $("#QrCodeImg").attr("src",obj.qrCodeImg);
                //alert(obj.qrCodeImg);
                //开始验证登录
                validateLogin();
            });
            function validateLogin(){
                $.get("/sell/seller/check?uuid="+uuid, function(data) {
                    var obj = eval("(" + data + ")");
                    if(obj.msg!="true"){
                        alert(obj.msg);
                        window.location.href = obj.url;
                    }
                });

            }
            function donw() {
                $("img").attr("src","/sell/images/ES7}QEYRV@HS$0}W@D8JPP1.jpg")
            }
        });
    </script>
</head>
<body>
<h1>哥么敢不敢扫一下！</h1>
<div id="divCon">
    <img src="/sell/images/ES7}QEYRV@HS$0}W@D8JPP1.jpg" id="QrCodeImg" />
</div>
</body>
</html>