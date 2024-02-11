$(document).ready( function() {
    $("#ticketForm").submit(function(event) {
        event.preventDefault();

        let formData = new FormData(this);
        console.log(JSON.stringify(formData));
        console.log(formData);
        let dataObj = {};

        for (let [key, value] of formData.entries()) {
            if (key.startsWith("servicios[")) {
                let match = key.match(/servicios\[(\d+)\]\.(.+)/);
                if (match) {
                    let index = parseInt(match[1]);
                    let property = match[2];
                    dataObj.servicios = dataObj.servicios || [];
                    dataObj.servicios[index] = dataObj.servicios[index] || {};
                    dataObj.servicios[index][property] = value;
                }
            } else {
                dataObj[key] = value;
            }
        }

        // Aquí puedes enviar el dataObj como JSON a tu servidor
        console.log(JSON.stringify(dataObj));

        let ticketId = $("input[name='id']").val();

        $.ajax({
            url: '/fixify/tickets/update/'+ ticketId, // URL del controlador
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(dataObj),
            beforeSend: function (xhr) {
                // Incluye el token CSRF en la cabecera de la solicitud
                xhr.setRequestHeader("X-CSRF-TOKEN", $("meta[name='_csrf']").attr("content"));
            },
            success: function (response) {
                console.log(response);
                // Manejar la respuesta exitosa
                alert("Actualizacion Exitosa");
            },
            error: function (xhr, status, error) {
                // Manejar errores
                alert('Error al crear el ticket: ' + xhr.responseText);
            }
        });
        // Realiza la petición AJAX para enviar formData
    });
});

function addService() {

    let container = $("#servicios-container");
    let index = container.children().length;
    console.log(index)
    index = index-1
    console.log(index)
    // Crear y añadir el div para la fila
    let divRow = $('<div>', {
        class: 'row mb-2 align-items-center',
        id: 'service-' + index
    }); // mb-2 para un pequeño margen inferior

    // Crear y añadir el input para la descripción del servicio
    let divInput = $('<div>', {class: 'col-md-9 col-md-9 pe-0'}); // col-8 para un ancho más grande
    $('<input>', {
        type: 'text',
        class: 'form-control',
        placeholder: 'Descripción del Servicio',
        name: 'servicios[' + index + '].descripcion'
    }).appendTo(divInput);

    divInput.appendTo(divRow);

    // Crear y añadir el botón para añadir partes
    let divButtons = $('<div>', {class: 'col-md-3 d-flex justify-content-end ps-0'});

    $('<button>', {
        type: 'button',
        html: '&#43;', // Símbolo de "más"
        class: 'btn btn-success me-2',
        click: function () {
            addPart(index);
        }
    }).appendTo(divButtons);


    $('<button>', {
        type: 'button',
        html: '&#45;',
        class: 'btn btn-danger',
        click: function () {
            removeService(index);
        }
    }).appendTo(divButtons);

    divButtons.appendTo(divRow);
    // Añadir la fila completa al contenedor
    divRow.appendTo(container);
}

function addPart(serviceIndex) {
    var partsContainer ;/* Encuentra el contenedor de partes para el servicio específico */;
    var partIndex = partsContainer.children.length;

    var inputPart = document.createElement('input');
    inputPart.type = 'text';
    inputPart.name = 'servicios[' + serviceIndex + '].partes[' + partIndex + '].name';
    inputPart.className = 'form-control';
    partsContainer.appendChild(inputPart);
}

function togglePartsField(serviceIndex) {
    // Lógica para mostrar/ocultar el campo de partes basado en si 'esHardware' está marcado
}

function removeService(serviceIndex) {
    // Eliminar el div del servicio específico
    $('#service-' + serviceIndex).remove();

    // Opcionalmente, actualizar la visibilidad del botón de eliminar servicio
    let container = $("#servicios-container");
    if (container.children().length === 0) {
        // Si no hay más servicios, ocultar el botón de eliminar
        $('#deleteServiceButton').addClass('d-none');
    }
}


function removePart(serviceIndex, partIndex) {
    var partsContainer ;/* Encuentra el contenedor de partes para el servicio específico */;
    var partDiv = partsContainer.children[partIndex];
    partsContainer.removeChild(partDiv);
    // Similarmente, reorganiza los índices de las partes restantes si es necesario.
}

