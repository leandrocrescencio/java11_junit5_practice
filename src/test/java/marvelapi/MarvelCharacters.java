package marvelapi;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.codeborne.selenide.logevents.SelenideLogger;

import dataprovider.MarvelCharacterProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.HashGenerator;
import utils.PropertiesUtils;
import utils.StaticValues;

@Epic("Teste de dados na api que prove detalhamentos geral sobre os personagens da marvel")
@Feature("Api de Personagens")
public class MarvelCharacters {
		
    @Test
    @Order(1)
    @DisplayName("Verifica disponibilidade Serviço")
    @Description("Verifica disponibilidade Serviço")
    @Severity(SeverityLevel.CRITICAL)
    @Story("O serviço deve estar disponível para executar as validaçõess das APIs")
    public void test_01() {
			given()
				.port(443)
				.queryParam("apikey", PropertiesUtils.getValue("apiKey"))
				.queryParam("hash", HashGenerator.generate(StaticValues.TIMESTAMP, PropertiesUtils.getValue("privateKey"),  PropertiesUtils.getValue("apiKey")))
				.queryParam("ts", StaticValues.TIMESTAMP)
				.baseUri(PropertiesUtils.getValue("baseuri"))
				.basePath(PropertiesUtils.getValue("path"))
				.when()
				.get()
				.then()
				.statusCode(200);

	}
	
    
    @ParameterizedTest(name = "{index} => personagem válido = ''{0}''")
	@Order(2)
    @DisplayName("Teste que valida se nome é Personagem Marvel")
    @Description("Teste que valida se nome é Personagem Marvel")
    @Severity(SeverityLevel.NORMAL)
    @Story("O serviço deve estar disponível para executar as validaçõess das APIs")
    @ValueSource(strings = { "Spider-Man", "Hulk", "Thor", "Iron Man", "Captain America", "Black Widow", "Black Panther", "Hawkeye", "Doctor Strange", "Thanos" } )
    public void test_02(String person) {
		Response response = 
			given()
				.accept(ContentType.JSON)
				.port(443)
				.queryParam("name", person)
				.queryParam("apikey",  PropertiesUtils.getValue("apiKey"))
				.queryParam("hash", HashGenerator.generate(StaticValues.TIMESTAMP, PropertiesUtils.getValue("privateKey"),  PropertiesUtils.getValue("apiKey")))
				.queryParam("ts",  StaticValues.TIMESTAMP)
				.baseUri(PropertiesUtils.getValue("baseuri"))
				.basePath(PropertiesUtils.getValue("path"))
				.when()
				.get()
				.then()
				.contentType(ContentType.JSON)
				.statusCode(200)
				.extract()
	            .response();
			
		final String result = response.jsonPath().get("data.results.name[0]");
		assertTrue((result).contains(person));
		assertEquals(person, result);
		
	}
    
    
    @ParameterizedTest(name = "{index} => Id de personagem encontrado = ''{0}''")
	@Order(3)
    @DisplayName("Busca o Personagem Marvel pelo id")
    @Description("Busca o Personagem Marvel pelo id")
    @Severity(SeverityLevel.NORMAL)
    @Story("O serviço deve estar disponível para executar as validaçõess das APIs e o personagem tem que ser válido")
    @ArgumentsSource(MarvelCharacterProvider.class)
    public void test_03(String[] args) {
		Response response = 
			given()
				.accept(ContentType.JSON)
				.port(443)
				.queryParam("apikey",  PropertiesUtils.getValue("apiKey"))
				.queryParam("hash", HashGenerator.generate(StaticValues.TIMESTAMP, PropertiesUtils.getValue("privateKey"),  PropertiesUtils.getValue("apiKey")))
				.queryParam("ts",  StaticValues.TIMESTAMP)
				.baseUri(PropertiesUtils.getValue("baseuri"))
				.basePath(PropertiesUtils.getValue("path"))
				.pathParam("charid", args[1])
				.when()
				.get("/{charid}")
				.then()
				.contentType(ContentType.JSON)
				.statusCode(200)
				.extract()
	            .response();
	
		final int result2 = response.jsonPath().get("data.results.id[0]");
		assertEquals(Integer.parseInt(args[1]),result2);

	}
   
    
    @ParameterizedTest(name = "{index} => Id de personagem encontrado e histórias calculadas = ''{0}''")
	@Order(4)
    @DisplayName("Traz o número total de histórias em Quadrinhos de Personagem Marvel pelo id")
    @Description("Traz o número total de histórias em Quadrinhos de Personagem Marvel pelo id")
    @Severity(SeverityLevel.NORMAL)
    @Story("O serviço deve estar disponível para executar as validaçõess das APIs e o personagem tem que ser válido")
    @ArgumentsSource(MarvelCharacterProvider.class)
    public void test_04(String[] args) {
		Response response = 
			given()
				.accept(ContentType.JSON)
				.port(443)
				.queryParam("apikey",  PropertiesUtils.getValue("apiKey"))
				.queryParam("hash", HashGenerator.generate(StaticValues.TIMESTAMP, PropertiesUtils.getValue("privateKey"),  PropertiesUtils.getValue("apiKey")))
				.queryParam("ts",  StaticValues.TIMESTAMP)
				.queryParam("format",  "comic")
				.baseUri(PropertiesUtils.getValue("baseuri"))
				.basePath(PropertiesUtils.getValue("path"))
				.pathParam("charid", args[1])
				.when()
				.get("/{charid}/comics")
				.then()
				.contentType(ContentType.JSON)
				.statusCode(200)
				.extract()
	            .response();
	
		final int result3 = response.jsonPath().get("data.total");
		assertEquals(Integer.parseInt(args[2]),result3);
		
	
	}
	
	
    @ParameterizedTest(name = "{index} => Id de personagem com foto encontrado = ''{0}''")
	@Order(5)
    @DisplayName("Valida e traz o thumbnail do Personagem Marvel pelo id")
    @Description("Valida e traz o thumbnail do Personagem Marvel pelo id")
    @Severity(SeverityLevel.NORMAL)
    @Story("O serviço deve estar disponível para executar as validaçõess das APIs e o personagem tem que ser válido")
    @ArgumentsSource(MarvelCharacterProvider.class)
    public void test_05(String[] args) {
		Response response = 
			given()
				.accept(ContentType.JSON)
				.port(443)
				.queryParam("apikey",  PropertiesUtils.getValue("apiKey"))
				.queryParam("hash", HashGenerator.generate(StaticValues.TIMESTAMP, PropertiesUtils.getValue("privateKey"),  PropertiesUtils.getValue("apiKey")))
				.queryParam("ts",  StaticValues.TIMESTAMP)
				.queryParam("format",  "comic")
				.baseUri(PropertiesUtils.getValue("baseuri"))
				.basePath(PropertiesUtils.getValue("path"))
				.pathParam("charid", args[1])
				.when()
				.get("/{charid}")
				.then()
				.contentType(ContentType.JSON)
				.statusCode(200)
				.extract()
	            .response();
	
		final String resultpath = response.jsonPath().get("data.results.thumbnail.path[0]");
		final String ext = response.jsonPath().get("data.results.thumbnail.extension[0]");
		assertEquals(resultpath +"."+ ext, args[3]);

	}
	
    @BeforeAll
    static void setuplog() {
		SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
	}
	
	@AfterAll
	static void tearDown() {
	    SelenideLogger.removeListener("AllureSelenide");
	}	


}