package com.example.petstore;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {

	//initialize pet list
	List<Pet> petList = new ArrayList<Pet>();

	//get all pets
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {

		return Response.ok(petList).build();
	}

	//get pet with pet ID
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Pet pet = new Pet();
		Boolean petExists=false;
		for (Pet petInList: petList) {
			if(petInList.getPetId()==petId){
				pet.setPetId(petInList.getPetId());
				pet.setPetAge(petInList.getPetAge());
				pet.setPetName(petInList.getPetName());
				pet.setPetType(petInList.getPetType());

				petExists=true;
			}
		}

		if (petExists){
			return Response.ok(pet).build();
		}
		else{
			return Response.status(Status.NOT_FOUND).build();
		}

		
	}

	//get pet with pet name
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for Name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the Name.") })
	@GET
	@Path("findByName/{petName}")
	public Response getPetByName(@PathParam("petName") String petName) {
		List<Pet> newPetList = new ArrayList<Pet>();

		Boolean petExists=false;
		for (Pet petInList: petList) {
			if(petInList.getPetName().equals(petName)){
				newPetList.add(petInList);
				petExists=true;
			}
		}

		if (petExists){
			return Response.ok(newPetList).build();
		}
		else{
			return Response.status(Status.NOT_FOUND).build();
		}


	}

	//get pet with pet name
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for Age", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the Age.") })
	@GET
	@Path("findByAge/{petAge}")
	public Response getPetByAge(@PathParam("petAge") Integer petAge) {
		List<Pet> newPetList = new ArrayList<Pet>();

		Boolean petExists=false;
		for (Pet petInList: petList) {

			if(petInList.getPetAge().equals(petAge)){
				newPetList.add(petInList);
				petExists=true;
			}
		}

		if (petExists){
			return Response.ok(newPetList).build();
		}
		else{
			return Response.status(Status.NOT_FOUND).build();
		}


	}

	//Insert New Pet to the list
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "412", description = "A pet with this ID already Exists") })

	@POST
	@Path("addPet")
	public Response setPet(Pet newPet) {

		boolean petExists=false;

		Pet pet = new Pet();
		pet.setPetId(newPet.getPetId());
		pet.setPetAge(newPet.getPetAge());
		pet.setPetName(newPet.getPetName());
		pet.setPetType(newPet.getPetType());

		for (Pet petInList: petList) {
			if(petInList.getPetId()==pet.getPetId()){
				petExists=true;
			}
		}

		if(petExists){
			return Response.status(Status.PRECONDITION_FAILED).build();
		}
		else {
			petList.add(pet);
			return Response.ok(pet).build();
		}


	}

	//update pet
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "412", description = "A pet with this ID does not exist") })
	@PUT
	@Path("updatePet/{petID}")
	public Response updatePet(@PathParam("petID") int petID, Pet newPet) {

		boolean petExists=false;


		for (Pet petInList: petList) {
			if(petInList.getPetId()==petID){
				petExists=true;
				petInList.setPetId(newPet.getPetId());
				petInList.setPetAge(newPet.getPetAge());
				petInList.setPetName(newPet.getPetName());
				petInList.setPetType(newPet.getPetType());
			}
		}

		if(petExists){
			return Response.ok(newPet).build();
		}
		else {
			return Response.status(Status.PRECONDITION_FAILED).build();
		}


	}


	//delete pet
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Delete Successful", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "412", description = "A pet with this ID does not exist") })
	@DELETE
	@Path("deletePet/{petID}")
	public Response deletePet(@PathParam("petID") int petID) {

		boolean petExists=false;
		List<Pet> newPetList = new ArrayList<Pet>();

		for (Pet petInList: petList) {
			if(petInList.getPetId()==petID){
				petExists=true;
			}
			else if(petInList.getPetId()!=petID){
				newPetList.add(petInList);
			}

		}
		petList=newPetList;

		if(petExists){
			return Response.status(Status.OK).build();
		}
		else {
			return Response.status(Status.PRECONDITION_FAILED).build();
		}


	}

}
