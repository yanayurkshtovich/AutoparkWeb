<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@page import="dto.dtos.VehicleDto" %>
<%@page import="utils.ProfitCalculationTool" %>
<%@page import="classes.Vehicle" %>
<%@page import="java.util.Comparator" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.stream.Collectors" %>
<%@page import="java.util.concurrent.atomic.AtomicReference" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.function.Predicate" %>
<%@page import="java.util.Optional" %>
<%@page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>View vehicles</title>
   <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
   <div class="vertical-center">
   <br />
       <a class="ml-20" href="/">MAIN PAGE</a>
       <br />
       <br />
       <hr />
       <br />
       <%
            String sortKey = null;
            String order = null;
            if (request.getParameter("type") != null) sortKey = "type";
            if (request.getParameter("model") != null) sortKey = "model";
            if (request.getParameter("regIdentifier") != null) sortKey = "regIdentifier";
            if (request.getParameter("mass") != null) sortKey = "mass";
            if (request.getParameter("year") != null) sortKey = "year";
            if (request.getParameter("color") != null) sortKey = "color";
            if (request.getParameter("engine") != null) sortKey = "engine";
            if (request.getParameter("mileage") != null) sortKey = "mileage";
            if (request.getParameter("tank") != null) sortKey = "tank";
            if (request.getParameter("asc") != null) order = "asc";
            if (request.getParameter("desc") != null) order = "desc";
       %>
       <%if (sortKey != null) {%>
           <%
               String clearPath = "/viewReports";
               String ascPath = "?" + sortKey + "&asc";
               String descPath = "?" + sortKey + "&desc";
           %>
           <div>
               <a class="center" href="<%=descPath%>">
                               &#128315</a>
               <a class="center" href="<%=ascPath%>">
                               &#128314</a>
               <a class="center" href="<%=clearPath%>">
                                Reset</a>
           </div>
           <br />
           <hr />
           <br />
       <%}%>
       <table style="width: 90%" class="center">
           <caption>ACCOUNTING &#128213</caption>
           <tr>
               <th>Type</th>
               <th>Model</th>
               <th>Registration Identifier</th>
               <th>Mass</th>
               <th>Manufacture Year</th>
               <th>Color</th>
               <th>Engine Type</th>
               <th>Mileage</th>
               <th>Rents Income</th>
               <th>Tax per month</th>
               <th>Profit from vehicle</th>
           </tr>
           <%
               DecimalFormat df = new DecimalFormat("#.##");
               List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");

               Comparator<VehicleDto> comparator = null;
               if (sortKey != null && sortKey.equals("type")) {
                   comparator = Comparator.comparing(vehicleDto -> vehicleDto.getType());
               }
               if (sortKey != null && sortKey.equals("model")) {
                   comparator = Comparator.comparing(vehicleDto -> vehicleDto.getModel());
               }
               if (sortKey != null && sortKey.equals("regIdentifier")) {
                   comparator = Comparator.comparing(vehicleDto -> vehicleDto.getRegistrationIdentifier());
               }
               if (sortKey != null && sortKey.equals("mass")) {
                   comparator = Comparator.comparingInt(vehicleDto -> vehicleDto.getMass());
               }
               if (sortKey != null && sortKey.equals("year")) {
                   comparator = Comparator.comparingInt(vehicleDto -> vehicleDto.getManufactureYear());
               }
               if (sortKey != null && sortKey.equals("color")) {
                   comparator = Comparator.comparing(vehicleDto -> vehicleDto.getColor());
               }
               if (sortKey != null && sortKey.equals("engine")) {
                   comparator = Comparator.comparing(vehicleDto -> vehicleDto.getEngineType());
               }
               if (sortKey != null && sortKey.equals("mileage")) {
                   comparator = Comparator.comparingInt(vehicleDto -> vehicleDto.getMileage());
               }
               if (order != null && comparator != null && order.equals("desc")) {
                   comparator = comparator.reversed();
               }
               if (comparator != null) {
                   dtoList.sort(comparator);
               }
               for (VehicleDto dto : dtoList) {
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
               <td><%=df.format(dto.getVehicleIncome())%></td>
               <td><%=df.format(dto.getVehicleTaxPerMonth())%></td>
               <td><%=df.format(dto.getVehicleProfit())%></td>
           </tr>
           <%}%>
       </table>
       <p class="center">Average tax per month:
       <strong><%=df.format(ProfitCalculationTool.calculateAvgTax(dtoList))%></strong></p>
       <p class="center">Average income per month:
       <strong><%=df.format(ProfitCalculationTool.calculateAvgIncome(dtoList))%></strong></p>
       <p class="center">Autopark profit:
       <strong><%=df.format(ProfitCalculationTool.calculateAutoparkProfit(dtoList))%></strong></p>
       <br />
       <ht />
       <br />
       <p class="center"><strong>Sort by:</strong></p>
       <br />
       <div>
           <% if(request.getParameter("type") == null) {%><a class="center" href="viewReports?type">Type</a><%}%>
           <% if(request.getParameter("model") == null) {%><a class="center" href="viewReports?model">Model</a><%}%>
           <% if(request.getParameter("regIdentifier") == null) {%><a class="center" href="viewReports?regIdentifier">Identifier</a><%}%>
           <% if(request.getParameter("mass") == null) {%><a class="center" href="viewReports?mass">Mass</a><%}%>
       </div>
       <br />
       <br />
       <div>
           <% if(request.getParameter("year") == null) {%><a class="center" href="viewReports?year">Year</a><%}%>
           <% if(request.getParameter("color") == null) {%><a class="center" href="viewReports?color">Color</a><%}%>
           <% if(request.getParameter("engine") == null) {%><a class="center" href="viewReports?engine">Engine</a><%}%>
           <% if(request.getParameter("mileage") == null) {%><a class="center" href="viewReports?mileage">Mileage</a><%}%>
       </div>
       <br />
       <br />
       </div>
   </div>
</body>
</html>

