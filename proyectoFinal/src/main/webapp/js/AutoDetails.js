document.addEventListener("DOMContentLoaded", function(){
   
    const queryParams = new URLSearchParams(window.location.search);
    const autoDetailId = {
        id: queryParams.get("idAuto")        
    }
        
    const autoDetailContainer = document.getElementById("autoDetails");
    const btnEliminarElement = document.getElementById("btnEliminar");
    const btnModificarElement = document.getElementById("btnModificar");
    const btnGuardarElement = document.getElementById("btnGuardar");
    const btnContainerElement = document.getElementById("btnContainer");
    
    let objetoAuto= {
        idauto: 0,
        marca: "",
        modelo: "",
        nacionalidad: "",
        periodo: "",
        potencia: "",
        aceleracion: "",
        velocidad: "",
        precio: 0,
        imagen: ""
    }
    
    function loadAuto(){
        
        fetch(`/app/autos?action=getById&idAuto=${autoDetailId.id}`)      
                .then(response => response.json())
                .then(data => {
                    autoDetailContainer.innerHTML += `
                        <div class="col-md-6 text-center">
                            <div class="clearfix">
                                <img src="data:image/jpeg;base64,${data.imagenBase64}" class="my-4" style="width: 75%" alt="imagen de auto"/>
                            </div>
                        </div>
                        <div class="card-body col-md-6"> 
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">
                                    <h2 class="card-title">${data.marca}</h2>
                                </li>
                                <li class="list-group-item">Marca: ${data.marca}</li>
                                <li class="list-group-item">Modelo: ${data.modelo}</li>
                                <li class="list-group-item">Nacionalidad: ${data.nacionalidad}</li>
                                <li class="list-group-item">Periodo: ${data.periodo}</li>
                                <li class="list-group-item">Potencia: ${data.potencia}</li>
                                <li class="list-group-item">Aceleración: ${data.aceleracion}</li>                        
                                <li class="list-group-item">Velocidad: ${data.velocidad}</li>                        
                                <li class="list-group-item">
                                    <h5>Precio U$S: ${data.precio}</h5>
                                </li>                        
                            </ul>    
                        </div>
                    `;
                    
                    objetoAuto.idauto = data.idAuto;
                    objetoAuto.marca = data.marca;
                    objetoAuto.modelo = data.modelo;
                    objetoAuto.nacionalidad = data.nacionalidad;
                    objetoAuto.periodo = data.periodo;
                    objetoAuto.potencia = data.potencia;
                    objetoAuto.aceleracion = data.aceleracion;
                    objetoAuto.velocidad = data.velocidad;
                    objetoAuto.precio = data.precio;
                    objetoAuto.imagen = data.imagen;
                    
                })
    }
    
    
    
    
    btnEliminarElement.addEventListener("click", function() {
        fetch(`/app/autos?action=delete&idAuto=${autoDetailId.id}`, {
            method: "DELETE"
        })
                .then(response => response.json())
                .then(data => {
                    if(data.success) {
                        window.location.href = `/app/index.html`;
                    }
        });
        
    });
        
    btnModificarElement.addEventListener("click", function() {
        btnGuardarElement.classList.remove("d-none");
        btnEliminarElement.classList.add("d-none");
        btnModificarElement.classList.add("d-none");
        
    });
    
    
    btnGuardarElement.addEventListener("click", function() {
        window.location.href = `/app/index.html`;
    })
    
    loadAuto();
});
