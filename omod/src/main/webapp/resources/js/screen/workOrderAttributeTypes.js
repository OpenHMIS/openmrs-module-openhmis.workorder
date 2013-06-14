curl(
	{ baseUrl: openhmis.url.resources },
	[
		openhmis.url.backboneBase +  'js/lib/jquery',
		openhmis.url.backboneBase +  'js/openhmis',
		openhmis.url.backboneBase +  'js/lib/backbone-forms',
		openhmis.url.workorderBase + 'js/model/workOrder',
		openhmis.url.backboneBase +  'js/view/generic'
	],
	function($, openhmis) {
		$(function() {
			openhmis.startAddEditScreen(openhmis.WorkOrderAttributeType, {
				listFields: ['name']
			});
		});
	}
);