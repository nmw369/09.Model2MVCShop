

<%@ page contentType="text/html;charset=EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%
	Purchase purchaseVO = (Purchase) request.getAttribute("purchase");
	String pay = "";
	if(purchaseVO != null){
		if(purchaseVO.getPaymentOption().equals("1")){
			pay = "���ݱ���";
		}else{
			pay = "�ſ뱸��";
		}
	}
%> --%>
<html>
	<head>
	<!-- ȭ�� ���� -->
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
		<title>Insert title here</title>
	</head>

	<body>

		<form name="updatePurchase" action="/purchase/updatePurchaseView?tranNo=${purchase.tranNo}" method="post">
		
		������ ���� ���Ű� �Ǿ����ϴ�.
		
		<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>��ǰ��ȣ</td>
				<td>${purchase.purchaseProd.prodNo}</td>
				<td></td>
			</tr>
			<tr>
				<td>�����ھ��̵�</td>
				<td>${purchase.buyer.userId}</td>
				<td></td>
			</tr>
			<tr>
				<td>���Ź��</td>
				<td>
				${purchase.paymentOption=='1'?"���ݱ���":"�ſ뱸��"}
				</td>
				<td></td>
			</tr>
			<tr>
				<td>�������̸�</td>
				<td>${purchase.receiverName}</td>
				<td></td>
			</tr>
			<tr>
				<td>�����ڿ���ó</td>
				<td>${purchase.receiverPhone}</td>
				<td></td>
			</tr>
			<tr>
				<td>���ż���</td>
				<td>${purchase.sEA}</td>
				<td></td>
			</tr>
			<tr>
				<td>�������ּ�</td>
				<td>${purchase.divyAddr}</td>
				<td></td>
			</tr>
				<tr>
				<td>���ſ�û����</td>
				<td>${purchase.divyRequest }</td>
				<td></td>
			</tr>
			<tr>
				<td>����������</td>
				<td>${purchase.divyDate}</td>
				<td></td>
			</tr>
		</table>
		</form>
	
	</body>
</html>