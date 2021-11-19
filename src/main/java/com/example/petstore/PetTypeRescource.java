package com.example.petstore;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/petType")
@Produces("application/json")
public class PetTypeRescource {

    //initialize pet list
    List<PetType> petTypeList = new ArrayList<PetType>();

    //get all pet Types
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "All Pet Types", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
    @GET
    public Response getPetTypes() {

        return Response.ok(petTypeList).build();
    }

    //get pet with pet ID
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "404", description = "No Pet Type found for the id.") })
    @GET
    @Path("{petTypeId}")
    public Response getPetTypeByID(@PathParam("petTypeId") int petTypeId) {
        if (petTypeId < 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        PetType pet = new PetType();
        Boolean petTypeExists=false;
        for (PetType petTypeInList: petTypeList) {
            if(petTypeInList.getPetTypeId()==petTypeId){
                pet.setPetTypeId(petTypeInList.getPetTypeId());
                pet.setPetType(petTypeInList.getPetType());
                petTypeExists=true;
            }
        }

        if (petTypeExists){
            return Response.ok(pet).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }


    }

    //Insert New Pet to the list
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "412", description = "A pet with this ID already Exists") })

    @POST
    @Path("addPet")
    public Response setPet(PetType newPetType) {

        boolean petTypeExists=false;

        PetType petType = new PetType();
        petType.setPetType(newPetType.getPetType());
        petType.setPetTypeId(newPetType.getPetTypeId());

        for (PetType petTypeInList: petTypeList) {
            if(petTypeInList.getPetTypeId()==petType.getPetTypeId()){
                petTypeExists=true;
            }
        }

        if(petTypeExists){
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        else {
            petTypeList.add(petType);
            return Response.ok(petType).build();
        }


    }

    //update pet Type
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Pet Type Updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "412", description = "A pet Type with this ID already Exists") })
    @PUT
    @Path("updatePetType/{petTypeID}")
    public Response updatePet(@PathParam("petTypeID") int petTypeID, PetType newPetType) {

        boolean petTypeExists=false;


        for (PetType petTypeInList: petTypeList) {
            if(petTypeInList.getPetTypeId()==petTypeID){
                petTypeExists=true;
                petTypeInList.setPetTypeId(newPetType.getPetTypeId());
                petTypeInList.setPetType(newPetType.getPetType());
            }
        }

        if(petTypeExists){
            return Response.ok(newPetType).build();
        }
        else {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }


    }


    //delete pet Type
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Delete Successful", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "412", description = "A pet Type with this ID does not exist") })
    @DELETE
    @Path("deletePetType/{petTypeID}")
    public Response deletePet(@PathParam("petTypeID") int petTypeID) {

        boolean petTypeExists=false;
        List<PetType> newPetTypeList = new ArrayList<PetType>();

        for (PetType petTypeInList: petTypeList) {
            if(petTypeInList.getPetTypeId()==petTypeID){
                petTypeExists=true;
            }
            else if(petTypeInList.getPetTypeId()!=petTypeID){
                newPetTypeList.add(petTypeInList);
            }

        }
        petTypeList=newPetTypeList;

        if(petTypeExists){
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }


    }
}
