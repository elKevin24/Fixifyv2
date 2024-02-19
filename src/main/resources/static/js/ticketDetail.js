$(document).ready(function () {
    $("#ticketForm").submit(function (event) {
        event.preventDefault();

        let formData = new FormData(this);
        formData.forEach((value, key) => console.log(`${key}: ${value}`));
        let dataObj = { servicios: [] };

        for (let [key, value] of formData.entries()) {
            // Procesar campos de servicios y partes
            if (key.startsWith("servicios[")) {
                let serviceMatch = key.match(/servicios\[(\d+)\](\.partes\[(\d+)\])?\.(.+)/);
                if (serviceMatch) {
                    let serviceIndex = parseInt(serviceMatch[1], 10);
                    let partIndex = serviceMatch[3] ? parseInt(serviceMatch[3], 10) : null;
                    let property = serviceMatch[4];

                    dataObj.servicios[serviceIndex] = dataObj.servicios[serviceIndex] || {};

                    if (partIndex !== null) {
                        dataObj.servicios[serviceIndex].parts = dataObj.servicios[serviceIndex].parts || [];
                        dataObj.servicios[serviceIndex].parts[partIndex] = dataObj.servicios[serviceIndex].parts[partIndex] || {};
                        dataObj.servicios[serviceIndex].parts[partIndex][property] = value;
                    } else {
                        dataObj.servicios[serviceIndex][property] = value;
                    }
                }
            } else {
                // Procesar campos generales del Ticket como id y technicalReview
                dataObj[key] = value;
            }
        }

        console.log(JSON.stringify(dataObj));

        let ticketId = $("input[name='id']").val();

        $.ajax({
            url: '/fixify/tickets/update/' + ticketId, // URL del controlador
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

function calcularSuma() {
    const campos = document.getElementsByClassName("precio");
    let suma = 0;
    for (let i = 0; i < campos.length; i++) {
        suma += Number(campos[i].value) || 0;
    }
    document.getElementById("suma").innerText = suma;
}

function addService() {

    let container = $("#servicios-container");
    let index = container.children().length;
    console.log(index)

    // Crear y añadir el div para la fila
    let divRow = $('<div>', {
        class: 'row g-3 align-items-center', // 'g-3' para un poco de espacio entre columnas
        id: 'service-' + index
    });

    // Input para la descripción del servicio
    let divDescripcion = $('<div>', {class: 'col-md-6 col-lg-7'}); // Ajustado para más espacio en descripción
    $('<input>', {
        type: 'text',
        class: 'form-control',
        placeholder: 'Descripción del Servicio',
        name: 'servicios[' + index + '].description'
    }).appendTo(divDescripcion);

    // Input para el precio del servicio
    let divPrecio = $('<div>', {class: 'col-md-4 col-lg-3'}); // Ajustado para espacio en precio
    $('<input>', {
        type: 'number',
        class: 'form-control precio',
        placeholder: '000.00',
        name: 'servicios[' + index + '].price',
        min: '0',
        step: '0.01',
        on: {
            input: function () {
                calcularSuma();
            }
        }
    }).appendTo(divPrecio);

    // Botones para añadir y eliminar servicios
    let divButtons = $('<div>', {class: 'col-md-2 col-lg-2 d-flex justify-content-end'}); // Asegura que los botones estén al final
    // Botón de añadir parte
    $('<button>', {
        type: 'button',
        html: '&#43;', // Símbolo de "más"
        class: 'btn btn-success me-2',
        click: function () {
            addPart(index);
        }
    }).appendTo(divButtons);
    // Botón de eliminar servicio
    $('<button>', {
        type: 'button',
        html: '&#45;', // Símbolo de "menos"
        class: 'btn btn-danger',
        click: function () {
            removeService(index);
        }
    }).appendTo(divButtons);

    // Añadir los contenedores de inputs y botones al contenedor de la fila
    divDescripcion.appendTo(divRow);
    divPrecio.appendTo(divRow);
    divButtons.appendTo(divRow);

    // Contenedor para las partes de este servicio
    let divPartsContainer = $('<div>', {
        id: 'parts-container-' + index,
        class: 'parts-container mt-1'
    }).appendTo(divRow);

    // Añadir la fila completa al contenedor principal
    divRow.appendTo(container);
    container.on('input', '.precio', function () {
        calcularSuma()
    })
}

function addPart(serviceIndex) {
    let partsContainer = $('#parts-container-' + serviceIndex);
    let partIndex = partsContainer.children().length;

    let divPartRow = $('<div>', {
        class: 'row g-3 align-items-center mb-1 ms-2',
        id: 'part-' + serviceIndex + '-' + partIndex
    });

    // Input para la descripción de la parte
    let divPartInput = $('<div>', {class: 'col-7'}); // Usa col-6 para ocupar la mitad del espacio en pantallas pequeñas
    $('<input>', {
        type: 'text',
        class: 'form-control',
        placeholder: 'Descripción de la Parte',
        name: 'servicios[' + serviceIndex + '].partes[' + partIndex + '].description'
    }).appendTo(divPartInput);

    // Input para el precio de la parte
    let divPartPrice = $('<div>', {class: 'col-3'}); // Usa col-4 para ocupar menos espacio que la descripción
    $('<input>', {
        type: 'number',
        class: 'form-control precio',
        placeholder: '000.00',
        name: 'servicios[' + serviceIndex + '].partes[' + partIndex + '].price',
        min: '0',
        oninput: calcularSuma
    }).appendTo(divPartPrice);

    // Botón para eliminar la parte
    let divPartButton = $('<div>', {class: 'col-2'}); // Usa col-2 para el botón, asegurando que ocupe el espacio mínimo necesario
    $('<button>', {
        type: 'button',
        html: '&#45;', // Símbolo de "menos"
        class: 'btn btn-danger', // btn-sm para botones más pequeños en pantallas pequeñas
        click: function () {
            $(this).closest('.row').remove(); // Elimina esta parte específica
        }
    }).appendTo(divPartButton);

    // Añadir los elementos a la fila
    divPartInput.appendTo(divPartRow);
    divPartPrice.appendTo(divPartRow);
    divPartButton.appendTo(divPartRow);

    // Añadir la fila completa al contenedor de partes
    divPartRow.appendTo(partsContainer);
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

function cancelTicket() {
    const ticketId = $('#ticket_id').val();

    const confirmCancel = confirm("¿Estás seguro de que deseas cancelar este ticket?");
    if (confirmCancel) {

        $.ajax({
            url: '/fixify/tickets/update/' + ticketId, // URL del controlador
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({ // Convierte el objeto JavaScript a una cadena JSON
                "status": {
                    "id": 9
                },
                "technicalReview": "cancelación anticipada"
            }),
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
        // Aquí iría la lógica para cancelar el ticket, por ejemplo, una llamada AJAX
        console.log("Procediendo a cancelar el ticket con ID: ", ticketId);
        // Recuerda que ticketId ya está disponible gracias al código anterior
    }
    console.warn("El ID del ticket es: ", ticketId);
}



