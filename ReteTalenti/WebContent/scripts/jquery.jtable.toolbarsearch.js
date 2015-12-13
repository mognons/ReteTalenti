/** JTABLE Multiple toolbar search extension 

**/
(function ($) {
	var base={
		_addRowToTableHead:$.hik.jtable.prototype._addRowToTableHead
	}
    function _toolbarsearch_convertDateFromEuroToISO (dateIn) {
    	var self = this;
    	dateIn = dateIn.substring(0,10);
		var dateParts = dateIn.split("-");
		if(dateParts==dateIn)
			dateParts = dateIn.split("/");
		if(dateParts==dateIn)
			dateParts = dateIn.split(".");
		return (dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0]);
    }
    $.extend(true, $.hik.jtable.prototype, {
		options: {
			toolbarSearch:false, 
			toolbarReset:true
		},
		/** Overrides Method
		/* Adds tr element to given thead element
        *************************************************************************/
        _addRowToTableHead: function ($thead) {
            var $tr = $('<tr></tr>')
                .appendTo($thead);

            this._addColumnsToHeaderRow($tr);
			if(this.options.toolbarSearch){			
	            var $tr = $('<tr></tr>')
                .appendTo($thead);
    	        this._toolbarsearch_addColumnsToHeaderRow($tr);
			}

		},

        /* Creates an array of options from given object.
        *************************************************************************/
        _buildOptionsArrayFromObject: function (options) {
            var list = [];

            $.each(options, function (propName, propValue) {
                list.push({
                    Value: propName,
                    DisplayText: propValue
                });
            });

            return list;
        },

        /* Creates array of options from giving options array.
        *************************************************************************/
        _buildOptionsFromArray: function (optionsArray) {
            var list = [];

            for (var i = 0; i < optionsArray.length; i++) {
                if ($.isPlainObject(optionsArray[i])) {
                    list.push(optionsArray[i]);
                } else { //assumed as primitive type (int, string...)
                    list.push({
                        Value: optionsArray[i],
                        DisplayText: optionsArray[i]
                    });
                }
            }

            return list;
        },

		/* Private method fot toolbarsearch: adds options to a select field
		 * by Alberto Mognoni. Options are returned by AJAX call in JSON format, same as jTable
		 */
        _toolbarsearch_addOptionsToSelectField: function ($targetField, $optionsURL) {
			// Caricamento OPTIONS della SELECT da backend e senza STRUTTO!
			var self = this;
			/* Load a generic catch all with value of -1 */
	        $($targetField).append($('<option>', {
	            value: -1,
	            text: '---'
	        }));
			if (typeof $optionsURL==='string') {
				$.getJSON($optionsURL, function(data) {
					$(data.options).each(function() {
				        $($targetField).append($('<option>', {
				            value: $(this).attr('Value'),
				            text: $(this).attr('DisplayText')
				        }));
					})
				});
			} else if (jQuery.isArray($optionsURL)) { //It is an array of options
                options = this._buildOptionsFromArray($optionsURL);
                console.log(options);
    	    	for (var i = 0; i < options.length; i++) {
			        $($targetField).append($('<option>', {
			            value: options[i].Value,
			            text: options[i].DisplayText
			        }));
                }
            } else { //It is an object that it's properties are options
                options = this._buildOptionsArrayFromObject($optionsURL);
    	    	for (var i = 0; i < options.length; i++) {
			        $($targetField).append($('<option>', {
			            value: options[i].Value,
			            text: options[i].DisplayText
			        }));
                }
            }
        },	
		/* Adds column header cells to given tr element.
		 * A.M. - Modified to move Reset "button" to REAL toolbar, along with other items in a standard
		 * jTable Layout
        *************************************************************************/
        _toolbarsearch_addColumnsToHeaderRow: function ($tr) {
			var self = this;
			if(this.options.selecting && this.options.selectingCheckboxes){
				$tr.append('<td/>');	
			}
	    	for (var i = 0; i < this._columnList.length; i++) {
    	    	var fieldName = this._columnList[i];
        	    var $headerCell = this._toolbarsearch_createHeaderCellForField(fieldName, this.options.fields[fieldName]);
            	$headerCell.appendTo($tr);
            }
			if (typeof this.options.actions.deleteAction != 'undefined'){
				$tr.append('<td/>');	
			};

			if (typeof this.options.actions.updateAction != 'undefined'){
				$tr.append('<td/>');	
			};

			if(this.options.toolbarReset){
				$reset = $('.jtable-toolbar');
				$resetbutton = $(
					'<span class="jtable-toolbar-item RESET" title="Azzera i criteri di ricerca" style="">'
					+ '<span class="jtable-toolbar-item-icon"' 
					+ 'style="background-image: url(icons/Cancel.png); background-position: initial initial; background-repeat: initial initial;">'
					+ '</span>'
					+ '<span class="jtable-toolbar-item-text">Reset</span></span>').appendTo($reset);
				$resetbutton.qtip({
				    position: {
				        viewport: $(window)
				    }
				});
				$resetbutton.click(function(){
					$('.jtable-toolbarsearch').val('');
					$('.jtable-toolbarsearch-select').val('-1');
					self.load({});				
				});
			}
        },		

        
        /* Creates a header cell for given field.
        *  Returns th jQuery object.
        *************************************************************************/		
        _toolbarsearch_createHeaderCellForField: function (fieldName, field) {
			var self = this;
			if(typeof field.options != 'undefined'){
				field.type = 'options';
			};
			if(typeof field.type === 'undefined'){
				field.type = 'text';
			};
			if(typeof field.searchable === 'undefined'){
				field.searchable = false;
			};
			if(typeof field.sqlName === 'undefined'){
				field.sqlName = fieldName;
			};
			if(typeof field.sqlOperator === 'undefined'){ //default sqlOperator if unspecified
				if (field.type=='text')
					field.sqlOperator = "LIKE";
				else
					field.sqlOperator = "=";
			};
            field.width = field.width || '10%'; //default column width: 10%.
            var $_id = field.type + '-' + field.sqlOperator;
            var $_name = 'jtable-toolbarsearch-' + field.sqlName;
			if (field.type=='options') {
				var $_type = 'select';
			} else {
				var $_type = 'text';
			}
 
			var $_title = 'Colonna con filtro, operatore: <b>' + field.sqlOperator + '</b>';
			if(field.type=="options" || field.type=="checkbox"){
				var $input = $('<SELECT '
						+ 'id="' + $_id + '"'
						+ 'name="' + $_name + '"'
						+ ' title="' + $_title + '" ></SELECT>')
						.addClass('jtable-toolbarsearch jtable-toolbarsearch-select')
						.css('width','90%');				
			} else {
				var $input = $('<input type="' + $_type + '" '
						+ 'id="' + $_id + '"'
						+ 'name="' + $_name + '"'
						+ ' title="' + $_title + '"/>')
						.addClass('jtable-toolbarsearch jtable-toolbarsearch-input')
						.css('width','90%');				
			}
			if(field.type=="date"){
				var displayFormat = field.displayFormat || this.options.defaultDateFormat;
            	$input.datepicker({
    				dateFormat: 'dd/mm/yy',
    				beforeShowDay: $.datepicker.noWeekends,
    				firstDay: 1
    			});
			}
			$input.qtip({
			    position: {
			        viewport: $(window)
			    }
			});
 			$input.bind('change',function(){
				var $q=[];
				var $opt=[];
				var $postData={};
				var $search_string = "";
				var $i =0;
				$('.jtable-toolbarsearch').each(function(){
					var $name = $(this).attr('name');
					var $id = $(this).attr('id');
		    		var $id_parts = $id.split("-");
		    		var $field_type = $id_parts[0];
					var $sql_operator = $id_parts[1];
					var $field_name = $name.replace('jtable-toolbarsearch-','').toUpperCase();
					var $field_value = $(this).val();
					if ($field_type=='date') {
						$field_value = _toolbarsearch_convertDateFromEuroToISO($field_value);
					}
					if($(this).val().length>=1 && 
							!(($field_type=='options' || $field_type=='checkbox') && $field_value==-1)){
						if ($sql_operator=='LIKE')
							$search_string = $search_string + ' AND ' + 
								$field_name + " LIKE'%" + $field_value + "%'";
						else 
							$search_string = $search_string + ' AND ' + 
								$field_name + ' ' + $sql_operator + " '" + $field_value + "'";
						$opt.push($name.replace('jtable-toolbarsearch-',''));								 
						$q.push($(this).val());
						$i++;
					}
				});
//				self.load({'q[]':$q,'opt[]':$opt});
				self.load({'jtFilter':$search_string});
			});
														
            var $headerContainerDiv = $('<div />')
                .addClass('jtable-column-header-container');
                
			if(field.searchable){	
				$headerContainerDiv.append($input);
				if (field.type=='options') {
					this._toolbarsearch_addOptionsToSelectField($input, field.options);
				}
				if (field.type=='checkbox') {
					this._toolbarsearch_addOptionsToSelectField($input, field.values);
				}
			}
		
            var $th = $('<th></th>')
                .addClass('jtable-column-header')
                .css('width', field.width)
                .data('fieldName', fieldName)
                .append($headerContainerDiv);
            
            return $th;
        }
	});
	
})(jQuery);
