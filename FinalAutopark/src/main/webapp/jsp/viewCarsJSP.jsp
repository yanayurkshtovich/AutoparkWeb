<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@page import="dtos.VehicleDto" %>
<%@page import="classes.Vehicle" %>
<%@page import="java.util.Comparator" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.stream.Collectors" %>
<%@page import="java.util.concurrent.atomic.AtomicReference" %>
<%@page import="java.util.function.Predicate" %>
<%@page import="java.util.Optional" %>
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
Set<String> uniqueTypes = dtoList.stream().map(VehicleDto::getType).collect(Collectors.toSet());
Set<String> uniqueModels = dtoList.stream().map(VehicleDto::getModel).collect(Collectors.toSet());
Set<String> uniqueColors = dtoList.stream().map(VehicleDto::getColor).collect(Collectors.toSet());
Set<String> uniqueEngineTypes = dtoList.stream().map(VehicleDto::getEngineType).collect(Collectors.toSet());
Set<String> uniqueMass = dtoList.stream().map(vehicleDto -> String.valueOf(vehicleDto.getMass())).collect(Collectors.toSet());
Set<String> uniqueManufactureYear = dtoList.stream().map(vehicleDto -> String.valueOf(vehicleDto.getManufactureYear())).collect(Collectors.toSet());
AtomicReference<Predicate<VehicleDto>> filter = new AtomicReference<>(vehicleDto -> true);
Optional.ofNullable(request.getParameter("type")).filter(s -> !s.isEmpty()).ifPresent(s -> {
   filter.set(filter.get().and(vehicleDto -> vehicleDto.getType().equals(s)));
});
Optional.ofNullable(request.getParameter("model")).filter(s -> !s.isEmpty()).ifPresent(s -> {
   filter.set(filter.get().and(vehicleDto -> vehicleDto.getModel().equals(s)));
});
Optional.ofNullable(request.getParameter("color")).filter(s -> !s.isEmpty()).ifPresent(s -> {
   filter.set(filter.get().and(vehicleDto -> vehicleDto.getColor().equals(s)));
});
Optional.ofNullable(request.getParameter("engine")).filter(s -> !s.isEmpty()).ifPresent(s -> {
   filter.set(filter.get().and(vehicleDto -> vehicleDto.getEngineType().equals(s)));
});
Optional.ofNullable(request.getParameter("mass")).filter(s -> !s.isEmpty()).ifPresent(s -> {
   filter.set(filter.get().and(vehicleDto -> String.valueOf(vehicleDto.getMass()).equals(s)));
});
Optional.ofNullable(request.getParameter("manufacture")).filter(s -> !s.isEmpty()).ifPresent(s -> {
   filter.set(filter.get().and(vehicleDto -> String.valueOf(vehicleDto.getManufactureYear()).equals(s)));
});
dtoList = dtoList.stream().filter(filter.get()).collect(Collectors.toList());
       %>
       <br />
       <a class="center" href="/">MAIN PAGE</a>
       <a class="center" href="/viewCars">RESET</a>
       <br />
       <br />
       <hr />
       <br />
       <table class="center">
           <caption>VEHICLES &#128663</caption>
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
               <th> Detailed Info</th>
           </tr>
           <%if (dtoList.size()==0) {%>
               <tr><td colspan="10">
No vehicles with those parameters</td></tr>
           <%}%>
           <%
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
               <td><a style="background-color: transparent" href="<%="/info?id=" + dto.getId()%>">&#x1f48c;</a></td>
           </tr>
           <%}%>
       </table>
       <br />
       <div>
           <hr />
           <br />
           <form method="get" action="/viewCars" class="flex">
                   <p class="center">Type</p>
                   <br />
                   <select  class="select" style="width: 300px" name="type" >
                       <option value="" <%=request.getParameter("type")==null?"selected":""%>>Not chosen</option>
                       <%for (String s : uniqueTypes) {%>
                       <option value="<%=s%>" <%=(request.getParameter("type")!=null && s.equals(request.getParameter("type"))?"selected":"")%>><%=s%></option>
                       <%}%>
                   </select>
                   <p class="center">Model</p>
                   <br />
                   <select class="select" style="width: 300px" name="model" >
                       <option value="" <%=request.getParameter("model")==null?"selected":""%>>Not chosen</option>
                       <%for (String s : uniqueModels) {%>
                       <option value="<%=s%>" <%=(request.getParameter("model")!=null && s.equals(request.getParameter("model"))?"selected":"")%>><%=s%></option>
                       <%}%>
                   </select>
                   <p class="center">Engine</p>
                   <br />
                   <select class="select" style="width: 300px" name="engine" >
                       <option value="" <%=request.getParameter("engine")==null?"selected":""%>>Not chosen</option>
                       <%for (String s : uniqueEngineTypes) {%>
                       <option value="<%=s%>" <%=(request.getParameter("engine")!=null && s.equals(request.getParameter("engine"))?"selected":"")%>><%=s%></option>
                       <%}%>
                   </select>
                   <p class="center">Color</p>
                   <br />
                   <select class="select" style="width: 300px" name="color" >
                       <option value="" <%=request.getParameter("color")==null?"selected":""%>>Not chosen</option>
                       <%for (String s : uniqueColors) {%>
                       <option value="<%=s%>" <%=(request.getParameter("color")!=null && s.equals(request.getParameter("color"))?"selected":"")%>><%=s%></option>
                       <%}%>
                   </select>
                  <p class="center">Mass</p>
                  <br />
                  <select class="select" style="width: 300px" name="mass" >
                      <option value="" <%=request.getParameter("mass")==null?"selected":""%>>Not chosen</option>
                      <%for (String s : uniqueMass) {%>
                      <option value="<%=s%>" <%=(request.getParameter("mass")!=null && s.equals(request.getParameter("mass"))?"selected":"")%>><%=s%></option>
                      <%}%>
                  </select>
                    <p class="center">Manufacture Year</p>
                    <br />
                    <select class="select" style="width: 300px" name="manufacture" >
                        <option value="" <%=request.getParameter("manufacture")==null?"selected":""%>>Not chosen</option>
                        <%for (String s : uniqueManufactureYear) {%>
                        <option value="<%=s%>" <%=(request.getParameter("manufacture")!=null && s.equals(request.getParameter("manufacture"))?"selected":"")%>><%=s%></option>
                        <%}%>
                    </select>
              <br />
              <br />
               <button class="button" style="width: 300px; background-color: #20B2AA; padding: 15px 32px;" type="submit">CHOOSE</button>
           </form>
           <br />
           <hr />
       </div>
   </div>
</div>
</body>
</html>
