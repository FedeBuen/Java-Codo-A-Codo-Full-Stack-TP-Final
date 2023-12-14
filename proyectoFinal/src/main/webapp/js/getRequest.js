document.addEventListener("DOMContentLoaded", function () {
    const autoCards = document.getElementById("autosCards");
    const autos = [];


    function loadAutosList() {
        fetch("/app/autos?action=getAll")
                .then(response => response.json())
                .then(data => {
                    data.forEach(auto => {
                        autos.push(auto)
                        autoCards.innerHTML += `
                            <div class="col-md-3 mb-4 ident" data-id-Auto = "${auto.idAuto}">
                                <div class="card h-100 animate-hover-card bg-light">  
                                    <div class="card-header text-center">
                                        <h2 class="card-title">${auto.marca}</h2>
                                    </div> 
                                    <img src="data:image/jpeg;base64,${auto.imagenBase64}" class="card-img" alt="Imagen Auto">   
                                    <div class="card-body">
                                        <h5 class="card-title">Modelo: ${auto.modelo}</h5> 
                             
                                    </div>  
                                    <div class="card-footer">
                                    <h6 class="card-title text-left">Precio U$S: ${auto.precio}</h6>
                                    </div>
                                </div>
                            </div>
                        `
                    });
                });
    }

    function filterAutos(datoBuscar) {
        const autosFiltrados = autos.filter(autos => {
            return autos.marca.toLowerCase().includes(datoBuscar.toLowerCase());
        });

        autoCards.innerHTML = "";
        autosFiltrados.forEach(auto => {
            const card = document.createElement("div");
            card.className = "col-md-3 mb-4 ident";
            card.setAttribute("data-id-Auto", auto.idAuto)
            card.innerHTML = `
                            <div class="card h-100 animate-hover-card bg-light">  
                                <div class="card-header text-center">
                                    <h2 class="card-title">${auto.marca}</h2>
                                </div> 
                                <img src="data:image/jpeg;base64,${auto.imagenBase64}" class="card-img" alt="Imagen Auto">   
                                <div class="card-body">
                                    <h5 class="card-title">Modelo: ${auto.modelo}</h5>                             
                                </div>  
                                <div class="card-footer">
                                <h6 class="card-title text-left">Precio U$S: ${auto.precio}</h6>
                                </div>
                            </div>
            `
            autoCards.appendChild(card);
        });

    }

    //evento que lanza el filtro de busqueda
    const searchForm = document.querySelector("form[role='search']");
    searchForm.addEventListener("submit", function (e) {
        e.preventDefault();
        const searchTerm = searchForm.querySelector("input[type='search']").value;
        if (searchTerm != "") {
            filterAutos(searchTerm);
        } else {
            autoCards.innerHTML = "";
            loadAutosList();
        }
        ;
        searchForm.querySelector("input[type='search']").value = "";
    });



    autoCards.addEventListener("click", function (e) {

        const clickedCard = e.target.closest(".ident");
        if (!clickedCard) {
            return;
        }
        const autoid = clickedCard.dataset.idAuto;
        fetch(`/app/autos?action=getDetails&idAuto=${autoid}`)
                .then(response => response.json())
                .then(autoDetail => {
                    const queryParams = new URLSearchParams({
                        idAuto: autoDetail.idAuto
                    });

                    window.location.href = `/app/pages/autoDetail.html?${queryParams.toString()}`;
                })
                .catch(error => console.error("Error en la peticion getDetail", error));
    });

    loadAutosList();

});