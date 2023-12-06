package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.AutoDAO;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Auto;

@WebServlet("/autos")
public class AutoServletController extends HttpServlet {

    List<Auto> listAutos = new ArrayList();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        String route = req.getParameter("action");
        if (route.equals("getAll")) {
            try {
                res.setContentType("application/json; charset=UTF-8");
                listAutos = AutoDAO.seleccionar();
                
                for (Auto auto : listAutos) {
                    byte[] imagenBytes = auto.getImagen();
                    String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
                    auto.setImagenBase64(imagenBase64);

                }
                mapper.writeValue(res.getWriter(), listAutos);
            } catch (IOException ex) {
                Logger.getLogger(AutoServletController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Parametro no valido");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) {

    }
}
