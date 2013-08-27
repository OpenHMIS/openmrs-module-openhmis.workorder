/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
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