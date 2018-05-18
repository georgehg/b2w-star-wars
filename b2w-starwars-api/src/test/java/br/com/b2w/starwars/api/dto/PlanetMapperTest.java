package br.com.b2w.starwars.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.internal.util.collections.Sets;

import br.com.b2w.starwars.api.domain.Planet;

public class PlanetMapperTest {
	
	private PlanetMapper mapper = Mappers.getMapper(PlanetMapper.class);
	
	@Test
	public void souldConvertDtoToEntity() {
		PlanetDto planetDto = new PlanetDto("Alderaan", Sets.newSet("temperate"), Sets.newSet("grasslands"));
		Planet planet = mapper.planetDtoToPlanet(planetDto);
		assertThat(planet.getName()).isEqualTo("Alderan");
	}

}
