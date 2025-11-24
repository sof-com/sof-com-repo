package servidor;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.swagger.ui.SwaggerUiConfig;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

/**
 * Software de Comunicaciones 25/26
 * <p>
 * Esta es la clase que construye el servidor.
 * </p>
 * <p>
 * NO SE DEBEN REALIZAR MODIFICACIONES EN ESTA CLASE.
 * </p>
 */
public class Servidor {
	
	public static void main(String[] args) {
		final JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
		factoryBean.setResourceClasses(AdaptadorOperacionesCalculadoraREST.class);
		factoryBean.setAddress("http://localhost:8080/");
		factoryBean.setProviders(Arrays.asList(new Object[] {new JacksonJsonProvider()}));
		enableOpenAPI(factoryBean);
		final Server server = factoryBean.create();
	}
	private static void enableOpenAPI(JAXRSServerFactoryBean factoryBean) {
		final OpenApiFeature feature = new OpenApiFeature();
		feature.setTitle("API REST de la práctica 4 de SOFCOM 25/26");
		feature.setVersion("1.0");
		feature.setContactName(System.getProperty("user.name")
				+ " (" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ")");
		feature.setDescription("Esta es la API REST de la práctica 4. " + feature.getContactName());
		feature.setSwaggerUiConfig(new SwaggerUiConfig().url("/openapi.json").queryConfigEnabled(false));
		factoryBean.getFeatures().add(feature);
	}
}
