package org.openmrs.module.openhmis.workorder.api.util;

import org.openmrs.annotation.AddOnStartup;

public class WorkOrderPrivilegeConstants {

	@AddOnStartup(description = "Able to add/edit/delete work order module metadata")
	public static final String MANAGE_METADATA = "Manage Cashier Metadata";

	@AddOnStartup(description = "Able to view work order module metadata")
	public static final String VIEW_METADATA = "View Cashier Metadata";

	@AddOnStartup(description = "Able to purge work order module metadata")
	public static final String PURGE_METADATA = "Purge Cashier Metadata";
}
