package servlets;

import dto.VehicleDtoMapper;
import dto.dtos.VehicleDto;
import exceptions.NotVehicleException;
import infrastructure.core.impl.ApplicationContext;
import infrastructure.core.impl.Context;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.impl.EntityManagerImpl;
import services.Mechanic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/viewPlanedDiagnostics")
public class ViewPlanedDiagnosticsServlet extends HttpServlet {
    private static final int DIAGNOSTICS_PERIOD = 300;
    VehicleDtoMapper vehicleDtoMapper;
    Mechanic mechanic;
    HttpSession currentSession;

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
        Object[] vehicles_id = null;
        currentSession = req.getSession();
        LocalTime startingPointTime = (LocalTime) currentSession.getAttribute("startingPointTime");
        currentSession.getAttribute("vehiclesBeforeRepairment");
        currentSession.getAttribute("vehiclesAfterRepairment");

        if (startingPointTime == null || DIAGNOSTICS_PERIOD <= LocalTime.now().toSecondOfDay() - startingPointTime.toSecondOfDay()) {
            currentSession.setAttribute("startingPointTime", LocalTime.now());
            try {
                vehicles_id = vehicleDtoMapper.getVehicleDtos().stream().map(VehicleDto::getId).toArray();
                mechanic.breakRandomVehicles(vehicles_id);
                currentSession.setAttribute("vehiclesBeforeRepairment", vehicleDtoMapper.getVehicleDtos());
                for (VehicleDto dto : vehicleDtoMapper.getVehicleDtos()) {
                    mechanic.repair(dto.getId());
                }
                currentSession.setAttribute("vehiclesAfterRepairment", vehicleDtoMapper.getVehicleDtos());
            } catch (NotVehicleException e) {
                throw new RuntimeException(e);
            }
        }

        Integer timeDifference = LocalTime.now().toSecondOfDay() -
                ((LocalTime) currentSession.getAttribute("startingPointTime")).toSecondOfDay();

        req.setAttribute("cars", currentSession.getAttribute("vehiclesBeforeRepairment"));
        req.setAttribute("carsAfterRepairment", currentSession.getAttribute("vehiclesAfterRepairment"));
        req.setAttribute("lastDiagnosticsTime", timeDifference);

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewPlanedDiagnosticsJSP.jsp");
        dispatcher.forward(req, resp);
    }

}
