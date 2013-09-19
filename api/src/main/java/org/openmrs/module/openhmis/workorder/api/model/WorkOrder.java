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
package org.openmrs.module.openhmis.workorder.api.model;

import org.apache.commons.lang.NotImplementedException;
import org.openmrs.Attributable;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.Utility;
import org.openmrs.module.openhmis.commons.api.entity.model.BaseCustomizableInstanceMetadata;
import org.openmrs.module.openhmis.commons.api.entity.model.LazyRole;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderDataService;

import java.util.LinkedList;
import java.util.List;

public class WorkOrder extends BaseCustomizableInstanceMetadata<WorkOrderType, WorkOrderAttribute>
	implements Attributable<WorkOrder> {
	public static final long serialVersionUID = 0L;

	private Integer workOrderId;
	protected WorkOrderStatus status = WorkOrderStatus.NEW;
	private User assignedToUser;
	private Role assignedToRole;
	protected WorkOrder parentWorkOrder = null;
	private Integer itemOrder = 0;

	private List<WorkOrder> workOrders;

	public void addWorkOrder(WorkOrder workOrder) {
		if (workOrder != null) {
			if (workOrders == null)
				workOrders = new LinkedList<WorkOrder>();
			workOrders.add(workOrder);
			workOrder.parentWorkOrder = this;
		}
	}
	
	public boolean removeWorkOrder(WorkOrder workOrder) {
		if (workOrders != null && workOrder != null) {
			workOrder.parentWorkOrder = null;
			return workOrders.remove(workOrder);
		}
		return false;
	}
	
	@Override
	public WorkOrder hydrate(String id) {
		return Context.getService(IWorkOrderDataService.class).getById(Integer.parseInt(id));
	}

	@Override
	public String serialize() {
		throw new NotImplementedException();
	}

	@Override
	public List<WorkOrder> getPossibleValues() {
		throw new NotImplementedException();
	}

	@Override
	public List<WorkOrder> findPossibleValues(String searchText) {
		throw new NotImplementedException();
	}

	@Override
	public String getDisplayString() {
		return "WorkOrderList " + getId();
	}
	
	/** Getters & setters **/
	@Override
	public Integer getId() {
		return workOrderId;
	}

	@Override
	public void setId(Integer id) {
		workOrderId = id;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public void setStatus(WorkOrderStatus status) {
		this.status = status;
	}

	public User getAssignedToUser() {
		return assignedToUser;
	}

	public void setAssignedToUser(User user) {
		this.assignedToUser = user;
	}

	public Role getAssignedToRole() {
		return assignedToRole;
	}

	public void setAssignedToRole(Role role) {
//		LazyRole roleC = Utility.as(LazyRole.class, role);
//		if (roleC == null) {
//			roleC = new LazyRole(role);
//		}

		this.assignedToRole = role;//C;
	}

	public List<WorkOrder> getWorkOrders() {
		return workOrders;
	}

	public void setWorkOrders(List<WorkOrder> workOrders) {
		this.workOrders = workOrders;
	}

	public WorkOrder getParentWorkOrder() {
		return parentWorkOrder;
	}

	public void setParentWorkOrder(WorkOrder parent) {
		// Remove this work order from current parent
		if (this.parentWorkOrder != null) {
			this.parentWorkOrder.removeWorkOrder(this);
		}
		// Add to the new parent
		if (parent != null) {
			parent.addWorkOrder(this);
		}
		parentWorkOrder = parent;
	}

	public Integer getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
	}
}
