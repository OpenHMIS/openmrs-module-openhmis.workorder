<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require allPrivileges="Manage Work Orders" otherwise="/login.htm" redirect="/module/openhmis/workorder/workrders.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<openmrs:htmlInclude file="/moduleResources/openhmis/workorder/js/screen/workOrders.js" />

<h2><openmrs:message code="openhmis.workorder.admin.workorders" /></h2>

<%@ include file="/WEB-INF/template/footer.jsp"%>