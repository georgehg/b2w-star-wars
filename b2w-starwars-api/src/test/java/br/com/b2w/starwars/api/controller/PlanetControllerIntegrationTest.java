package br.com.b2w.starwars.api.controller;

import br.com.b2w.starwars.api.domain.Film;
import br.com.b2w.starwars.api.dto.PlanetDto;
import br.com.b2w.starwars.api.repository.PlanetRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT )
@AutoConfigureJsonTesters
public class PlanetControllerIntegrationTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets/index/v1");

	@Autowired
    private WebApplicationContext context;

	@Autowired
	private JacksonTester<PlanetDto> json;

	@Autowired
	private PlanetRepository planetRepo;

	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	private MockMvc mvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			  MediaType.APPLICATION_JSON.getSubtype(),
			  Charset.forName("utf8"));
	
	private List<Film> films;
	
	private Boolean initialized = false;

	@Before
	public void setup() throws Exception {
		// Prepare Spring Restdocs with default directory and preprocessors
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
						.apply(documentationConfiguration(this.restDocumentation))
						.alwaysDo(MockMvcResultHandlers.print())
						.build();
		
		if (!initialized) {
			films = Arrays.asList(
					Film.of("A New Hope",
	                "George Lucas",
	                "Gary Kurtz, Rick McCallum",
	                formatter.parse("1977-05-25"),
	                "https://swapi.co/api/films/1/"),
					
					Film.of("Return of the Jedi",
	                "Richard Marquand",
	                "Howard G. Kazanjian, George Lucas, Rick McCallum",
	                formatter.parse("1983-05-25"),
	                "https://swapi.co/api/films/3/"),
					
					Film.of("Revenge of the Sith",
				 	"George Lucas",
	                "Rick McCallum",
	                formatter.parse("2005-05-19"),
	                "https://swapi.co/api/films/6/"));
			
			initialized = true;
		}
	}

	@After
	public void cleanDataBase() {
		planetRepo.deleteAll();
	}

	@Test
	public void createPlanet() throws Exception {
		//Arrange
		PlanetDto inputDto = PlanetDto.of(null, "Alderaan", Sets.newSet("temperate"), Sets.newSet("grasslands"), null);

		//Act
		ResultActions result =
				this.mvc.perform(post("/api/v1/planets").contextPath("/api/v1")
						.contentType(contentType)
						.content(json.write(inputDto).getJson()));

		//Assert
		result.andExpect(status().isCreated())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.name", is("Alderaan")))
				.andExpect(jsonPath("$.climate[0]", is("temperate")))
				.andExpect(jsonPath("$.terrain[0]", is("grasslands")));

		//Document
		result.andDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						responseHeaders(
							headerWithName("Location").description("URL for newly created Planet")),
						requestFields(
							fieldWithPath("name").description("The Planet name"),
							fieldWithPath("climate").description("The Planet climate list"),
							fieldWithPath("terrain").description("The Planet terrain list")),
						responseFields(
							fieldWithPath("name").description("The Planet name"),
							fieldWithPath("climate").description("The Planet climate list"),
							fieldWithPath("terrain").description("The Planet terrain list"),
							fieldWithPath("films").description("Planet appearances list in films"))));
	}

	@Test
	public void getPlanetsList() throws Exception {
		//Arrange
		Arrays.asList(json.write(PlanetDto.of(null, "Tatooine", Sets.newSet("arid"), Sets.newSet("desert"), null)),
					  json.write(PlanetDto.of(null, "Alderaan", Sets.newSet("temperate"), Sets.newSet("grasslands"), null)))
				.forEach(planetDto -> {
					try {
						this.mvc.perform(post("/api/v1/planets").contextPath("/api/v1")
								.contentType(contentType)
								.content(planetDto.getJson()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		//Await
		Thread.sleep(10000);

		//Act
		ResultActions result =
				this.mvc.perform(get("/api/v1/planets").contextPath("/api/v1")
						.contentType(contentType));

		//Assert
		result.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is("Tatooine")))
				.andExpect(jsonPath("$[0].climate[0]", is("arid")))
				.andExpect(jsonPath("$[0].terrain[0]", is("desert")))
				.andExpect(jsonPath("$[0].films.length()", greaterThan(0)))
				.andExpect(jsonPath("$[1].name", is("Alderaan")))
				.andExpect(jsonPath("$[1].climate[0]", is("temperate")))
				.andExpect(jsonPath("$[1].terrain[0]", is("grasslands")))
				.andExpect(jsonPath("$[1].films.length()", greaterThan(0)));

		//Document
		result.andDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						responseFields(
							fieldWithPath("[]").type(JsonFieldType.ARRAY).description("List of all registered Planets"),
							fieldWithPath("[].name").description("The Planet name"),
							fieldWithPath("[].climate").description("The Planet climate list"),
							fieldWithPath("[].terrain").description("The Planet terrain list"),
							fieldWithPath("[].films").description("Planet appearances list in films"),
							fieldWithPath("[].films[].title").description("Film title"),
							fieldWithPath("[].films[].director").description("Film director(s)"),
							fieldWithPath("[].films[].producer").description("Film producer(s)"),
							fieldWithPath("[].films[].release_date").description("Film release date"),
							fieldWithPath("[].films[].url").description("Film url on SW APi"))));
}

	@Test
	public void getPlanetById() throws Exception {
		//Arrange
		PlanetDto planetDto = PlanetDto.of(null, "Tatooine", Sets.newSet("arid"), Sets.newSet("desert"), null);
		ResultActions createResult =
				this.mvc.perform(post("/api/v1/planets").contextPath("/api/v1")
						.contentType(contentType)
						.content(json.write(planetDto).getJson()));

		String planetId = createResult.andReturn().getResponse().getHeader("Location").split("/planets/")[1];

		//Await
		Thread.sleep(10000);

		//Act
		ResultActions result =
				this.mvc.perform(get("/api/v1/planets/{planetId}", planetId).contextPath("/api/v1")
						.contentType(contentType));

		//Assert
		result.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.name", is("Tatooine")))
				.andExpect(jsonPath("$.climate[0]", is("arid")))
				.andExpect(jsonPath("$.terrain[0]", is("desert")))
				.andExpect(jsonPath("$.films.length()", greaterThan(0)));

		//Document
		result.andDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						pathParameters(
							parameterWithName("planetId").description("The Planet Identity")),
						responseFields(
							fieldWithPath("name").description("The Planet name"),
							fieldWithPath("climate").description("The Planet climate list"),
							fieldWithPath("terrain").description("The Planet terrain list"),
							fieldWithPath("films").description("Planet appearances list in films"),
							fieldWithPath("films[].title").description("Film title"),
							fieldWithPath("films[].director").description("Film director(s)"),
							fieldWithPath("films[].producer").description("Film producer(s)"),
							fieldWithPath("films[].release_date").description("Film release date"),
							fieldWithPath("films[].url").description("Film url on SW APi"))));
	}

	@Test
	public void searchPlanet() throws Exception {
		//Arrange
		Arrays.asList(json.write(PlanetDto.of(null, "Tatooine", Sets.newSet("arid"), Sets.newSet("desert"), null)),
					  json.write(PlanetDto.of(null, "Alderaan", Sets.newSet("temperate"), Sets.newSet("grasslands"), null)))
				.forEach(planetDto -> {
					try {
						this.mvc.perform(post("/api/v1/planets").contextPath("/api/v1")
								.contentType(contentType)
								.content(planetDto.getJson()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		//Await
		Thread.sleep(10000);

		//Act
		ResultActions result =
					this.mvc.perform(get("/api/v1/planets/search?name={name}", "Alderaan").contextPath("/api/v1")
							.contentType(contentType));

		//Assert
		result.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.name", is("Alderaan")))
				.andExpect(jsonPath("$.climate[0]", is("temperate")))
				.andExpect(jsonPath("$.terrain[0]", is("grasslands")))
				.andExpect(jsonPath("$.films.length()", greaterThan(0)));

		//Document
		result.andDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						requestParameters(
							parameterWithName("name").description("The Planet name")),
						responseFields(
							fieldWithPath("name").description("The Planet name"),
							fieldWithPath("climate").description("The Planet climate list"),
							fieldWithPath("terrain").description("The Planet terrain list"),
							fieldWithPath("films").description("Planet appearances list in films"),
							fieldWithPath("films[].title").description("Film title"),
							fieldWithPath("films[].director").description("Film director(s)"),
							fieldWithPath("films[].producer").description("Film producer(s)"),
							fieldWithPath("films[].release_date").description("Film release date"),
							fieldWithPath("films[].url").description("Film url on SW APi"))));
	}

	@Test
	public void deletePlanet() throws Exception {
		//Arrange
		PlanetDto planetDto = PlanetDto.of(null, "Tatooine", Sets.newSet("arid"), Sets.newSet("desert"), null);
		ResultActions createResult =
				this.mvc.perform(post("/api/v1/planets").contextPath("/api/v1")
						.contentType(contentType)
						.content(json.write(planetDto).getJson()));

		String planetId = createResult.andReturn().getResponse().getHeader("Location").split("/planets/")[1];

		//Await
		Thread.sleep(10000);

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