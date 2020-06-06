package testesPetSore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetStoreTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2";
    }
/*
    1) Crie os usuarios Ana Maia, Rodrigo Mendes, Tatiana Vasconcelos
 */
    @Test
    @Order(1)
    public void cadastrarListaDeUsuarios() {

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("[\n" +
                            "  {\n" +
                            "    \"id\": 1,\n" +
                            "    \"username\": \"ana_maria\",\n" +
                            "    \"firstName\": \"Ana Maria\",\n" +
                            "    \"lastName\": \"Teste\",\n" +
                            "    \"email\": \"anamaria@teste.com\",\n" +
                            "    \"password\": \"anamaria\",\n" +
                            "    \"phone\": \"199999999\",\n" +
                            "    \"userStatus\": 0\n" +
                            "  },\n" +
                            "   {\n" +
                            "    \"id\": 2,\n" +
                            "    \"username\": \"rodrigo_mendes\",\n" +
                            "    \"firstName\": \"Rodrigo\",\n" +
                            "    \"lastName\": \"Mendes\",\n" +
                            "    \"email\": \"rodrigomendes@teste.com\",\n" +
                            "    \"password\": \"rodrigo\",\n" +
                            "    \"phone\": \"199999999\",\n" +
                            "    \"userStatus\": 0\n" +
                            "  },\n" +
                            "   {\n" +
                            "    \"id\": 3,\n" +
                            "    \"username\": \"tatiane_vasconcelos\",\n" +
                            "    \"firstName\": \"Tatiane\",\n" +
                            "    \"lastName\": \"Vasconcelos\",\n" +
                            "    \"email\": \"tatianevasconcelos@teste.com\",\n" +
                            "    \"password\": \"tatiane\",\n" +
                            "    \"phone\": \"199999999\",\n" +
                            "    \"userStatus\": 0\n" +
                            "  }\n" +
                            "]")
                .when()
                     .post("user/createWithList")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("message", Matchers.equalTo("ok"));
    }

/*
*   2) Crie os pets Snoopy (dog), Bichento (cat) e Perry (platypus)
*/

    @Test
    @Order(2)
    public void cadastrarPetSnoopy() {

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("\n" +
                            "\t{\n" +
                            "  \"id\": 501,\n" +
                            "  \"category\": {\n" +
                            "    \"id\": 1,\n" +
                            "    \"name\": \"dog\"\n" +
                            "  },\n" +
                            "  \"name\": \"Snoopy\",\n" +
                            "  \"photoUrls\": [],\n" +
                            "  \"tags\": [],\n" +
                            "  \"status\": \"available\"\n" +
                            "}")
                .when()
                   .post("pet")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("name", Matchers.equalTo("Snoopy"));
    }

    @Test
    @Order(3)
    public void cadastrarPetBichento() {

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "  \"id\": 502,\n" +
                            "  \"category\": {\n" +
                            "    \"id\": 2,\n" +
                            "    \"name\": \"cat\"\n" +
                            "  },\n" +
                            "  \"name\": \"Bichento\",\n" +
                            "  \"photoUrls\": [],\n" +
                            "  \"tags\": [],\n" +
                            "  \"status\": \"available\"\n" +
                            "}")
                .when()
                      .post("pet")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("name", Matchers.equalTo("Bichento"));

    }

    @Test
    @Order(4)
    public void cadastrarPetPerry() {

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "  \"id\": 503,\n" +
                            "  \"category\": {\n" +
                            "    \"id\": 3,\n" +
                            "    \"name\": \"platypus\"\n" +
                            "  },\n" +
                            "  \"name\": \"Perry\",\n" +
                            "  \"photoUrls\": [],\n" +
                            "  \"tags\": [],\n" +
                            "  \"status\": \"available\"\n" +
                            "}")
                .when()
                      .post("pet")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("name", Matchers.equalTo("Perry"));
    }

/*
*    3) Faça a venda do Perry para a Ana Maia, do Snoopy para o Rodrigo Mendes e do Snoopy para a Tatiana Vanconcelos
*
*    4) Mude o status da ordem de venda do Perry e do Snoopy para "approved" e do Bichento para "delivered"
*/

    @Test
    @Order(5)
    public void criarOrdemVendaBichento_Deliverd() {
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "  \"id\": 8,\n" +
                            "  \"petId\": 502,\n" +
                            "  \"quantity\": 1,\n" +
                            "  \"shipDate\": \"2020-06-06T16:36:26.840Z\",\n" +
                            "  \"status\": \"delivered\",\n" +
                            "  \"complete\": true\n" +
                            "}")
                .when()
                      .post("store/order")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("petId", Matchers.equalTo(502))
                    .body("status", Matchers.equalTo("delivered"));
    }

    @Test
    @Order(6)
    public void criarOrdemVendaSnoopy_Approved() {
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "  \"id\": 7,\n" +
                            "  \"petId\": 501,\n" +
                            "  \"quantity\": 1,\n" +
                            "  \"shipDate\": \"2020-06-06T16:36:26.840Z\",\n" +
                            "  \"status\": \"approved\",\n" +
                            "  \"complete\": true\n" +
                            "}")
                .when()
                     .post("store/order")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("petId", Matchers.equalTo(501))
                    .body("status", Matchers.equalTo("approved"));
    }

    @Test
    @Order(7)
    public void criarOrdemVendaPerry_Approved() {
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "  \"id\": 9,\n" +
                            "  \"petId\": 503,\n" +
                            "  \"quantity\": 1,\n" +
                            "  \"shipDate\": \"2020-06-06T16:36:26.840Z\",\n" +
                            "  \"status\": \"approved\",\n" +
                            "  \"complete\": true\n" +
                            "}")
                .when()
                     .post("store/order")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("petId", Matchers.equalTo(503))
                    .body("status", Matchers.equalTo("approved"));
    }

    /*
     *  Atualizando o cadastro dos pets pós-vendas
     */

    @Test
    @Order(8)
    public void atualizarStatusPetBichento_PosVenda(){
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "  \"id\": 502,\n" +
                            "  \"category\": {\n" +
                            "    \"id\": 2,\n" +
                            "    \"name\": \"cat\"\n" +
                            "  },\n" +
                            "  \"name\": \"Bichento\",\n" +
                            "  \"photoUrls\": [],\n" +
                            "  \"tags\": [],\n" +
                            "  \"status\": \"sold\"\n" +
                            "}")
                .when()
                    .put("pet")
                .then()
                     .assertThat()
                     .statusCode(200)
                     .body("name", Matchers.equalTo("Bichento"))
                     .body("id", Matchers.equalTo(502))
                     .body("status", Matchers.equalTo("sold"));
    }
    @Test
    @Order(9)
    public void atualizarStatusPetSnoopy_PosVenda(){
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": 501,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"dog\"\n" +
                        "  },\n" +
                        "  \"name\": \"Snoopy\",\n" +
                        "  \"photoUrls\": [],\n" +
                        "  \"tags\": [],\n" +
                        "  \"status\": \"sold\"\n" +
                        "}")
                .when()
                .put("pet")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo("Snoopy"))
                .body("id", Matchers.equalTo(501))
                .body("status", Matchers.equalTo("sold"));
    }
    @Test
    @Order(10)
    public void atualizarStatusPetPerry_PosVenda(){
        RestAssured
                .given()
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                "  \"id\": 503,\n" +
                                "  \"category\": {\n" +
                                "    \"id\": 3,\n" +
                                "    \"name\": \"platypus\"\n" +
                                "  },\n" +
                                "  \"name\": \"Perry\",\n" +
                                "  \"photoUrls\": [],\n" +
                                "  \"tags\": [],\n" +
                                "  \"status\": \"sold\"\n" +
                                "}")
                .when()
                      .put("pet")
                .then()
                        .assertThat()
                        .statusCode(200)
                        .body("name", Matchers.equalTo("Perry"))
                        .body("id", Matchers.equalTo(503))
                        .body("status", Matchers.equalTo("sold"));
    }

    /*
     *  5) Consulte as 3 ordens geradas
     */

    @Test
    @Order(11)
    public void consultarOrdemDeVenda_Snoopy() {
        RestAssured
                    .given()
                     .when()
                        .get("store/order/7")

                    .then()
                        .assertThat()
                        .statusCode(200)
                        .body("id", Matchers.equalTo(7))
                        .body("petId", Matchers.equalTo(501));

    }
    @Test
    @Order(12)
    public void consultarOrdemDeVenda_Bichento() {
        RestAssured
                .given()
                .when()
                    .get("store/order/8")

                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("id", Matchers.equalTo(8))
                    .body("petId", Matchers.equalTo(502));

    }
    @Test
    @Order(13)
    public void consultarOrdemDeVenda_Perry() {
        RestAssured
                .given()
                .when()
                    .get("store/order/9")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("id", Matchers.equalTo(9))
                    .body("petId", Matchers.equalTo(503));

    }

    @Test
    @Order(14)
    public void criarUsuario() {
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("   {\n" +
                            "    \"id\": 504,\n" +
                            "    \"username\": \"debora\",\n" +
                            "    \"firstName\": \"Debora\",\n" +
                            "    \"lastName\": \"Teste\",\n" +
                            "    \"email\": \"debora@teste.com\",\n" +
                            "    \"password\": \"debora\",\n" +
                            "    \"phone\": \"199999999\",\n" +
                            "    \"userStatus\": 0\n" +
                            "  }")
                .when()
                    .post("user")

                .then()
                .assertThat()
                .statusCode(200)
                .body("message", Matchers.equalTo("504"));
    }


    @Test
    @Order(15)
    public void consultarUsuario() {
        RestAssured
                .given()
                .when()
                    .get("user/debora")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("id", Matchers.equalTo(504))
                    .body("username", Matchers.equalTo("debora"));

    }
    @Test
    @Order(23)
    public void consultarStoreInventory() {
        RestAssured
                .given()
                .when()
                .get("store/inventory")
                .then()
                .assertThat()
                .statusCode(200);
    }




    @Test
    @Order(18)
    public void testDeletarUsuario_Cadastrado() {
        RestAssured
                .given()
                .when()
                    .delete("user/debora")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("message", Matchers.equalTo("debora"));

    }

    @Test
    @Order(16)
    public void testDeletarUsuarioInexistente() {
        RestAssured
                .given()
                .when()
                .delete("user/gandalf")
                .then()
                .assertThat()
                .statusCode(404);
   }
    @Test
    @Order(17)
    public void consultarOrdemDeVenda_Inexistente() {
        RestAssured
                .given()
                .when()
                    .get("store/order/3003")
                .then()
                    .assertThat()
                    .statusCode(404)
                    .body("type", Matchers.equalTo("error"))
                    .body("message", Matchers.equalTo("Order not found"));
    }
     @Test
     @Order(20)
     public void testDeletarOrdemDeVenda_Existente() {
        RestAssured
                .given()
                .when()
                    .delete("store/order/7")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("message", Matchers.equalTo("7"));
    }
    @Test
    @Order(19)
    public void cadastrarPetScadufax() {

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "  \"id\": 505,\n" +
                            "  \"category\": {\n" +
                            "    \"id\": 2,\n" +
                            "    \"name\": \"horse\"\n" +
                            "  },\n" +
                            "  \"name\": \"Scadufax\",\n" +
                            "  \"photoUrls\": [],\n" +
                            "  \"tags\": [],\n" +
                            "  \"status\": \"available\"\n" +
                            "}")
                    .when()
                       .post("pet")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("name", Matchers.equalTo("Scadufax"));
    }
    @Test
    @Order(22)
    public void testDeletarPet_Cadastrado() {
        RestAssured
                .given()
                .when()
                      .delete("pet/505")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("message", Matchers.equalTo("505"));
    }
    @Test
    @Order(21)
    public void testDeletarPet_Inexistente() {
        RestAssured
                .given()
                .when()
                     .delete("pet/212")
                .then()
                    .assertThat()
                    .statusCode(404);
    }
}

