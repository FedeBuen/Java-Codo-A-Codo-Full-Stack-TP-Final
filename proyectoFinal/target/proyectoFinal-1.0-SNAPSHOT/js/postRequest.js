document.addEventListener("DOMContentLoaded", function() {
    
    const addAutoForm = document.getElementById("addAutoForm");
    const parrafoAlert = document.createElement("p");
    const marcaElement = document.getElementById("marca");
    const modeloElement = document.getElementById("modelo");
    const nacionalidadElement = document.getElementById("nacionalidad");
    const periodoElement = document.getElementById("periodo");
    const potenciaElement = document.getElementById("potencia");
    const aceleracionElement = document.getElementById("aceleracion");
    const velocidadElement = document.getElementById("velocidad");
    const precioElement = document.getElementById("precio");
           
    const imagenElement = document.getElementById("imagen");
    const imagenPreview = document.getElementById("imagenPreview");
    const imagenContainer = document.getElementById("imagenContainer");
    
   
    addAutoForm.addEventListener("submit", function(e) {
        
        e.preventDefault();
        
        const datos = new FormData();
        
        datos.append("action", "add");
        datos.append("marca", marcaElement.value);
        datos.append("modelo", modeloElement.value);
        datos.append("nacionalidad", nacionalidadElement.value);
        datos.append("periodo", periodoElement.value);
        datos.append("potencia", potenciaElement.value);
        datos.append("aceleracion", aceleracionElement.value);
        datos.append("velocidad", velocidadElement.value);
        datos.append("imagen", imagenElement.files[0]);
        datos.append("precio", precioElement.value);
        
        fetch("/app/autos", {
            method: "POST",
            body: datos
        })
                .then(response => response.json())
                .then(data => {
                    parrafoAlert.textContent = data.message;
                    addAutoForm.appendChild(parrafoAlert);

                    setTimeout(() =>{
                        parrafoAlert.remove();
                        marcaElement.value = "";
                        modeloElement.value = "";
                        nacionalidadElement.value = "";
                        periodoElement.value = "";
                        potenciaElement.value = "";
                        aceleracionElement.value = "";
                        velocidadElement.value = "";
                        precioElement.value = "";
                        imagenContainer.classList.add("d-none");
                    }, 3000);
                            
                });
        
        
    });
    
        
    imagenElement.addEventListener("change", function() {
       
        const imagenSelected = imagenElement.files[0];
        
        if (imagenSelected) {
            const reader = new FileReader();
            reader.onload = function(e) {
                imagenPreview.src = e.target.result;
                imagenContainer.classList.remove("d-none");
            }
            reader.readAsDataURL(imagenSelected);
        } else {
            imagenPreview.src = "";
        }
    });

});
    