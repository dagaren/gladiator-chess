README
======

¿Qué es gladiator?
------------------

Gladiator es un motor de ajedrez escrito en Java.y distribuido como un proyecto maven para facilitar 
su construcción y gestión de dependencias.

Es compatible con el protocolo XBoard/Winboard v2, por lo que puede ser utilizado desde cualquier
interfaz gráfica de ajedrez que soporte motores de este tipo, como XBoard, Arena, etc.

El código fuente de Gladiator está disponible bajo los terminos de la licencia open source GPL v3.

Requisitos
-----------

Para poder ejecutar Gladiator es necesario que la máquina tenga instalada una máquina virtual de 
Java SE6, que es la versión mínima requerida.

En el caso de querer generar la aplicación a partir de la última versión del código fuente disponible
en Github será necesario tener instalado dos herramientas: *git* para poder descargar el repositorio 
y *Maven* para llevar a cabo la construcción del proyecto.

Instalación
-----------

En primer lugar se crea el directorio en el que se desee que se almacenen el repositorio
de Gladiator temporalmente  (por ejemplo, */tmp/gladiator/*), y se establece como el 
directorio de trabajo:

```bash
$ mkdir /tmp/gladiator && cd /tmp/gladiator
```

Una vez heccho esto el siguiente paso consiste en descargar el repositorio utilizando *git*:


```bash
$ git clone git://github.com/dagaren/gladiator-chess.git
```

Después se abre con un editor de textos el fichero *pom.xml* que se encuentra dentro del
directorio en el que se ha descargado el repositorio (*/tmp/gladiator/*) y se localizan
las líneas siguientes:

```xml
<properties>
  <installDir>/directorio/de/instalacion</installDir>
</properties>
```

Se reemplaza la cadena */directrio/de/instalacion* por la ruta absoluta del directorio
en el que se quiea instalar la apliación, se guardan los cambios en el fichero, y se ejecuta
el siguiente comando (desde el directorio del repositorio):

```bash
$ mvn package
```

Con esto se lleva ha cabo la compilación y generación de los ficheros ejecutables y de configuración
necesarios. Si todo funciona correctamente al final debe aparecer por consola un mensaje como el siguiente:


```bash
[INFO] ------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL 
[INFO] ------------------------------------------------------------------
[INFO] Total time: 7 seconds 
[INFO] Finished at: Tue Aug 07 08:56:27 CEST 2012 
[INFO] Final Memory: 26M/240M 
[INFO] ------------------------------------------------------------------
```

Tras esto, dentro del directorio que se haya especificado para la instalación se deben encontrar los ficheros
ejecutables, de configuración, librerías y scripts necesarios para la ejecución de Gladiator.

Ejecución
----------
**TODO: Rellenar con explicación de ejecución desde línea de comandos y junto interfaces gráficas Xboard y Arena**


Contacto
--------
- Email: dagaren@gmail.com
- Twitter: @dagaren
