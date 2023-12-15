package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.AutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Auto;
import org.apache.commons.io.IOUtils;

@WebServlet("/autos")
@MultipartConfig(
        location = "/media/temp",
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)

public class AutoServletController extends HttpServlet {

    List<Auto> listAutos = new ArrayList();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String route = req.getParameter("action");
        switch (route) {
            case "getAll" -> {
                try {
                    res.setContentType("application/json; charset=UTF-8");
                    listAutos = AutoDAO.seleccionar();

                    for (Auto auto : listAutos) {
                        auto.setImagenBase64(encoderImagen(auto.getImagen()));
                        /*
                    byte[] imagenBytes = auto.getImagen();
                    String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
                    auto.setImagenBase64(imagenBase64);
                         */
                    }
                    mapper.writeValue(res.getWriter(), listAutos);
                } catch (IOException ex) {
                    Logger.getLogger(AutoServletController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            case "getDetails" -> {
                String idauto = req.getParameter("idAuto");
                res.setContentType("application/json; charset=UTF-8");

                Auto autoDetail = AutoDAO.seleccionarId(Integer.parseInt(idauto));

                mapper.writeValue(res.getWriter(), autoDetail);
            }

            case "getById" -> {
                String idauto = req.getParameter("idAuto");
                res.setContentType("application/json; charset=UTF-8");

                Auto autoDetail = AutoDAO.seleccionarId(Integer.parseInt(idauto));

                byte[] imagenByte = autoDetail.getImagen();
                String imagenBase64 = Base64.getEncoder().encodeToString(imagenByte);
                autoDetail.setImagenBase64(imagenBase64);

                mapper.writeValue(res.getWriter(), autoDetail);
            }
            default -> {
                System.out.println("Parametro no valido");
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String route = req.getParameter("action");
        switch (route) {
            case "add" -> {
                String marca = req.getParameter("marca");
                String modelo = req.getParameter("modelo");
                String nacionalidad = req.getParameter("nacionalidad");
                String periodo = req.getParameter("periodo");
                String potencia = req.getParameter("potencia");
                String aceleracion = req.getParameter("aceleracion");
                String velocidad = req.getParameter("velocidad");

                double precio = Double.parseDouble(req.getParameter("precio"));

                Part filePart = req.getPart("imagen");
                byte[] imagenBytes = IOUtils.toByteArray(filePart.getInputStream());

                Auto auto = new Auto(marca, modelo, nacionalidad, periodo, potencia, aceleracion, velocidad, imagenBytes, precio);
                AutoDAO.insertar(auto);

                res.setContentType("application/json");
                Map<String, String> response = new HashMap();
                response.put("message", "Auto agregado exitooooooosamente");
                mapper.writeValue(res.getWriter(), response);
            }
            case "update" -> {
                try {
                    int idAuto = Integer.parseInt(req.getParameter("idAuto"));
                    String marca = req.getParameter("marca");
                    String modelo = req.getParameter("modelo");
                    String nacionalidad = req.getParameter("nacionalidad");
                    String periodo = req.getParameter("periodo");
                    String potencia = req.getParameter("potencia");
                    String aceleracion = req.getParameter("aceleracion");
                    String velocidad = req.getParameter("velocidad");

                    double precio = Double.parseDouble(req.getParameter("precio"));

                    Part filePart = req.getPart("imagen");
                    byte[] imagenBytes = IOUtils.toByteArray(filePart.getInputStream());

                    Auto auto = new Auto(idAuto, marca, modelo, nacionalidad, periodo, potencia, aceleracion, velocidad, imagenBytes, precio);
                    AutoDAO.modificar(auto);
                    
                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");

                    Map<String, String> response = new HashMap<>();
                    response.put("success", "true");
                    mapper.writeValue(res.getWriter(), response);
                } catch (Exception e) {
                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");

                    Map<String, String> responseFail = new HashMap<>();
                    responseFail.put("success", "false");
                    responseFail.put("message", e.getMessage());
                    mapper.writeValue(res.getWriter(), responseFail);
                }

            }

        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String route = req.getParameter("action");
        switch (route) {
            case "delete" -> {

                try {
                    int idauto = Integer.parseInt(req.getParameter("idAuto"));
                    res.setContentType("application/json; charset=UTF-8");
                    int result = AutoDAO.eliminar(idauto);

                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");
                    Map<String, String> response = new HashMap();
                    response.put("success", "true");
                    mapper.writeValue(res.getWriter(), response);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            default -> {
                System.out.println("Error al borrar el registro");
            }
        }
    }

    private String encoderImagen(byte[] imagenBytes) {
        return Base64.getEncoder().encodeToString(imagenBytes);
    }
}
