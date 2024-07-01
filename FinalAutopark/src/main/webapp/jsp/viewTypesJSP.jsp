<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@page import="dtos.VehicleTypeDto" %>
<%@page import="classes.VehicleType" %>
<%@page import="java.util.Comparator" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View vehicle types</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
    <section class="section-container">
    <div class="center flex full-vh">
        <div class="vertical-center">
            <br />
            <a display: block justify-content: center align-items: center href="/">MAIN PAGE</a>
            <br />
            <br />
            <hr />
            <br />
            <%
                String sortKey = null;
                String order = null;
                if (request.getParameter("name") != null) sortKey = "name";
                if (request.getParameter("tax") != null) sortKey = "tax";
                if (request.getParameter("asc") != null) order = "asc";
                if (request.getParameter("desc") != null) order = "desc";
            %>
            <%if (sortKey != null) {%>
                <%
                    String clearPath = "/viewTypes";
                    String ascPath = "?" + sortKey + "&asc";
                    String descPath = "?" + sortKey + "&desc";
                %>
                <div>
                    <a class="center" href="<%=descPath%>">
                                    Descending sort</a>
                    <a class="center" href="<%=ascPath%>">
                                    Ascending sort</a>
                    <a class="center" href="<%=clearPath%>">
                                     Reset</a>
                </div>
                <br />
                <hr />
                <br />
            <%}%>
            <table class="center">
                <caption>VEHICLE TYPES &#128668</caption>
                <tr>
                    <th>Type Name</th>
                    <th>Tax Coefficient</th>
                </tr>
                <%
                    List<VehicleTypeDto> dtoList = (List<VehicleTypeDto>) request.getAttribute("types");
                    Comparator<VehicleTypeDto> comparator = null;
                    if (sortKey != null && sortKey.equals("name")) {
                        comparator = Comparator.comparing(vehicleTypeDto -> vehicleTypeDto.getName());
                    }
                    if (sortKey != null && sortKey.equals("tax")) {
                        comparator = Comparator.comparingDouble(vehicleTypeDto -> vehicleTypeDto.getTaxCoefficient());
                    }
                    if (order != null && comparator != null && order.equals("desc")) {
                        comparator = comparator.reversed();
                    }
                    if (comparator != null) {
                        dtoList.sort(comparator);
                    }
                    for (VehicleTypeDto dto : dtoList) {
                %>
                <tr>
                    <td><%=dto.getName()%></td>
                    <td><%=dto.getTaxCoefficient()%></td>
                </tr>
                <%}%>
            </table>
            <%if (dtoList.size() > 0) {%>
            <p class="center">Minimal coefficient:
    <strong><%=dtoList.stream().map(VehicleTypeDto::getTaxCoefficient).min(Double::compare).get()%></strong></p>
            <p class="center">Maximal coefficient:
    <strong><%=dtoList.stream().map(VehicleTypeDto::getTaxCoefficient).max(Double::compare).get()%></strong></p>
            <%}%>
            <br />
            <ht />
            <br />
            <div>
                <% if(request.getParameter("name") == null) {%><a class="center" href="viewTypes?name">Sort by name</a><%}%>
                <% if(request.getParameter("tax") == null) {%><a class="center" href="viewTypes?tax">Sort by tax</a><%}%>
            </div>
        </div>
    </div>
    </section>
</body>
</html>
