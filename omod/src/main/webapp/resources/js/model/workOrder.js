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
		openhmis.url.backboneBase + 'js/openhmis',
		openhmis.url.backboneBase + 'js/lib/i18n',
		openhmis.url.backboneBase + 'js/model/generic',
        openhmis.url.backboneBase + 'js/model/fieldGenHandler'
	],
	function(openhmis, __) {
		openhmis.WorkOrderAttributeType = openhmis.GenericModel.extend({
            meta: {
                name: "Attribute Type",
                openmrsType: 'metadata',
                restUrl: "v2/workorder/attributetype"
				//modelType: "WorkOrderAttributeType"
            },

            schema: {
                name: { type: 'Text' },
                format: {
                    type: 'Select',
                    options: new openhmis.FieldFormatCollection()
                },
                foreignKey: { type: 'BasicNumber' },
                regExp: { type: 'Text' },
                required: { type: 'Checkbox' },
				attributeOrder: { type: "Number", hidden: true }
            },
            
            validate: function(attrs, options) {
   				if (!attrs.name) return { name: __("A name is required") }
                return null;
            },
            
            toString: function() { return this.get('name'); },
			
			toJSON: function(options) {
				var attrs = openhmis.GenericModel.prototype.toJSON.call(this, options);
				attrs.type = "WorkOrderAttributeType";
				return attrs;
			}

		});
		
		openhmis.WorkOrderType = openhmis.GenericModel.extend({
			meta: {
				name: __("Work Order Type"),
				openmrsType: 'metadata',
				restUrl: 'v2/workorder/workordertype'
			},
			
			schema: {
				name: 'Text',
				description: 'Text',
                attributeTypes: { type: 'List', itemType: 'NestedModel', model: openhmis.WorkOrderAttributeType }
			},
			
			validate: function(attrs, options) {
				if (!attrs.name) return { name: __("A name is required.")}
				return null;
			},
			
			parse: function(resp) {
				if (resp && resp.attributeTypes)
					resp.attributeTypes = new openhmis.GenericCollection(resp.attributeTypes, { model: openhmis.WorkOrderAttributeType }).models;
				return resp;
			},
			
			toString: function() {
				return this.get('name');
			},
			
			toJSON: function(options) {
				var attributeTypes = this.get("attributeTypes");
				if (attributeTypes && attributeTypes.length > 0) {
					this.set("attributeTypes", new openhmis.GenericCollection(
						attributeTypes,
						{ model: openhmis.WorkOrderAttributeType }
					).toJSON({ subResource: true }));
				}
				return openhmis.GenericModel.prototype.toJSON.call(this, options);
			}
		});

		openhmis.WorkOrderAttribute = openhmis.GenericModel.extend({
			meta: {
				name: __("Work Order Attribute"),
				openmrsType: 'metadata',
				restUrl: 'v2/workorder/workorderattribute'
			},
			
			schema: {
				name: 'Text',
				description: 'Text',
                attributeType: {
					type: 'GenericModelSelect',
					modelType: openhmis.WorkOrderAttributeType,
					options: new openhmis.GenericCollection([], { model: openhmis.WorkOrderAttributeType }),
					objRef: true
				},
				value: { type: "Text" }
			},
			
			validate: function(attrs, options) {
				if (!attrs.name) return { name: __("A name is required.")}
				return null;
			},
			
			toString: function() {
				return this.get('name');
			},
			
			toJSON: function(options) {
				var attributes = openhmis.GenericModel.prototype.toJSON.call(this, options);
				if (attributes.attributeType)
					attributes.attributeType = { uuid: attributes.attributeType, type: "WorkOrderAttributeType" }
				return attributes;
			}
		});
		
		openhmis.WorkOrder = openhmis.GenericModel.extend({
			meta: {
				name: __("Work Order"),
				openmrsType: 'metadata',
				children: "workOrders",
				restUrl: 'v2/workorder/workorder'
			},
			
			schema: {
				name: 'Text',
				workOrderType: {
					type: 'GenericModelSelect',
					modelType: openhmis.WorkOrderType,
					options: new openhmis.GenericCollection([], { model: openhmis.WorkOrderType }),
					objRef: true
				},
				description: { type: "TextArea", title: "Instructions" },
                attributes: {
					type: 'List',
					itemType: 'NestedModel',
					model: openhmis.WorkOrderAttribute,
					objRef: true
				}
			},
			
			validate: function(attrs, options) {
				if (!attrs.name) return { name: __("A name is required.")}
				return null;
			},
			
			parse: function(resp) {
				if (resp) {
					if (resp.attributes)
						resp.attributes = new openhmis.GenericCollection(resp.attributes, { model: openhmis.WorkOrderAttribute, parse: true }).models;
					if (resp.workOrders)
						resp.workOrders = new openhmis.GenericCollection(resp.workOrders, { model: openhmis.WorkOrder, parse: true });
					if (resp.workOrderType)
						resp.workOrderType = new openhmis.WorkOrderType(resp.workOrderType);
				}
				return resp;
			},
			
			toString: function() {
				return this.get('name');
			}
		});
		
		return openhmis;
	}
);
