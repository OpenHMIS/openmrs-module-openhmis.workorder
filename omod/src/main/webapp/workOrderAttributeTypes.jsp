<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require allPrivileges="Manage Work Order Metadata" otherwise="/login.htm" redirect="/module/openhmis/workOrder/workorderAttributeTypes.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<openmrs:htmlInclude file="/moduleResources/openhmis/workorder/js/screen/workOrderAttributeType.js" />

<h2><openmrs:message code="openhmis.workorder.admin.workorderattributetypes" /></h2>

<%@ include file="/WEB-INF/template/footer.jsp"%>