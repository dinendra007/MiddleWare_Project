package com.example.petstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name="PetType")
public class PetType {

    @Schema(required = true, description = "Pet id")
    @JsonProperty("pet_id")
    private Integer petTypeId;

    @Schema(required = true, description = "Pet type")
    @JsonProperty("pet_type")
    private String petType;

    public Integer getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(Integer petId) {
        this.petTypeId = petId;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }
}
