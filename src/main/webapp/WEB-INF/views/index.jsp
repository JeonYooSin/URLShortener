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
        <input type="button" value="단축 URL 열기" onclick="javascript:window.open($('#outputURL').val());">
        <input type="button" value="단축 URL 정보" onclick="chartInfo()">
    </div>

    <div id="chart_div" style="display: none;">
        <table style="margin-top: 15px; width: 600px">
            <tbody>
            <tr>
                <th>생성일</th>
                <td><p id="chart_createDate"></p></td>
            </tr>
            <tr>
                <th>URL</th>
                <td><p id="chart_inputURL"></p></td>
            </tr>
            <tr>
                <th>단축 URL</th>
                <td><p id="chart_shortURL"></p></td>
            </tr>
            <tr>
                <th>단축 URL 호출 수</th>
                <td><p id="chart_callCount"></p></td>
            </tr>
            </tbody>
        </table>
    </div>
</body>


<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
    var url = window.location.protocol + "//" + window.location.host;

    function shortening(){
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
            $("#chart_div").hide();

            if(data["result"] == 0){
                $("#outputURL").val(data["shortURL"]);
            }else{
                $("#outputURL").val("");
                alert(data["msg"]);
            }

        }).fail(function(data, textStatus, jqXHR) {
            alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
            alert("실패");
        });
    }

    function chartInfo(){
        $.ajax({
            type : 'GET',
            url : $("#outputURL").val() + "+" ,
            dataType : 'json',
            async: true
        }).done(function(data) {
            console.log(data);

            if(data["result"] == 0){
                $("#chart_div").show();

                $("#chart_inputURL").text(data["inputURL"]);
                $("#chart_shortURL").text(data["shortURL"]);
                $("#chart_createDate").text(data["createDate"]);
                $("#chart_callCount").text(data["callCount"]);

            }else{
                $("#chart_div").hide();

                alert(data["msg"]);
            }

        }).fail(function(data, textStatus, jqXHR) {
            alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
            alert("실패");
        });
    }

</script>


<style>
    table {
        margin-left:auto;
        margin-right:auto;
    }

    th {
        background: aqua;
        width: 30%;
    }

    table, td, th {
        border-collapse : collapse;
        border : 1px solid black;
    }
</style>
</html>
