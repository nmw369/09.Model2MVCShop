<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<html>
<head>
<!-- ȭ�� ���� -->
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
<title>��ǰ �����ȸ</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css" >
 <style>


/*  body{
    background: url("/images/sunset.jpg") no-repeat center center fixed; 
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
    
     
}  */
</style>

<script type="text/javascript">
	function fncGetList(currentPage){
		
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
	 function fncGetList2(currentPage,sorting){
			
		document.getElementById("currentPage").value = currentPage;
		document.getElementById("sorting").value = sorting;
		document.detailForm.submit();
	} 
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct" method="post">
<input name="menu" value="${param.menu }" type="hidden">
<table width="100%" height="37" border="0"  cellpadding="0"	cellspacing="0" >
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<%-- <%if(menu.equals("manage")){ %>
							<td width="93%" class="ct_ttl01">��ǰ ����</td>
					<%}else{ %>
							<td width="93%" class="ct_ttl01">��ǰ �����ȸ</td>
					<%} %> --%>
					
					<td width="93%" class="ct_ttl01">
					${param.menu== "manage"? "��ǰ����":"��ǰ �����ȸ"}
					</td>
									
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	
	
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${search.searchCondition =="0"? "selected" : ""}>��ǰ��ȣ</option>
				<option value="1" ${search.searchCondition =="1"? "selected" : ""}>��ǰ��</option>
				<option value="2" ${search.searchCondition =="2"? "selected" : ""}>��ǰ����</option>
			</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" 
			value="${search.searchKeyword}" onkeypress="if(event.keyCode == 13){ javascript:fncGetList('1')};">
		</td>
		
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
		��ü  ${resultPage.totalCount } ��, ���� ${resultPage.currentPage} ������ 
		<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ǰ���� 
		&nbsp;<input type="button" onClick="location.href='/listProduct.do?sorting=high'" value="������&nabla;">
		 <input type="button" onClick="location.href='/listProduct.do?sorting=low'" value="������&Delta;">  -->
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">��ǰ�̹���</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100" >
		<c:if test="${sorting=='low'}">
		<input type="hidden" name="sorting" value="${sorting}">
		<input type="button" onClick="location.href='/product/listProduct?sorting=high'" value="���ݳ�����&Delta;">
		</c:if>
		<c:if test="${sorting=='high'}">
		<input type="hidden" name="sorting" value="${sorting}">
		<input type="button" onClick="location.href='/product/listProduct?sorting=low'" value="���ݳ�����&nabla;">
		</c:if> 
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">
		<c:if test="${empty daysorting||daysorting=='lowDay'}">
		<input type="hidden" name="daysorting" value="${daysorting}">
		<input type="button" onClick="location.href='/product/listProduct?daysorting=highDay'" value="�ֽż�&Delta;">
				</c:if>
		<c:if test="${daysorting=='highDay'}">
		<input type="hidden" name="daysorting" value="${daysorting}">
		<input type="button" onClick="location.href='/product/listProduct?daysorting=lowDay'" value="�����ȼ�&nabla;">
		</c:if>
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ����</td> 
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">�������</td>
		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	
	
		
	<c:set var="i" value="${currentPage}"></c:set>
	<c:set var="j" value="${pageSize}"></c:set>
	<c:set var="k" value="2"></c:set>
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${i+1}"></c:set>
		
		
	<tr class="ct_list_pop" bgcolor="#ffffff">
		<td align="center">${i}</td>
		<td></td>
		<td align="center">
		<c:if test="${!empty product.fileName}">
		<img src = "/images/uploadFiles/${product.fileName}" width = "150">
		</c:if>
		<c:if test="${empty product.fileName}">
		<img src = "/images/uploadFiles/notimage.gif" width = "150">
		</c:if>
		</td>
		<td></td>
		<td align="center">
		<c:if test="${user.role=='user' || empty user.role }">
		<a href="/product/getProduct?prodNo=${product.prodNo}&menu=${param.menu}&lookup=yes">${product.prodName}</a>
		</c:if>
		<c:if test="${user.role=='admin' }">
		<a href="/product/updateProductView?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>
		</c:if>
		</td>
		<td></td>
		<td align="center">${product.price}</td>
		<td></td>
		<td align="center">${product.regDate}</td>
		<td></td>
		<td align="center">${product.nEA}</td>
		<td align="center">
					
				<c:if test="${product.eaCode != '0' && !empty user}">
						<td align="center">�Ǹ���</td>
					</c:if>
				
				<c:if test="${product.eaCode == '0' }">
						<c:if test="${user.role=='admin' }">
							<td align="center">������</td>
					
						</c:if>
						<c:if test="${user.role=='user' }">
							<td align="center">����</td>	
						</c:if>		
							
				</c:if>
						
					</tr>
					<tr>
						<td colspan="11" bgcolor="D6D7D6" height="1"></td>
					</tr>
				

	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>


<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			
			
			<jsp:include page="../common/pageNavigator.jsp"/>
			
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->
</form>
</div>

</body>
</html>