/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
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
package org.openmrs.module.openhmis.workorder.api.search;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.openhmis.commons.api.entity.search.BaseMetadataTemplateSearch;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;

public class WorkOrderSearch extends BaseMetadataTemplateSearch<WorkOrderTemplate> {
	public WorkOrderSearch() {
		this(new WorkOrderTemplate(), StringComparisonType.EQUAL, false);
	}

	public WorkOrderSearch(WorkOrderTemplate template) {
		super(template);
	}

	public WorkOrderSearch(WorkOrderTemplate template, Boolean includeRetired) {
		super(template, includeRetired);
	}

	public WorkOrderSearch(WorkOrderTemplate template, StringComparisonType nameComparisonType, Boolean includeRetired) {
		super(template, nameComparisonType, includeRetired);
	}

	private ComparisonType parentComparisonType;
	private ComparisonType instanceTypeComparisonType;

	@Override
	public void updateCriteria(Criteria criteria) {
		super.updateCriteria(criteria);

		WorkOrder wo = getTemplate();
		if (wo.getInstanceType() != null ||
				(instanceTypeComparisonType != null && instanceTypeComparisonType != ComparisonType.EQUAL)) {
			criteria.add(createCriterion("instanceType", wo.getInstanceType(), instanceTypeComparisonType));
		}
		if (wo.getStatus() != null) {
			criteria.add(Restrictions.eq("status", wo.getStatus()));
		}
		if (wo.getAssignedToUser() != null) {
			criteria.add(Restrictions.eq("assignedToUser", wo.getAssignedToUser()));
		}
		if (wo.getAssignedToRole() != null) {
			criteria.add(Restrictions.eq("assignedToRole", wo.getAssignedToRole()));
		}
		if (wo.getParentWorkOrder() != null ||
				(parentComparisonType != null && parentComparisonType != ComparisonType.EQUAL)) {
			criteria.add(createCriterion("parentWorkOrder", wo.getParentWorkOrder(), parentComparisonType));
		}
	}

	public ComparisonType getParentComparisonType() {
		return parentComparisonType;
	}

	public void setParentComparisonType(ComparisonType parentComparisonType) {
		this.parentComparisonType = parentComparisonType;
	}

	public ComparisonType getInstanceTypeComparisonType() {
		return instanceTypeComparisonType;
	}

	public void setInstanceTypeComparisonType(ComparisonType instanceTypeComparisonType) {
		this.instanceTypeComparisonType = instanceTypeComparisonType;
	}
}
