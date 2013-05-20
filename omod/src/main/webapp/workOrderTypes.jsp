<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require allPrivileges="Manage Work Order Metadata" otherwise="/login.htm" redirect="/module/openhmis/workorder/workOrderTypes.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<openmrs:htmlInclude file="/moduleResources/openhmis/workorder/js/screen/workOrderTypes.js" />

<h2><openmrs:message code="openhmis.workorder.admin.workordertypes" /></h2>

<%@ include file="/WEB-INF/template/footer.jsp"%>