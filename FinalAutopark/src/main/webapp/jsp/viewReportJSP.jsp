<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@page import="dtos.VehicleDto" %>
<%@page import="utils.ProfitCalculationTool" %>
<%@page import="classes.Vehicle" %>
<%@page import="java.util.Comparator" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.stream.Collectors" %>
<%@page import="java.util.concurrent.atomic.AtomicReference" %>
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
       </div>
   </div>
</body>
</html>

