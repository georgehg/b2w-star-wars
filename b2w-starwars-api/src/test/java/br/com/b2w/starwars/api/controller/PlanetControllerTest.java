package br.com.b2w.starwars.api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.domain.Terrain;
import br.com.b2w.starwars.api.dto.PlanetDto;
import br.com.b2w.starwars.api.service.PlanetService;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanetController.class)
@AutoConfigureJsonTesters
@Import(ControllerConfig.class)
public class PlanetControllerTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets/index/v1");

	@MockBean
	private PlanetService planetService;

	@Autowired
    private WebApplicationContext context;

	@Autowired
	private JacksonTester<PlanetDto> json;

	private MockMvc mvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			  MediaType.APPLICATION_JSON.getSubtype(),
			  Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {
		// Prepare Spring Restdocs with default directory and preprocessors
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
						.apply(documentationConfiguration(this.restDocumentation))
						.alwaysDo(MockMvcResultHandlers.print())
						.build();
	}

	@Test
	public void createPlanet() throws Exception {
		//Arrange
		PlanetDto inputDto = PlanetDto.of(null, "Alderaan", Sets.newSet("temperate"), Sets.newSet("grasslands"));
		Planet resultEntity = Planet.of("Alderaan", Climate.init(Sets.newSet("temperate")), Terrain.init(Sets.newSet("grasslands")));
		resultEntity.setId("5afeee4f5cf07b3524b39aad");

		when(this.planetService.newPlanet(anyObject())).thenReturn(resultEntity);

		//Act
		ResultActions result = this.mvc.perform(post("/api/v1/planets").contextPath("/api/v1")
									.contentType(contentType)
									.content(json.write(inputDto).getJson()));

		//Assert
		ResultActions assertResult = result.andExpect(status().isCreated())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("id", is("5afeee4f5cf07b3524b39aad")))
				.andExpect(jsonPath("name", is("Alderaan")))
				.andExpect(jsonPath("climate[0]", is("temperate")))
				.andExpect(jsonPath("terrain[0]", is("grasslands")));

		//Document
		assertResult.andDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
							requestFields(
								fieldWithPath("name").description("The Planet name"),
								fieldWithPath("climate").description("The Planet climate list"),
								fieldWithPath("terrain").description("The Planet terrain list")),
							responseFields(
								fieldWithPath("id").description("The Planet Identity"),
								fieldWithPath("name").description("The Planet name"),
								fieldWithPath("climate").description("The Planet climate list"),
								fieldWithPath("terrain").description("The Planet terrain list"))));
	}

	@Test
	public void getPlanetsList() throws IOException, Exception {
		//Arrange
		List<Planet> planetList =
				Arrays.asList(
						Planet.of("Tatooine", Climate.init(Sets.newSet("arid")), Terrain.init(Sets.newSet("desert"))),
						Planet.of("Alderaan", Climate.init(Sets.newSet("temperate")), Terrain.init(Sets.newSet("grasslands"))));

		when(this.planetService.getPlanetsList()).thenReturn(planetList);

		//Act
		ResultActions result = this.mvc.perform(get("/api/v1/planets").contextPath("/api/v1").contentType(contentType));

		//Assert
		result.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("[*]", hasSize(2)))
				.andExpect(jsonPath("[0].name", is("Tatooine")))
				.andExpect(jsonPath("[0].climate[0]", is("arid")))
				.andExpect(jsonPath("[0].terrain[0]", is("desert")))
				.andExpect(jsonPath("[1].name", is("Alderaan")))
				.andExpect(jsonPath("[1].climate[0]", is("temperate")))
				.andExpect(jsonPath("[1].terrain[0]", is("grasslands")));

		//Document
		result.andDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
							responseFields(
								fieldWithPath("[]").type(JsonFieldType.ARRAY).description("List of all registered Planets"))));
	}

	@Test
	public void getPlanetById() throws IOException, Exception {
		//Arrange
		String planetId = "5afeee4f5cf07b3524b39ccf";
		Planet resultEntity = Planet.of("Tatooine", Climate.init(Sets.newSet("arid")), Terrain.init(Sets.newSet("desert")));
		resultEntity.setId("5afeee4f5cf07b3524b39aad");
		when(this.planetService.getPlanet(planetId)).thenReturn(resultEntity);

		//Act
		ResultActions result = this.mvc.perform(get("/api/v1/planets/{planetId}", planetId).contextPath("/api/v1")
									.contentType(contentType));

		//Assert
		result.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("name", is("Tatooine")))
				.andExpect(jsonPath("climate[0]", is("arid")))
				.andExpect(jsonPath("terrain[0]", is("desert")));

		//Document
		result.andDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						pathParameters(
							parameterWithName("planetId").description("The Planet Identity")),
						responseFields(
							fieldWithPath("id").description("The Planet Identity"),
							fieldWithPath("name").description("The Planet name"),
							fieldWithPath("climate").description("The Planet climate list"),
							fieldWithPath("terrain").description("The Planet terrain list"))));

	}

	@Test
	public void searchPlanet() throws IOException, Exception {
		//Arrange
		String planetName = "Alderaan";
		Planet resultEntity = Planet.of(planetName, Climate.init(Sets.newSet("temperate")), Terrain.init(Sets.newSet("grasslands")));
		resultEntity.setId("5afeee4f5cf07b3524b39aad");
		when(this.planetService.searchPlanet(planetName)).thenReturn(resultEntity);

		//Act
		ResultActions result = this.mvc.perform(get("/api/v1/planets/search?name={name}", planetName).contextPath("/api/v1")
									.contentType(contentType));

		//Assert
		result.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("name", is(planetName)))
				.andExpect(jsonPath("climate[0]", is("temperate")))
				.andExpect(jsonPath("terrain[0]", is("grasslands")));

		//Document
		result.andDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						requestParameters(
							parameterWithName("name").description("The Planet name")),
						responseFields(
							fieldWithPath("id").description("The Planet Identity"),
							fieldWithPath("name").description("The Planet name"),
							fieldWithPath("climate").description("The Planet climate list"),
							fieldWithPath("terrain").description("The Planet terrain list"))));
	}

	@Test
	public void deletePlanet() throws Exception {
		//Arrange
		String planetId = "5afeee4f5cf07b3524b39ccf";
		//when(this.planetService.remove(planetId)).thenReturn(null);

		//Act
		ResultActions result = this.mvc.perform(delete("/api/v1/planets/{planetId}", planetId).contextPath("/api/v1"));

		//Assert
		result.andExpect(status().isNoContent());

		//Document
		result.andDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						pathParameters(
							parameterWithName("planetId").description("The Planet Identity"))));
	}

}