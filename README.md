# About
- [https://en.m.wikipedia.org/wiki/Mandelbrot_set](https://en.m.wikipedia.org/wiki/Mandelbrot_set)
- desktop Mandelbrot Set viewer with zooming, a zoom history, coloring the image in different ways with presets or custom colors and smooth colors
- has a limited zoom because of double precision limitations, but that can be solved by applying [perturbation theory](https://en.m.wikipedia.org/wiki/Perturbation_theory) and using arbitrary precision numbers such as BigDecimal's in Java, although the rendering would be much slower
- [live demo](https://drive.google.com/file/d/1uS5Z0hYD2HUGPr19evmQtBnb5jpwutZq/view)

# How to use
If you have [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and up installed you can:
- download the .jar executable from the release  
**OR**
- compile and run the repository as a Maven project with your method of choice.</br></br>
`mvn clean compile exec:java` to run from a command.
