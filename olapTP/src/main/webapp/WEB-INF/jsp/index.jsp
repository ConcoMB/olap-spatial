<%@ page contentType="text/html"%>
<%@ page pageEncoding="iso-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
<p>El archivo de configuración de la base de datos se encuentra en la carpeta OLAP\WEB-INF\classes y se llama setup.properties, por favor introduzca en él el nombre de usuario, contraseña y url de la base de datos</p>
<p>El archivo multidim de entrada se debe ubicar en la raíz del servidor y se debe llamar "input.xml"</p>
<p>El archivo geomondrian de salida lo encontrará en la carpeta raíz del servidor y se llamará "geomondrian.xml"</p>

	<div id="content">
	<form action="createAutomaticOutput" method="POST">
		<fieldset id="marcoLogin">
			<h2>Cree el MDX XML creando la tabla automáticamente en la base de datos proporcionada pulsando el siguiente botón</h2>
			<td><input type="submit" value="Crear automáticamente" /></td>
		</fieldset>
	</form>
		
	<form action="selectTable" method="POST">
		<fieldset id="marcoLogin">
			<h2>O cree el MDX XML seleccionando la tabla deseada de la base de datos proporcionada en el archivo de configuración</h2>
			<td><input type="submit" value="Seleccionar tabla" /></td>
		</fieldset>
	</form>
	</div>
</body>
</html>