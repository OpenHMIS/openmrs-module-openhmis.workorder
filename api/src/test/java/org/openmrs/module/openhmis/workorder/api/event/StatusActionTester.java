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
package org.openmrs.module.openhmis.workorder.api.event;

import java.util.LinkedList;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;
import org.openmrs.module.openhmis.workorder.api.util.WorkOrderStatusAction;

public class StatusActionTester implements WorkOrderStatusAction {
	private LinkedList<Object> values;
	public StatusActionTester() {
		values = new LinkedList<Object>();
		for (int i = 0; i < 10; i++)
			values.add(null);
	}
	public Object getTestValue() { return values.peek(); }
	public Object getTestValue(int index) { return values.get(index); }
	public void setTestValue(Object value) {
		values.remove(9);
		values.addFirst(value);
	}
	@Override
	public void apply(WorkOrder workOrder, WorkOrderStatus previousStatus) {
		setTestValue(workOrder.getStatus());
	}
}
