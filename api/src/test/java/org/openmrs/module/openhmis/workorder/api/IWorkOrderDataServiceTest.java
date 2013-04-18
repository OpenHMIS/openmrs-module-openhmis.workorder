package org.openmrs.module.openhmis.workorder.api;

import org.junit.Assert;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

public class IWorkOrderDataServiceTest extends IMetadataDataServiceTest<IWorkOrderDataService, WorkOrder> {
	public static final String DATASET = TestConstants.BASE_DATASET_DIR + "WorkOrderTest.xml";
	@Override
	public void before() throws Exception {
		super.before();
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
		return 1;
	}

	@Override
	protected void updateEntityFields(WorkOrder entity) {
		entity.setName(entity.getName() + " updated");
		entity.setDescription(entity.getDescription() + " updated");
		entity.setStatus(entity.getStatus() == WorkOrderStatus.CANCELLED ? WorkOrderStatus.NEW : WorkOrderStatus.CANCELLED);
	}

	@Override
	protected void assertEntity(WorkOrder expected, WorkOrder actual) {
		super.assertEntity(expected, actual);
		Assert.assertEquals(expected.getStatus(), actual.getStatus());
		Assert.assertEquals(expected.getAssignedTo(), actual.getAssignedTo());
	}
}
