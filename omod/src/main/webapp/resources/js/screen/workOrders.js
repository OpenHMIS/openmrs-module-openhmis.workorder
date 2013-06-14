curl(
	{ baseUrl: openhmis.url.resources },
	[
		openhmis.url.backboneBase +  'js/lib/jquery',
		openhmis.url.backboneBase +  'js/openhmis',
		'link!' + openhmis.url.workorderBase + 'css/style.css',
		openhmis.url.workorderBase + 'js/model/workOrder',
		openhmis.url.backboneBase +  'js/view/generic',
		openhmis.url.workorderBase + 'js/view/workOrder'
	],
	function($, openhmis) {
		$(function() {
			openhmis.startAddEditScreen(openhmis.WorkOrder, {
				listElement: $("#list"),
				addEditElement: $("#addEdit"),
				addEditViewType: openhmis.WorkOrderAddEditView,
				listView: openhmis.WorkOrderListView
			});
		});
	}
);