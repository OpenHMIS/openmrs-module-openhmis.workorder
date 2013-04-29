package org.openmrs.module.openhmis.workorder.api.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderEventService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.util.WorkOrderAction;

public class WorkOrderEventServiceImpl extends BaseOpenmrsService implements IWorkOrderEventService {
	
	private static Map<String, Set<WorkOrderAction>> statusHandlers = new HashMap<String, Set<WorkOrderAction>>();
	
	private static final Pattern moduleIdPattern = Pattern.compile("org\\.openmrs\\.module\\.([a-z\\.]*)\\.(.*)");

	/**
	 * Attempt to identify the module ID of a class
	 * @param clazz Class to get module ID from
	 * @return String module id
	 * @should get the module ID from an openmrs module class
	 */
	public static String parseModuleIdFromClass(Class<?> clazz) {
		Matcher matcher = moduleIdPattern.matcher(clazz.getName());
		if (matcher.find())
			return matcher.group(1);
		return null;
}
	
	@Override
	public void registerWorkOrderStatusHandler(WorkOrderAction action) {
		registerWorkOrderStatusHandler(parseModuleIdFromClass(action.getClass()), action);
	}

	@Override
	public void registerWorkOrderStatusHandler(String moduleId, WorkOrderAction action) {
		registerHandler(statusHandlers, moduleId, action);
	}
	
	@Override
	public void unregisterWorkOrderStatusHandler(WorkOrderAction action) {
		unregisterWorkOrderStatusHandler(parseModuleIdFromClass(action.getClass()), action);
	}

	@Override
	public void unregisterWorkOrderStatusHandler(String moduleId, WorkOrderAction action) {
		unregisterHandler(statusHandlers, moduleId, action);
	}
	
	public void fireStatusChanged(WorkOrder workOrder) {
		fireHandlers(statusHandlers, workOrder);
	}
	
	private static void registerHandler(Map<String, Set<WorkOrderAction>> handlerMap, String moduleId, WorkOrderAction action) {
		if (handlerMap.get(moduleId) == null)
			handlerMap.put(moduleId, new HashSet<WorkOrderAction>());
		handlerMap.get(moduleId).add(action);		
	}

	private static boolean unregisterHandler(Map<String, Set<WorkOrderAction>> handlerMap, String moduleId, WorkOrderAction action) {
		Set<WorkOrderAction> handlerSet = handlerMap.get(moduleId);
		if (handlerSet == null)
			return false;
		return handlerSet.remove(action);
	}
	
	private static void fireHandlers(Map<String, Set<WorkOrderAction>> handlerMap, WorkOrder workOrder) {
		for (Set<WorkOrderAction> set : handlerMap.values()) {
			for (WorkOrderAction action : set) {
				action.apply(workOrder);
			}
		}
	}
}
