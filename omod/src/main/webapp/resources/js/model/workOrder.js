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
                restUrl: 'v2/workorder/workorderattributetype'
            },

            schema: {
                name: { type: 'Text' },
                format: {
                    type: 'Select',
                    options: new openhmis.FieldFormatCollection()
                },
                foreignKey: { type: 'BasicNumber' },
                regExp: { type: 'Text' },
                required: { type: 'Checkbox' }
            },
            
            validate: function(attrs, options) {
   				if (!attrs.name) return { name: __("A name is required") }
                return null;
            },
            
            toString: function() { return this.get('name'); }

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
			
			toString: function() {
				return this.get('name');
			}
		});
		
		return openhmis;
	}
);
