package org.openmrs.module.openhmis.workorder.api;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

public class IWorkOrderDataServiceTest extends IMetadataDataServiceTest<IWorkOrderDataService, WorkOrder> {
	public static final String DATASET = TestConstants.BASE_DATASET_DIR + "WorkOrderTest.xml";
	@Override
	public void before() throws Exception {
		super.before();

		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(DATASET);
	}
	
	@Override
	protected WorkOrder createEntity(boolean valid) {
		WorkOrder workOrder = new WorkOrder();
		if (valid)
			workOrder.setName("Test work order");
		
		workOrder.setDescription("A work order to test.");
		workOrder.setStatus(WorkOrderStatus.NEW);
		return workOrder;
	}

	@Override
	protected int getTestEntityCount() {
		return 3;
	}

	@Override
	protected void updateEntityFields(WorkOrder entity) {
		entity.setName(entity.getName() + " updated");
		entity.setDescription(entity.getDescription() + " updated");
		entity.setStatus(entity.getStatus() == WorkOrderStatus.IN_PROGRESS ? WorkOrderStatus.NEW : WorkOrderStatus.IN_PROGRESS);
	}

	@Override
	protected void assertEntity(WorkOrder expected, WorkOrder actual) {
		super.assertEntity(expected, actual);

		Assert.assertEquals(expected.getStatus(), actual.getStatus());
		Assert.assertEquals(expected.getAssignedToUser(), actual.getAssignedToUser());
		Assert.assertEquals(expected.getAssignedToRole(), actual.getAssignedToRole());
	}

	/**
	 * @verifies return all work orders with the specified status for specified user
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnAllWorkOrdersWithTheSpecifiedStatusForSpecifiedUser() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return specified work orders for user
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnSpecifiedWorkOrdersForUser() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return specified work orders for user role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnSpecifiedWorkOrdersForUserRole() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return specified work orders with user role as child role of work order role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnSpecifiedWorkOrdersWithUserRoleAsChildRoleOfWorkOrderRole() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return specified work orders with user role as grandchild role of work order role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnSpecifiedWorkOrdersWithUserRoleAsGrandchildRoleOfWorkOrderRole() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies not return work orders when user role not descendant of work order role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldNotReturnWorkOrdersWhenUserRoleNotDescendantOfWorkOrderRole() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies not return work orders when user role is parent of work order role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldNotReturnWorkOrdersWhenUserRoleIsParentOfWorkOrderRole() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return empty list when no work orders
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnEmptyListWhenNoWorkOrders() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return paged work orders when paging is specified
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnPagedWorkOrdersWhenPagingIsSpecified() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return all work orders when paging is null
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnAllWorkOrdersWhenPagingIsNull() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies throw NullPointerException when user is null
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldThrowNullPointerExceptionWhenUserIsNull() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return all work orders for user when status is null
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnAllWorkOrdersForUserWhenStatusIsNull() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}
}
