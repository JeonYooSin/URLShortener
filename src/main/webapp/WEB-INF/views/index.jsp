<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>URL Shortener</title>
</head>
<body style="text-align: center">
    <h1>단축 URL 생성</h1>

    <div>
        <input type="text" id="inputURL" value="" placeholder="http:// URL을 입력하세요." style="width: 500px; height: 20px;">
        <p></p>
        <input type="button" value="단축 URL 생성하기" onclick="shortening()">
    </div>

    <div style="padding-top: 10px;">
        <input type="text" id="outputURL" value="" style="width: 300px; height: 20px;">
        <p></p>
        <button>페이지 이동</button> <button>통계</button>
    </div>
</body>


<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
    var url = window.location.protocol + "//" + window.location.host;

    function shortening(){
        alert("test")

        var dst_params = {
            "inputURL" : $("#inputURL").val()
        }

        $.ajax({
            type : 'POST',
            url : url + "/shortening",
            dataType : 'json',
            data : dst_params,
            async: true
        }).done(function(data) {
            console.log(data);

            if(data["result"] == 0){
                $("#outputURL").val(data["outputURL"]);
            }

        }).fail(function(data, textStatus, jqXHR) {
            alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
            alert("실패");
        });
    }

    function ajax(dst_params){
        var url = window.location.protocol + "//" + window.location.host + "/decrypt";

        $.ajax({
            type : 'POST',
            url : url,
            dataType : 'json',
            data : dst_params,
            async: true,
        }).done(function(data) {
            console.log(data);

            var encode;
            var decode;
            if(data.type == "E"){
                encode = data.code;
                decode = data.requestCode;
            }else{
                encode = data.requestCode;
                decode = data.code;
            }


            var html = "<li>"
                +  "    <p>Encode</p>"
                +  "    <P>" + encode + "</p>"
                +  "</li>"
                +   "<li>"
                +  "    <p>Decode</p>"
                +  "    <P>" + decode + "</p>"
                +  "</li>";

            $("#photo").append(html);

        }).fail(function(data, textStatus, jqXHR) {
            alert("실패");
        });
    }

</script>

</html>
