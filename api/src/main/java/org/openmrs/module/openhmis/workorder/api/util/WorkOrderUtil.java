package org.openmrs.module.openhmis.workorder.api.util;

import java.util.Set;

import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.CustomValueDescriptor;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderAttributeTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;

public class WorkOrderUtil {
	public static WorkOrderAttribute getAttributeByTypeName(WorkOrder workOrder, String typeName) {
		WorkOrderAttributeType type = Context.getService(IWorkOrderAttributeTypeDataService.class)
				.getByFormatUnique(typeName, workOrder.getWorkOrderType());
		Set<WorkOrderAttribute> attrs = workOrder.getActiveAttributes((CustomValueDescriptor) type);
		if (attrs.size() > 0)
			return attrs.toArray(new WorkOrderAttribute[1])[0];
		else
			return null;
	}
}
