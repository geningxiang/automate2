<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <jsp:include page="common/head.jsp"/>
    <style>
        html, body {
            width: 100%;
            height: 100%;
        }

        * {
            margin: 0;
            padding: 0;
        }

        body {
            background: black url('/resources/img/bg.jpg') center / cover no-repeat;
            opacity: 0;
            transition: opacity .5s linear;
        }

        body.show {
            opacity: 1;
        }

        h2, input, button {
            font-family: "Source Code Pro", "Microsoft Yahei", serif;
        }

        input, button {
            outline: none !important;
        }

        .login_box {
            box-sizing: content-box;
            box-shadow: -15px 15px 15px rgba(6, 17, 47, 0.6);
            opacity: 1;
            width: 310px;
            height: 240px;
            position: absolute;
            left: 0;
            right: 0;
            margin: auto;
            top: 0;
            bottom: 0;
            padding: 30px 40px 40px 40px;
            background: #35394a;
            background: -webkit-gradient(linear, left bottom, right top, color-stop(0%, #35394a), color-stop(100%, rgb(0, 0, 0)));
            background: -webkit-linear-gradient(230deg, rgba(53, 57, 74, 0) 0%, rgb(0, 0, 0) 100%);
            background: linear-gradient(230deg, rgba(53, 57, 74, 0) 0%, rgb(0, 0, 0) 100%);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='rgba(53, 57, 74, 0)', endColorstr='rgb(0, 0, 0)', GradientType=1);
            color: #D3D7F7;
            text-align: center;
        }

        .login_box h2 {
            font-size: 25px;
            padding: 10px 0;
            margin: 0;
        }

        .login_box input {
            display: block;
            width: 100%;
            line-height: 1.4;
            color: #555;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 4px;
            -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
            -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
            transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
            position: relative;
            height: auto;
            padding: 10px;
            box-sizing: border-box;
            margin-bottom: 20px;
            border-radius: 5px;
            border: 1px solid #eaeaea;
            box-shadow: none;
            font-size: 16px;
        }

        .login_box button {
            border-radius: 50px;
            background: transparent;
            padding: 10px 50px;
            border: 2px solid #4FA1D9;
            color: #4FA1D9;
            font-size: 16px;
            font-weight: bold;
            -webkit-transition-property: background, color;
            transition-property: background, color;
            -webkit-transition-duration: .2s;
            transition-duration: .2s;
            cursor: pointer;
            margin-top: 5px;
            font-family: "Source Code Pro", "Microsoft Yahei", serif;
        }

        .login_box button:focus {
            color: #4FA1D9;
        }

        .login_box button:hover {
            color: white;
            background: #4FA1D9;
        }
    </style>
</head>
<body>
<!-- 动画效果 -->
<canvas id="canvas" width="100%" height="100%"></canvas>

<!-- 登陆版 -->
<div class="login_box">
    <h2>Welcome to Automate2</h2>
    <div class="login-wrap">
        <input type="text" id="userName" name="automate-user-name" class="form-control" placeholder="Username"
               autocomplete="false" autofocus value="admin">
        <input type="password" id="passWord" name="automate-pass-word" class="form-control" placeholder="Password" value="admin">

        <button class="btn btn-lg btn-login btn-block" type="button" id="loginBtn">SIGN IN</button>
    </div>
</div>

<jsp:include page="common/common_js.jsp"/>
<script type="text/javascript" src="/resources/js/login-page.js"></script>
<script>
    $(function () {
        $("body").addClass("show");
    });


    toastr.options = {
        "closeButton": false,
        "debug": false,
        "progressBar": false,
        "positionClass": "toast-top-center",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }

    var doLogin = function () {
        var userName = $("#userName").val();
        var passWord = $("#passWord").val();
        if (userName && passWord) {
            $.post('/doLogin', {
                userName: userName,
                passWord: passWord
            }, function (data) {
                console.log(data);
                if (data.status == 200) {
                    window.location.href = "/index";
                } else {
                    toastr.warning(data.msg, "提示")
                }

            });
        }
    };

    $("#loginBtn").click(doLogin);
    $(".login_box").on("keydown", function (e) {
        if (e && e.keyCode == 13) { // enter 键
            doLogin();
        }
    });
</script>
</body>
</html>
