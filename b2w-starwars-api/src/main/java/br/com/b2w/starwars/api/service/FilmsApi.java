package br.com.b2w.starwars.api.service;

import br.com.b2w.starwars.api.dto.FilmDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FilmsApi {

    private final RestTemplate restTemplate;

    public FilmsApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FilmDto getFilms(String filmdId) {
       /* ResponseEntity<FilmDto> responseEntity  =
                restTemplate.getForEntity("http://localhost:8085/v1/films/{id}",
                        FilmDto.class,
                        filmdId);*/

       /* ParameterizedTypeReference<Resources<Resource<ClienteDto>>> clienteBean =
                new ParameterizedTypeReference<Resources<Resource<ClienteDto>>>() {};

        ResponseEntity<Resources<Resource<ClienteDto>>> responseEntity  = restTemplate.exchange("http://localhost:8085/v1/clientes/search/status-integracao?status=NOVO",
                HttpMethod.GET, null, clienteBean);

        List<ClienteDto> clientes = new ArrayList<ClienteDto>();

        responseEntity.getBody().forEach(cliente -> {
            String[] linkParts = cliente.getId().getHref().split("/");
            String idClienteFromLink = linkParts[linkParts.length-1];
            clientes.add(cliente.getContent().updateCodigo(idClienteFromLink));
        });*/

        return null;
    }


   /* public void updateClienteIntegrationStatus(String idCliente, StatusIntegracao statusIntegracao, String mensagemIntegracao) {
        String response = restTemplate.patchForObject("http://localhost:8085/v1/clientes/{id}", StatusPatch.of(idCliente, statusIntegracao, mensagemIntegracao), String.class, idCliente);
        //System.out.println(response);
    }*/

}
