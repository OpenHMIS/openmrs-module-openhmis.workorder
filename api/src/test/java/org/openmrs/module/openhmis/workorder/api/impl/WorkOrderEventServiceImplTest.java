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


import org.apache.struts.action.Action;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class WorkOrderEventServiceImplTest extends BaseModuleContextSensitiveTest {

	/**
	 * @see WorkOrderEventServiceImpl#parseModuleIdFromClass(Class)
	 * @verifies get the module ID from an openmrs module class
	 */
	@Test
	public void parseModuleIdFromClass_shouldGetTheModuleIDFromAnOpenmrsModuleClass()
			throws Exception {
		Action a = new Action() { };
		String moduleId = WorkOrderEventServiceImpl.parseModuleIdFromClass(a.getClass());
		Assert.assertEquals("openhmis.workorder.api.impl", moduleId);
	}
}