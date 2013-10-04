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
package org.openmrs.module.openhmis.workorder.api.model;

import org.openmrs.module.openhmis.commons.api.entity.model.BaseInstanceAttributeType;

public class WorkOrderAttributeType extends BaseInstanceAttributeType<WorkOrderType> {
	public static final long serialVersionUID = 0L;

	public WorkOrderAttributeType() {

	}

	public WorkOrderAttributeType(String name, String format, Integer foreignKey, String regExp, boolean required) {
		setName(name);
		setFormat(format);
		setForeignKey(foreignKey);
		setRegExp(regExp);
		setRequired(required);
	}
}
