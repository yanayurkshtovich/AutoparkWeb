package servlets;

import dto.VehicleTypeDtoMapper;
import exceptions.NotVehicleException;
import infrastructure.core.impl.ApplicationContext;
import infrastructure.core.impl.Context;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.impl.EntityManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/viewTypes")
public class ViewCarTypesServlet extends HttpServlet {
    VehicleTypeDtoMapper vehicleTypeDtoMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(EntityManager.class, EntityManagerImpl.class);
        Context appContext = new ApplicationContext("infrastructure", interfaceToImplementation);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.vehicleTypeDtoMapper = appContext.getObject(VehicleTypeDtoMapper.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("types", vehicleTypeDtoMapper.getVehicleTypeDtos());
        } catch (NotVehicleException e) {
            System.out.println(e.getMessage());
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewTypesJSP.jsp");
        dispatcher.forward(req, resp);
    }
}
