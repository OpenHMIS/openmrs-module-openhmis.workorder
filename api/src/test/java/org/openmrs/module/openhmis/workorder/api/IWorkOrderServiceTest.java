package org.openmrs.module.openhmis.workorder.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.security.InvalidParameterException;

public class IWorkOrderServiceTest extends BaseModuleContextSensitiveTest {
	private IWorkOrderService service;
	private IWorkOrderTypeDataService typeDataService;

	@Before
	public void before() throws Exception{
		executeDataSet(IWorkOrderDataServiceTest.DATASET);

		this.service = Context.getService(IWorkOrderService.class);
		this.typeDataService = Context.getService(IWorkOrderTypeDataService.class);
	}

	/**
	 * @verifies throw NullPointerException if type is null
	 * @see IWorkOrderService#registerModuleJavascript(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, String)
	 */
	@Test(expected = NullPointerException.class)
	public void registerModuleJavascript_shouldThrowNullPointerExceptionIfTypeIsNull() throws Exception {
		service.registerModuleJavascript(null, "some path");
	}

	/**
	 * @verifies throw InvalidParameterException if javascript path is null
	 * @see IWorkOrderService#registerModuleJavascript(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, String)
	 */
	@Test(expected = InvalidParameterException.class)
	public void registerModuleJavascript_shouldThrowInvalidParameterExceptionIfJavascriptPathIsNull() throws Exception {
		WorkOrderType type = typeDataService.getById(0);

		service.registerModuleJavascript(type, null);
	}

	/**
	 * @verifies throw InvalidParameterException if javascript path is empty
	 * @see IWorkOrderService#registerModuleJavascript(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, String)
	 */
	@Test(expected = InvalidParameterException.class)
	public void registerModuleJavascript_shouldThrowInvalidParameterExceptionIfJavascriptPathIsEmpty() throws Exception {
		WorkOrderType type = typeDataService.getById(0);

		service.registerModuleJavascript(type, "");
	}

	/**
	 * @verifies register the javascript path only for the specified type
	 * @see IWorkOrderService#registerModuleJavascript(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, String)
	 */
	@Test
	public void registerModuleJavascript_shouldRegisterTheJavascriptPathOnlyForTheSpecifiedType() throws Exception {
		WorkOrderType type0 = typeDataService.getById(0);
		WorkOrderType type1 = typeDataService.getById(1);
		String path0 = "path0";
		String path1 = "path1";

		service.registerModuleJavascript(type0, path0);
		service.registerModuleJavascript(type1, path1);

		String result0 = service.getModuleJavascript(type0);
		String result1 = service.getModuleJavascript(type1);

		Assert.assertFalse(result0.equals(result1));
	}

	/**
	 * @verifies throw NullPointerException if type is null
	 * @see IWorkOrderService#getModuleJavascript(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType)
	 */
	@Test(expected = NullPointerException.class)
	public void getModuleJavascript_shouldThrowNullPointerExceptionIfTypeIsNull() throws Exception {
		WorkOrderType type = null;

		service.getModuleJavascript(type);
	}

	/**
	 * @verifies return null if no javascript file has been registered for the specified type
	 * @see IWorkOrderService#getModuleJavascript(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType)
	 */
	@Test
	public void getModuleJavascript_shouldReturnNullIfNoJavascriptFileHasBeenRegisteredForTheSpecifiedType() throws Exception {
		WorkOrderType type = typeDataService.getById(2);

		String result = service.getModuleJavascript(type);
		Assert.assertNull(result);

		result = service.getModuleJavascript(type.getUuid());
		Assert.assertNull(result);
	}

	/**
	 * @verifies return the javascript for only the specified type
	 * @see IWorkOrderService#getModuleJavascript(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType)
	 */
	@Test
	public void getModuleJavascript_shouldReturnTheJavascriptForOnlyTheSpecifiedType() throws Exception {
		WorkOrderType type0 = typeDataService.getById(0);
		WorkOrderType type1 = typeDataService.getById(1);
		String path0 = "path0";
		String path1 = "path1";

		service.registerModuleJavascript(type0, path0);
		service.registerModuleJavascript(type1, path1);

		String result0 = service.getModuleJavascript(type0);
		String result1 = service.getModuleJavascript(type1);

		Assert.assertFalse(result0.equals(result1));

		result0 = service.getModuleJavascript(type0.getUuid());
		result1 = service.getModuleJavascript(type1.getUuid());

		Assert.assertFalse(result0.equals(result1));
	}

	/**
	 * @verifies throw InvalidParameterException if type uuid is null
	 * @see IWorkOrderService#getModuleJavascript(String)
	 */
	@Test(expected = InvalidParameterException.class)
	public void getModuleJavascript_shouldThrowInvalidParameterExceptionIfTypeUuidIsNull() throws Exception {
		String path = null;
		service.getModuleJavascript(path);
	}

	/**
	 * @verifies throw InvalidParameterException if type uuid is empty
	 * @see IWorkOrderService#getModuleJavascript(String)
	 */
	@Test(expected =  InvalidParameterException.class)
	public void getModuleJavascript_shouldThrowInvalidParameterExceptionIfTypeUuidIsEmpty() throws Exception {
		String path = "";
		service.getModuleJavascript(path);
	}
}
