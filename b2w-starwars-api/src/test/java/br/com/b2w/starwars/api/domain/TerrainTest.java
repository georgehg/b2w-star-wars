package br.com.b2w.starwars.api.domain;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class TerrainTest {

    @Test
    public void shouldInstanceNewTerrain() throws ParseException {
        Terrain terrain = Terrain.init()
                .addVegetation("jungle")
                .addVegetation("rainforests");

        assertThat(terrain.getVegetations()).contains("jungle").contains("rainforests");
    }

    @Test
    public void shouldTerrainAddAllVegetations() throws ParseException {
        Terrain terrain = Terrain.init();
        assertThat(terrain.addAllVegetation(Sets.newSet("temperate", "tropical"))).isTrue();
        assertThat(terrain.getVegetations()).contains("temperate").contains("tropical");
    }

    @Test
    public void shouldTerrainClearVegetations() throws ParseException {
        Terrain terrain = Terrain.init();
        assertThat(terrain.addAllVegetation(Sets.newSet("temperate", "tropical"))).isTrue();
        terrain.clearVegetation();
        assertThat(terrain.getVegetations()).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void shouldIssueErrorForNullTerrain() throws ParseException {
        Terrain.init().addVegetation(null);
    }

}