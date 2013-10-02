package org.openmrs.module.openhmis.workorder.api;

import org.junit.Assert;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;
import org.openmrs.module.openhmis.commons.api.f.Action2;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
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
		
		if (makeValid) {
			workOrderType.setName("Test Work Order Type");
		}

		workOrderType.setDescription("Work order type description");

		WorkOrderAttributeType attributeType = new WorkOrderAttributeType();
		attributeType.setName("Test attr. type");
		attributeType.setFormat("java.lang.String");
		attributeType.setRequired(false);
		workOrderType.addAttributeType(attributeType);

		return workOrderType;
	}

	@Override
	protected int getTestEntityCount() {
		return 3;
	}

	@Override
	protected void updateEntityFields(WorkOrderType workOrder) {
		workOrder.setName("Different name");
		workOrder.setDescription("Different description");

		WorkOrderAttributeType attributeType = new WorkOrderAttributeType();
		attributeType.setName("Another test");
		attributeType.setFormat("java.lang.Boolean");
		attributeType.setRequired(false);
		workOrder.addAttributeType(attributeType);
	}

	@Override
	protected void assertEntity(WorkOrderType expected, WorkOrderType actual) {
		super.assertEntity(expected, actual);

		assertCollection(expected.getAttributeTypes(), actual.getAttributeTypes(), new Action2<WorkOrderAttributeType, WorkOrderAttributeType>() {
			@Override
			public void apply(WorkOrderAttributeType expectedAttribute, WorkOrderAttributeType actualAttribute) {
				assertOpenmrsMetadata(expectedAttribute, actualAttribute);

				Assert.assertEquals(expectedAttribute.getFormat(), actualAttribute.getFormat());
				Assert.assertEquals(expectedAttribute.getForeignKey(), actualAttribute.getForeignKey());
				Assert.assertEquals(expectedAttribute.getRegExp(), actualAttribute.getRegExp());
				Assert.assertEquals(expectedAttribute.getRequired(), actualAttribute.getRequired());
			}
		});
	}
}
