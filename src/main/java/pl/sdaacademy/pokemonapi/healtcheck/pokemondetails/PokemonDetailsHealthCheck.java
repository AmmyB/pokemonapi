package pl.sdaacademy.pokemonapi.healtcheck.pokemondetails;

import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.stereotype.Component;
import pl.sdaacademy.pokemonapi.healtcheck.PokeApiHealthCheck;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Component("pokemondetails")
public class PokemonDetailsHealthCheck implements CompositeHealthContributor {

    private final Map<String, HealthContributor> contributorMap = new LinkedHashMap<>();

    public PokemonDetailsHealthCheck(
            PokemonDetailsDatabaseHealthCheck pokemonDetailsDatabaseHealthCheck,
            PokeApiHealthCheck pokeApiHealthCheck
    ) {
        contributorMap.put("pokemondetailsdb", pokemonDetailsDatabaseHealthCheck);
        contributorMap.put("pokeapi", pokeApiHealthCheck);

    }


    @Override
    public HealthContributor getContributor(String name) {
        return contributorMap.get(name);
    }

    @Override
    public Iterator<NamedContributor<HealthContributor>> iterator() {
        return contributorMap.entrySet().stream()
                .map(entry -> NamedContributor.of(entry.getKey(), entry.getValue()))
                .iterator();
    }
}