package servlets;

import dtoMappers.OrderDtoMapper;
import dtoMappers.VehicleDtoMapper;
import infrastructure.core.impl.ApplicationContext;
import infrastructure.core.impl.Context;
import infrastructure.dto.EntityManager;
import infrastructure.dto.impl.EntityManagerImpl;
import services.Mechanic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/repair")
public class RepairServlet extends HttpServlet {
    Mechanic mechanic;

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

        this.mechanic = appContext.getObject(Mechanic.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idToDelete = req.getParameter("id");
        mechanic.repair(Integer.parseInt(idToDelete));
        resp.sendRedirect("viewDiagnostics");
    }
}
