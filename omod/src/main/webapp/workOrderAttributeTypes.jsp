<%@ include file="/WEB-INF/template/include.jsp"%>
<%--
  ~ The contents of this file are subject to the OpenMRS Public License
  ~ Version 1.1 (the "License"); you may not use this file except in
  ~ compliance with the License. You may obtain a copy of the License at
  ~ http://license.openmrs.org
  ~
  ~ Software distributed under the License is distributed on an "AS IS"
  ~ basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
  ~ License for the specific language governing rights and limitations
  ~ under the License.
  ~
  ~ Copyright (C) OpenMRS, LLC.  All Rights Reserved.
  --%>
<openmrs:require allPrivileges="Manage Work Order Metadata" otherwise="/login.htm" redirect="/module/openhmis/workOrder/workorderAttributeTypes.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<openmrs:htmlInclude file="/moduleResources/openhmis/workorder/js/screen/workOrderAttributeTypes.js" />

<h2><openmrs:message code="openhmis.workorder.admin.workorderattributetypes" /></h2>

<%@ include file="/WEB-INF/template/footer.jsp"%>