<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="main-content">
	<div class="main-content-inner">
		<!-- #section:basics/content.breadcrumbs -->


		<!-- /section:basics/content.breadcrumbs -->
		<div class="page-content">
			<!-- #section:settings.box -->
			<div class="ace-settings-container" id="ace-settings-container">



			</div>
			<!-- /.ace-settings-container -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>
					活动 <small> <i class="ace-icon fa fa-angle-double-right"></i>
						活动管理
					</small>

				</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->


					<table id="grid-table"></table>

					<div id="grid-pager"></div>

					<script type="text/javascript">
									var $path_base = "..";//in Ace demo this will be used for editurl parameter
								</script>

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
	</div>
</div>
<!-- /.main-content -->

<script type="text/javascript">
			
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-pager";
				
				//resize to fit page size
				$(window).on('resize.jqGrid', function () {
					$(grid_selector).jqGrid( 'setGridWidth', $(".page-content").width() );
			    })
				//resize on sidebar collapse/expand
				var parent_column = $(grid_selector).closest('[class*="col-"]');
				$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
					if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
						//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
						setTimeout(function() {
							$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
						}, 0);
					}
			    })
			
				jQuery(grid_selector).jqGrid({
					url:'${pageContext.request.contextPath }/activity/jqgrid/${code}',
					datatype: "json",
					height:  $(document).height()-360,
					colNames:[ '序号','活动名称', '已报名人数','活动名额','报名开始时间','报名截止时间', '状态','是否允许推荐','街道或社工委','社区'],
					colModel:[
						
						{name:'seq',index:'seq', width:60, sorttype:"int", editable: true,search:false},
						{name:'name',index:'name',width:90, editable:true},
						{name:'applyNumber',index:'applyNumber', width:70, editable: true,search:false},
						{name:'quota',index:'quota', width:150, sortable:false,editable: true,search:false},
						{name:'applicationStartDates',index:'applicationStartDates', width:150,editable: true,search:false},
						{name:'applicationEndDates',index:'applicationEndDates', width:150, sortable:false,search:false},
						{name:'applicationStatus',index:'applicationStatus', width:90, editable: true,search:false},
						{name:'needPropose',index:'needPropose', width:90},
						{name:'govParentName',index:'govOrg.parent.name', width:90},
						{name:'govName',index:'govOrg.name', width:90},
						
					], 
			
					viewrecords : true,
					rowNum:10,
					rowList:[10,20,30],
					pager : pager_selector,
					altRows: true,
					//toppager: true,
					
					multiselect: true,
					//multikey: "ctrlKey",
			        multiboxonly: true,
			        gridComplete: function (){
			        	$('#searchmodfbox_grid-table').remove();
			        	$('.ui-widget-overlay').remove();
			        	var ids = jQuery(grid_selector).jqGrid('getDataIDs'); 
			            for (var i = 0; i < ids.length; i++) { 
			            	var rowdata = jQuery(grid_selector).getRowData(ids[i]);
			            	if(rowdata.needPropose=='true'){
			            		jQuery(grid_selector).jqGrid('setRowData', ids[i], {needPropose: "是",name: "<a href='${pageContext.request.contextPath }/activity/showComment/${code}/"+ids[i]+"'>"+rowdata.name+"</a>"}); 
			            	}else{
			            		jQuery(grid_selector).jqGrid('setRowData', ids[i], {needPropose: "否",name: "<a href='${pageContext.request.contextPath }/activity/showComment/${code}/"+ids[i]+"'>"+rowdata.name+"</a>"}); 
			            	}
			            	
			            } 
			        },
					loadComplete : function() {
						var table = this;
						setTimeout(function(){
							styleCheckbox(table);
							
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},
			
					//editurl: "/dummy.html",//nothing is saved
					caption: "活动管理"
			
					//,autowidth: true,
			
			
					/**
					,
					grouping:true, 
					groupingView : { 
						 groupField : ['name'],
						 groupDataSorted : true,
						 plusicon : 'fa fa-chevron-down bigger-110',
						 minusicon : 'fa fa-chevron-up bigger-110'
					},
					caption: "Grouping"
					*/
			
				});
				$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
				
				
			
				//enable search/filter toolbar
				//jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
				//jQuery(grid_selector).filterToolbar({});
			
			
				//switch element when editing inline
				function aceSwitch( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=checkbox]')
							.addClass('ace ace-switch ace-switch-5')
							.after('<span class="lbl"></span>');
					}, 0);
				}
				//enable datepicker
				function pickDate( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=text]')
								.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
					}, 0);
				}
			
			
				//navButtons
				jQuery(grid_selector).jqGrid('navGrid',pager_selector,
					{ 	//navbar options
						edit: true,
						editicon : 'ace-icon fa fa-pencil blue',
						edittext:'编辑',
						edittitle:'编辑',
						add: true,
						addicon : 'ace-icon fa fa-plus-circle purple',
						addtext:'新增',
						addtitle:'新增',
						del: true,
						delicon : 'ace-icon fa fa-trash-o red',
						deltext:'删除',
						deltitle :'删除选中的行',
						search: true,
						searchicon : 'ace-icon fa fa-search orange',
						searchtext:'查找',
						searchtitle:'查找',
						
						refresh: true,
						refreshicon : 'ace-icon fa fa-refresh green',
						refreshtext:'刷新',
						refreshtitle:'刷新',
						view: false,
						viewicon : 'ace-icon fa fa-search-plus grey',
						viewtext:'查看',
						viewtitle:'查看',
						excel:false,
						send:false,
						enabled : true,
						enabledtext:'启用',
						enabledtitle:'启用',
						enabledicon : 'ace-icon fa glyphicon-ok red',
						notenabled : true,
						notenabledtext:'停用',
						notenabledtitle:'停用',
						notenabledicon : 'ace-icon fa glyphicon-remove red',
					},
					{
						//edit record form
						//closeAfterEdit: true,
						//width: 700,
						recreateForm: true,
						url: '${pageContext.request.contextPath }/activity/edit/${code}', 
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//new record form
						//width: 700,
						closeAfterAdd: true,
						recreateForm: true,
						url: '${pageContext.request.contextPath }/activity/add/${code}', 
				         mtype: 'POST',  
						viewPagerButtons: false,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
							.wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						recreateForm: true,
						url: '${pageContext.request.contextPath }/activity/del', 
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						},
						onClick : function(e) {
							//alert(1);
						}
					},
					{
						//search form
						recreateForm: true,
						afterShowSearch: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
							style_search_form(form);
						},
						afterRedraw: function(){
							style_search_filters($(this));
						}
						,
						multipleSearch: true,
						/**
						multipleGroup:true,
						showQuery: true
						*/
					},
					{
						//view record form
						recreateForm: true,
						beforeShowForm: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
						}
					},
					{
						recreateForm: true,
						url: '${pageContext.request.contextPath }/activity/enable', 
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						},
						onClick : function(e) {
							//alert(1);
						}
					},
					{
						recreateForm: true,
						url: '${pageContext.request.contextPath }/activity/notenable', 
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						},
						onClick : function(e) {
							//alert(1);
						}
					}
				)
			
			
				
				function style_edit_form(form) {
					//enable datepicker on "sdate" field and switches for "stock" field
					form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
					
					//form.find('input[name=stock]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
							   //don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
							  //.addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
			
							
					//update buttons classes
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
					buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')
					
					buttons = form.next().find('.navButton a');
					buttons.find('.ui-icon').hide();
					buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
					buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');		
				}
			
				function style_delete_form(form) {
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
					buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
				}
				
				function style_search_filters(form) {
					form.find('.delete-rule').val('X');
					form.find('.add-rule').addClass('btn btn-xs btn-primary');
					form.find('.add-group').addClass('btn btn-xs btn-success');
					form.find('.delete-group').addClass('btn btn-xs btn-danger');
				}
				function style_search_form(form) {
					var dialog = form.closest('.ui-jqdialog');
					var buttons = dialog.find('.EditTable')
					buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
					buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
					buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
				}
				
				function beforeDeleteCallback(e) {
					var form = $(e[0]);
					if(form.data('styled')) return false;
					
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_delete_form(form);
					
					form.data('styled', true);
				}
				
				function beforeEditCallback(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_edit_form(form);
				}
			
			
			
				//it causes some flicker when reloading or navigating grid
				//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
				//or go back to default browser checkbox styles for the grid
				function styleCheckbox(table) {
				/**
					$(table).find('input:checkbox').addClass('ace')
					.wrap('<label />')
					.after('<span class="lbl align-top" />')
			
			
					$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
					.find('input.cbox[type=checkbox]').addClass('ace')
					.wrap('<label />').after('<span class="lbl align-top" />');
				*/
				}
				
			
				//unlike navButtons icons, action icons in rows seem to be hard-coded
				//you can change them like this in here if you want
				function updateActionIcons(table) {
					/**
					var replacement = 
					{
						'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
						'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
						'ui-icon-disk' : 'ace-icon fa fa-check green',
						'ui-icon-cancel' : 'ace-icon fa fa-times red'
					};
					$(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
					*/
				}
				
				//replace icons with FontAwesome icons like above
				function updatePagerIcons(table) {
					var replacement = 
					{
						'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
						'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
						'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
						'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
					};
					$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
				}
			
				function enableTooltips(table) {
					$('.navtable .ui-pg-button').tooltip({container:'body'});
					$(table).find('.ui-pg-div').tooltip({container:'body'});
				}
			
				//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
			
				$(document).one('ajaxloadstart.page', function(e) {
					$(grid_selector).jqGrid('GridUnload');
					$('.ui-jqdialog').remove();
				});
			});
		</script>