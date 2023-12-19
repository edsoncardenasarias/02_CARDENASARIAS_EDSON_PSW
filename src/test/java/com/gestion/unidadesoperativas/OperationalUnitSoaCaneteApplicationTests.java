package com.gestion.unidadesoperativas;

import com.gestion.unidadesoperativas.domain.model.OperationalUnit;
import com.gestion.unidadesoperativas.repository.OperationalUnitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import java.util.List;



@SpringBootTest
@AutoConfigureWebTestClient
class OperationalUnitSoaCaneteApplicationTests {
    @Autowired
    OperationalUnitRepository operationalUnitRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateOperationalUnit() {
        OperationalUnit funcionary = new OperationalUnit(
                "SOA Callao",
                "Ericka Alicia Chumioque Hidalgo",
                "934837321",
                "Cruce Av. Santa Rosa y Av. Oscar Benavides, al lado de la Corte Superior de Justicia del Callao - Perú",
                "070101",
                "A");

        webTestClient.post()
                .uri("/ms-soa")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(funcionary), OperationalUnit.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OperationalUnit.class)
                .value(OperationalUnit::getId_operativeunit, notNullValue())
                .value(OperationalUnit::getName, equalTo("SOA Callao"))
                .value(OperationalUnit::getDirector, equalTo("Ericka Alicia Chumioque Hidalgo"))
                .value(OperationalUnit::getPhonenumber, equalTo("934837321"))
                .value(OperationalUnit::getAddress, equalTo("Cruce Av. Santa Rosa y Av. Oscar Benavides, al lado de la Corte Superior de Justicia del Callao - Perú"))
                .value(OperationalUnit::getCodubi, equalTo("070101"))
                .value(OperationalUnit::getStatus, equalTo("A"));
    }

    @Test
    public void testListOperatinalUnit() {
        webTestClient.get().uri("/ms-soa/listData")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(OperationalUnit.class)
                .consumeWith(response -> {
                    List<OperationalUnit> dataList = response.getResponseBody();
                    Assertions.assertTrue(dataList != null && !dataList.isEmpty(), "La lista no debe de estar vacía: ");
                    Assertions.assertFalse(false, "La lista debe contener al menos " + 1 + " elementos");
                    System.out.println(dataList);
                });


    }

    @Test
    public void testListActiveLegalGuardian() {
        webTestClient.get().uri("/ms-soa/listData/active")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(OperationalUnit.class)
                .consumeWith(response -> {
                    List<OperationalUnit> dataList = response.getResponseBody();
                    Assertions.assertTrue(dataList != null && !dataList.isEmpty(), "La lista no debe de estar vacía: ");
                    Assertions.assertTrue(true, "La lista debe contener al menos " + 1 + " elementos");
                    System.out.println(dataList);
                });

    }

    @ParameterizedTest
    @ValueSource(ints = {2})
    public void testUpdateFuncionary(int dataId) {
        OperationalUnit UpdateFuncionary = new OperationalUnit(
                "SOA Callao",
                "Ericka Alicia Chumioque Hidalgo",
                "934837321",
                "Cruce Av. Santa Rosa y Av. Oscar Benavides, al lado de la Corte Superior de Justicia del Callao - Perú",
                "070101",
                "A");

        webTestClient.put().uri("/ms-soa/{id}", dataId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(UpdateFuncionary)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OperationalUnit.class)
                .consumeWith(response -> {
                });
    }
}
