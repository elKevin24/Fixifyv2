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
        class: 'row mb-1 align-items-center',
        id: 'service-' + index
    }); // mb-2 para un pequeño margen inferior

    // Crear y añadir el input para la descripción del servicio
    let divInput = $('<div>', {class: 'col-md-9 pe-0'}); // col-8 para un ancho más grande
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

    // Contenedor para las partes de este servicio
    let divPartsContainer = $('<div>', {
        id: 'parts-container-' + index,
        class: 'parts-container mt-1'
    }).appendTo(divRow);

    divRow.appendTo(container);
}

function addPart(serviceIndex) {
    let partsContainer = $('#parts-container-' + serviceIndex);
    let partIndex = partsContainer.children().length;

    let divPartRow = $('<div>', {
        class: 'row g-3 align-items-center mb-1 ',
        id: 'part-' + serviceIndex + '-' + partIndex
    });

    let divPartInput = $('<div>', {class: 'col-md ms-3'});
    $('<input>', {
        type: 'text',
        class: 'form-control',
        placeholder: 'Descripción de la Parte',
        name: 'servicios[' + serviceIndex + '].partes[' + partIndex + '].descripcion'
    }).appendTo(divPartInput);

    let divPartButton = $('<div>', {class: 'col-auto'});
    $('<button>', {
        type: 'button',
        html: '&#45;', // Símbolo de "menos" para eliminar parte
        class: 'btn btn-danger',
        click: function () {
            $(this).closest('.row').remove(); // Elimina la fila de esta parte específica
        }
    }).appendTo(divPartButton);

    divPartInput.appendTo(divPartRow);
    divPartButton.appendTo(divPartRow);

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
function cancelTicket(){
    const ticketId = $('#ticket_id').val();

    const confirmCancel = confirm("¿Estás seguro de que deseas cancelar este ticket?");
    if (confirmCancel) {

        $.ajax({
            url: '/fixify/tickets/update/'+ ticketId, // URL del controlador
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
    // $.ajax({
    //     url: '/ruta/a/tu/controlador/cancelar/' + ticketId,
    //     type: 'POST', // o 'PUT', según tu backend
    //     // En caso de que necesites enviar un token CSRF u otros headers, aquí es cómo añadirlos:
    //     // headers: {
    //     //     'X-CSRF-TOKEN': token
    //     // },
    //     success: function(response) {
    //         alert('Ticket cancelado con éxito.');
    //         // Opcional: redirigir o actualizar la UI aquí
    //     },
    //     error: function(xhr, status, error) {
    //         alert('Hubo un problema al cancelar el ticket.');
    //     }
    // });

}
$('#cancelTicketBtn').click(function() {
    $('#cancelTicketModal').modal('show');

    // const isConfirmed = confirm("¿Estás seguro de que deseas cancelar este ticket?");
    // if (isConfirmed) {
        // Asume que 'ticketId' está disponible, podría ser insertado en el DOM o en una variable de JS
        console.warn("elimido")
        // $.ajax({
        //     url: '/ruta/a/tu/controlador/cancelar/' + ticketId,
        //     type: 'POST', // o 'PUT', según tu backend
        //     // En caso de que necesites enviar un token CSRF u otros headers, aquí es cómo añadirlos:
        //     // headers: {
        //     //     'X-CSRF-TOKEN': token
        //     // },
        //     success: function(response) {
        //         alert('Ticket cancelado con éxito.');
        //         // Opcional: redirigir o actualizar la UI aquí
        //     },
        //     error: function(xhr, status, error) {
        //         alert('Hubo un problema al cancelar el ticket.');
        //     }
        // });
    // }
});




