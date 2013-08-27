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

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class IWorkOrderAttributeTypeDataServiceTest extends BaseModuleContextSensitiveTest {
	private IWorkOrderAttributeTypeDataService service;
	
	@Before
	public void before() throws Exception {
		executeDataSet(IWorkOrderDataServiceTest.DATASET);
		service = Context.getService(IWorkOrderAttributeTypeDataService.class);
	}

	/**
	 * @see IWorkOrderAttributeTypeDataService#getByFormat(String,Integer)
	 * @verifies filter based on data type and work order
	 */
	@Test
	public void getByFormat_shouldFilterBasedOnDataTypeAndWorkOrder()
			throws Exception {
		List<WorkOrderAttributeType> results = service.getByFormat("org.openmrs.Location", 0);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals("Location", results.get(0).getName());
	}
	

}