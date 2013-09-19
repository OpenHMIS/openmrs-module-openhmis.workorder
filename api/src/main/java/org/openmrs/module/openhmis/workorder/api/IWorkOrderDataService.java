/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.workorder.api;

import org.openmrs.User;
import org.openmrs.annotation.Authorized;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;
import org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch;
import org.openmrs.module.openhmis.workorder.api.util.PrivilegeConstants;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IWorkOrderDataService extends IMetadataDataService<WorkOrder> {
	/** 
	 * Returns the {@link WorkOrder}s with the specified {@link WorkOrderStatus} for the specified {@link User}. 
	 * @param user The {@link User}.
	 * @param status The {@link WorkOrderStatus}.
	 * @param paging The paging information or {@code null} to return all results.
	 * @return The work orders with the specified status associated with the specified user 
	 * @should return all work orders with the specified status for specified user
	 * @should return specified work orders for user
	 * @should return specified work orders for user role
	 * @should return specified work orders with user role as child role of work order role
	 * @should return specified work orders with user role as grandchild role of work order role
	 * @should not return work orders when user role not descendant of work order role
	 * @should not return work orders when user role is parent of work order role
	 * @should return empty list when no work orders
	 * @should return paged work orders when paging is specified
	 * @should return all work orders when paging is null
	 * @should throw NullPointerException when user is null
	 * @should return all work orders for user when status is null
	 */
	@Transactional(readOnly = true)
	@Authorized({PrivilegeConstants.VIEW_METADATA})
	List<WorkOrder> getUserWorkOrders(User user, WorkOrderStatus status, PagingInfo paging);

	/**
	 * Finds all {@link WorkOrder}s using the specified {@link WorkOrderSearch} settings.
	 * @param workOrderSearch The work order search settings.
	 * @return The work orders found or an empty list if no work orders were found.
	 */
	@Transactional(readOnly = true)
	@Authorized({PrivilegeConstants.VIEW_METADATA})
	List<WorkOrder> findWorkOrders(WorkOrderSearch workOrderSearch);

	/**
	 * Finds all {@link WorkOrder}s using the specified {@link WorkOrderSearch} settings.
	 * @param workOrderSearch The work order search settings.
	 * @param paging The paging information
	 * @return The work orders found or an empty list if no work orders were found.
	 * @should throw NullPointerException if search object is null
	 * @should throw NullPointerException if search object template is null
	 * @should return empty list if not results are found
	 * @should return work orders filtered by instance type
	 * @should return work orders filtered by status
	 * @should return work orders filtered by assigned to user
	 * @should return work orders filtered by assigned to role
	 * @should return work orders filtered by parent work order
	 * @should return all results if paging is null
	 * @should return paged results of paging is specified
	 */
	@Transactional(readOnly = true)
	@Authorized({PrivilegeConstants.VIEW_METADATA})
	List<WorkOrder> findWorkOrders(WorkOrderSearch workOrderSearch, PagingInfo paging);
}
