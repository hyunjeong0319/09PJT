<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

function fncGetUserList(currentPage) {
	//document.getElementById("currentPage").value = currentPage;
	$("#currentPage").val(currentPage);
	$('input[name="menu"]').val('${menu}'); 
   	//document.detailForm.submit();
   	$("form")[0].reset();
	$("form").attr("method" , "POST").attr("action" , "/product/listProduct").submit();
	//$(self.location).attr('href','/product/listProduct?menu=${menu}&currentPage='+currentPage);
}
//===========================================//
 $(function() {
	 
	//==> 검색 Event 연결처리부분
	//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)s
	//==> 1 과 3 방법 조합 : $("tagName.className:filter함수") 사용함. 
	 $( "td.ct_btn01:contains('검색')" ).on("click" , function() {
		//Debug..
		//alert(  $( "td.ct_btn01:contains('검색')" ).html() );
		$("#currentPage").val(1);
		$('input[name="menu"]').val('${menu}'); 
		$("form").attr("method" , "POST").attr("action" , "/product/listProduct").submit();

	});
	
	$('.prodName').on("click" , function() {
					//Debug..
					//alert(  $( this ).text().trim() );
					var text = $(this).text();
					console.log(text);
					var prodNo = $(this).next().text();
					console.log(prodNo);
					if('${menu}'=='manage')
					{
					self.location='/product/updateProductView?prodNo='+prodNo;

					}
					if('${menu}'=='search'){
					self.location='/product/getProduct?prodNo='+prodNo;

					}	
					
	});
	
	//==> UI 수정 추가부분  :  userId LINK Event End User 에게 보일수 있도록 
	$( ".ct_list_pop td:nth-child(3)" ).css("color" , "blue");
	$("h7").css("color" , "red");
	
	
	//==> 아래와 같이 정의한 이유는 ??
	//==> 아래의 주석을 하나씩 풀어 가며 이해하세요.					
	$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");
	//console.log ( $(".ct_list_pop:nth-child(1)" ).html() );
	//console.log ( $(".ct_list_pop:nth-child(2)" ).html() );
	//console.log ( $(".ct_list_pop:nth-child(3)" ).html() );
	//console.log ( $(".ct_list_pop:nth-child(4)" ).html() ); //==> ok
	//console.log ( $(".ct_list_pop:nth-child(5)" ).html() ); 
	//console.log ( $(".ct_list_pop:nth-child(6)" ).html() ); //==> ok
	//console.log ( $(".ct_list_pop:nth-child(7)" ).html() ); 
});	
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" >
<input type="hidden" name="menu">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
							${menu == 'manage' ? "상품관리" : "상품검색"}
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${!empty search.searchCondition && search.searchCondition == 0 ? "selected" : ""}>상품번호</option>
				<option value="1" ${!empty search.searchCondition && search.searchCondition == 1 ? "selected" : ""}>상품명</option>
				<option value="2" ${!empty search.searchCondition && search.searchCondition == 2 ? "selected" : ""}>상품가격</option>
					</select>
			<%--<input 	type="text" name="searchKeyword"  value="${search.searchkeyword}" class="ct_input_g" style="width:200px; height:19px" > --%>
		<input type = "text" name ="searchKeyword" 
				value ="${! empty search.searchKeyword ? search.searchKeyword : "" }"
				class = "ct_input_g" style ="width:200px ; height:20px">	
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						검색
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
		<td colspan="11" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명
		<h7>(상품명 click:상세정보)</h7>
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr> 
		<c:set var = "i" value ="0"/>
		<c:forEach var ="product" items ="${list}">
			<c:set var ="i" value  = "${i+1}"/>
		<tr class="ct_list_pop">

		<td align="center">${i}</td>
		<td></td>
		<c:if test = "${menu == 'manage'}">
			<td align="left">
				<div>
					<span class="prodName">${product.prodName}</span>
					<span hidden="">${product.prodNo}</span>
				</div>
			</td>
		</c:if>
		<c:if test = "${menu == 'search'}">
			<td align="left">
				<div>
					<span class="prodName">${product.prodName}</span>
					<span hidden="">${product.prodNo}</span>
				</div>
			</td>
		</c:if>
		<td></td>
		<td align="left">${product.price}</td>
		<td></td>
		<td align="left">${product.manuDate}</td>
		<td></td>
		<td align="left">
		
			판매중
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/> 
		<jsp:include page="../common/pageNavigator.jsp"/>	
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
