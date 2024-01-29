$(document).ready( function() {
    $("#ticketForm").submit(function(event) {
        event.preventDefault();
        const Data = $(this).serialize();
        console.log(Data);
        console.log(JSON.stringify(Data));
        let ticketId = $("input[name='ticketId']").val();
        let technicalReview = $("#technicalReview").val(); // Ejemplo de campo del formulario

        const data = {
            technicalReview: technicalReview
            // Añade aquí otros campos según sea necesario
        };
        console.log(data)
        console.log(JSON.stringify(data))
        $.ajax({
            url: '/fixify/tickets/update/'+ ticketId, // URL del controlador
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function (xhr) {
                // Incluye el token CSRF en la cabecera de la solicitud
                xhr.setRequestHeader("X-CSRF-TOKEN", $("meta[name='_csrf']").attr("content"));
            },
            success: function (response) {
                console.log(response);
                // Manejar la respuesta exitosa
                alert(response.message +  " Ticket: " + response.id);
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

