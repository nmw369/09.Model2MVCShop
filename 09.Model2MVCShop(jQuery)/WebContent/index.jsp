<%@ page contentType="text/html; charset=euc-kr" %>

<html>
<head>
<title>Model2 MVC Shop</title>
<!--ȭ�� ��� ȭ�鿡 ���缭 �����ֱ�  -->
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
</head>
<script type="text/javascript">
/* PC���� ��������� Check */

var filter = "win16|win32|win64|mac|macintel";



if( navigator.platform  ){

	if( filter.indexOf(navigator.platform.toLowerCase())<0 ){

		alert("����� ��⿡�� ����");

		// Ư�� �ּҷ� �̵���Ű�� ��
		location.href = "/Mobileindex.jsp";
		
	}else{

		/* alert("PC���� ����"); */
		location.href = "/PCindex.jsp";

	}

}


</script>


</html>