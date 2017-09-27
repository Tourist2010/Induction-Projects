package com.gymmanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymmanagement.beans.Member;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Class used for Integration Testing.
 * @author Ajay.Srivas
 * @author Keerthana.Pai
 * @author Ashwini.D
 * @since 19/09/2017
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GymApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GymApplicationTest {
    /**
     * Maps the defined port into the variable port.
     */
    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    /**
     * Method which will execute before each test case.
     * Makes sure database always have the defined members.
     * @throws Exception If a problem occurs
     */
    @Before
    public void setUp() throws Exception {
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> deleteEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                createURL("/member/allGymMembers"),
                HttpMethod.DELETE, deleteEntity, String.class);

        Member sampleMember = new Member(101, "Cristiano", "ronaldo",
                "some block, some street, some country", 45, "1234567890");

        Member sampleMember2 = new Member(102, "Wayne", "rooney",
                "some block1, some street1, some country1", 54, "0987654321");


        HttpEntity<Member> Entity = new HttpEntity<Member>(sampleMember, headers);
        ResponseEntity<String> Response1 = restTemplate.exchange(
                createURL("/member/gymMember"),
                HttpMethod.POST, Entity, String.class);

        HttpEntity<Member> Entity2 = new HttpEntity<Member>(sampleMember2, headers);
        ResponseEntity<String> Response2 = restTemplate.exchange(
                createURL("/member/gymMember"),
                HttpMethod.POST, Entity2, String.class);
    }

    /**
     * Test Method to check createMember Method's functionality.
     */
    @Test
    public void MemberCreatedTest() {
        Member sampleMember = new Member(110, "Lionel", "Messi", "some block5, some street5, some country5", 23, "6754378901");

        HttpEntity<Member> entity = new HttpEntity<Member>(sampleMember, headers);
        ResponseEntity<String> actualOut = restTemplate.exchange(createURL("/member/gymMember"), HttpMethod.POST, entity, String.class);

        String expectedOut = "Member with 110 created successfully :)" ;

        Assert.assertEquals(expectedOut, actualOut.getBody());
    }

    /**
     * Test Method to check createMember Method's functionality.
     */
    @Test
    public void MemberAlreadyExistsTest() {
        Member sampleMember = new Member(101, "Cristiano", "ronaldo", "some block, some street, some country", 45, "1234567890");

        HttpEntity<Member> entity = new HttpEntity<Member>(sampleMember, headers);
        ResponseEntity<String> actualOut = restTemplate.exchange(createURL("/member/gymMember"), HttpMethod.POST, entity, String.class);

        String expectedOut = "Member already exists! :/";

        Assert.assertEquals(expectedOut, actualOut.getBody());
    }

    /**
     * Test Method to check getMember Method's functionality.
     * @throws JSONException The JSONException is thrown by the JSON.org classes when things are amiss
     * @throws JsonProcessingException Exception is thrown when processing (parsing, generating) JSON content that are not pure I/O problems
     */
    @Test
    public void getGymMemberByIdTest() throws JSONException, JsonProcessingException {
        int id = 101;
        Member sampleMember = new Member(101, "Cristiano", "ronaldo", "some block, some street, some country", 45, "1234567890");

        HttpEntity<Integer> entity = new HttpEntity<Integer>(id, headers);
        ResponseEntity<String> actualOut = restTemplate.exchange(createURL("/member/gymMemberById/101"), HttpMethod.GET, entity, String.class);
        String expectedOut = objToJson(sampleMember);

        JSONAssert.assertEquals(expectedOut, actualOut.getBody(), false);
    }

    /**
     * Test Method to check getAll Method's functionality.
     * @throws JsonProcessingException Exception is thrown when processing (parsing, generating) JSON content that are not pure I/O problems
     * @throws JSONException The JSONException is thrown by the JSON.org classes when things are amiss
     */
    @Test
    public void getAllGymMembersTest() throws JsonProcessingException, JSONException {
        Member sampleMember = new Member(101, "Cristiano", "ronaldo",
                "some block, some street, some country", 45, "1234567890");
        Member sampleMember2 = new Member(102, "Wayne", "rooney",
                "some block1, some street1, some country1", 54, "0987654321");

        String a = objToJson(sampleMember);
        String b = objToJson(sampleMember2);

        String expectedOut = "[" + a + "," + b + "]";
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> actualOut = restTemplate.exchange(createURL("/member/allGymMembers"), HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(expectedOut, actualOut.getBody(), false);
    }

    /**
     * Test Method to check updateMember Method's functionality.
     * @throws JsonProcessingException Exception is thrown when processing (parsing, generating) JSON content that are not pure I/O problems
     * @throws JSONException The JSONException is thrown by the JSON.org classes when things are amiss
     */
    @Test
    public void gymMemberExistUpdateTest() throws JsonProcessingException, JSONException {
        Member  updateMem = new Member(101,"Ashwini", "D", "Tumkur, Banglore",  21, "9740209573");

        String expectedOut = "Member Updated!";
        HttpEntity<Member> entity = new HttpEntity<Member>(updateMem,  headers);
        ResponseEntity<String> actualOut = restTemplate.exchange(createURL("/member/gymMember"), HttpMethod.PUT, entity, String.class);

        Assert.assertEquals(expectedOut , actualOut.getBody());
    }

    /**
     * Test Method to check updateMember Method's functionality.
     * @throws JsonProcessingException Exception is thrown when processing (parsing, generating) JSON content that are not pure I/O problems
     * @throws JSONException The JSONException is thrown by the JSON.org classes when things are amiss
     */
    @Test
    public void gymMemberDoesntExistUpdateTest() throws JsonProcessingException, JSONException {
        Member  updateMem = new Member(1045,"Ashwini", "D", "Tumkur, Banglore",  21, "9740209573");

        String expectedOut = "Member does not exists!";
        HttpEntity<Member> entity = new HttpEntity<Member>(updateMem,  headers);
        ResponseEntity<String> actualOut = restTemplate.exchange(createURL("/member/gymMember"), HttpMethod.PUT, entity, String.class);

        Assert.assertEquals(expectedOut , actualOut.getBody());
    }


    /**
     * Test Method to check deleteMember Method's functionality.
     * @throws JSONException The JSONException is thrown by the JSON.org classes when things are amiss
     * @throws JsonProcessingException Exception is thrown when processing (parsing, generating) JSON content that are not pure I/O problems
     */
	@Test
	public void deleteGymMemberByIdTest() throws JSONException, JsonProcessingException{
        int id = 101;
        HttpEntity<Integer> entity = new HttpEntity<Integer>(id, headers);
        ResponseEntity<String> actualOut = restTemplate.exchange(createURL("/member/gymMemberById/101"), HttpMethod.DELETE, entity, String.class);
        String expectedOut = "Member with " + 101 + " deleted";

        Assert.assertEquals(expectedOut,actualOut.getBody());
    }

    /**
     * Test Method to check deleteAll Method's functionality.
     */
    @Test
	public void deleteAllGymMembersTest(){
        String expectedOut = "Database Empty!";
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> actualOut = restTemplate.exchange(createURL("/member/allGymMembers"), HttpMethod.DELETE, entity, String.class);

        Assert.assertEquals(expectedOut,actualOut.getBody());
    }

    /**
     * Method to Create the URL with the defined port.
     * @param URL Takes a url prefix
     * @return Complete url with defined port
     */
    private String createURL(String URL) {
	    return "http://localhost:" + port + URL;
    }

    /**
     * Method to convert JSON object to String.
     * @param m Member of the Gym
     * @return String format of the JSON Object
     * @throws JsonProcessingException Exception is thrown when processing (parsing, generating) JSON content that are not pure I/O problems
     */
    private String objToJson(Member m) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(m);
    }
}
