/**
 * 
 * GLOBAL VARIABLES
 */
var __SUCCESS = "success", __ERROR = "error", __DELETED = "deleted";
var __OPEN_PAGES_DIALOG = 1;
/*
 * RUN SCRIPT
 */
$(document).ready(function () {
    showAllPages();
    successCreateSpace("status");
    showDialogAttachment();
    getSpaceInfo();
    closeDialog();
    submitAttachmentsDownloadInfo()
});
/*
 *  FUNCTION 
 * 
 */
function getUrlParameter(sParam) {
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
}
/* message create space */
function successCreateSpace(sParam) {
    var status = getUrlParameter("status");
    console.log(status);

    if (status == __SUCCESS) {
        require('aui/flag')({
            type: "success",
            title: AJS.I18n.getText("helloworld.lang.message.flagTitle"),
            body: AJS.I18n.getText("helloworld.lang.message.newSpaceSuccess"),
        });
    } else if (status == __ERROR) {
        $("#message01").show();
    } else if (status == __DELETED) {
        require('aui/flag')({
            type: "success",
            title: AJS.I18n.getText("helloworld.lang.message.flagTitleDelete"),
            body: AJS.I18n.getText("helloworld.lang.message.spaceDeleted"),
        });
    }
}

/*
 *  Show pages (currently under construction)
 */
function showAllPages() {
    $(":radio").click(function (e) {
        __OPEN_PAGES_DIALOG = 1;
        $('#page-select').empty();
        var page = $(e.target);
        //var spaceName = page.attr("name");
        $.getJSON(page.attr("value"), function (data) {
            $("#combobox-label").text(AJS.I18n.getText('helloworld.lang.choosePage') + ":");
            createComboBox(data);
        });
    });
}

function createComboBox(data) {
    $.each(data, function (index, object) {
        for (var key in object) {
            var option = document.createElement('option');
            option.setAttribute("value", key);
            option.setAttribute("name", object[key]);
            var optionText = document.createTextNode(object[key]);
            option.appendChild(optionText);
            $('#page-select').append(option);
        }
    });
    //show combobox
    $("#pages").css("display", "block");
}
/*
 * Pop up attachment
 * 
 */

function showDialogAttachment() {
    // Shows the dialog when the "Show dialog" select is changed
    $("#page-select").click(function () {
        console.log(__OPEN_PAGES_DIALOG);
        if (__OPEN_PAGES_DIALOG % 2 == 0) {
            var pageId = $("#page-select option:selected").val();
            var pageName = $("#page-select option:selected").text();
            $("#dialog-header").text(pageName);
            popupAttachment(pageId);
            AJS.dialog2("#attachment-dialog").show();
        }
        __OPEN_PAGES_DIALOG++;
    });
}

function popupAttachment(pageid) {
    $.getJSON("spacecontent.action", {"pageid": pageid, "option": "2"}, function (data) {
        createTable(data);
        submitAttachmentsDownloadInfo();
    });
}

function createTable(data) {
    $.each(data, function (index, object) {
        var bodyRow = document.createElement("tr");
        var datName = document.createElement("td");
        var link = document.createElement("a");
        link.setAttribute("href", AJS.contextPath() + object.downloadPath);
        link.setAttribute("download", object.name);
        var textName = document.createTextNode(object.name);
        datName.appendChild(link);
        datName.setAttribute("class", "attachment-name");
        link.appendChild(textName);

        var datSize = document.createElement("td");
        var textSize = document.createTextNode(object.size);
        datSize.setAttribute("class", "attachment-size");
        datSize.appendChild(textSize);

        var datCreator = document.createElement("td");
        var textCreator = document.createTextNode(object.creator);
        datCreator.setAttribute("class", "attachment-creator");
        datCreator.appendChild(textCreator);

        var datDate = document.createElement('td');
        var textDate = document.createTextNode(object.creationDate);
        datDate.setAttribute("class", "attachment-creationDate");
        datDate.appendChild(textDate);

        var datIcon = document.createElement("td");
        datIcon.setAttribute("class", "save-csv");
        var span = document.createElement("span");
        span.setAttribute("class", "aui-icon aui-icon-small aui-iconfont-page-default");
        datIcon.appendChild(span);

        bodyRow.appendChild(datName);
        bodyRow.appendChild(datSize);
        bodyRow.appendChild(datCreator);
        bodyRow.appendChild(datDate);
        bodyRow.appendChild(datIcon);
        $('#attachment-table-body').append(bodyRow);
    });
}

/* show confirm delete dialog */
function getSpaceInfo() {
    $('.del-space-btn').click(function (e) {
        e.preventDefault();
        var target = e.target;
        var parent = $(target).parent();
        var url = $(parent).attr("href");
        var parents = $(target).parentsUntil('tbody');
        var datas = $(parents[parents.length - 1]).children();
        var _space_name, _space_key;
        for (var i = 0; i < datas.length; i++) {
            if ($(datas[i]).hasClass("space-name")) {
                _space_name = $(datas[i]).text();
            } else if ($(datas[i]).hasClass("space-key")) {
                _space_key = $(datas[i]).text();
            }
        }
        $("#confirm-dialog").empty();
        $("#confirm-dialog").append(AJS.I18n.getText("helloworld.lang.message.deleteConfirm") + _space_name + " - key:  " + _space_key);
        $("#del-link").attr('href', url);
        AJS.dialog2("#confirm-delete").show();
    });
}
function closeDialog() {
    $('#confirm-close-button').click(function () {
        AJS.dialog2("#confirm-delete").hide();
    });
    $("#attachments-close-button").click(function () {
        AJS.dialog2("#attachment-dialog").hide();
    });
}
function submitAttachmentsDownloadInfo() {
    var links = $("#attachment-table-body .attachment-name");
    $(links).click(function (e) {
        var attachmentName = $(e.target).text();
        var userName = AJS.params.remoteUser;
        $.post("save-attachment.action", {
            "attachmentName": attachmentName,
            "userName": userName,
        }, function (data, status) {
            require('aui/flag')({
                type: status,
                title: AJS.I18n.getText("helloworld.lang.message.flagTitle"),
                body: AJS.I18n.getText("helloworld.lang.message.newSpaceSuccess"),
            });
        });
    });
}
    