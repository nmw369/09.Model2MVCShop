<%@ page contentType="text/html; charset=euc-kr" %>

<%@ page import="com.model2.mvc.service.domain.User" %>

<%
	User vo=(User)session.getAttribute("user");
	String role="";
	
	if(vo != null) {
		role=vo.getRole();
	}
%>

<html>
<head>
<title>Model2 MVC Shop</title>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
<link href="/css/left.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function history(){
	popWin = window.open("/product/history","popWin","left=300, top=200, width=300, height=200, marginwidth=0, marginheight=0, scrollbars=no, scrolling=no, menubar=no, resizable=no");
}

function chating() {
	popWin 
		= window.open("/product/chating","popWin", "left=300,top=200,width=500,height=200,marginwidth=0,marginheight=0,scrollbars=no,scrolling=no,menubar=no,resizable=no");
}
</script>

</head>

<!-- <body background="/images/left/imgLeftBg.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  > -->
<body topmargin="10">



<!--menu 01 line-->
<tr>
<td valign="top"> 
	<table  border="0"  >	
		<% 	if(vo != null){ %>
		<tr>
			
				<a href="/user/getUser?userId=<%=vo.getUserId() %>" target="rightFrame">����������ȸ</a>
				
		
		<%	}  %>
		<% if(role.equals("admin")){%>
				&nbsp;
				<a href="/user/listUser" target="rightFrame">ȸ��������ȸ</a>
		<% } %>
		
	<!-- 	</tr>
	</table>
</td>
</tr> -->

<%	if(role.equals("admin")){ %>
<!--menu 02 line-->
<!-- <tr>
	<td valign="top"> 
		<table  border="0" cellspacing="0" cellpadding="0" width="159">
			<tr>
				<td class="Depth03"> -->
					&nbsp;
					<a href="../product/addProductView.jsp;" target="rightFrame">�ǸŻ�ǰ���</a>
					&nbsp;
					<a href="/purchase/listSale.do?"  target="rightFrame">�ǸŵȻ�ǰ����</a>
					&nbsp;
					<br/>
					<a href="/purchase/cancelList.do" target="rightFrame">������Ҹ��</a>
				
<% } %>

<!--menu 03 line-->
<!-- <tr>
	<td valign="top"> 
		<table  border="0" cellspacing="0" cellpadding="0" width="159">
			<tr>
				<td class="Depth03"> -->
				&nbsp;
					<a href="/listProduct.do?menu=search" target="rightFrame">�� ǰ �� ��</a>
				
			<%	if(vo != null && role.equals("user")){%>
					&nbsp;
					<a href="/purchase/listPurchase?cancel=yes"  target="rightFrame">�����̷���ȸ</a>
				
			<%  }%>
					&nbsp;
					<a href="javascript:history()">�ֱ� �� ��ǰ</a>
					&nbsp;
					<br/>				
					<a href="/purchase/mainView" target="rightFrame">Home</a>
					&nbsp;
					<a href="javascript:chating()">�ǽð����</a>
					&nbsp;
					<a href="https://docs.oracle.com/javaee/7/api/javax/websocket/package-summary.html">ä��API</a>
				</td>
			</tr>
		</table>
	</td>
</tr>

</table>

</body>
</html>
