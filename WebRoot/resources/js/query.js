var tableNames = "";
var fieldNames = "";
var queryData = "";
var count = "";
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
        $("select[name='tableName']").multiselect({
        	multiple: false,
    		header: "Option tables",
    		noneSelectedText: "Option tables",
    		selectedList: 1
        });
        
        
        
    });
    
    //表名发生改变时
    $("select[name='tableName']").on("change",function(event){
        $("select[name='fieldMultiSelect']").remove();
        $("div[name='fieldConditionDiv']").remove();
        $.get("query/getFieldNames?tableName="+$(this).val(),function(data){
            fieldNames = data;
            var fieldSelect = createFieldSelect("fieldMultiSelect");
          
            $("#t_tableInfoDiv").append(fieldSelect);
            $("select[name='fieldMultiSelect']").multiselect({
            	noneSelectedText: "Option Fields",
     			selectedList: 4
     		});
            $("select[name='fieldMultiSelect']").multiselect().multiselectfilter();
            
            
        });
         count=0;
    });

    //添加按钮的事件
   
    $("#addCondition").on("click",function(event){
    	
    	if(count==0){
    		 var condition = createCondition("");
    	}else{
    		var or_and=document.getElementById("or_and").options[document.getElementById("or_and").selectedIndex].text;
    		var condition = createCondition(or_and);
    	}
    	count=count+1;
    	
        $("#f_conditionDiv #buttonDiv").before(condition);
        
    });
    
    
    //给删除条件添加事件
    $("#f_conditionDiv").on("click",".deleteCondition",deleteCondition);
    
    $("#reset").on("click",function(){
    	$("div[name='fieldConditionDiv']").remove();
    	count=0;
    });

}
/*--------------------------*/


function formSubmit(){
	changeValue();
    $.ajax({
    	type:"post",
    	url:"query/tableDownload",
    	success:function(){
    		alert("Download table Successfully!");
    	},
    	error:function(){
    		alert("Sorry, have not fits or other reasons!");
    	}
    });
}
/*--------------------创建option---------------------*/
function createOption(value,text){
    var option = "<option value='"+value+"'>"+text+"</option>";
    return option;
}

function createOption2(value,text){
    var option = "<option selected value='"+value+"'>"+text+"</option>";
    return option;
}
/*--------------------创建FieldSelect---------------------*/
function createFieldSelect(selectName){
    var fieldSelect ="<select id='fieldMultiSelect' name='"+selectName+"' class='fieldMutiselect' multiple style='width:400px'>";
    //fieldSelect = fieldSelect.concat(createOption("","Field"));
    for(var i=0;i<fieldNames.length;i++){
        fieldSelect = fieldSelect.concat(createOption2(fieldNames[i],fieldNames[i]));
    }
    fieldSelect = fieldSelect.concat("</select>");
    return  fieldSelect;
}

function createFieldSelect2(selectName){
    var fieldSelect ="<select  name='"+selectName+"' >";
    //fieldSelect = fieldSelect.concat(createOption("","Field"));
    for(var i=0;i<fieldNames.length;i++){
        fieldSelect = fieldSelect.concat(createOption(fieldNames[i],fieldNames[i]));
    }
    fieldSelect = fieldSelect.concat("</select>");
    return  fieldSelect;
}

/*---------创建操作符 选择框--------------*/
function createOperation(opName){
    var operation = "<select  name='"+opName+"' class='op' >" +
        "<option value='0'>  </option>" +
        "<option value='1'> = </option>" +
        "<option value='2'> < </option>" +
        "<option value='3'> > </option>" +
        "<option value='4'> >= </option>" +
        "<option value='5'> <= </option>" +
        "</select>";
    return operation;
}
/*------------创建查询条件-------------------------*/
function createCondition(oparation){
	
	var addcontion="<a>"+oparation+"</a>"
    var fieldSelect = createFieldSelect2("");
    var operation = createOperation("");
    var input = createInput();
    
    return "<div name='fieldConditionDiv'>"+addcontion+fieldSelect+operation+input+"</div>";
}


/*-----------创建文本输入框------------*/
function createInput(){
    var input = "<input type='text'/><input type='button' value='delete' class='deleteCondition' /> <input type='hidden'/>";
    return input;
}
function createHidden(name,value){
    var hidden = "<input type='hidden' name='"+name+"' value='"+value+"'/>";
    return hidden;
}
/*---------------删除条件------------*/

function deleteCondition(){
    $(this).parent("div[name='fieldConditionDiv']").remove();
    count=0;
    
}

/*--------------- ------------*/
function setCondition(){
    /*var fieldName = $(this).prev("select").val().trim();
    var operation = $(this).val();
    var operationTem = parseInt(operation);
    var op = getOp(operationTem);
    var fieldValue = $(this).next("input").val().trim();

    //对于为空的字段，不将其加入到表单的隐藏域中
    if(""==fieldName || fieldName.length <=0){
        alert("字段不能为空.....");
        return;
    }*/
    var condition = new Array();
    $(".op").each(function(index){
        var fieldName = $(this).prev("select").val().trim();
        var operation = $(this).val();
        var operationTem = parseInt(operation);
        var op = getOp(operationTem);
        var fieldValue = $(this).next("input[type='text']").val().trim();
        //对于为空的字段，不将其加入到表单的隐藏域中
        if(""!=fieldName && fieldName.length >0&&""!=op&&op.length>0&&fieldValue!=""&&fieldValue.length>0){
              condition.push(fieldName+":"+fieldName+op+fieldValue);
        }
    });
    condition.push("tableName:"+$("#t_tableInfoDiv select[name='tableName']").val());
    condition.push("or_and:"+$("#buttonDiv select[name='or_and']").val());
    return condition;

}

function getOp(n){
    switch (n){
        case 0: return "";
        case 1: return "=";
        case 2: return "<";
        case 3: return ">";
        case 4: return ">=";
        case 5: return "<=";
        default : return "";
    }
}

function getFieldNameFromCondition(condition){
    var array = condition.split(":");
    return array[0];
}
function getFieldValueFromCondition(condition){
    var array = condition.split(":");
    return array[1];
}

/*以下转化会导致同名值被覆盖*/
function getJsonByArray(conditions){
    var json = "{";
    for(var s in conditions){
        var name = getFieldNameFromCondition(conditions[s]);
        var value= getFieldValueFromCondition(conditions[s]);
        json=json.concat("\""+name+"\":\""+value+"\"");
        if(s != conditions.length-1) json=json.concat(",");
    }
   // json=json.concat(",\"tableName\":"+"\""+$("#t_tableInfoDiv select[name='tableName']").val()+"\"");
    json = json.concat("}");
    //alert(json);
    json=jQuery.parseJSON(json);
    return json;
}
/*为避免同名覆盖，只要是查询条件，除了tableName和or_and参数，其他的json数据的key为有序数字*/
function getJsonByArray2(conditions){
    var n=1;
    var json = "{";
    for(var s in conditions){
        var name = getFieldNameFromCondition(conditions[s]);
        if("tableName"!=name && "or_and"!=name){
             name=n++;
        }
        var value= getFieldValueFromCondition(conditions[s]);
        json=json.concat("\""+name+"\":\""+value+"\"");
        if(s != conditions.length-1) json=json.concat(",");
    }
    // json=json.concat(",\"tableName\":"+"\""+$("#t_tableInfoDiv select[name='tableName']").val()+"\"");
    json = json.concat("}");
   // json=jQuery.parseJSON(json);
   //alert(json);
    return json;
}

function addJpage(){
    $("div.holder").jPages({
        containerID : "tbody",
        previous : "←",
        next : "→",
        perPage : 15,
        delay : 20,
        keyBrowse:true,
        callback:function( pages, items ){
            //alert(pages.current+","+items.count);
        }
    });
}

/*--------test   打印条件------------------*/
function print(conditions){
    for(var s in conditions){
        alert(conditions[s]);
    }
}