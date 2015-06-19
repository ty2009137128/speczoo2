<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>Home</title>
	<meta name="viewport" content="width=device-width,intial-scale=1" >
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/mydb.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/user.css">
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
    </script>
</head>

<body>
<div style="margin-left: 20%;width:800px;">
    <div style="margin-left: 290px;width: 250px;">
        <h2> Welcome to Specview! </h2>
    </div>

    <div>
        <article>
            <br/>
            　　Specview is a tool for 1-D spectral visualization and analysis of astronomical spectrograms. It is written
            in Java thus can be run anywhere Java is supported. Specview is capable of reading all the Hubble Space
            Telescope spectral data formats, as well as data from several other instruments (such as IUE, FUSE, ISO,
            FORS and SDSS), preview spectra from MAST, and data from generic FITS and ASCII tables. It can also read
            data from Virtual Observatory servers, and read and write spectrogram data in Virtual Observatory SED
            format. It can also read files in the SPC Galactic format used in the chemistry field.<p/><br/>
            　　Once ingested, data can be plotted and examined with a large selection of custom settings. Specview
            supports
            instrument-specific data quality handling, flexible spectral units conversions, custom plotting attributes,
            plot annotations, tiled plots, hardcopy to JPEG files and PostScript file or printer, etc.<p/><br/>
            　　A spectral feature quick measurement tool enables the user, with a few mouse actions, to perform and
            record,
            in VOTable or FITS format, measurements on selected spectral features.<p/><br/>
            　　Specview can be used to build wide-band SEDs, overploting or combining data from the same astronomical
            source taken with different instruments and/or spectral bands. Data can be further processed with averaging,
            splicing, detrending, and Fourier filtering tools.<p/><br/>
            　　Specview has a spectral model fitting capability that enables the user to work with multi-component models
            (including user-defined models) in a number of ways, and fit models to data.<p/><br/>
            　　Support exists for overplotting and interactively renormalize data from spectral templates.<p/><br/>
            　　Specview can overplot spectral line identifications taken from a variety of line lists, including
            user-supplied lists.<p/><br/>
            <br/><br/>
        </article>
    </div>
</div>
</body>
</html>
