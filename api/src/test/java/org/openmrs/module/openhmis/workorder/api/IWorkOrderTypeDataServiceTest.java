package org.openmrs.module.openhmis.workorder.api;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

public class IWorkOrderTypeDataServiceTest extends IMetadataDataServiceTest<IWorkOrderTypeDataService, WorkOrderType> {
	@Override
	public void before() throws Exception {
		super.before();
		executeDataSet(IWorkOrderDataServiceTest.DATASET);
	}
	@Override
	protected WorkOrderType createEntity(boolean makeValid) {
		WorkOrderType workOrderType = new WorkOrderType();
		
		if (makeValid)
			workOrderType.setName("Test Work Order Type");
		workOrderType.setDescription("Work order type description");
		workOrderType.addAttributeType("Test attr. type", "java.lang.String", null, null, false, null);
		return workOrderType;
	}

	@Override
	protected int getTestEntityCount() {
		return 1;
	}

	@Override
	protected void updateEntityFields(WorkOrderType workOrder) {
		workOrder.setName("Different name");
		workOrder.setDescription("Different description");
		workOrder.addAttributeType("Another test", "java.lang.Boolean", null, null, false, null);
	}
}
