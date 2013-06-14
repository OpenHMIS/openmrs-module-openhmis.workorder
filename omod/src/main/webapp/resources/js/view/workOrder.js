define(
[
	openhmis.url.backboneBase + 'js/lib/jquery',
	openhmis.url.backboneBase + 'js/lib/underscore',
	openhmis.url.backboneBase + 'js/openhmis',
	openhmis.url.backboneBase + 'js/view/generic'
],
function($, _, openhmis) {
	openhmis.WorkOrderListView = openhmis.GenericListView.extend({
		itemView: openhmis.NestedListItemView,
		
		initialize: function(options) {
			options = options ? options : {};
			options.itemActions = options.itemActions ? ["expand"].concat(options.itemActions) : ["expand"];
			options.listFields = options.listFields ? options.listFields : ["name"];
			openhmis.GenericListView.prototype.initialize.call(this, options);
		}
	});
	
	openhmis.WorkOrderAddEditView = openhmis.GenericAddEditView.extend({
		edit: function(workOrder) {
			var self = this;
			if (workOrder instanceof openhmis.WorkOrder) {
				var typeUuid = workOrder.get("workOrderType").id;
				this._fetchModulePath(typeUuid, {
					success: function(path, status, xhr) {
						if (path === null)
							openhmis.GenericAddEditView.prototype.edit.call(self, workOrder);
						else {
							self._newCustomAddEditView(path, {
								success: function(view) {
									view.edit(workOrder);
								}
							});
						}
					},
					error: function(namespace, status, xhr) {
						openhmis.GenericAddEditView.prototype.edit.call(self, workOrder);
					}
				})
			}
			else
				openhmis.GenericAddEditView.prototype.edit.call(this, workOrder);
		},
		
		_fetchModulePath: function(workOrderTypeUuid, options) {
			$.ajax({
				url: openhmis.url.page + openhmis.url.workorderBase
						+ "options.json?jsModuleForWorkOrderType=" + workOrderTypeUuid,
				success: function(path, status, xhr) {
					if (path === "") path = null;
					if (options.success) options.success(path, status, xhr);
				},
				error: options.error
			});
		},
		
		_newCustomAddEditView: function(modulePath, options) {
			var self = this;
			curl(
				{ baseUrl: openhmis.url.openmrs + "moduleResources/" },
				[ modulePath ],
				function(viewType) {
					var customView = new viewType({ collection: new openhmis.GenericCollection({ model: openhmis.WorkOrder }) });
					customView.setElement(self.el);
					if (options.success)
						options.success(customView);
				}
			);
		}
	});
});