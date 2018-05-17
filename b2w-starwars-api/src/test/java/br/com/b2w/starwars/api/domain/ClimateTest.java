package br.com.b2w.starwars.api.domain;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class ClimateTest {

    @Test
    public void shouldInstanceNewClimate() throws ParseException {
        Climate climate =
                Climate.init()
                .addTemperature("temperate")
                .addTemperature("tropical");

        assertThat(climate.getTemperatures()).contains("temperate").contains("tropical");
    }

    @Test
    public void shouldClimateAddAllTemperatures() throws ParseException {
        Climate climate = Climate.init();
        assertThat(climate.addAllTemperature(Sets.newSet("temperate", "tropical"))).isTrue();
        assertThat(climate.getTemperatures()).contains("temperate").contains("tropical");
    }

    @Test
    public void shouldClimateClearTemperatures() throws ParseException {
        Climate climate = Climate.init();
        assertThat(climate.addAllTemperature(Sets.newSet("temperate", "tropical"))).isTrue();
        climate.clearTemperature();
        assertThat(climate.getTemperatures()).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void shouldIssueErrorForNullClimate() throws ParseException {
        Climate.init().addTemperature(null);
    }

}