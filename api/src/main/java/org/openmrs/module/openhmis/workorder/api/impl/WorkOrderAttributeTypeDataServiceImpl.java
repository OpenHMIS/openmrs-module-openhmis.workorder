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

import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderAttributeTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

public class WorkOrderAttributeTypeDataServiceImpl extends
		BaseMetadataDataServiceImpl<WorkOrderAttributeType> implements
		IWorkOrderAttributeTypeDataService {

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return null;
	}

	@Override
	protected void validate(WorkOrderAttributeType object) throws APIException {
	}


	@Override
	public List<WorkOrderAttributeType> getByFormat(final String format, final Integer workOrderTypeId) {
		final WorkOrderType orderType = new WorkOrderType();
		orderType.setId(workOrderTypeId);
		return getByFormat(format, orderType);
	}

	@Override
	public List<WorkOrderAttributeType> getByFormat(final String format, final WorkOrderType workOrderType) {
		List<WorkOrderAttributeType> types = executeCriteria(getEntityClass(), null, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				if (workOrderType != null && workOrderType.getId() != null)
					criteria.add(Restrictions.eq("owner", workOrderType));
				criteria.add(Restrictions.eq("format", format));
				criteria.add(Restrictions.eq("retired", false));
			}
		});
		return types;
	}

	@Override
	public WorkOrderAttributeType getByFormatUnique(final String format, final Integer workOrderTypeId) throws NonUniqueResultException {
		final WorkOrderType orderType = new WorkOrderType();
		orderType.setId(workOrderTypeId);
		return getByFormatUnique(format, workOrderTypeId);
	}

	@Override
	public WorkOrderAttributeType getByFormatUnique(final String format, final WorkOrderType workOrderType) throws NonUniqueResultException {
		List<WorkOrderAttributeType> results = getByFormat(format, workOrderType);
		if (results.size() > 1)
			throw new NonUniqueResultException("The work order type has more than one attribute of type " + format + ".");
		else if (results.isEmpty())
			return null;
		return results.get(0);
	}

}
