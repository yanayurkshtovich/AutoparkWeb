<%@page import="java.util.Set" %>
<%@page import="java.util.stream.Collectors" %>
<%@page import="java.util.concurrent.atomic.AtomicReference" %>
<%@page import="java.util.function.Predicate" %>
<%@page import="java.util.Optional" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@page import="dtos.VehicleDto" %>
<%@page import="classes.Vehicle" %>
<%@page import="java.util.Comparator" %>
<%@page import="dtos.OrderDto" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>View vehicles</title>
   <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center">
   <div class="center">
       <%
       List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");
       List<VehicleDto> dtoAfterRepairmentList = (List<VehicleDto>) request.getAttribute("carsAfterRepairment");
       %>
       <br />
       <a class="center" href="/">MAIN PAGE</a>
       <br />
       <br />
       <hr />
       <br />
       <table class="center">
            <caption>VEHICLES AFTER DIAGNOSTICS &#128296</caption>
            <tr>
                <th>Type</th>
               <th>Model</th>
               <th>Registration identifier</th>
               <th>Mass</th>
               <th>Manufacture Year</th>
               <th>Color</th>
               <th>Engine type</th>
               <th>Mileage</th>
               <th>Tank Volume / Battery Charge</th>
               <th>Repairment required</th>
               <th>Was repaired</th>
            </tr>
            <% int counter = 0;
               for(VehicleDto dto : dtoList) {
           %>
           <tr>
               <td><%=dto.getType()%></td>
               <td><%=dto.getModel()%></td>
               <td><%=dto.getRegistrationIdentifier()%></td>
               <td><%=dto.getMass()%></td>
               <td><%=dto.getManufactureYear()%></td>
               <td><%=dto.getColor()%></td>
               <td><%=dto.getEngineType()%></td>
               <td><%=dto.getMileage()%></td>
               <td><%=dto.getTankVolumeOrBatteryCharge()%></td>
               <% if (dto.isBroken()) {%>
                    <td>&#10004;</td>
                  <%} else {%>
                    <td>&#10006;</td>
                  <%}
              if (!dtoAfterRepairmentList.get(counter).isBroken() && dto.isBroken()) {%>
                <td>&#10004;</td>
              <%} else {%>
                <td>&#10006;</td>
              <%}%>
           </tr>
           <%}%>
       </table>
       </div>
   </div>
</body>
</html>