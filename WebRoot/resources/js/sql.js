var tableNames = "";
var fieldNames = "";
var queryData = "";
$(function(){
    init();
});

/*------------初始化表格名数据--------------*/
function initData(){
     tableNames = "";
     fieldNames = "";
}
function init(){
    $.get("query/getTableNames",function(data){
        // alert(data);
        tableNames = data;
        var tableSelect = "";
        for(var i=0;i<tableNames.length;i++){
            var option = createOption(tableNames[i],tableNames[i]);
            tableSelect = tableSelect.concat(option);
        }
        $("select[name='tableName']").append(tableSelect);
        tableNames = "";
        $("select[name='tableName']").multiselect({
        	multiple: false,
    		header: "Option tables",
    		noneSelectedText: "Option tables",
    		selectedList: 1
        });
    });
    //表名发生改变时
    $("select[name='tableName']").on("change",function(event){
        $("select[name='filedSelectName']").remove();
        $("div[name='fieldConditionDiv']").remove();
        $.get("query/getFieldNames?tableName="+$(this).val(),function(data){
            fieldNames = data;
            var fieldSelect = createFieldSelect("filedSelectName");
            $("#t_tableInfoDiv").append(fieldSelect);
            
            $("select[name='filedSelectName']").multiselect({
            	multiple: false,
        		header: "Option fields",
        		noneSelectedText: "Option fields",
        		selectedList: 1
     		});
           
        });
        
    });

   
}
/*--------------------创建option---------------------*/
function createOption(value,text){
    var option = "<option value='"+value+"'>"+text+"</option>";
    return option;
}
/*--------------------创建FieldSelect---------------------*/
function createFieldSelect(selectName){
    var fieldSelect =" <select name='"+selectName+"' multiple>";
   // fieldSelect = fieldSelect.concat(createOption("","Field"));
    for(var i=0;i<fieldNames.length;i++){
        fieldSelect = fieldSelect.concat(createOption(fieldNames[i],fieldNames[i]));
    }
    fieldSelect = fieldSelect.concat("</select>");
    return  fieldSelect;
}
