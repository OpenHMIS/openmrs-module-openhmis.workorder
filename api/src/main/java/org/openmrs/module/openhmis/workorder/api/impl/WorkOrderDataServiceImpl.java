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
package org.openmrs.module.openhmis.workorder.api.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class WorkOrderDataServiceImpl
		extends BaseMetadataDataServiceImpl<WorkOrder>
		implements IWorkOrderDataService {
	/**
	 * @should ensure that child work orders are ordered contiguously from zero
	 */
	@Override
	public WorkOrder save(WorkOrder object) throws APIException {
		if (object.getWorkOrders() != null && !object.getWorkOrders().isEmpty()) {
			Iterator<WorkOrder> iter = object.getWorkOrders().iterator();
			WorkOrder subOrder = null;

			try {
				for (int i = 0; (subOrder = iter.next()) != null; i++) {
					subOrder.setItemOrder(i);
				}
			} catch (NoSuchElementException e) { }
		}

		return super.save(object);
	}

	@Override
	protected Collection<? extends OpenmrsObject> getRelatedObjects(WorkOrder entity) {
		return entity.getWorkOrders();
	}

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return null;
	}

	@Override
	protected void validate(WorkOrder object) throws APIException {
		if (object.getAssignedTo() == null &&
				(object.getStatus() == WorkOrderStatus.COMPLETE || object.getStatus() == WorkOrderStatus.CANCELLED)) {
			throw new APIException("A work order must be assigned to a user to be saved as " + object.getStatus());
		}
	}

	/**
	 * Overrides the default get all logic to return only the root categories (ie, categories with no parent).
	 * @param pagingInfo The {@link org.openmrs.module.openhmis.commons.api.PagingInfo} object to load with the record count.
	 * @return The root categories.
	 */
	@Override
	public List<WorkOrder> getAll(final boolean includeRetired, PagingInfo pagingInfo) {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			Context.requirePrivilege(privileges.getGetPrivilege());
		}

		return executeCriteria(WorkOrder.class, pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				criteria.add(Restrictions.isNull("parentWorkOrder"));

				if (!includeRetired) {
					criteria.add(Restrictions.eq("retired", false));
				}
			}
		});
	}
}
