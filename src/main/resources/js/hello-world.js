$(document).ready(function () {
    
    //success created new space
    var getUrlParameter = function getUrlParameter(sParam) {
        var sPageURL = window.location.search.substring(1),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
            }
        }
    };

    var status = getUrlParameter("status");
    console.log(status);

    if (status == "success") {
        require('aui/flag')({
            type: "success",
            title: 'New Space',
            body: "New Space has been created."
        });
    }else if(status == "error"){
        require('aui/flag')({
            type: "error",
            title: "Error",
            body: "Cannot create new space."
        });
    }
    //=====================end of method=======================
    
    
    //Check input space name
    $("#txtSpaceName").focusout(function () {
        var temp = $("#txtSpaceName").val();
        console.log(temp);
        if (temp.length <= 0) {
            showError("space-name-error", "Space name is empty");
        } else {
            removeError("space-name-error");
        }
    });

    //check input space key
    $("#txtSpaceKey").focusout(function () {
        var temp = $("#txtSpaceKey").val();
        var valid = true;
        if (temp.match(/^[a-zA-Z0-9]+$/) && temp.length <= 255) {
            var listKey = $(".spaceKey");
            for (var i = 0; i < listKey.length; i++) {
                if (temp.match($(listKey[i]).text())) {
                    valid = false;
                    showError("space-key-error", "Dulicate Space Key");
                    break;
                }
            }
        } else {
            valid = false;
            showError("space-key-error", "space key khong hop le, space key chi duoc [a-z][0-9]");
        }
        if (valid) {
            removeError("space-key-error");
        }
    });

    //remove error notice
    function removeError(spaceErrorClass) {
        if ($("#message01").hasClass(spaceErrorClass)) {
            $("#message01").removeClass(spaceErrorClass);
            $("#message01").slideUp(1000);
            $("#body").empty();
            $("#addBtn").removeAttr("disabled");
        }
    }

    //show message error
    function showError(spaceErrorClass, message) {
        $("#body").empty();
        $("#message01").slideDown(1000);
        $("#message01").addClass(spaceErrorClass);
        $("#body").append(message);
        $("#addBtn").attr("disabled", "disabled");
    }
    
    // function go on
    showAllPages();
    popupAttachment();
});
/*
 *  FUNCTION 
 * 
 */


/*
 *  Show pages (currently under construction)
 */
function showAllPages(){
    $("#space-table").click(function(e){
        e.preventDefault();
        var a = $(e.target);
        $.getJSON(a.attr("href"), function(data){
            createComboBox(data);
        });
    });
}

function createComboBox(data){
        $.each(data, function(index, object){
           for(var key in object){
               var option = document.createElement('option');
               option.setAttribute("value", key);
               var optionText = document.createTextNode(object[key]);
               option.appendChild(optionText);
               $('#sync-product-single-select').append(option);
           }
        });
        //show 
        $("#pages").css("display", "block");
}
/*
 * Pop up attachment
 * 
 */

var selectPage;
function popupAttachment(){
    selectPage = $("#sync-product-single-select");
    selectPage.change(function(){
        var pageid = selectPage.val();
        console.log(pageid);
        var table_container = $("#table-container");
        table_container.empty();
        $.getJSON("spacecontent.action",{"pageid" : pageid, "option": "2"}, function(data){
          createTable(data); 
        });
    });
}

function createTable(data){
    var table = document.createElement('table');
    table.setAttribute("class", "aui");
    var thead = document.createElement('thead');
    var tbody = document.createElement('tbody');
    table.appendChild(thead);
    table.appendChild(tbody);
    // add title for each column
    var headRow = document.createElement('tr');
    thead.appendChild(headRow);
    for(var key in data[0]){
        console.log("column title");
        var tdata = document.createElement('th');
        var text = document.createTextNode(key);
        tdata.appendChild(text);
        headRow.appendChild(tdata);
    }
    $.each(data, function(index, _attachment){
        var bodyRow = document.createElement('tr');
        tbody.appendChild(bodyRow);
        $.each(_attachment, function(i, value){
            var rowData = document.createElement('td');           
            var dataValue = document.createTextNode(value);
            rowData.appendChild(dataValue);
            bodyRow.appendChild(rowData);
        });
    });
    
    // show table
    $('#table-container').append(table);
}