curl(
	{ baseUrl: openhmis.url.resources },
	[
		openhmis.url.backboneBase +  'js/lib/jquery',
		openhmis.url.backboneBase +  'js/openhmis',
		openhmis.url.backboneBase +  'js/lib/backbone-forms',
		openhmis.url.workorderBase + 'js/model/workOrder',
		openhmis.url.backboneBase +  'js/view/generic',
		openhmis.url.workorderBase + 'js/view/editors'
	],
	function($, openhmis) {
		$(function() {
			openhmis.startAddEditScreen(openhmis.WorkOrder, {
				listElement: $("#list"),
				addEditElement: $("#addEdit"),
				itemView: openhmis.NestedListItemView,
				itemActions: ["expand"],
				modelChildren: "workOrders",
				listFields: ['name', 'description']
			});
		});
	}
);