<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>

<%-- <%
	HashMap<String, Object> map = (HashMap<String, Object>) request.getAttribute("map");
	Search searchVO = (Search) request.getAttribute("Search");
	
	int total = 0;
	int pageNumber = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
	
	ArrayList<Purchase> list = null;
	if (map != null) {
		total = ((Integer) map.get("count")).intValue();
		list = (ArrayList<Purchase>) map.get("list");
	}
	int currentPage = searchVO.getCurrentPage();
	
	System.out.println(currentPage+"::::LISTPURCHASE Ȯ��!@@!@!!@!@!@@!!@");
	
	int totalPage = 0;
	if (total > 0) {
		totalPage = total / searchVO.getPageSize();
		if (total % searchVO.getPageSize() > 0)
			totalPage += 1;
	}
	
	int start = 1 + ( pageNumber * ((searchVO.getCurrentPage() - 1) / pageNumber) );
	int end = start + pageNumber - 1;
	if(end > totalPage){
		end = totalPage;
	}
	
%> --%>
<html>
	<head>
	<!-- ȭ�� ���� -->
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
	<title>���� �����ȸ</title>
		
		<link rel="stylesheet" href="/css/admin.css" type="text/css">
		
		<script type="text/javascript">
			function fncGetList(currentPage) {
				document.getElementById("currentPage").value = currentPage;
				document.detailForm.submit();
			}
		</script>
	</head>
	<style>

/* body{
    background: url("/images/clock2.jpg") no-repeat center center fixed; 
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
} */
</style>
	
	
	
	
	<body bgcolor="#ffffff" text="#000000">
	
		<div style="width: 98%; margin-left: 10px;">
		
		<form name="detailForm" action="/purchase/listSale" method="post">
		
		<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
			<tr>
				<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
				<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="93%" class="ct_ttl01">���� �����ȸ</td>
						</tr>
					</table>
				</td>
				<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
			</tr>
		</table>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
			<tr>
				<td colspan="11">��ü ${resultPage.totalCount} ��, ���� ${resultPage.currentPage } ������</td>
			</tr>
			<tr>
				<td class="ct_list_b" width="100">No</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="100">������Id</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="100">��ǰ�̹���</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">���Ż�ǰ����</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">��ǰ����</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">���ż���</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">��������Ȯ��/����</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">��ǰ��Ȳ</td>
				
			</tr>
				
				<c:set var="i" value="${currentPage}"></c:set>
				<%-- <c:set var="j" value="${pageSize}"></c:set>
				<c:set var="k" value="2"></c:set>
				 --%>
				
				<c:forEach var="vo" items="${list}">
					<c:if test="${vo.cancelCode == '0'}">					
						<%-- <c:set var="i" value="${i*j-k}"></c:set> --%>
						<c:set var="i" value="${i+1}"></c:set>
					<tr class="ct_list_pop" bgcolor="#ffffff">
						
						<td class="ct_list_test1" align="center">
							<a href="/getPurchase.do?tranNo=${vo.tranNo}">
								${i}
							</a>
							<%-- <c:set var="k" value="${k-1}"></c:set>
							<c:set var="i" value="${currentPage}"></c:set> --%>
						</td>
						<td></td>
						
						<td class="ct_list_test1" align="left">
							<a	href="/getUser.do?userId=${vo.buyer.userId}">
								${vo.buyer.userId}
							</a>
						</td>
						<td></td>
						
						<td align="center">
						<c:if test="${!empty vo.purchaseProd.fileName}">
						<img src = "/images/uploadFiles/${vo.purchaseProd.fileName}" width = "150">
						</c:if>
						<c:if test="${empty vo.purchaseProd.fileName}">
						<img src = "/images/uploadFiles/notimage.gif" width = "150">
						</c:if>
						<td></td>
						
						<td class="ct_list_test1" align="left">
							<a	href="/product/getProduct?prodNo=${vo.purchaseProd.prodNo}&menu=search&cancel=yes">
								${vo.purchaseProd.prodName}
							</a>
						</td>
						<td></td>
						
						<td class="ct_list_test1" align="left">${vo.purchaseProd.price}</td>
						<td></td>
						
						<td class="ct_list_test1" align="left">${vo.sEA}</td>
						<td></td>
						
						<td class="ct_list_test1" align="left">
						<a href="/purchase/getPurchase?tranNo=${vo.tranNo}">
								��������</a>
						</td>
						<td></td>
						
						<c:if test="${vo.tranCode=='2'}">
							<td class="ct_list_test1" align="left">�����Ϸ�  
							<a href="/purchase/updateTranCodeByProd?tranNo=${vo.tranNo}&tranCode=${vo.tranCode}">����ϱ�</a>
							</td>
						</c:if>
						<c:if test="${vo.tranCode=='3'}">
							<td class="ct_list_test1" align="left">�����
						</c:if>
						<c:if test="${vo.tranCode=='4'}">
							<td class="ct_list_test1" align="left">��ۿϷ�</td>
						</c:if>
						<td></td>
						
						
						
					</tr>
					<tr>
						<td colspan="11" bgcolor="D6D7D6" height="1"></td>
					</tr>
					</c:if>
				</c:forEach>
		</table>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
			<tr>
				<td align="center">
			
					<input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}"/>
				 
				 <jsp:include page="../common/pageNavigator.jsp"/>
				 
				 
				</td>
			</tr>
		</table>
		
		<!--  ������ Navigator �� -->
		</form>
		
		</div>
		
	</body>
</html>