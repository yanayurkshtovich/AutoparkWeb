package servlets;

import dtoMappers.OrderDtoMapper;
import dtoMappers.VehicleDtoMapper;
import dtos.OrderDto;
import dtos.VehicleDto;
import exceptions.NotVehicleException;
import infrastructure.core.impl.ApplicationContext;
import infrastructure.core.impl.Context;
import infrastructure.dto.EntityManager;
import infrastructure.dto.impl.EntityManagerImpl;
import services.Mechanic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/viewDiagnostics")
public class ViewDiagnosticsServlet extends HttpServlet {
    VehicleDtoMapper vehicleDtoMapper;
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

        this.vehicleDtoMapper = appContext.getObject(VehicleDtoMapper.class);
        this.mechanic = appContext.getObject(Mechanic.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("cars", vehicleDtoMapper.getVehicleDtos());
            for (VehicleDto dto : vehicleDtoMapper.getVehicleDtos()) {
                mechanic.repair(dto.getId());
            }
            req.setAttribute("carsAfterRepairment", vehicleDtoMapper.getVehicleDtos());
        } catch (NotVehicleException e) {
            System.out.println(e.getMessage());
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewDiagnosticsJSP.jsp");
        dispatcher.forward(req, resp);
    }
}
