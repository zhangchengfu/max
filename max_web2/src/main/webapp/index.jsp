<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <%request.getSession().setAttribute("user", "shlly");%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
    <script src="https://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!--<script type="text/javascript" src="js/jquery-1.7.2.js"></script>-->
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <title>webSocket测试</title>
    <script type="text/javascript">
        $(function () {
            var websocket;
            var user = "<%=request.getSession().getAttribute("user")%>";
            if ('WebSocket' in window) {
                websocket = new WebSocket("ws://localhost:9000/websocket");
            } else if ('MozWebSocket' in window) {
                websocket = new MozWebSocket("ws://127.0.0.1:9000/websocket");
            } else {
                websocket = new SockJS("http://127.0.0.1:9000/websocket");
                stompClient = Stomp.over(websocket);
                stompClient.connect({}, function (frame) {
                    setConnected(true);
                });
            }
            websocket.onopen = function (evnt) {
                $("#tou").html("链接服务器成功!")
            };
            websocket.onopen = function (evnt) {
                $("#tou").html("链接服务器成功!")
            };
            var stompClient = Stomp.over(websocket);
            //当有消息时，会自动调用此方法
            websocket.onmessage = function (evnt) {
                $("#msg").html($("#msg").html() + "<br/>" + evnt.data);
            };
            websocket.onerror = function (evnt) {
            };
            websocket.onclose = function (evnt) {
                $("#tou").html("与服务器断开了链接!")
            }
            $('#send').bind('click', function () {
                send();
            });

            function send() {
                debugger;
                if (websocket != null) {
                    var message = document.getElementById('message').value;
                    websocket.send("{\"toname\":\"all\",\"message\":\"" + message + "\,\"user\":\"" + user + "\"}");
                } else {
                    alert('未与服务器链接.');
                }
            }

            //强制关闭浏览器断开连接
            window.onbeforeunload = function () {
                if (stompClient != null) {
                    stompClient.disconnect();
                }
            }
        });
    </script>

</head>
<body>

<%=request.getSession().getAttribute("user")%>
<div class="page-header" id="tou">
    webSocket及时聊天Demo程序
</div>
<div class="well" id="msg">
</div>
<div class="col-lg">
    <div class="input-group">
        <input type="text" class="form-control" placeholder="发送信息..." id="message">
        <span class="input-group-btn">
        <button class="btn btn-default" type="button" id="send">发送</button>
      </span>
    </div><!-- /input-group -->
</div><!-- /.col-lg-6 -->
</body>

</html>