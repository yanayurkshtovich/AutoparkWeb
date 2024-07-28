package servlets;

import dto.RentDtoMapper;
import dto.VehicleDtoMapper;
import dto.dtos.RentDto;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/info")
public class ViewCarInfoServlet extends HttpServlet {
    VehicleDtoMapper vehicleDtoMapper;
    RentDtoMapper rentDtoMapper;

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
        this.rentDtoMapper = appContext.getObject(RentDtoMapper.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            req.setAttribute("cars", vehicleDtoMapper.getVehicleDtos().stream()
                            .filter(vehicleDto -> id == vehicleDto.getId()).collect(Collectors.toList()));
            List<RentDto> certainVehicleRents = rentDtoMapper.getRentDtos().stream()
                    .filter(rentDto -> rentDto.getVehicleId() == id).toList();
            req.setAttribute("rents", certainVehicleRents);
        } catch (NotVehicleException e) {
            System.out.println(e.getMessage());
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewCarInfoJSP.jsp");
        dispatcher.forward(req, resp);
    }

}
