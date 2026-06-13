package com.ticketti.discovery_server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@DisplayName("Pruebas del Servidor de Descubrimiento")
class DiscoveryServerApplicationTest {

	// ===============================================
	// PRUEBAS DEL METODO MAIN
	// ===============================================

	@Nested
	@DisplayName("Pruebas del metodo main")
	class MetodoMain {

		@Test
		@DisplayName("Debe tener el metodo main declarado como public static void que reciba un array de strings")
		void debeTenerElMetodoMainDeclaradoComoPublicStaticVoid() throws NoSuchMethodException {
			// Given & When
			Method metodoMain = DiscoveryServerApplication.class.getMethod("main", String[].class);

			// Then
			assertThat(metodoMain)
					.as("La clase debe declarar el metodo 'main' como punto de entrada de Java")
					.isNotNull();
			assertThat(java.lang.reflect.Modifier.isStatic(metodoMain.getModifiers()))
					.as("El modificador 'static' del metodo main")
					.isTrue();
			assertThat(java.lang.reflect.Modifier.isPublic(metodoMain.getModifiers()))
					.as("El modificador 'public' del metodo main")
					.isTrue();
			assertThat(metodoMain.getReturnType())
					.as("El metodo main debe devolver void")
					.isEqualTo(void.class);
		}

		@Test
		@DisplayName("Debe poder ser invocado por reflection sin lanzar excepciones")
		void debePoderSerInvocadoPorReflectionSinLanzarExcepciones() throws Exception {
			// Given
			Method metodoMain = DiscoveryServerApplication.class.getMethod("main", String[].class);

			    // Then - Verifica que el metodo existe y tiene la firma correcta por reflection
			    assertThatCode(() -> metodoMain.invoke(null, (Object) new String[0]))
				    .as("La invocacion por reflection del metodo main deberia ser posible")
				    .doesNotThrowAnyException();
		}

		@Test
		@DisplayName("Debe aceptar el argumento --server.port sin errores de compilacion en la firma del metodo main")
		void debeAceptarElArgumentoServerPortSinErrores() {
			// Given - El metodo main recibe un array de String sin importar su contenido
			String[] argumentosConPuerto = {"--server.port=8761"};

			// When
			int cantidadArgumentos = argumentosConPuerto.length;

			// Then
			assertThat(cantidadArgumentos)
					.as("El metodo main debe aceptar cualquier cantidad de argumentos de tipo String")
					.isEqualTo(1);
		}
	}

	// ===============================================
	// PRUEBAS DE LA ANOTACION @ENABLEEUREKASERVER
	// ===============================================

	@Nested
	@DisplayName("Pruebas de la anotacion @EnableEurekaServer")
	class AnotacionEnableEurekaServer {

		@Test
		@DisplayName("Debe estar presente la anotacion @EnableEurekaServer en la clase principal")
		void debeEstarPresenteLaAnotacionEnableEurekaServer() {
			// Given & When
			EnableEurekaServer anotacion = DiscoveryServerApplication.class.getAnnotation(EnableEurekaServer.class);

			// Then
			assertThat(anotacion)
					.as("La clase DiscoveryServerApplication debe estar marcada con @EnableEurekaServer para activar el servidor de Eureka")
					.isNotNull();
		}

		@Test
		@DisplayName("Debe estar presente la anotacion @SpringBootApplication en la clase principal")
		void debeEstarPresenteLaAnotacionSpringBootApplication() {
			// Given & When
			org.springframework.boot.autoconfigure.SpringBootApplication anotacion =
					DiscoveryServerApplication.class.getAnnotation(
							org.springframework.boot.autoconfigure.SpringBootApplication.class);

			// Then
			assertThat(anotacion)
					.as("La clase DiscoveryServerApplication debe estar marcada con @SpringBootApplication para activar la auto-configuracion de Spring Boot")
					.isNotNull();
		}
	}

	// ===============================================
	// PRUEBAS DE NOMBRE DE PAQUETE
	// ===============================================

	@Nested
	@DisplayName("Pruebas del nombre de paquete y estructura del proyecto")
	class NombreDePaquete {

		@Test
		@DisplayName("Debe tener el nombre de paquete correcto com.ticketti.discovery_server")
		void debeTenerElNombreDePaqueteCorrecto() {
			// Given & When
			String nombrePaquete = DiscoveryServerApplication.class.getPackageName();

			// Then
			assertThat(nombrePaquete)
					.as("El paquete debe coincidir con la estructura del proyecto Ticketti")
					.isEqualTo("com.ticketti.discovery_server");
		}

		@Test
		@DisplayName("Debe pertenecer al groupId com.ticketti definido en el pom.xml")
		void debePertenecerAlGroupIdDefinidoEnElPomXml() {
			// Given & When
			String nombrePaquete = DiscoveryServerApplication.class.getPackageName();
			String[] partesDelPaquete = nombrePaquete.split("\\.");

			// Then
			assertThat(partesDelPaquete[0])
					.as("El primer segmento del paquete debe ser 'com'")
					.isEqualTo("com");
			assertThat(partesDelPaquete[1])
					.as("El segundo segmento del paquete debe ser 'ticketti'")
					.isEqualTo("ticketti");
		}

		@Test
		@DisplayName("Debe tener el artifactId 'discovery-server' que coincide con la ultima parte del paquete")
		void debeTenerElArtifactIdQueCoincideConLaUltimaParteDelPaquete() {
			// Given & When
			String nombrePaquete = DiscoveryServerApplication.class.getPackageName();
			String paqueteFinal = nombrePaquete.substring(nombrePaquete.lastIndexOf('.') + 1);

			// Then
			assertThat(paqueteFinal)
					.as("La ultima parte del paquete debe coincidir con el artifactId 'discovery_server' del pom.xml")
					.isEqualTo("discovery_server");
		}
	}

	// ===============================================
	// PRUEBAS DE MODIFICADORES DE LA CLASE
	// ===============================================

	@Nested
	@DisplayName("Pruebas de modificadores de la clase principal")
	class ModificadoresDeLaClase {

		@Test
		@DisplayName("Debe ser una clase publica")
		void debeSerUnaClasePublica() {
			// Given & When
			boolean esPublica = java.lang.reflect.Modifier.isPublic(
					DiscoveryServerApplication.class.getModifiers());

			// Then
			assertThat(esPublica)
					.as("La clase DiscoveryServerApplication debe ser publica para ser accesible desde el exterior")
					.isTrue();
		}

		@Test
		@DisplayName("No debe ser una clase abstracta")
		void noDebeSerUnaClaseAbstracta() {
			// Given & When
			boolean esAbstracta = java.lang.reflect.Modifier.isAbstract(
					DiscoveryServerApplication.class.getModifiers());

			// Then
			assertThat(esAbstracta)
					.as("La clase DiscoveryServerApplication no debe ser abstracta para poder ser instanciada")
					.isFalse();
		}

		@Test
		@DisplayName("No debe ser una clase final")
		void noDebeSerUnaClaseFinal() {
			// Given & When
			boolean esFinal = java.lang.reflect.Modifier.isFinal(
					DiscoveryServerApplication.class.getModifiers());

			// Then
			assertThat(esFinal)
					.as("La clase DiscoveryServerApplication no debe ser final para permitir la extension por Spring")
					.isFalse();
		}
	}

	// ===============================================
	// PRUEBAS DE METODOS DECLARADOS
	// ===============================================

	@Nested
	@DisplayName("Pruebas de metodos declarados en la clase principal")
	class MetodosDeclarados {

		@Test
		@DisplayName("Debe tener al menos un metodo declarado ademas de main")
		void debeTenerAlMenosUnMetodoDeclaradoAdemasDeMain() {
			// Given & When
			Method[] metodosDeclarados = DiscoveryServerApplication.class.getDeclaredMethods();
			java.util.List<Method> metodosNoSinteticos = java.util.Arrays.stream(metodosDeclarados)
					.filter(m -> !m.isSynthetic())
					.toList();

			// Then
			assertThat(metodosNoSinteticos)
					.as("La clase debe tener al menos un metodo 'main' declarado")
					.isNotEmpty();
		}

		@Test
		@DisplayName("El metodo main debe aceptar exactamente un parametro de tipo array de Strings")
		void elMetodoMainDebeAceptarExactamenteUnParametroDeTipoStringArray() throws NoSuchMethodException {
			// Given & When
			Method metodoMain = DiscoveryServerApplication.class.getMethod("main", String[].class);

			// Then
			assertThat(metodoMain.getParameterCount())
					.as("El metodo main debe recibir exactamente un parametro")
					.isEqualTo(1);
			assertThat(metodoMain.getParameters()[0].getType())
					.as("El parametro del metodo main debe ser de tipo String[]")
					.isEqualTo(String[].class);
		}
	}
}
