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
    console.log("Añadiendo servicio"); // Esto debería aparecer en la consola del navegador cuando se ejecute la función
    $('#deleteServiceButton').removeClass('d-none');
    let container = $("#servicios-container");
    let index = container.children().length;
    index = index-1

    // Crear y añadir el div para la fila
    let divRow = $('<div>', {class: 'row mb-2'}); // mb-2 para un pequeño margen inferior

    // Crear y añadir el input para la descripción del servicio
    let divInput = $('<div>', {class: 'col-md-10'}); // col-8 para un ancho más grande
    $('<input>', {
        type: 'text',
        class: 'form-control',
        placeholder: 'Descripción del Servicio',
        name: 'servicios[' + index + '].descripcion'
    }).appendTo(divInput);

    divInput.appendTo(divRow);

    // Crear y añadir el botón para añadir partes
    let divButton = $('<div>', {class: 'col-auto'});
    $('<button>', {
        type: 'button',
        html: '&#43;', // Símbolo de "más"
        class: 'btn btn-success add-part-button',
        click: function () {
            addPart(index);
        }
    }).appendTo(divButton);

    divButton.appendTo(divRow);




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
    var container = document.getElementById('servicios-container');
    var serviceDiv = container.children[serviceIndex];
    container.removeChild(serviceDiv);
    // Opcionalmente, podrías querer reorganizar los índices de los servicios restantes.
}

function removePart(serviceIndex, partIndex) {
    var partsContainer ;/* Encuentra el contenedor de partes para el servicio específico */;
    var partDiv = partsContainer.children[partIndex];
    partsContainer.removeChild(partDiv);
    // Similarmente, reorganiza los índices de las partes restantes si es necesario.
}

