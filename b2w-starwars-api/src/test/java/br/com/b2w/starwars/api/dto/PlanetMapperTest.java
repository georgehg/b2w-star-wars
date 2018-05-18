package br.com.b2w.starwars.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Terrain;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import br.com.b2w.starwars.api.domain.Planet;

public class PlanetMapperTest {
	
	private PlanetMapper mapper = new PlanetMapper();
	
	@Test
	public void souldConvertDtoToEntity() {
		PlanetDto planetDto = PlanetDto.of(null,
										"Alderaan",
										Sets.newSet("temperate"),
										Sets.newSet("grasslands"));

		Planet planet = mapper.dtoToPlanet(planetDto);
		assertThat(planet.getName()).isEqualTo("Alderaan");
		assertThat(planet.getClimate().getTemperatures()).contains("temperate");
		assertThat(planet.getTerrain().getVegetations()).contains("grasslands");
	}

	@Test
	public void souldConvertEntityToDto() {
		Planet planet = Planet.of("Tatooine",
				Climate.init().addTemperature("arid"),
				Terrain.init().addVegetation("desert"));

		PlanetDto dto = mapper.planetToDto(planet);
		assertThat(dto.getName()).isEqualTo("Tatooine");
		assertThat(dto.getClimate()).contains("arid");
		assertThat(dto.getTerrain()).contains("desert");
	}

}
