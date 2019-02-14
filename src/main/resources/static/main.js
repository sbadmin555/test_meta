/**
 * Create HTML table row.
 *
 * \param text (str) HTML code to be placed inside the row.
 */
function tr(text) {
    return '<tr>' + text + '</tr>';
}

/**
 * Create HTML table cell element.
 *
 * \param text (str) The text to be placed inside the cell.
 */
function td(text) {
    return '<td>' + text + '</td>';
}

/**
 * Edit value: load key and value into inputs.
 */
function editButton(deviceId, metadataId) {
    var format = '<button '
        + 'class="btn btn-default" '
        + 'onclick="editKey(\'{metadataId}\')" '
        + '>Edit</button>';
    return format.replace(/{metadataId}/g, metadataId);
}

/**
 * Create HTML button with which a key-value pair is deleted from persistence.
 *
 * \param key (str) that'll be deleted when the created button is clicked.
 */
function deleteButton(deviceId, key) {
    var format = '<button '
        + 'class="btn btn-default" '
        + 'data-key="{key}" '
        + 'onclick="deleteKey(\'{deviceId}\', \'{key}\')" '
        + '>Delete</button>'
    return format.replace(/{key}/g, key).replace(/{deviceId}/g, deviceId);
}

/**
 * Create HTML table row element.
 *
 * \param key (str) text into the key cell.
 * \param value (str) text into the value cell.
 */
function row(key, value) {
    return $(
        tr(
            td(value.deviceId)+
            td(value.deviceName)+
            td(key) +
            td(value.type) +
            td(editButton(value.deviceId,key)) +
            td(deleteButton(value.deviceId,key))));
}

/**
 * Clear and reload the values in data table.
 */
function refreshTable() {
    $.get('/api/v1/camMetadata/1234', function(data) {
        var attr,
            mainTable = $('#mainTable tbody');
        mainTable.empty();
        for (attr in data) {
            if (data.hasOwnProperty(attr)) {
                mainTable.append(row(attr, data[attr]));
            }
        }
    });
}

function editKey(metadataId) {
    /* Find the row with key in third column (metadataid column). */
    var format = '#mainTable tbody td:nth-child(3):contains("{metadataId}")',
        selector = format.replace(/{metadataId}/, metadataId),
        cells = $(selector).parent().children(),
        deviceId = cells[0].textContent,
        deviceName = cells[1].textContent,
        metadataId = cells[2].textContent,
        metadataType = cells[3].textContent,
        deviceIdInput = $('#deviceIdInput'),
        deviceNameInput = $('#deviceNameInput');
        metaIdInput = $('#metaIdInput'),
            metaTypeInput = $('#metaTypeInput');

    /* Load the key and value texts into inputs
     * Select value text so it can be directly typed to
     */
    deviceIdInput.val(deviceId);
    deviceNameInput.val(deviceName);
    metaIdInput.val(metadataId);
    metaTypeInput.val(metadataType)
    metaTypeInput.select();
}

/**
 * Delete key-value pair.
 *
 * \param key (str) The key to be deleted.
 */
function deleteKey(deviceId, key) {
    /*
     * Delete the key.
     * Reload keys and values in table to reflect the deleted ones.
     * Set keyboard focus to key input: ready to start typing.
     */
    $.delete('/camMetadata/'+deviceId+'/'+key, function() {
        refreshTable();
        $('#metaIdInput').focus();
    });
}

$(document).ready(function() {
    var deviceIdInput = $('#deviceIdInput'),
        deviceNameInput = $('#deviceNameInput'),
        metaIdInput = $('#metaIdInput'),
        metaTypeInput = $('#metaTypeInput');

    refreshTable();
    $('#addForm').on('submit', function(event) {
        var data = {
            "deviceId": deviceIdInput.val(),
            "deviceName": deviceNameInput.val(),
            "id": metaIdInput.val(),
            "type": metaTypeInput.val()
        };

        /*
         * Persist the new key-value pair.
         * Clear the inputs.
         * Set keyboard focus to key input: ready to start typing.
         */
        $.post('/api/v1/camMetadata?deviceId='+deviceIdInput.val(), JSON.stringify(data), function() {
            refreshTable();
            deviceIdInput.val('');
            deviceNameInput.val('');
            metaIdInput.val('');
            metaTypeInput.val('');
            deviceIdInput.focus();
        });
        /* Prevent HTTP form submit */
        event.preventDefault();
    });

    /* Focus keyboard input into key input; ready to start typing */
    deviceIdInput.focus();
});
