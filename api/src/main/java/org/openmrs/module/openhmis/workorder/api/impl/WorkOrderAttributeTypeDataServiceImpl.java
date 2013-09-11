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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderAttributeTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

import javax.persistence.NonUniqueResultException;
import java.util.List;

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
	public List<WorkOrderAttributeType> findByClass(final WorkOrderType type, final Class attributeClass) {
		return executeCriteria(getEntityClass(), null, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				if (type != null && type.getId() != null)
					criteria.add(Restrictions.eq("owner", type));
				criteria.add(Restrictions.eq("format", attributeClass.getName()));
				criteria.add(Restrictions.eq("retired", false));
			}
		});
	}

	@Override
	public WorkOrderAttributeType getByClass(WorkOrderType type, Class attributeClass) throws NonUniqueResultException {
		List<WorkOrderAttributeType> results = findByClass(type, attributeClass);

		if (results.size() > 1) {
			throw new NonUniqueResultException("The work order type has more than one attribute of type " + attributeClass.getName() + ".");
		} else if (results.isEmpty()) {
			return null;
		} else {
			return results.get(0);
		}
	}

}
