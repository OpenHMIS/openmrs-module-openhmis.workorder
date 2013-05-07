package org.openmrs.module.openhmis.workorder.api.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Attributable;
import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderAttributeTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;

public class WorkOrderAttributeTypeDataServiceImpl extends
		BaseMetadataDataServiceImpl<WorkOrderAttributeType> implements
		IWorkOrderAttributeTypeDataService {

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return null;
	}

	@Override
	protected void validate(WorkOrderAttributeType object) throws APIException {
	}

	@Override
	public <T extends Attributable<?>> WorkOrderAttribute convertToAttribute(T value) {
		if (value == null) throw new IllegalArgumentException("Value cannot be null.");
		String className = value.getClass().getCanonicalName();
		WorkOrderAttribute attribute = new WorkOrderAttribute();
		attribute.setAttributeType(getByDatatypeClassname(className));
		attribute.setValueReferenceInternal(value.serialize());
		return attribute;
	}

	@Override
	public WorkOrderAttributeType getByDatatypeClassname(final String datatypeClassname) {
		List<WorkOrderAttributeType> types = executeCriteria(getEntityClass(), null, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				criteria.add(Restrictions.eq("datatypeClassname", datatypeClassname));
				criteria.add(Restrictions.eq("retired", false));
			}
		});
		if (types.size() == 1)
			return types.get(0);
		else
			throw new APIException("Couldn't find a WorkOrderAttributeType for " + datatypeClassname);
	}
}
