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