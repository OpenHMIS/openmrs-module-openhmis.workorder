package org.openmrs.module.openhmis.workorder.api.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrder;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderEventService;
import org.openmrs.module.openhmis.workorder.api.util.IWorkOrderAction;

public class WorkOrderEventServiceImpl extends BaseOpenmrsService implements IWorkOrderEventService {
	
	private static Map<String, Set<IWorkOrderAction>> statusHandlers = new HashMap<String, Set<IWorkOrderAction>>();
	
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
	public void registerWorkOrderStatusHandler(IWorkOrderAction action) {
		registerWorkOrderStatusHandler(parseModuleIdFromClass(action.getClass()), action);
	}

	@Override
	public void registerWorkOrderStatusHandler(String moduleId, IWorkOrderAction action) {
		registerHandler(statusHandlers, moduleId, action);
	}
	
	@Override
	public void unregisterWorkOrderStatusHandler(IWorkOrderAction action) {
		unregisterWorkOrderStatusHandler(parseModuleIdFromClass(action.getClass()), action);
	}

	@Override
	public void unregisterWorkOrderStatusHandler(String moduleId, IWorkOrderAction action) {
		unregisterHandler(statusHandlers, moduleId, action);
	}
	
	public void fireStatusChanged(IWorkOrder workOrder) {
		fireHandlers(statusHandlers, workOrder);
	}
	
	private static void registerHandler(Map<String, Set<IWorkOrderAction>> handlerMap, String moduleId, IWorkOrderAction action) {
		if (handlerMap.get(moduleId) == null)
			handlerMap.put(moduleId, new HashSet<IWorkOrderAction>());
		handlerMap.get(moduleId).add(action);		
	}

	private static boolean unregisterHandler(Map<String, Set<IWorkOrderAction>> handlerMap, String moduleId, IWorkOrderAction action) {
		Set<IWorkOrderAction> handlerSet = handlerMap.get(moduleId);
		if (handlerSet == null)
			return false;
		return handlerSet.remove(action);
	}
	
	private static void fireHandlers(Map<String, Set<IWorkOrderAction>> handlerMap, IWorkOrder workOrder) {
		for (Set<IWorkOrderAction> set : handlerMap.values()) {
			for (IWorkOrderAction action : set) {
				action.apply(workOrder);
			}
		}
	}
}
