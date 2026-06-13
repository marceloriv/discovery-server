package com.ticketti.discovery_server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.netflix.eureka.server.EurekaController;
import org.springframework.cloud.netflix.eureka.server.EurekaDashboardProperties;
import org.springframework.cloud.netflix.eureka.server.EurekaProperties;
import org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean;
import org.springframework.cloud.netflix.eureka.server.InstanceRegistry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeastOnce;

@DisplayName("Pruebas Unitarias con Mockito del Servidor de Descubrimiento")
class DiscoveryServerUnitTest {

	// ===============================================
	// PRUEBAS MOCK DE EUREKA SERVER CONFIG BEAN
	// ===============================================

	@Nested
	@DisplayName("Pruebas de mock de EurekaServerConfigBean con Mockito")
	class MockDeEurekaServerConfigBean {

		@Test
		@DisplayName("Debe simular la URL del servidor de Eureka usando Mockito")
		void debeSimularLaUrlDelServidorDeEurekaUsandoMockito() {
			// Given - Se crea un mock de EurekaServerConfigBean con Mockito
			EurekaServerConfigBean configuracionMock = mock(EurekaServerConfigBean.class);
			String urlEsperada = "http://localhost:8761/eureka/";

			// When - Se define el comportamiento simulado del mock
			when(configuracionMock.getMyUrl()).thenReturn(urlEsperada);

			// Then - Se verifica que el mock devuelve el valor esperado
			assertThat(configuracionMock.getMyUrl())
					.as("La URL del servidor de Eureka simulada debe coincidir con el valor esperado")
					.isEqualTo(urlEsperada);
		}

		@Test
		@DisplayName("Debe devolver false por defecto cuando shouldEnableSelfPreservation es llamado sin configuracion previa")
		void debeDevolverFalsePorDefectoCuandoShouldEnableSelfPreservationEsLlamadoSinConfiguracion() {
			// Given
			EurekaServerConfigBean configuracionMock = mock(EurekaServerConfigBean.class);

			// When - Se llama sin definir previamente el comportamiento del metodo en el mock
			boolean resultado = configuracionMock.shouldEnableSelfPreservation();

			// Then - Mockito devuelve false por defecto para metodos que devuelven boolean
			assertThat(resultado)
					.as("Los metodos booleanos no configurados deben devolver false por defecto")
					.isFalse();
		}

		@Test
		@DisplayName("Debe simular shouldDisableDelta devolviendo true usando Mockito")
		void debeSimularShouldDisableDeltaDevolviendoTrueUsandoMockito() {
			// Given
			EurekaServerConfigBean configuracionMock = mock(EurekaServerConfigBean.class);

			// When
			when(configuracionMock.shouldDisableDelta()).thenReturn(true);

			// Then
			assertThat(configuracionMock.shouldDisableDelta())
					.as("shouldDisableDelta debe devolver el valor simulado true")
					.isTrue();
		}

		@Test
		@DisplayName("Debe simular shouldLogIdentityHeaders devolviendo false usando Mockito")
		void debeSimularShouldLogIdentityHeadersDevolviendoFalseUsandoMockito() {
			// Given
			EurekaServerConfigBean configuracionMock = mock(EurekaServerConfigBean.class);

			// When
			when(configuracionMock.shouldLogIdentityHeaders()).thenReturn(false);

			// Then
			assertThat(configuracionMock.shouldLogIdentityHeaders())
					.as("shouldLogIdentityHeaders debe devolver el valor simulado false")
					.isFalse();
		}
	}

	// ===============================================
	// PRUEBAS MOCK DE EUREKA PROPERTIES
	// ===============================================

	@Nested
	@DisplayName("Pruebas de mock de EurekaProperties con Mockito")
	class MockDeEurekaProperties {

		@Test
		@DisplayName("Debe simular el metodo getDatacenter de EurekaProperties devolviendo 'default' usando Mockito")
		void debeSimularElMetodoGetDatacenterDeEurekaPropertiesDevolviendoDefault() {
			// Given
			EurekaProperties propiedadesMock = mock(EurekaProperties.class);

			// When
			when(propiedadesMock.getDatacenter()).thenReturn("default");

			// Then
			assertThat(propiedadesMock.getDatacenter())
					.as("El datacenter simulado debe ser 'default'")
					.isEqualTo("default");
		}

		@Test
		@DisplayName("Debe simular el metodo getEnvironment de EurekaProperties devolviendo 'test' usando Mockito")
		void debeSimularElMetodoGetEnvironmentDeEurekaPropertiesDevolviendoTest() {
			// Given
			EurekaProperties propiedadesMock = mock(EurekaProperties.class);

			// When
			when(propiedadesMock.getEnvironment()).thenReturn("test");

			// Then
			assertThat(propiedadesMock.getEnvironment())
					.as("El entorno simulado de Eureka debe ser 'test'")
					.isEqualTo("test");
		}
	}

	// ===============================================
	// PRUEBAS MOCK DE EUREKA DASHBOARD PROPERTIES
	// ===============================================

	@Nested
	@DisplayName("Pruebas de mock de EurekaDashboardProperties con Mockito")
	class MockDeEurekaDashboardProperties {

		@Test
		@DisplayName("Debe simular el metodo getPath de EurekaDashboardProperties devolviendo '/' usando Mockito")
		void debeSimularElMetodoGetPathDeEurekaDashboardPropertiesDevolviendoSlash() {
			// Given
			EurekaDashboardProperties propiedadesDashboardMock = mock(EurekaDashboardProperties.class);

			// When
			when(propiedadesDashboardMock.getPath()).thenReturn("/");

			// Then
			assertThat(propiedadesDashboardMock.getPath())
					.as("El path simulado del dashboard debe ser '/'")
					.isEqualTo("/");
		}

		@Test
		@DisplayName("Debe simular el metodo isEnabled de EurekaDashboardProperties devolviendo true usando Mockito")
		void debeSimularElMetodoIsEnabledDeEurekaDashboardPropertiesDevolviendoTrue() {
			// Given
			EurekaDashboardProperties propiedadesDashboardMock = mock(EurekaDashboardProperties.class);

			// When
			when(propiedadesDashboardMock.isEnabled()).thenReturn(true);

			// Then
			assertThat(propiedadesDashboardMock.isEnabled())
					.as("El dashboard simulado debe estar habilitado")
					.isTrue();
		}

		@Test
		@DisplayName("Debe devolver null por defecto cuando getPath es llamado sin configuracion previa")
		void debeDevolverNullPorDefectoCuandoGetPathEsLlamadoSinConfiguracion() {
			// Given
			EurekaDashboardProperties propiedadesDashboardMock = mock(EurekaDashboardProperties.class);

			// When - Se llama sin definir previamente el comportamiento del metodo en el mock
			String path = propiedadesDashboardMock.getPath();

			// Then - Mockito devuelve null por defecto para metodos que devuelven objetos
			assertThat(path)
					.as("El path no configurado debe devolver null por defecto")
					.isNull();
		}
	}

	// ===============================================
	// PRUEBAS MOCK DE EUREKA CONTROLLER
	// ===============================================

	@Nested
	@DisplayName("Pruebas de mock de EurekaController con Mockito")
	class MockDeEurekaController {

		@Test
		@DisplayName("Debe simular el metodo status de EurekaController devolviendo 'UP' usando Mockito")
		void debeSimularElMetodoStatusDeEurekaControllerDevolviendoUp() {
			// Given
			EurekaController controladorMock = mock(EurekaController.class);

			// When
			when(controladorMock.status(null, null)).thenReturn("UP");

			// Then
			assertThat(controladorMock.status(null, null))
					.as("El estado simulado del servidor desde EurekaController debe ser 'UP'")
					.isEqualTo("UP");
		}

		@Test
		@DisplayName("Debe simular el metodo lastn de EurekaController devolviendo una representacion JSON vacia usando Mockito")
		void debeSimularElMetodoLastnDeEurekaControllerDevolviendoJsonVacio() {
			// Given
			EurekaController controladorMock = mock(EurekaController.class);

			// When
			when(controladorMock.lastn(null, null)).thenReturn("[]");

			// Then
			assertThat(controladorMock.lastn(null, null))
					.as("La lista de ultimas instancias simulada debe ser un array JSON vacio")
					.isEqualTo("[]");
		}

		@Test
		@DisplayName("Debe simular el metodo status devolviendo 'DOWN' usando Mockito")
		void debeSimularElMetodoStatusDeEurekaControllerDevolviendoDown() {
			// Given
			EurekaController controladorMock = mock(EurekaController.class);

			// When
			when(controladorMock.status(null, null)).thenReturn("DOWN");

			// Then
			assertThat(controladorMock.status(null, null))
					.as("El estado simulado del servidor desde EurekaController debe ser 'DOWN'")
					.isEqualTo("DOWN");
		}
	}

	// ===============================================
	// PRUEBAS MOCK DE INSTANCE REGISTRY
	// ===============================================

	@Nested
	@DisplayName("Pruebas de mock de InstanceRegistry con Mockito")
	class MockDeInstanceRegistry {

		@Test
		@DisplayName("Debe crear un mock de InstanceRegistry correctamente usando Mockito")
		void debeCrearUnMockDeInstanceRegistryCorrectamenteUsandoMockito() {
			// Given & When
			InstanceRegistry registroMock = mock(InstanceRegistry.class);

			// Then
			assertThat(registroMock)
					.as("El mock de InstanceRegistry debe crearse correctamente y no ser nulo")
					.isNotNull();
		}
	}

	// ===============================================
	// PRUEBAS DE INTERACCION CON MOCKITO
	// ===============================================

	@Nested
	@DisplayName("Pruebas de interaccion con Mockito")
	class InteraccionConMockito {

		@Test
		@DisplayName("Debe verificar que el mock de EurekaServerConfigBean es llamado al menos una vez con getMyUrl")
		void debeVerificarQueElMockDeEurekaServerConfigBeanEsLlamadoConGetMyUrl() {
			// Given
			EurekaServerConfigBean configuracionMock = mock(EurekaServerConfigBean.class);
			when(configuracionMock.getMyUrl()).thenReturn("http://localhost:8761/eureka/");

			// When
			configuracionMock.getMyUrl();

			// Then - Verifica que el metodo getMyUrl fue llamado exactamente una vez
			org.mockito.Mockito.verify(configuracionMock, org.mockito.Mockito.times(1)).getMyUrl();
		}

		@Test
		@DisplayName("Debe verificar que el mock de EurekaController es llamado con status y lastn")
		void debeVerificarQueElMockDeEurekaControllerEsLlamadoConStatusYLastn() {
			// Given
			EurekaController controladorMock = mock(EurekaController.class);
			when(controladorMock.status(null, null)).thenReturn("UP");
			when(controladorMock.lastn(null, null)).thenReturn("[]");

			// When
			controladorMock.status(null, null);
			controladorMock.lastn(null, null);

			// Then - Verifica que ambos metodos fueron llamados exactamente una vez
			org.mockito.Mockito.verify(controladorMock, org.mockito.Mockito.times(1)).status(null, null);
			org.mockito.Mockito.verify(controladorMock, org.mockito.Mockito.times(1)).lastn(null, null);
		}

		@Test
		@DisplayName("Debe permitir encadenar llamadas en un mock de EurekaServerConfigBean")
		void debePermitirEncadenarLlamadasEnUnMockDeEurekaServerConfigBean() {
			// Given
			EurekaServerConfigBean configuracionMock = mock(EurekaServerConfigBean.class);
			when(configuracionMock.shouldDisableDelta()).thenReturn(true);
			when(configuracionMock.shouldEnableSelfPreservation()).thenReturn(true);

			// When
			boolean sinDelta = configuracionMock.shouldDisableDelta();
			boolean conPreservacion = configuracionMock.shouldEnableSelfPreservation();

			// Then
			assertThat(sinDelta)
					.as("El mock con shouldDisableDelta debe devolver true")
					.isTrue();
			assertThat(conPreservacion)
					.as("El mock con shouldEnableSelfPreservation debe devolver true")
					.isTrue();
		}
	}

	// ===============================================
	// PRUEBAS DE VERIFICACION DE INTERACCIONES CON MOCKITO
	// ===============================================

	@Nested
	@DisplayName("Pruebas de verificacion de interacciones con Mockito")
	class VerificacionDeInteracciones {

		@Test
		@DisplayName("Debe verificar que el metodo getMyUrl fue llamado exactamente una vez en el mock de EurekaServerConfigBean")
		void debeVerificarQueElMetodoGetMyUrlFueLlamadoExactamenteUnaVez() {
			// Given
			EurekaServerConfigBean configuracionMock = mock(EurekaServerConfigBean.class);
			when(configuracionMock.getMyUrl()).thenReturn("http://localhost:8761/eureka/");

			// When
			configuracionMock.getMyUrl();

			// Then - verifica que el metodo fue llamado una sola vez
			verify(configuracionMock, times(1)).getMyUrl();
		}

		@Test
		@DisplayName("Debe verificar que el metodo status nunca fue llamado cuando no se invoca")
		void debeVerificarQueElMetodoStatusNuncaFueLlamadoCuandoNoSeInvoca() {
			// Given
			EurekaController controladorMock = mock(EurekaController.class);

			// Then - verifica que sin invocar, el metodo no fue llamado
			verify(controladorMock, never()).status(any(), any());
		}

		@Test
		@DisplayName("Debe verificar que el metodo getDatacenter fue llamado al menos una vez")
		void debeVerificarQueElMetodoGetDatacenterFueLlamadoAlMenosUnaVez() {
			// Given
			EurekaProperties propiedadesMock = mock(EurekaProperties.class);
			when(propiedadesMock.getDatacenter()).thenReturn("default");

			// When
			propiedadesMock.getDatacenter();
			propiedadesMock.getDatacenter();

			// Then - verifica que fue llamado al menos una vez
			verify(propiedadesMock, atLeastOnce()).getDatacenter();
		}

		@Test
		@DisplayName("Debe verificar que getPath fue llamado exactamente dos veces")
		void debeVerificarQueGetPathFueLlamadoExactamenteDosVeces() {
			// Given
			EurekaDashboardProperties propiedadesDashboardMock = mock(EurekaDashboardProperties.class);
			when(propiedadesDashboardMock.getPath()).thenReturn("/eureka");

			// When
			propiedadesDashboardMock.getPath();
			propiedadesDashboardMock.getPath();

			// Then - verifica que fue llamado exactamente dos veces
			verify(propiedadesDashboardMock, times(2)).getPath();
		}

		@Test
		@DisplayName("Debe verificar que status y lastn fueron llamados exactamente una vez cada uno")
		void debeVerificarQueStatusYLastnFueronLlamadosExactamenteUnaVezCadaUno() {
			// Given
			EurekaController controladorMock = mock(EurekaController.class);
			when(controladorMock.status(null, null)).thenReturn("UP");
			when(controladorMock.lastn(null, null)).thenReturn("[]");

			// When
			controladorMock.status(null, null);
			controladorMock.lastn(null, null);

			// Then
			verify(controladorMock, times(1)).status(null, null);
			verify(controladorMock, times(1)).lastn(null, null);
		}

		@Test
		@DisplayName("Debe verificar que dos llamadas iguales al metodo getMyUrl no cuentan como duplicadas")
		void debeVerificarQueDosLlamadasIgualesNoSeanContadasComoDuplicadas() {
			// Given
			EurekaServerConfigBean configuracionMock = mock(EurekaServerConfigBean.class);
			when(configuracionMock.getMyUrl()).thenReturn("http://localhost:8761/eureka/");

			// When
			configuracionMock.getMyUrl();
			configuracionMock.getMyUrl();

			// Then - debe registrar las dos llamadas, no una sola
			verify(configuracionMock, times(2)).getMyUrl();
		}
	}
}

