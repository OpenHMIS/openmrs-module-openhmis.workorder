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
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

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
	public List<WorkOrder> getUserWorkOrders(final User user, WorkOrderStatus status, PagingInfo paging);
}
