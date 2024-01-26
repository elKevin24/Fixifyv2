function addService() {
    var container = document.getElementById('servicios-container');
    var index = container.children.length;
    var serviceDiv = document.createElement('div');

    var inputService = document.createElement('input');
    inputService.type = 'text';
    inputService.name = 'servicios[' + index + '].descripcion';
    inputService.className = 'form-control';
    serviceDiv.appendChild(inputService);

    var checkboxHardware = document.createElement('input');
    checkboxHardware.type = 'checkbox';
    checkboxHardware.name = 'servicios[' + index + '].esHardware';
    checkboxHardware.onchange = function() { togglePartsField(index); };
    serviceDiv.appendChild(checkboxHardware);

    container.appendChild(serviceDiv);
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

