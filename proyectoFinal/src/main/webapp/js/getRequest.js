document.addEventListener("DOMContentLoaded", function() {
    const autoCards = document.getElementById("autosCards");
    const autos = [];

    function loadAutosList() {
        fetch("/app/autos?action=getAll")
                .then(response => response.json())
                .then(data => {
                    data.forEach(auto => {
                        autos.push(auto);
                        autoCards.innerHTML += `
                        <div class="col-md-3 mb-4 ident" data-auto-id="${auto.idAuto}"> 
                            <div calss="card h-100 animate-hover-card">
                                <img src="data:image/jpeg;base64,${auto.imagenBase64}" class="card-img-top h-75" alt="Imagen Auto"></img>
                                <div class="card-body">
                                    <h5 class="card-title">${auto.modelo}</h5>
                                    <p class="card-text">${auto.velocidad}</p>
                                </div>
                            </div>
                        </div>
                    `
                    });

                });
    }

    function cargaAutos() {
        fetch("app/autos?action=getAll")
                .then(response=> response.json())
                .then(data => {
                    data.forEach(auto => {
                        autos.push(auto);
                        autoCards.innerHTML += `
                            <ul>
                                <li>
                                    <p>${auto.idAuto}</p>
                                </li>
                                <li>
                                    <p>${auto.modelo}</p>
                                </li>
                            </ul>
                        `
                    });
        });
        
    }


    cargaAutos();



});