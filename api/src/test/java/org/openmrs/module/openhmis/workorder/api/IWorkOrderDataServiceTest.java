package org.openmrs.module.openhmis.workorder.api;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;
import org.openmrs.module.openhmis.commons.api.entity.search.BaseObjectTemplateSearch;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;
import org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IWorkOrderDataServiceTest extends IMetadataDataServiceTest<IWorkOrderDataService, WorkOrder> {
	public static final String DATASET = TestConstants.BASE_DATASET_DIR + "WorkOrderTest.xml";
	private IWorkOrderTypeDataService workOrderTypeDataService;

	@Override
	public void before() throws Exception {
		super.before();

		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(DATASET);

		this.workOrderTypeDataService = Context.getService(IWorkOrderTypeDataService.class);
	}
	
	@Override
	protected WorkOrder createEntity(boolean valid) {
		WorkOrder workOrder = new WorkOrder();
		if (valid)
			workOrder.setName("Test work order");
		
		workOrder.setDescription("A work order to test.");
		workOrder.setStatus(WorkOrderStatus.NEW);
		workOrder.setInstanceType(workOrderTypeDataService.getById(0));

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

	@Test
	public void getAll_shouldReturnResultsSortedByCreationDate() throws Exception {
		List<WorkOrder> results = service.getAll();

		Assert.assertNotNull(results);
		Assert.assertEquals(3, results.size());

		Assert.assertEquals(0, (int)results.get(0).getId());
		Assert.assertEquals(2, (int)results.get(1).getId());
		Assert.assertEquals(1, (int)results.get(2).getId());
	}

	@Test
	public void getAll_shouldOnlyReturnWorkOrdersWithNoParent() throws Exception {
		WorkOrder wo = createEntity(true);
		wo.setParentWorkOrder(service.getById(0));

		service.save(wo);
		Context.flushSession();

		List<WorkOrder> results = service.getAll();

		Assert.assertNotNull(results);
		Assert.assertEquals(3, results.size());
	}

	@Test
	public void findByName_shouldReturnResultsSortedByCreationDate() throws Exception {
		List<WorkOrder> results = service.findByName("Test", false);

		Assert.assertNotNull(results);
		Assert.assertEquals(3, results.size());

		Assert.assertEquals(0, (int)results.get(0).getId());
		Assert.assertEquals(2, (int)results.get(1).getId());
		Assert.assertEquals(1, (int)results.get(2).getId());
	}

	/**
	 * @verifies return all work orders with the specified status for specified user
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnAllWorkOrdersWithTheSpecifiedStatusForSpecifiedUser() throws Exception {
		User user = Context.getUserService().getUser(1);

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(3, results.size());

		Assert.assertEquals(0, (int)results.get(0).getId());
		Assert.assertEquals(2, (int)results.get(1).getId());
		Assert.assertEquals(1, (int)results.get(2).getId());

		results = service.getUserWorkOrders(user, WorkOrderStatus.NEW, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(2, results.size());

		Assert.assertEquals(service.getById(0).getId(), results.get(0).getId());
		Assert.assertEquals(service.getById(1).getId(), results.get(1).getId());

		results = service.getUserWorkOrders(user, WorkOrderStatus.COMPLETED, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());

		Assert.assertEquals(service.getById(2).getId(), results.get(0).getId());
	}

	/**
	 * @verifies return specified work orders for user
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnSpecifiedWorkOrdersForUser() throws Exception {
		User user = Context.getUserService().getUser(1);

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(3, results.size());

		User otherUser = Context.getUserService().getUser(5506);
		WorkOrder wo = results.get(0);
		wo.setAssignedToUser(otherUser);
		service.save(wo);
		Context.flushSession();

		results = service.getUserWorkOrders(user, null, null);
		Assert.assertNotNull(results);
		Assert.assertEquals(2, results.size());


		results = service.getUserWorkOrders(otherUser, null, null);
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(wo.getId(), results.get(0).getId());
	}

	/**
	 * @verifies return specified work orders for user role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnSpecifiedWorkOrdersForUserRole() throws Exception {
		User baseUser = Context.getUserService().getUser(0);
		User user = Context.getUserService().getUser(5506);
		Set<Role> roles = user.getRoles();
		Role[] roleArray = new Role[roles.size()];
		roles.toArray(roleArray);

		WorkOrder workOrder = createEntity(true);
		workOrder.setCreator(baseUser);
		workOrder.setAssignedToRole(roleArray[0]);

		service.save(workOrder);
		Context.flushSession();

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(workOrder.getId(), results.get(0).getId());
	}

	/**
	 * @verifies return specified work orders with user role as child role of work order role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnSpecifiedWorkOrdersWithUserRoleAsChildRoleOfWorkOrderRole() throws Exception {
		User baseUser = Context.getUserService().getUser(0);

		// This user has the Child Role which is a child of the Parent role
		User user = Context.getUserService().getUser(5506);

		WorkOrder workOrder = createEntity(true);
		workOrder.setCreator(baseUser);

		// Set up this transaction type to be for users of the Parent role
		workOrder.setAssignedToRole(Context.getUserService().getRole("Parent"));

		service.save(workOrder);
		Context.flushSession();

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(workOrder.getId(), results.get(0).getId());
	}

	/**
	 * @verifies return specified work orders with user role as grandchild role of work order role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnSpecifiedWorkOrdersWithUserRoleAsGrandchildRoleOfWorkOrderRole() throws Exception {
		User baseUser = Context.getUserService().getUser(0);
		User user = Context.getUserService().getUser(5506);

		Set<Role> roles = new HashSet<Role>();
		roles.add(Context.getUserService().getRole("Grandchild"));
		user.setRoles(roles);
		Context.getUserService().saveUser(user, "1wWhatever");
		Context.flushSession();

		WorkOrder workOrder = createEntity(true);
		workOrder.setCreator(baseUser);

		// Set up this transaction type to be for users of the Parent role
		workOrder.setAssignedToRole(Context.getUserService().getRole("Parent"));

		service.save(workOrder);
		Context.flushSession();

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(workOrder.getId(), results.get(0).getId());
	}

	/**
	 * @verifies not return work orders when user role not descendant of work order role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldNotReturnWorkOrdersWhenUserRoleNotDescendantOfWorkOrderRole() throws Exception {
		User baseUser = Context.getUserService().getUser(0);
		User user = Context.getUserService().getUser(5506);

		Set<Role> roles = new HashSet<Role>();
		roles.add(Context.getUserService().getRole("Other"));
		user.setRoles(roles);
		Context.getUserService().saveUser(user, "1wWhatever");
		Context.flushSession();

		WorkOrder workOrder = createEntity(true);
		workOrder.setCreator(baseUser);

		// Set up this transaction type to be for users of the Parent role
		workOrder.setAssignedToRole(Context.getUserService().getRole("Parent"));

		service.save(workOrder);
		Context.flushSession();

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());
	}

	/**
	 * @verifies not return work orders when user role is parent of work order role
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldNotReturnWorkOrdersWhenUserRoleIsParentOfWorkOrderRole() throws Exception {
		User baseUser = Context.getUserService().getUser(0);
		User user = Context.getUserService().getUser(5506);

		Set<Role> roles = new HashSet<Role>();
		roles.add(Context.getUserService().getRole("Parent"));
		user.setRoles(roles);
		Context.getUserService().saveUser(user, "1wWhatever");
		Context.flushSession();

		WorkOrder workOrder = createEntity(true);
		workOrder.setCreator(baseUser);

		// Set up this transaction type to be for users of the Parent role
		workOrder.setAssignedToRole(Context.getUserService().getRole("Child"));

		service.save(workOrder);
		Context.flushSession();

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());
	}

	/**
	 * @verifies return empty list when no work orders
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnEmptyListWhenNoWorkOrders() throws Exception {
		User user = Context.getUserService().getUser(5506);

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());
	}

	/**
	 * @verifies return paged work orders when paging is specified
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnPagedWorkOrdersWhenPagingIsSpecified() throws Exception {
		User user = Context.getUserService().getUser(1);

		PagingInfo paging = new PagingInfo(1, 1);
		List<WorkOrder> results = service.getUserWorkOrders(user, null, paging);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());

		Assert.assertEquals(0, (int)results.get(0).getId());
		Assert.assertEquals(3L, (long)paging.getTotalRecordCount());
	}

	/**
	 * @verifies return all work orders when paging is null
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnAllWorkOrdersWhenPagingIsNull() throws Exception {
		User user = Context.getUserService().getUser(1);

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(3, results.size());
	}

	/**
	 * @verifies throw NullPointerException when user is null
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test(expected = NullPointerException.class)
	public void getUserWorkOrders_shouldThrowNullPointerExceptionWhenUserIsNull() throws Exception {
		service.getUserWorkOrders(null, null, null);
	}

	/**
	 * @verifies return all work orders for user when status is null
	 * @see IWorkOrderDataService#getUserWorkOrders(org.openmrs.User, org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getUserWorkOrders_shouldReturnAllWorkOrdersForUserWhenStatusIsNull() throws Exception {
		User user = Context.getUserService().getUser(1);

		List<WorkOrder> results = service.getUserWorkOrders(user, null, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(3, results.size());
	}

	/**
	 * @verifies throw NullPointerException if search object is null
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test(expected = NullPointerException.class)
	public void findWorkOrders_shouldThrowNullPointerExceptionIfSearchObjectIsNull() throws Exception {
		service.findWorkOrders(null);
	}

	/**
	 * @verifies throw NullPointerException if search object template is null
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test(expected = NullPointerException.class)
	public void findWorkOrders_shouldThrowNullPointerExceptionIfSearchObjectTemplateIsNull() throws Exception {
		WorkOrderSearch search = new WorkOrderSearch();
		search.setTemplate(null);

		service.findWorkOrders(search);
	}

	/**
	 * @verifies return work orders filtered by instance type
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void findWorkOrders_shouldReturnWorkOrdersFilteredByInstanceType() throws Exception {
		WorkOrder wo = service.getById(0);
		WorkOrderSearch search = new WorkOrderSearch();

		search.getTemplate().setInstanceType(wo.getInstanceType());

		List<WorkOrder> results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(wo.getId(), results.get(0).getId());

		search.setInstanceTypeComparisonType(BaseObjectTemplateSearch.ComparisonType.NOT_EQUAL);

		results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(2, results.size());
	}

	/**
	 * @verifies return work orders filtered by status
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void findWorkOrders_shouldReturnWorkOrdersFilteredByStatus() throws Exception {
		WorkOrder wo = service.getById(2);
		WorkOrderSearch search = new WorkOrderSearch();

		search.getTemplate().setStatus(WorkOrderStatus.COMPLETED);

		List<WorkOrder> results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(wo.getId(), results.get(0).getId());
	}

	/**
	 * @verifies return work orders filtered by assigned to user
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void findWorkOrders_shouldReturnWorkOrdersFilteredByAssignedToUser() throws Exception {
		WorkOrder wo = createEntity(true);
		wo.setAssignedToUser(Context.getUserService().getUser(5506));

		service.save(wo);
		Context.flushSession();

		WorkOrderSearch search = new WorkOrderSearch();
		search.getTemplate().setAssignedToUser(wo.getAssignedToUser());

		List<WorkOrder> results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(wo.getId(), results.get(0).getId());
	}

	/**
	 * @verifies return work orders filtered by assigned to role
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void findWorkOrders_shouldReturnWorkOrdersFilteredByAssignedToRole() throws Exception {
		WorkOrder wo = createEntity(true);
		wo.setAssignedToRole(Context.getUserService().getRole("Other"));

		service.save(wo);
		Context.flushSession();

		WorkOrderSearch search = new WorkOrderSearch();
		search.getTemplate().setAssignedToRole(wo.getAssignedToRole());

		List<WorkOrder> results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(wo.getId(), results.get(0).getId());
	}

	/**
	 * @verifies return work orders filtered by parent work order
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void findWorkOrders_shouldReturnWorkOrdersFilteredByParentWorkOrder() throws Exception {
		WorkOrder wo = createEntity(true);
		wo.setParentWorkOrder(service.getById(0));

		service.save(wo);
		Context.flushSession();

		WorkOrderSearch search = new WorkOrderSearch();
		search.getTemplate().setParentWorkOrder(wo.getParentWorkOrder());

		List<WorkOrder> results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(wo.getId(), results.get(0).getId());

		search.setParentComparisonType(BaseObjectTemplateSearch.ComparisonType.IS_NOT_NULL);
		results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(wo.getId(), results.get(0).getId());

		search.setParentComparisonType(BaseObjectTemplateSearch.ComparisonType.IS_NULL);
		results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(3, results.size());

		search.setParentComparisonType(BaseObjectTemplateSearch.ComparisonType.NOT_EQUAL);
		results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());
	}

	/**
	 * @verifies return all results if paging is null
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void findWorkOrders_shouldReturnAllResultsIfPagingIsNull() throws Exception {
		WorkOrderSearch search = new WorkOrderSearch();
		search.setParentComparisonType(BaseObjectTemplateSearch.ComparisonType.IS_NULL);

		List<WorkOrder> results = service.findWorkOrders(search, null);

		Assert.assertNotNull(results);
		Assert.assertEquals(3, results.size());
	}

	/**
	 * @verifies return paged results of paging is specified
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void findWorkOrders_shouldReturnPagedResultsOfPagingIsSpecified() throws Exception {
		WorkOrderSearch search = new WorkOrderSearch();
		search.setParentComparisonType(BaseObjectTemplateSearch.ComparisonType.IS_NULL);

		PagingInfo pagingInfo = new PagingInfo(1, 1);
		List<WorkOrder> results = service.findWorkOrders(search, pagingInfo);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(3L, (long)pagingInfo.getTotalRecordCount());
	}

	/**
	 * @verifies return empty list if not results are found
	 * @see IWorkOrderDataService#findWorkOrders(org.openmrs.module.openhmis.workorder.api.search.WorkOrderSearch, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void findWorkOrders_shouldReturnEmptyListIfNotResultsAreFound() throws Exception {
		WorkOrderSearch search = new WorkOrderSearch();
		search.setParentComparisonType(BaseObjectTemplateSearch.ComparisonType.IS_NOT_NULL);

		List<WorkOrder> results = service.findWorkOrders(search);

		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());
	}
}
