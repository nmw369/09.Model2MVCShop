<%@ page contentType="text/html; charset=euc-kr" %>

<html>
<head>
<title>Model2 MVC Shop</title>
<!--ȭ�� ��� ȭ�鿡 ���缭 �����ֱ�  -->
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
</head>
<!-- <script type="text/javascript">
/* PC���� ��������� Check */

var filter = "win16|win32|win64|mac|macintel";



if( navigator.platform  ){

	if( filter.indexOf(navigator.platform.toLowerCase())<0 ){

		

		// Ư�� �ּҷ� �̵���Ű�� ��
		location.href = "/layout/left2.jsp";
		
	}else{

		

	}

}


</script> -->


<!-- <frameset rows="80,*" cols="*" frameborder="NO" border="0" framespacing="0"> -->

  <frameset ROWS="70px,*">
  <frame src="/layout/top.jsp" name="topFrame" scrolling="NO" >
	  
  <frameset ROWS="70px,*"> 
   <frame src="/layout/left2.jsp" name="leftFrame" scrolling="NO">
   
    <frame src="/purchase/mainView" name="rightFrame"  scrolling="auto">
  </frameset>



<!-- <html> 
<head><title>"Ȩ�� ����"</title></head> 
 
 
<frame src="ex_1.html"> 
<frame src="ex_2.html" name="ha"> 
</frameset> 
  
</html>-->

</html>