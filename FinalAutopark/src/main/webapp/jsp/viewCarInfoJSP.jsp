<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@page import="dtos.VehicleDto" %>
<%@page import="dtos.RentDto" %>
<%@page import="classes.VehicleType" %>
<%@page import="java.util.Comparator" %>
<%@page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View vehicle</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
    <div class="center flex full-vh">
        <div class="vertical-center">
        <br />
            <a display: block justify-content: center align-items: center href="/viewCars">PREVIOUS PAGE</a>
            <a display: block justify-content: center align-items: center href="/">MAIN PAGE</a>
            <br />
            <br />
            <hr />
            <br />
            <table style="width: 100%; font-size:15px" class="center">
                <caption>VEHICLE INFO</caption>
                <tr>
                    <th>Type</th>
                    <th>Model</th>
                    <th>Registration Identifier</th>
                    <th>Mass</th>
                    <th>Manufacture Year</th>
                    <th>Color</th>
                    <th>Engine Type</th>
                    <th>Mileage</th>
                    <th>Fuel tank volume / Battery charge</th>
                    <th>Fuel Consumption / Electricity Consumption</th>
                    <th>Tax Coefficient</th>
                    <th>Max km per full tank / battery</th>
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
                    <td><%=dto.getTankVolumeOrBatteryCharge()%></td>
                    <td><%=dto.getFuelOrElectricityConsumption()%></td>
                    <td><%=dto.getEngineTaxPerMonth()%></td>
                    <td><%=dto.getMaxKilometers()%></td>
                </tr>
                <%}%>
            </table>
            <p class="center">Vehicle tax per month:
            <strong><%=df.format(dtoList.get(0).getVehicleTaxPerMonth())%></strong></p>
        </div>
        <div>
            <br />
            <br />
            <hr />
            <br />
             <table class="center">
                <caption>RENTS</caption>
                <tr>
                    <th>Data</th>
                    <th>Rent Cost</th>
                    </tr>
                <%
                    List<RentDto> rentDtoList = (List<RentDto>) request.getAttribute("rents");
                    for (RentDto dto : rentDtoList) {
                %>
                <tr>
                    <td><%=dto.getRentDate()%></td>
                    <td><%=dto.getRentCost()%></td>
                </tr>
                <%}%>
        </table>
        <p class="center">Total Income:
        <strong><%=df.format(dtoList.get(0).getVehicleIncome())%></strong></p>
        <p class="center">Total Profit:
        <strong><%=df.format(dtoList.get(0).getVehicleProfit())%></strong></p>
        </div>
    </div>
</body>
</html>

